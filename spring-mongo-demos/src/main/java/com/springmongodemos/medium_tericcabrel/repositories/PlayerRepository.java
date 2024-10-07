package com.springmongodemos.medium_tericcabrel.repositories;

import com.springmongodemos.medium_tericcabrel.models.Player;
import com.springmongodemos.medium_tericcabrel.models.PlayerPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findByPositionAndIsAvailable(PlayerPosition playerPosition, boolean isAvailable);

    List<Player> findDistinctNameByPositionIn(List<PlayerPosition> playerPositions);

    List<Player> findByBirthDateIsBetweenOrderByBirthDate(Date fromDate, Date toDate);

    Player findFirstByOrderByBirthDateDesc();

    List<Player> findFirst10ByOrderByBirthDate();

    Page<Player> findByIdIsNotNull(Pageable pageable);
}
