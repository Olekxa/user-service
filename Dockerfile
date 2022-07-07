# syntax=docker/dockerfile:1
FROM openjdk:18-alpine3.13
WORKDIR /app
COPY .mvn/ ./mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN
CMD ["./mvnw", "spring-boot:run"]