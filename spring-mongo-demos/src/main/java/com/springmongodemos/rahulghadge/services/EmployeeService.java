package com.springmongodemos.rahulghadge.services;

import com.springmongodemos.rahulghadge.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeService {

    @Autowired
    private MongoTemplate mongoTemplate;

    List<Employee> getAll() {
        return mongoTemplate.findAll(Employee.class);
    }

    List<Employee> getEmployeeByFirstName(String firstName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(firstName));
        return mongoTemplate.find(query, Employee.class);
    }

    Employee getSingleEmployeeByFirstName(String firstName) {
        return mongoTemplate.findOne(
                new Query().addCriteria(Criteria.where("firstName").is(firstName)),
                Employee.class
        );
    }

    // select * from employee where lastName like %Gurung% limit 1 --> (case insensitive)
    List<Employee> getEmployeeByFirstNameLike(String firstName) {
        return mongoTemplate.find(
                new Query().addCriteria(
                        Criteria.where(firstName).regex(firstName, "i")
                ),
                Employee.class
        );
    }

    List<Employee> getEmployeeBySalaryGreaterThan(int salary) {
        Query query = new Query();
        query.addCriteria(Criteria.where("salary").gt(salary));
        query.with(Sort.by(Sort.Direction.ASC, "firstName"));
        query.with(Sort.by(new Sort.Order(Sort.Direction.ASC, "firstName").ignoreCase()));

        return mongoTemplate.find(query, Employee.class);
    }
}
