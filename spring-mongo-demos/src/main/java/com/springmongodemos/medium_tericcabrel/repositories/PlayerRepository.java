package com.springmongodemos.medium_tericcabrel.repositories;

import com.springmongodemos.medium_tericcabrel.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
}
