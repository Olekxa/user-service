# greedobank-kharkiv/report-service

## Getting Started

Download links:

SSH clone URL: ssh://git@git.jetbrains.space/java-internship/greedobank-kharkiv/report-service.git

HTTPS clone URL: https://git.jetbrains.space/java-internship/greedobank-kharkiv/report-service.git

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

## Prerequisites

What things you need to install the software and how to install them.

```
Examples
```

## Deployment

Add additional notes about how to deploy this on a production system.

## Resources

Add links to external resources for this project, such as CI server, bug tracker, etc.\

## Documentation

It's available within http://localhost:8080/swagger-ui/index.html

## Environment variables

```
DB_USER={login}; 
DB_PASSWORD={password}; 
DB_URL=jdbc:postgresql://localhost:5432/greedobank?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true;
SERVICE_URL=http://localhost:8082/api/v1/users;
SERVICE_ADMIN={login_service};
SERVICE_PASSWORD={password_service};
SERVICE_URL_AUTH=http://localhost:8082/api/v1/signin;
MAIL_USER={login_mail};
MAIL_PASSWORD={password_mail};
spring.mail.host=smtp.gmail.com
```

## Docker build command

* mac_os:

```
$ docker run -d -p 8080:8080\ 
--env SPRING_PROFILES_ACTIVE={profile}\
--env DB_USER={login}\
--env DB_PASSWORD={pasword}\
--env DB_URL="dbc:postgresql://docker.for.mac.localhost:5432/greedobank?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"\
--env SERVICE_URL="http://localhost:8082/api/v1/users"\
--env MAIL_HOST={smtp.gmail.com}\
--env MAIL_USER={login}\
--env MAIL_PASSWOR={pasword}\
--env SERVICE_URL_AUTH=http://localhost:8082/api/v1/signin\
--env SERVICE_ADMIN={login}\
--env SERVICE_PASSWORD={pasword}\
{image_name}
```

* windows_os:


$ docker run -d -p 8080:8080\ 
--env SPRING_PROFILES_ACTIVE={profile}\
--env DB_USER={login}\
--env DB_PASSWORD={pasword}\
--env DB_URL="dbc:postgresql://host.docker.internal:5432/greedobank?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"\
--env SERVICE_URL="http://localhost:8082/api/v1/users"\
--env MAIL_HOST={smtp.gmail.com}\
--env MAIL_USER={login}\
--env MAIL_PASSWOR={pasword}|
--env SERVICE_URL_AUTH=http://localhost:8082/api/v1/signin\
--env SERVICE_ADMIN={login}\
--env SERVICE_PASSWORD={pasword}\
{image_name}

## Docker compose up command 
```
docker-compose up --build
```

