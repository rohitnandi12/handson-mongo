package com.springmongodemos.telusko.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "telusko_post")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private String profile;
    private String description;
    private int experience;
    private String techs[];
}
