package com.springmongodemos.dailycodebuffer.repositories;

import com.springmongodemos.dailycodebuffer.collections.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByFirstNameStartsWith(String name);

    List<Person> findByAgeIsBetween(Integer minAge, Integer maxAge);

    // Both exclusive
    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "{'age' : { $gte : ?0, $lte : ?1}}",
        fields = "{addresses: 0, hobbies: 0}")
    List<Person> findByPersonAgeBetweenQuery(Integer minAge, Integer maxAge);
}
