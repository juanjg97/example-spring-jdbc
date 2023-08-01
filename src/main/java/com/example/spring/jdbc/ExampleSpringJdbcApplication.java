package com.example.spring.jdbc;

import com.example.spring.jdbc.models.Address;
import com.example.spring.jdbc.models.Employee;
import com.example.spring.jdbc.mappers.EmployeeRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;


@Slf4j
@SpringBootApplication
public class ExampleSpringJdbcApplication implements CommandLineRunner {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ExampleSpringJdbcApplication.class, args);
		DataSource dataSource = context.getBean(DataSource.class);
		log.info("Data source implementation {}",dataSource.getClass().getName());
	}

	@Override
	public void run(String... args) throws Exception {
		//viewMaxSalary();
		//insertAdress();
		//getEmployee();
		//insertEmployeeException();
		//getEmployee2();
		//insertAddressBatch();
		getKey();
	}

	public void viewMaxSalary(){
		Integer salary = jdbcTemplate.queryForObject("SELECT MAX(salary) FROM employee",Integer.class);
		log.info("Max Salary: {}",salary);
	}

	public void insertAdress(){
		String sentence = "INSERT INTO address (street,number,employee_id) VALUES (?,?,?)";
		int rowsImpacted=jdbcTemplate.update(sentence,"Copilco","5",8);
		log.info("Rows impacted: {}",rowsImpacted);
	}

	public void getEmployee(){
		List<Employee> employees = jdbcTemplate.query("SELECT * FROM employee",new EmployeeRowMapper());

		for(Employee employee : employees){
			log.info("id {}, name {},last_name {},salary {}",
					employee.getId(),employee.getName(),employee.getLast_name(),employee.getSalary());
		}
	}

	public void getEmployee2(){
		String sentence = "SELECT name FROM employee WHERE salary >=?";
		int salary = 33000;
		List<String>names=jdbcTemplate.queryForList(sentence,new Object[] { salary }, String.class);
		log.info("The employees with a salary over {} are: {}",salary,names);
	}

	public void insertEmployeeException(){
		try{
			int badId = 100;
			String sentence = "INSERT INTO address (street,number,employee_id) VALUES (?,?,?)";
			int rowsImpacted=jdbcTemplate.update(sentence,"Copilco","5",badId);
			log.info("Rows impacted: {}",rowsImpacted);
		}catch(DataAccessException e){
			log.info("Exception received: {}",e.getClass());
			log.info("Caused By: {}",e.getCause());
		}
	}

	public void insertAddressBatch(){
		List<Address> addresses = Arrays.asList(
				new Address("Calle1","1",2),
				new Address("Calle1", "1", 2),
				new Address("Calle2", "15", 5),
				new Address("Avenida Principal", "20", 8),
				new Address("Calle del Sol", "7", 3),
				new Address("Avenida Central", "123", 1),
				new Address("Calle de la Luna", "10", 4),
				new Address("Calle Mayor", "50", 6),
				new Address("Avenida Norte", "100", 9),
				new Address("Calle Secundaria", "35", 7),
				new Address("Avenida del Parque", "70", 10)
		);

		String sentence = "INSERT INTO address (street,number,employee_id) VALUES (?,?,?)";
		int[] rowsImpacted = jdbcTemplate.batchUpdate(sentence, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Address address = addresses.get(i);
				ps.setString(1, address.getStreet());
				ps.setString(2, address.getNumber());
				ps.setInt(3, address.getEmployeeId());
			}

			@Override
			public int getBatchSize() {
				return addresses.size();
			}
		});
		for(int row :rowsImpacted){
			log.info("Rows impacted: {}", row);
		}
	}

	public void getKey(){
		KeyHolder keyHolder= new GeneratedKeyHolder();
		int rowsImpacted=jdbcTemplate.update(new PreparedStatementCreator() {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sentence = "INSERT INTO address (street,number,employee_id) VALUES (?,?,?)";
				PreparedStatement statement = con.prepareStatement(sentence, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1,"Avenida a");
				statement.setString(2,"2");
				statement.setInt(3,5);
				return statement;
			}
		},keyHolder);
		log.info("Rows impacted: {}",rowsImpacted);
		log.info("Generated key: {}",keyHolder.getKey().intValue());

	}


}
