# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/hello-0.0.1-SNAPSHOT.jar hello.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "hello.jar"]
