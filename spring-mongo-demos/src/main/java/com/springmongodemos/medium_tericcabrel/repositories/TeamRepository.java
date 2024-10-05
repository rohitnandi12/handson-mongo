package com.springmongodemos.medium_tericcabrel.repositories;

import com.springmongodemos.medium_tericcabrel.models.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
}
