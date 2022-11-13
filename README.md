# 4WT Assignment - Advanced Programming Topics & Mobile Development

For a class assigment we had to create a mobile application which ran on a deployed backend architecture consisting of at least 2 backend microservices with 1 edge-service on top which is responsible for handling the users requests.

This architecture looks like:
![Architecture](https://user-images.githubusercontent.com/58487061/201539793-498001af-9138-4e67-b053-1033f9fda12e.png)

## Description
The mobile application is a POC for a Geo-location AR scavenger hunt. The user can find and interact with 3D models which are augmented in the real world around the user. 

The mobile application was created in `Flutter` and used the `Wikitude AR SDK`. The repo for the mobile application can be found [here](https://github.com/KevinVandeputte-TM/flutter_wikitude_project).

The mobile application will send requests to the `edge-service` which will connect to 2 lower microservices `game-service` and `user-serice` to request information which it then will process and combine into a single response to the user. The user will only communicate directly with the `edge-service`. To avoid unnecessary requests the `Flutter` application is provided with a simple state management by using the `Provider` package.

### Repo's (Click the repo name)
- [EDGE-SERVICE](https://github.com/ValerieBecquart/edgeService):

    The service is a Spring Boot REST service with 100% code coverage in tests and a `SwaggerUI` implementation. There is a `Github Action` in place to establish a CI/CD pipeline that checks compilation, tests the code through `SonarCloud`, and then builds. Finally, the `Docker Container` is uploaded to `Docker Hub`.


- [GAME-SERVICE](https://github.com/ValerieBecquart/game_service):

    The service is a Spring Boot REST Microservice which connects to a Dockerized `MariaDB` database. The service is responsible for providing the game with all necessary game information. `Github Actions` are used to establish a CI/CD pipeline for compiling, testing, and building the container. Finally, the `Docker Container` is uploaded to `Docker Hub`.


- [USER-SERVICE](https://github.com/ValerieBecquart/users_service2):

  The service is a Spring Boot REST Microservice which connects to a Dockerized `MongoDB` database. The service is responsible for providing the game with all necessary user information. `Github Actions` are used to establish a CI/CD pipeline for compiling, testing, and building the container. Finally, the `Docker Container` is uploaded to `Docker Hub`.


- [DEPLOYMENT](https://github.com/ValerieBecquart/microservices-docker-compose):

  The backend was deployed on `Okteto`. Therefor a seperate repository was created containing a single `Docker compose` file which gives the instructions to pull the images of `docker hub` and set up all microservices and underlying databases. 

# Running the example

The lower services each connect to a Dockerized database. These databases need to be up and running before the project can be ran locally.

Set up the Docker Container with the MariaDB database:
``` pwsh
docker run --name game-sql -p 3306:3306 -e MARIADB_ROOT_PASSWORD=abc123 -e MARIADB_USER=user -e MARIADB_PASSWORD=user123 -d mariadb
```

Set up the Docker Container with the MongoDB database:
``` pwsh
docker run --name users-mongodb -p 27017:27017 -d mongo 
```

Once this is done you can start the `game-service` and `user-service` applications. These applications include a method to fill up the databases with dummy information for testing purposes. 

Before running the `edge-service` you should know that it, right now, is set up to be deployed. For you to run the project locally you need to go into the `application.properties` file and change following lines of code:

    userservice.baseurl = ${USER_SERVICE_BASEURL:192.168.99.100:8052}
    gameservice.baseurl = ${GAME_SERVICE_BASEURL:192.168.99.100:8051}
into:
``` pwsh
userservice.baseurl = ${USER_SERVICE_BASEURL:localhost:8052}
gameservice.baseurl = ${GAME_SERVICE_BASEURL:localhost:8051}
```

After this adjustment you can start the `edge-service` application.

Now you should be able to go to:
``` pwsh
http://localhost:8053/scores
```
And you should see a similar output:
![localhost](https://user-images.githubusercontent.com/58487061/200133538-f3d0b4ba-681a-4259-9166-4710b7bb0791.png)

# Testing
- [Postman](https://www.postman.com/)

The `edge-service` API was tested by using Postman. Hereby the test results of those tests as proof of functioning of the `edge-service`:

![postman](https://user-images.githubusercontent.com/58487061/201540718-531d4eaa-207c-40ec-8802-9f03a58c02a1.png)

- [SwaggerUI](https://swagger.io/tools/swagger-ui/)

The service has `SwaggerUI` implemented for interactively testing and exposing the API. After setup the `SwaggerUI` will be running locally on: http://localhost:8053/swagger-ui.html

![swagger](https://user-images.githubusercontent.com/58487061/201539983-2d29211e-721a-43ab-abfe-9839d01710ef.png)


- Unit and Integration Tests

All repositories have been provided with Unit tests with 100% code coverage for the controllers, models, and repositories inside the project.
The lower services `user-service` and `game-service` have also been provided IntegrationTests with 100% code coverage.

#### Unit Tests Code coverage results:
![UnitTests](https://user-images.githubusercontent.com/58487061/201540192-ce08d444-7bf6-49dc-b2e8-fdf15b0a11d6.png)

#### Integration Tests Code coverage results:
![integrationtests](https://user-images.githubusercontent.com/58487061/201540275-c30ec8a2-9cb2-4a80-bb65-97351c333c72.png)
