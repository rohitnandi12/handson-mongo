package com.springmongodemos.tericcabrel.dtos;

import com.springmongodemos.tericcabrel.models.Address;
import com.springmongodemos.tericcabrel.models.Team;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTeamRequest {
    private String name;

    private String acronym;

    private Address address;

    public Team toTeam() {
        return new Team().setName(name).setAcronym(acronym).setAddress(address);
    }
}
