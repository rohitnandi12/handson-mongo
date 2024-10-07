package com.springmongodemos.tericcabrel.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Setter
@Getter
public class Address {
    private String city;

    private String postalCode;

    private String street;

}