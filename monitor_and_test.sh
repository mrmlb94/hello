#!/bin/bash

# Function to check container logs
check_logs() {
  local service=$1
  docker logs $service
}

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
sleep 30

# Check PostgreSQL logs
echo "Checking PostgreSQL logs..."
check_logs hello_postgres_1

# Wait for the webapp to be ready
echo "Waiting for webapp to be ready..."
sleep 60

# Check webapp logs
echo "Checking webapp logs..."
check_logs hello_webapp_1

# Run Maven tests with the test profile
echo "Running Maven tests..."
mvn test -Dspring.profiles.active=test
