# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Install dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    postgresql-client \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the application files to the container
COPY . /app

# Ensure the wait-for-postgres.sh script is executable
RUN chmod +x /app/wait-for-postgres.sh

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the wait-for-postgres script and then the application
ENTRYPOINT ["./wait-for-postgres.sh", "postgres", "5432", "mvn", "spring-boot:run"]
