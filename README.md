[![CI with Docker Compose and Maven](https://github.com/mrmlb94/hello/actions/workflows/maven.yml/badge.svg)](https://github.com/mrmlb94/hello/actions/workflows/maven.yml)


# Hello Application

This repository contains the source code for the Hello application. 
This update introduces Docker support for the application, allowing it to be easily deployed and run in a containerized environment.

## Description of Changes

### Summary:
This update introduces Docker support for the application, allowing it to be easily deployed and run in a containerized environment. 
The changes include the addition of a `Dockerfile` to build the application image and a `docker-compose.yml` file to manage multi-container Docker applications, including the PostgreSQL database.

### Details of Changes:

1. **Dockerfile Addition:**
    - A `Dockerfile` was added to create a Docker image for the application.
    - The Dockerfile uses a multi-stage build process:
        - **Stage 1**: Uses Maven to build the application from source.
        - **Stage 2**: Uses an OpenJDK image to run the built application.

2. **Docker Compose Configuration:**
    - A `docker-compose.yml` file was added to define and run multi-container Docker applications.
    - It includes two services:
        - `app`: The application service, built from the Dockerfile.
        - `db`: The PostgreSQL database service, using the latest PostgreSQL image from Docker Hub.

3. **Application Configuration:**
    - Updated application properties to use environment variables for database connection details, enhancing flexibility and security.

4. **Docker Hub Deployment:**
    - Instructions were provided to build, tag, and push the Docker image to Docker Hub.
    - The Docker image can now be pulled and run on any system with Docker installed, simplifying the deployment process.

5. **Testing and Documentation:**
    - Added detailed instructions for running the application using Docker in this `README.md` file.
    - Ensured the end-to-end test (`UserFlowE2ETest`) runs correctly within the Docker environment.

### New/Modified Files:

- **Added**:
    - `Dockerfile`: Defines the Docker image build process.
    - `docker-compose.yml`: Defines the multi-container setup for the application and database.
    - `README.md`: Provides instructions for running the application using Docker.

- **Updated**:
    - `src/main/resources/application.properties`: Configured to use environment variables for database connection.

## Usage

### Clone the Repository
```sh
git clone <repository_url>
cd <repository_directory>
```

### Build and Push the Docker Image to Docker Hub
```sh
docker build -t mrmlb/hello-app .
docker push mrmlb/hello-app
```

1.  Pull and Run the Docker Image on Another Machine
2.  Ensure Docker and Docker Compose are installed.

Use the provided docker-compose.yml file to start the services:

```sh
docker-compose pull
docker-compose up -d
```

3.  Access the application at http://localhost:8080.



### To Stop the Application

```sh
docker-compose down
```


