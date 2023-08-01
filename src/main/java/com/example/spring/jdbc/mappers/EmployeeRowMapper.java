package com.example.spring.jdbc.mappers;


import com.example.spring.jdbc.models.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt(1));
        employee.setName(rs.getString(2));
        employee.setLast_name(rs.getString(3));
        employee.setSalary(rs.getInt(4));
        return employee;
    }
}
