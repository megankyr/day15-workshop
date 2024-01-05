FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
COPY db db

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/day13-workshop-0.0.1-SNAPSHOT.jar .
COPY --from=builder /app/db db

ENV PORT=8080

CMD SERVER_PORT=${PORT} java -jar target/day13-workshop-0.0.1-SNAPSHOT.jar --dataDir db
