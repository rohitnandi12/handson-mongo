package com.springmongodemos.dailycodebuffer;

import com.springmongodemos.dailycodebuffer.collections.Person;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {

    private final MongoTemplate mongoTemplate;

    public AddressController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/address1/exists")
    public List<Person> address1Exists() {
        Query query = new Query();
        query.fields().include("addresses");
        query.addCriteria(Criteria.where("addresses.address1").exists(true));

        return mongoTemplate.find(query, Person.class);
    }
}
