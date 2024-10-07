package com.springmongodemos.tericcabrel.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.springmongodemos.tericcabrel.dtos.CreatePlayerRequest;
import com.springmongodemos.tericcabrel.dtos.CreateTeamRequest;
import com.springmongodemos.tericcabrel.models.Player;
import com.springmongodemos.tericcabrel.models.Team;
import com.springmongodemos.tericcabrel.repositories.PlayerRepository;
import com.springmongodemos.tericcabrel.repositories.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SoccerController {
    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    public SoccerController(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getTeams() {
//        List<Team> teamCreated = teamRepository.findAll();
        List<Team> teamCreated = teamRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));

        return new ResponseEntity<>(teamCreated, HttpStatus.OK);
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayers() {
        List<Order> orders = new ArrayList<>() {{
            add(Order.by("position").with(Sort.Direction.ASC));
            add(Order.by("name").with(Sort.Direction.DESC));
        }};
        List<Player> teamCreated = playerRepository.findAll(Sort.by(orders));

        return new ResponseEntity<>(teamCreated, HttpStatus.OK);
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> oneTeam(@PathVariable String id) {
        Optional<Team> teamOptional = teamRepository.findById(id);

        return teamOptional
                .map(team -> new ResponseEntity<>(team, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> onePlayer(@PathVariable String id) {
        Optional<Player> playerOptional = playerRepository.findById(id);

        return playerOptional
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/players-page")
    public ResponseEntity<Page<Player>> listPlayersPage(@RequestParam int page) {
        Page<Player> players = playerRepository.findByIdIsNotNull(PageRequest.of(page - 1, 10));

        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamRequest createTeamDto) {
        Team teamCreated = teamRepository.save(createTeamDto.toTeam());

        return new ResponseEntity<>(teamCreated, HttpStatus.CREATED);
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerRequest createPlayerDto) {
        Player playerCreated = playerRepository.save(createPlayerDto.toPlayer());

        return new ResponseEntity<>(playerCreated, HttpStatus.CREATED);
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable String id, @RequestBody CreateTeamRequest createTeamDto) {
        Optional<Team> optionalTeam = teamRepository.findById(id);

        if (optionalTeam.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Team teamToUpdate = optionalTeam.get()
                .setAddress(createTeamDto.getAddress())
                .setName(createTeamDto.getName())
                .setAcronym(createTeamDto.getAcronym());

        Team teamUpdated = teamRepository.save(teamToUpdate);

        return new ResponseEntity<>(teamUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id, @RequestBody CreateTeamRequest createTeamDto) {
        teamRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/players/bulk")
    public ResponseEntity<List<Player>> createPlayers(@RequestBody List<CreatePlayerRequest> createPlayerDtoList) {
        List<Player> players = createPlayerDtoList
                .stream()
                .map(CreatePlayerRequest::toPlayer)
                .collect(Collectors.toList());

        List<Player> playersCreated = playerRepository.saveAll(players);

        return new ResponseEntity<>(playersCreated, HttpStatus.CREATED);
    }

    @PostMapping("/teams/{id}/players")
    public ResponseEntity<Team> addPlayersToTeam(@PathVariable String id, @RequestBody List<String> playerIds) {
        Optional<Team> optionalTeam = teamRepository.findById(id);

        if (optionalTeam.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Team teamToUpdate = optionalTeam.get();

        Set<Player> playersToAdd = playerIds.stream()
                .map(playerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        teamToUpdate.setPlayers(playersToAdd);

        Team teamUpdated = teamRepository.save(teamToUpdate);

        return new ResponseEntity<>(teamUpdated, HttpStatus.OK);
    }
}