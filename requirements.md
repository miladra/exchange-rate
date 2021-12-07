# currency-rate-exchange
# Summary:
Using a Java framework of your choice, build a RESTful service for a “Currency Rate Exchange” application. The application aims to pull currency exchange rates from different currency converter public APIs periodically (i.e. every 5 minutes, configurable per provider), store the historical data, and expose all the actual rates through the API. Choose at least two public APIs on your convenience which are providing currency exchange rate data (i.e. ​ Fixer​ , ​ ExchangeRate-API​ ) and try to integrate with them. In addition to this, it’s possible to have our customer currency exchange rate controlled over the RESTful API (adding/updating/removing exchange rate, i.e. USD to EUR). Expected to have an endpoint to be able to convert currencies. Use multilayered architecture and demonstrate concepts of OO design and design patterns (i.e. Dependency Injection, DAOs/Repositories, Services, ...).

# Git:

Use Git as version control. Create a new project in your own GitHub account (if you don’t have an account, create one at ​ github.com​ ) and push the code to it. It is extremely important to commit often so we can see the history of the commits. We will evaluate the project by not only the end result, but also the history of the commits. Projects with only 2-3 commits will be rejected.

# RESTful API:

Design and build a RESTful API to allow CRUD operations on currency rates. Support JSON for requests and responses. Note access should be restricted only for Authenticated users.

# Unit and/or Integration Tests:
 Provide unit and/or integration tests for at least one important part of your application. You don’t need to achieve any specific test coverage number, just try to cover all reasonable cases for one part of the code.
 
# DTOs:

Separate DTOs for REST API requests and responses from internal domain objects.

# Docker:

Provide a docker or docker-compose file which launches the Java application. A docker-compose file which additionally launches the database instance gets more bonus points.

# Authentication:

Use Basic HTTP Authentication.

# Demonstration:

Provide Swagger Documentation. Please write a detailed README on how to run and use an application.

# In addition, choose and implement at least one of these:

- Build it as a non-blocking/reactive application
- Use JWT Tokens for authentication