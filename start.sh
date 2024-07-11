#!/bin/bash

# Start Docker Compose
docker-compose up --build -d

# Function to check if the web application is up
check_app() {
  curl --silent --head --fail http://localhost:8080 > /dev/null
}

# Wait until the web application is available
echo "Waiting for the web application to be available..."
until check_app; do
  sleep 5
done

# Open the browser
echo "Opening the web application in the browser..."
open http://localhost:8080  # On macOS
# xdg-open http://localhost:8080  # On Linux (use `xdg-open` instead of `open`)

echo "Web application is now accessible at http://localhost:8080"
