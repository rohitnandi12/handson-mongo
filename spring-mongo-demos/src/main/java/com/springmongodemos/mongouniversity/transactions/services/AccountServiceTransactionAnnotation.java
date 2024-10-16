package com.springmongodemos.mongouniversity.transactions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceTransactionAnnotation {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public void transferAmount(String senderId, String receiverId, int amount) {

        mongoTemplate.updateFirst(
                Query.query(Criteria.where("account_id").is(senderId)),
                new Update().inc("balance", -amount),
                "accounts"
        );

        mongoTemplate.updateFirst(
                Query.query(Criteria.where("account_id").is(receiverId)),
                new Update().inc("balance", amount),
                "accounts"
        );
    }
}
