FROM eclipse-temurin:18-jdk-focal

WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]