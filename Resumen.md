# Spring JDBC
Módulo dentro del ecosistema de Spring Framework que proporciona una abstracción simplificada para trabajar con JDBC (Java Database Connectivity), que es la API de Java para acceder a bases de datos relacionales.

La API JDBC puede ser tediosa y repetitiva, ya que requiere la apertura y cierre de conexiones, la creación y liberación de declaraciones, y la gestión de excepciones. Spring JDBC resuelve estos problemas y simplifica significativamente el acceso a la base de datos al proporcionar características y abstracciones adicionales, incluyendo:

## DataSource
Spring JDBC utiliza un objeto DataSource para obtener y administrar conexiones a la base de datos. 

Un DataSource puede ser configurado para conectarse a una base de datos específica y administrar el conjunto de conexiones, lo que mejora la eficiencia y rendimiento al reutilizar conexiones.

## JdbcTemplate
La clase JdbcTemplate es el componente central de Spring JDBC. 

Proporciona un conjunto de métodos convenientes para ejecutar consultas, actualizaciones y operaciones con resultados. 

JdbcTemplate maneja automáticamente la creación y liberación de conexiones, la ejecución de declaraciones y el manejo de excepciones, lo que evita que el desarrollador tenga que preocuparse por detalles de bajo nivel.

## RowMapper
Cuando se obtienen resultados de una consulta, Spring JDBC utiliza la interfaz RowMapper para convertir cada fila de resultados en un objeto Java. 

Los RowMapper personalizados permiten mapear las filas de resultados a clases de dominio o POJOs (Plain Old Java Objects) de manera más sencilla.

## Exception Translation

Spring JDBC ofrece una traducción de excepciones que convierte las excepciones específicas de JDBC en excepciones de Spring más significativas y fáciles de manejar, lo que simplifica la gestión de errores en la capa de acceso a datos.

## Transacciones

Spring JDBC proporciona soporte para transacciones mediante la anotación @Transactional o mediante el uso programático de la clase TransactionTemplate. 

Esto permite realizar operaciones atómicas y consistentes en la base de datos, asegurando la integridad de los datos.

# Métodos de JdbcTemplate
### queryForObject
Este método se utiliza para ejecutar una consulta SQL que espera un solo resultado y devuelve el resultado mapeado a un objeto Java. 

Debe usarse cuando se espera una sola fila de resultados y se quiere mapear esos resultados a un objeto Java. 

Si la consulta no devuelve ningún resultado, o si devuelve más de una fila, se lanzará una excepción.
```java
String sql = "SELECT name FROM employees WHERE id = ?";
String name = jdbcTemplate.queryForObject(sql, new Object[] { 101 }, String.class);
```
### update

Este método se utiliza para ejecutar una sentencia SQL que realiza actualizaciones o modificaciones en la base de datos, como INSERT, UPDATE o DELETE. 

Se utiliza cuando no se espera un resultado de retorno, sino simplemente actualizar o cambiar datos en la base de datos.

```java
String sql = "UPDATE employees SET salary = ? WHERE id = ?";
int rowsAffected = jdbcTemplate.update(sql, 50000.0, 101);
```

### query

Este método se utiliza para ejecutar una consulta SQL que devuelve múltiples filas de resultados. 

Los resultados se mapean a objetos Java utilizando un RowMapper (o una clase RowMapper anónima) que convierte cada fila de resultados en un objeto Java. Se utiliza cuando se espera más de una fila de resultados y se desea mapear esos resultados a una lista de objetos Java.

```java
String sql = "SELECT * FROM employees WHERE department = ?";
List<Employee> employees = jdbcTemplate.query(sql, new Object[] { "IT" }, new EmployeeRowMapper());
```

### queryForList

Este método es similar a "query", pero se utiliza cuando no se necesita un RowMapper personalizado. 

En lugar de eso, devuelve una lista de mapas donde cada mapa representa una fila de resultados. 

Los nombres de las columnas son las claves de los mapas, y los valores son los valores de las columnas. 

Se utiliza cuando se desea obtener una lista de resultados sin la necesidad de una conversión específica en objetos Java.

```java
String sql = "SELECT name, salary FROM employees WHERE department = ?";
List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, new Object[] { "HR" });
```
### batchUpdate:

Este método se utiliza para ejecutar varias sentencias SQL en una sola llamada, lo que mejora el rendimiento y reduce la sobrecarga de la red. 

Se utiliza cuando se desea realizar actualizaciones o modificaciones en lotes, como múltiples inserciones o actualizaciones en un solo paso.
```java
String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
List<Object[]> batchArgs = new ArrayList<>();
batchArgs.add(new Object[] { "John", "IT", 60000.0 });
batchArgs.add(new Object[] { "Jane", "HR", 55000.0 });
jdbcTemplate.batchUpdate(sql, batchArgs);
```






s