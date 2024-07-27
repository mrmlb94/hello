#!/bin/bash

# Start Spring Boot application in the background
echo "Starting Spring Boot application..."
mvn spring-boot:run &
SPRING_PID=$!

# Wait for the application to start
# This example uses curl to check the application's health endpoint.
# Replace `8080` with your application's port and adjust the endpoint if necessary.
echo "Waiting for the application to start..."
while ! curl -s http://localhost:8080/actuator/health | grep -q 'UP'; do
  sleep 10
done

echo "Application started. Running tests..."

# Run Maven tests
mvn test

# Optionally, stop the Spring Boot application after tests
# Uncomment the next line if you want to stop the application after tests
# kill $SPRING_PID

echo "Tests completed."

# End of the script
