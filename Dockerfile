# Use a base image that includes the JDK
FROM openjdk:17-jdk-slim

# Install Maven for building the application
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Copy the local code to the container
COPY . .

# Build the application using Maven
RUN mvn package -DskipTests  # Skip tests here; we'll handle them separately

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/hello-0.0.1-SNAPSHOT.jar"]
