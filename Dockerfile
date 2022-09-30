FROM openjdk:18-jdk-alpine

ARG JAR_FILE=target/*.jar

WORKDIR /report

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]