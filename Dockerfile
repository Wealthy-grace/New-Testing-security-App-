# Stage 1: Build using Gradle
FROM gradle:8.5-jdk17 AS build

WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .

# Build the application (skip tests in Docker build for speed)
RUN gradle --no-daemon clean build -x test

# Stage 2: Runtime image
FROM openjdk:17-jdk-slim

# Install useful utilities and clean up
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Expose the correct port (8081 as per your configuration)
EXPOSE 8081

# Set environment variables
ENV TZ=UTC \
    LANG=C.UTF-8 \
    JAVA_OPTS="-Xms256m -Xmx512m"

# Add a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown -R appuser:appuser /app
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8081/actuator/health || exit 1

# Entry point
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]