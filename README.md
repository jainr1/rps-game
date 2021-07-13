### System requirements
#### Java 11
#### Gradle 7.0.2

### Flyway schema to create and update initial ddl
#### gradle -Dflyway.configFiles=src/main/resources/application-postgres.properties flywayMigrate

### Initial player config sql.
#### BCryptPasswordEncoder is used to create password hash
#### Raw password in sample config is test1234
##### src/main/resources/data.sql

### run application
#### Using in memory database (for testing). This will auto create tables and load initial player details.
##### java -jar rps-game-0.0.1-SNAPSHOT.jar

#### Using postgres database (need to run flyway config to create tables and data.sql to create initial players)
##### java -jar rps-game-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application-postgres.properties

## Things to improve
#### 1. Logging
#### 2. Basic auth is using username and password for each player. But it is not checking if user is playing with its own player id and also not restricting access to other player stats.
#### 3. More integration test

### Curl request to invoke api's
#### 1. Throw a Shape
curl -u user1:test1234 \
--request POST \
--url http://localhost:8080/api/players/12345/throws/rock 

#### 2. Get Statistics
curl -u user1:test1234 \
--request GET \
--url http://localhost:8080/api/players/12345/statistics

#### 3. Get players
curl -u user1:test1234 \
--request GET \
--url http://localhost:8080/api/players