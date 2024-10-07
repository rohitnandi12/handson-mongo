package com.springmongodemos.dailycodebuffer.collections;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {
    private String address1;
    private String address2;
    private String city;
}
