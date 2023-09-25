# shopApp
Rest API with Spring Boot

## About

This is a sample of an API REST for shopping online with Java/Maven/Spring Boot (version 2.7.16)

## Technologies

* Java 11
* Spring Boot 2.7.16
* Spring Security
* Hibernate
* PostgreSQL
* Lombok
* Maven

## Installation & Run

* Before running the API server, you should update the database config inside the [application.properties](https://github.com/loganag/shopApp/blob/master/src/main/resources/application.properties) file. 
* Update username and password as per your local database config.

```
    spring.datasource.url=jdbc:postgresql://localhost/shopApp
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    spring.datasource.username=postgres
    spring.datasource.password=root
```

## API Root Endpoint
```
https://localhost:8080/order
```
