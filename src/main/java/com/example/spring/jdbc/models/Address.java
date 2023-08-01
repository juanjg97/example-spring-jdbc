package com.example.spring.jdbc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    private Integer id;
    private String street;
    private String number;
    private Integer employeeId;

    public Address(String street, String number, Integer employeeId) {
        this.street = street;
        this.number = number;
        this.employeeId = employeeId;
    }
}
