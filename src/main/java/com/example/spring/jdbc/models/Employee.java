package com.example.spring.jdbc.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private Integer id;
    private String name;
    private String last_name;
    private Integer salary;
}
