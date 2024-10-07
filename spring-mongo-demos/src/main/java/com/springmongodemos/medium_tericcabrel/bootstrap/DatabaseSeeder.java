package com.springmongodemos.medium_tericcabrel.bootstrap;

import com.github.javafaker.Faker;
import com.springmongodemos.medium_tericcabrel.models.Address;
import com.springmongodemos.medium_tericcabrel.models.Player;
import com.springmongodemos.medium_tericcabrel.models.PlayerPosition;
import com.springmongodemos.medium_tericcabrel.models.Team;
import com.springmongodemos.medium_tericcabrel.repositories.PlayerRepository;
import com.springmongodemos.medium_tericcabrel.repositories.TeamRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final Faker faker;

    public DatabaseSeeder(
            TeamRepository teamRepository, PlayerRepository playerRepository, Faker faker
    ) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.faker = faker;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        teamRepository.deleteAll();
        playerRepository.deleteAll();

        Map<String, String> teamNames = new HashMap<>();
        AtomicInteger counter = new AtomicInteger();

        while (counter.get() < 10) {
            String teamName = faker.team().name();
            String acronym = createAcronym(teamName);
            teamNames.computeIfAbsent(acronym, key -> {
                counter.incrementAndGet();
                return teamName;
            });
        }

        for (String teamName : teamNames.values()) {
            createAndPersistTeam(teamName);
        }

    }

    private String createAcronym(String teamName) {
        return teamName.replaceAll(" ", "")
                .toUpperCase().substring(0, 4);
    }

    private void createAndPersistTeam(String teamName) {
        List<Player> players = Stream.generate(this::createPlayer)
                .limit(10)
                .toList();

        List<Player> createdPlayers = playerRepository.saveAll(players);

        Address address = new Address(
                faker.address().city(),
                faker.address().zipCode(),
                faker.address().streetAddress()
        );

        Team team = new Team()
                .setName(teamName)
                .setAcronym(createAcronym(teamName))
                .setAddress(address)
                .setPlayers(new HashSet<>(createdPlayers));

        teamRepository.save(team);
    }

    private Player createPlayer() {
        PlayerPosition[] positions = PlayerPosition.toArray();

        return new Player()
                .setName(faker.name().firstName() + " " + faker.name().lastName())
                .setBirthDate(faker.date().birthday(18, 38))
                .setPosition(positions[faker.random().nextInt(0, positions.length - 1)])
                .setAvailable(faker.random().nextBoolean());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
