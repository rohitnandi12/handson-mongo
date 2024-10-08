package com.springmongodemos.dailycodebuffer.services;

import ch.qos.logback.core.util.StringUtil;
import com.springmongodemos.dailycodebuffer.collections.Person;
import com.springmongodemos.dailycodebuffer.repositories.PersonRepository;
import org.bson.Document;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import javax.print.attribute.standard.DocumentName;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MongoTemplate mongoTemplate;

    public PersonService(
            PersonRepository personRepository, MongoTemplate mongoTemplate
    ) {
        this.personRepository = personRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Person> getAllPersons() {
//         mongoTemplate.findAll();
        return personRepository.findAll();
    }

    public Person addPerson(Person person) {
//        mongoTemplate.save(person)
        return personRepository.save(person);
    }

    public Person deletePersonById(String id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        personRepository.delete(person);
        return person;
    }

    public List<Person> getPersonsStartWithName(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    public List<Person> getByPersonAgeBetween(Integer minAge, Integer maxAge) {
//        return personRepository.findByAgeIsBetween(minAge, maxAge);
//        return personRepository.findByAgeBetween(minAge, maxAge);
        return personRepository.findByPersonAgeBetweenQuery(minAge, maxAge);
    }

    public Page<Person> search(
            String name, Integer minAge, Integer maxAge, String city, Pageable pageable
    ) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (!StringUtil.isNullOrEmpty(name)) {
            criteria.add(Criteria.where("firstName").regex(name, "i"));
        }

        if (minAge != null && maxAge != null) {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if (!StringUtil.isNullOrEmpty(city)) {
            criteria.add(Criteria.where("addresses.city").is(city));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }

        Page<Person> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class), pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Person.class));

        return people;
    }

    public List<Document> getOldestPersonByCity() {

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");

        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(
                unwindOperation,
                sortOperation,
                groupOperation
        );

        List<Document> person = mongoTemplate.aggregate(
                aggregation, Person.class, Document.class
        ).getMappedResults();

        return person;
    }

    public List<Document> getPopulationByCity() {

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");

        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .count().as("popCount");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "popCount");

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("popCount").as("count")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(
                unwindOperation,
                groupOperation,
                projectionOperation
        );

        List<Document> documents = mongoTemplate.aggregate(
                aggregation,
                Person.class,
                Document.class
        ).getMappedResults();
        return documents;
    }
}
