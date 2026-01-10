FROM ubuntu:latest AS build

RUN apt-get update
FROM eclipse-temurin:11-jre
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:11-jdk-slim

EXPOSE 8080

COPY --from=build /target/helpdesk-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]