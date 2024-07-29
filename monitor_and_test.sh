#!/bin/bash

# Function to check container health
check_health() {
  local service=$1
  local status=$(docker inspect --format='{{.State.Health.Status}}' $service)
  echo $status
}

# Function to check container logs
check_logs() {
  local service=$1
  docker logs $service
}

# Wait for PostgreSQL to be healthy
echo "Waiting for PostgreSQL to be healthy..."
while [[ "$(check_health hello_postgres_1)" != "healthy" ]]; do
  current_status=$(check_health hello_postgres_1)
  echo "PostgreSQL status: $current_status"
  if [[ "$current_status" == "unhealthy" ]]; then
    echo "PostgreSQL container is unhealthy. Exiting."
    check_logs hello_postgres_1
    exit 1
  fi
  sleep 5
done

# Wait for the webapp service to be healthy
echo "Waiting for webapp to be healthy..."
while [[ "$(check_health hello_webapp_1)" != "healthy" ]]; do
  current_status=$(check_health hello_webapp_1)
  echo "Webapp status: $current_status"
  if [[ "$current_status" == "unhealthy" ]]; then
    echo "Webapp container is unhealthy. Exiting."
    check_logs hello_webapp_1
    exit 1
  fi
  sleep 5
done

# Wait additional 10 seconds
echo "Webapp is healthy. Waiting additional 10 seconds."
sleep 10

# Run Maven tests with the test profile
echo "Running Maven tests..."
mvn test -Dspring.profiles.active=test
