# Stage 1: Build using Gradle
FROM gradle:8.5-jdk17 AS build

WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .

RUN gradle --no-daemon clean build -x test

# Stage 2: Copy and run JAR
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENV TZ=UTC \
    LANG=C.UTF-8

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
