package com.springmongodemos.medium_tericcabrel.dtos;

import com.springmongodemos.medium_tericcabrel.models.Player;
import com.springmongodemos.medium_tericcabrel.models.PlayerPosition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class CreatePlayerRequest {
    private String name;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    private PlayerPosition position;

    private boolean isAvailable;

    public Player toPlayer() {
        return new Player()
                .setName(name)
                .setBirthDate(birthDate)
                .setPosition(position)
                .setAvailable(isAvailable);
    }
}
