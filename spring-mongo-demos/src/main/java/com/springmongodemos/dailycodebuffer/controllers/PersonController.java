package com.springmongodemos.dailycodebuffer.controllers;

import com.springmongodemos.dailycodebuffer.collections.Person;
import com.springmongodemos.dailycodebuffer.services.PersonService;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/name")
    public List<Person> getPersonStartsWith(@RequestParam String name) {
        return personService.getPersonsStartWithName(name);
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @DeleteMapping("/{id}")
    public Person deletePersonBy(@PathVariable String id) {
        return personService.deletePersonById(id);
    }

    @GetMapping("/age")
    public List<Person> getByPersonAgeBetween(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge
    ) {
        return personService.getByPersonAgeBetween(minAge, maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPersons(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return personService.search(name, minAge, maxAge, city, pageable);
    }

    @GetMapping("/oldestPerson")
    public List<Document> getOldestPerson() {
        return personService.getOldestPersonByCity();
    }
}
