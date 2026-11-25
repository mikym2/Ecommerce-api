# Use Java 21 runtime
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot JAR
COPY deployment/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]