CREATE TABLE employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    last_name VARCHAR(50),
    salary INT
);


INSERT INTO employee (name, last_name, salary)
VALUES 
    ('Juan', 'Pérez', 30000),
    ('María', 'López', 25000),
    ('Carlos', 'González', 35000),
    ('Laura', 'Martínez', 28000),
    ('Pedro', 'Ramírez', 40000),
    ('Ana', 'Hernández', 32000),
    ('Luis', 'Díaz', 27000),
    ('Sofía', 'Torres', 39000),
    ('Diego', 'Sánchez', 31000),
    ('Marta', 'Gómez', 33000);
      
CREATE TABLE address (
    id INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(100),
    number VARCHAR(10),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

INSERT INTO address (street, number, employee_id) VALUES
('Calle Principal', '123', 2),
('Avenida Central', '456', 3),
('Calle Secundaria', '789', 1),
('Callejón de los Sueños', '5', 4),
('Avenida del Sol', '987', 6);


SELECT * FROM employee;
SELECT * FROM address;

DROP TABLE address;
DROP TABLE employee;
