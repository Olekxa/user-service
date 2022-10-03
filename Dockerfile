FROM eclipse-temurin:18-jdk-focal

WORKDIR /app

COPY . .

CMD ["./mvnw", "spring-boot:run"]