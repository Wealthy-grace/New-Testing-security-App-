# Stage 1: Build using Gradle
FROM gradle:8.5-jdk17 AS build

WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle --no-daemon clean build -x test

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Expose correct port (8081)
EXPOSE 8081

ENV TZ=UTC LANG=C.UTF-8 JAVA_OPTS="-Xms256m -Xmx512m"

# Security - non-root user
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown -R appuser:appuser /app
USER appuser

# Health check - Use a simple endpoint or disable authentication for actuator
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8081/actuator/health || \
        curl -f http://localhost:8081/ || \
        exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]