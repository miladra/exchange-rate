# currency-rate-exchange

## Used Stack 

   * Spring Boot
   * Java 11
   * Spring Framework
   * Apache-maven-3.8.4
   * RestTemplate
   * MySQL 5.7 and for test H2database
   * Swagger
   * Docker Compose
   * Lombok
   * Junit
  
# Thought Process
* I assumed that the application receives the exchange rate from different public web services based on the same base currency, as I saw they have this ability.
* I wrote two approaches for integrating exchange rates, time or higher rate.
* In order to better performance, I integrated live data into a ConcurrentHashMap, and an API exposes those data, as well as I stored data into the database.

### Build Project

```
mvn clean install
```

### Run app with docker-compose

In root directory
```
mvn clean install

docker-compose up --build
```

### Run app without docker or just run .jar

If you want run application standalone please change your database configuration in application.properties

### Swagger

http://localhost:8080/swagger-ui.html

###### By: Milad Ranjbari
