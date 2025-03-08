#!/bin/bash

# Start Docker containers
echo "Starting Docker containers..."
docker-compose up -d

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
until docker exec $(docker-compose ps -q postgres) pg_isready -U postgres001 -d user_db; do
  sleep 5
  echo "Waiting for database to be ready..."
done

echo "Database is ready. Starting the application..."

# Run Maven verify to execute tests
echo "Running tests..."
mvn verify

# Stop Docker containers after tests
echo "Stopping Docker containers..."
docker-compose down

echo "All tests completed successfully!"
