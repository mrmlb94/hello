# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY ./target/hello-0.0.1-SNAPSHOT.jar /app/app.jar
COPY ./src/main/resources/templates /app/templates
COPY ./src/main/resources/application.properties /app/application.properties


# Expose the application port
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
