package com.springmongodemos.tericcabrel.repositories;

import com.springmongodemos.tericcabrel.models.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
    List<Team> findByNameContainingIgnoreCaseOrderByNameDesc(String nameKeyword);

    List<Team> findByAddressCityIgnoreCase(String city);
}
