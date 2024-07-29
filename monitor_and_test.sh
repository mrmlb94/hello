#!/bin/bash

# Function to check container health
check_health() {
  local service=$1
  local status=$(docker inspect --format='{{.State.Health.Status}}' $service)
  echo $status
}

# Wait for the webapp service to be healthy
echo "Waiting for webapp to be healthy..."
while [[ "$(check_health hello_webapp_1)" != "healthy" ]]; do
  current_status=$(check_health hello_webapp_1)
  echo "Current status: $current_status"
  if [[ "$current_status" == "unhealthy" ]]; then
    echo "Webapp container is unhealthy. Exiting."
    exit 1
  fi
  docker logs hello_webapp_1 | tail -n 10
  sleep 5
done

# Wait additional 10 seconds
echo "Webapp is healthy. Waiting additional 10 seconds."
sleep 10

# Run Maven tests with the test profile
echo "Running Maven tests..."
mvn test -Dspring.profiles.active=test
