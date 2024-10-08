package com.springmongodemos.telusko.repositories;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.springmongodemos.telusko.collections.Post;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SearchRepositoryImpl implements SearchRepository {

    private final String databaseName;
    private final MongoClient mongoClient;
    private final MongoConverter mongoConverter;

    public SearchRepositoryImpl(
            @Value("spring.data.mongodb.database") String databaseName,
            MongoClient mongoClient,
            MongoConverter mongoConverter
    ) {
        this.databaseName = databaseName;
        this.mongoClient = mongoClient;
        this.mongoConverter = mongoConverter;
    }

    @Override
    public List<Post> findByText(String text) {

        final List<Post> posts = new ArrayList<>();
        System.out.println(databaseName);
        MongoDatabase database = mongoClient.getDatabase("spring-mongo-demos");
        MongoCollection<Document> collection = database.getCollection("telusko_posts");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("techs", "desc", "profile")))),
                new Document("$sort",
                        new Document("exp", 1L)),
                new Document("$limit", 5L)));

        result.forEach(doc -> posts.add(mongoConverter.read(Post.class, doc)));

        return posts;
    }
}
