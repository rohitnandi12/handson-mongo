package com.springmongodemos.medium_tericcabrel.dtos;

import com.springmongodemos.medium_tericcabrel.models.Address;
import com.springmongodemos.medium_tericcabrel.models.Team;
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
