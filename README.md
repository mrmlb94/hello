[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=bugs)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=coverage)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Java CI with Maven](https://github.com/mrmlb94/hello/actions/workflows/maven.yml/badge.svg)](https://github.com/mrmlb94/hello/actions/workflows/maven.yml)[![codecov](https://codecov.io/github/mrmlb94/hello/branch/main/graph/badge.svg?token=UZPZ0UC5J3)](https://codecov.io/github/mrmlb94/hello)



# Java Web Application - Quiz Management System

## Introduction

This project is a Java web application developed as a quiz management system, allowing users to register, log in, create quizzes, and submit quiz responses. It utilizes the Spring framework to provide a robust and flexible environment for enterprise-level applications. The application is designed with scalability, maintainability, and extensibility in mind.

Comprehensive testing, including unit, integration, and end-to-end tests, has been conducted to ensure the application's reliability and functionality. The project also integrates Continuous Integration and Continuous Deployment (CI/CD) using GitHub Actions, with a focus on achieving 100% code coverage using JaCoCo.

## Techniques and Frameworks

### Design Patterns

The application is built using well-known design patterns:
- **Model-View-Controller (MVC)**: Separates the application into Model, View, and Controller components.
- **Singleton**: Ensures a class has only one instance, commonly used for managing resources.
- **Repository**: Abstracts the data access layer, providing a clean API for CRUD operations.

### Transaction Management

The application uses Spring's robust transaction management capabilities, supporting both programmatic and declarative approaches to ensure data integrity and consistency.

### ACID Properties

The application adheres to ACID principles (Atomicity, Consistency, Isolation, Durability) to ensure reliable transaction processing in a database system.

## Running the Application

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Apache Maven**
- **A relational database** (e.g., PostgreSQL)

### Steps to Run

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/mrmlb94/hello
    cd hello
    ```

2. **Run the Application using Docker**:
    - Build the Docker images and start the containers using Docker Compose:
        ```bash
        docker-compose --build
        docker-compose up
        ```

3. **Access the Application**:
    - Open a web browser and navigate to `http://localhost:8080`.

## Testing

The application includes two testing profiles:

1. **Unit and Integration Tests**:
    - Use the `skip-e2e-tests` profile to run only unit and integration tests:
        ```bash
        mvn test -P skip-e2e-tests
        ```

2. **End-to-End Tests**:
    - To run all 76 E2E tests, use the default profile:
        ```bash
        mvn test
        ```

## CI/CD and Code Quality

The project is integrated with GitHub Actions for CI/CD, ensuring automated builds, tests, and deployments. The code quality is monitored with SonarCloud, achieving 100% code coverage and 0 technical debt.

## Conclusion

This project demonstrates best practices in software development, combining a solid architectural foundation with rigorous testing and automated deployment strategies. It serves as a reliable, scalable, and maintainable solution for enterprise-level applications.
