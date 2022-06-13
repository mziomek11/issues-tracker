# IssuesTracker

Issues tracker is application that helps organizations to track issues inside their projects. Application has been created for educational purposes. Demo is available [here](https://issuestracker.mateuszziomek.com)

## Authors

Backend: [Mateusz Ziomek](https://github.com/hommat)
Frontend: [Piotr Ligaj](https://github.com/ptrlgj)

## Frontend

## Why such an application?

The purpose of creating this application was to gain practical knowledge on topics related to both Spring (Spring/Spring Boot/Spring Web/Spring WebFlux) and architecture/good practices (Domain Driven Design/CQRS/Event Sourcing/Event Driven Architecture/Testing). I have decided to not use any libraries for CQRS/Event Sourcing in order to not having to learn too many thing at the same time. Of course architecture and tools are not chosen the best if it comes to application problem, but priority was to gain knowledge and not to create real product.

## Architecture

The project consists of a total of 11 applications. The backend layer almost entirely communicates asynchronously. The application includes

- Issues Command (Spring Web)
- Issues Query (Spring WebFlux)
- Organization Command (Spring Web)
- Organization Query (Spring WebFlux)
- Users Command (Spring Web)
- Users Query (Spring WebFlux)
- Notifications (Spring WebFlux)
- Api gateway (Spring Cloud Gateway)
- Discovery service (Eureka)
- Reverse proxy (nginx)
- Frontend (React + nginx)

Each application that requires a database has its own mognodb instance (I chose mongodb so I don't have to worry about database schema and migration). Kafka was used as a message broker, but I did not spend too much time understanding all its configurations (I was more interested in getting to know the concepts related to event driven architecture than spending time on huge concept which is kafka).

@TODO Image

## Client/Server communication

Communications between frontend and backend applications takes place in two ways

### Synchronous communication - it occurs when a logged out user uses the application

@TODO Image

1. The client sends the request
2. Server returns a response

Nothing special

### Asynchronous communication - it occurs when a logged in user uses the application

@TODO Image

1. Client starts listening to SSE (notification service)
2. Client sends a command (for example OpenIssueCommand to Issues Command application)
3. Command is being processed and server returns a response with id of created entity
4. Event is being sent to message broken (for example IssueOpenedEvent)
5. Query application (for example Issues Query application) receives event, processes it and then sends message to Notification application about data change 
6. Through SSE Notification application informs client about changes (for example IssueOpened - { issuesId: ..., organizationId: ..., projectId: .... })
7. Client decides what he wants to do. It can be asking backend for the new data with GET request, updating UI or just doing nothing

## Local development

In order to start application locally you need to install docker and docker-compose on your machine first. Then you can start everything with docker-compose up command. First launch of the application may take a while, because all dependencies must be downloaded. When everything is ready (you can check that by visiting http://localhost/eureka and seeing @TODO Image) you will have access to the following sites:

- Frontend application - http://localhost
- Api documentation - http://localhost/swagger
- Eureka dashboard - http://localhost/eureka
- Mailhog (this is the place, where all mails are sent in dev mode) - http://localhost/mailhog

## Known issues

- Lack of outbox/inbox pattern - possibility of losing and multiple processing of the same message
- Lack of database transactions
- Auth logic inside users module - module responsible for auth should be created instead
- Access check inside commands/queries - probably some kind of decorator should be used
- Integration tests uses repository mock - real DB should be used instead
- Lack of query apps test
- Lack of event handler in command apps
- Lack of HATEOAS
- Production docker config (mongo/kafka/networking/docker-compose) is not the best (it's just bad). The reason behind that is that I didn't want to spend too much time on infrastructure related stuff

## Conclusions

Using such a complex architecture is probably the best thing i could do. Thanks to this, I learned a lot of new concepts. At the same time, I saw how much there is still to learn ;)
