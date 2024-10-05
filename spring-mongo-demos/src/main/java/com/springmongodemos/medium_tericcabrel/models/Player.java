package com.springmongodemos.medium_tericcabrel.models;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "medium_tericcabrel_players")
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class Player extends BaseModel {
    @Indexed
    private String name;

    @Field(name = "dateOfBirth")
    private Date birthDate;

    @Indexed
    @Field(targetType = FieldType.STRING)
    private PlayerPosition position;

    private boolean isAvailable;
}
