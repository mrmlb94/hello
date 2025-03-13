# Java Web Application - User Management and Quiz System

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=bugs)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mrmlb94_hello&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mrmlb94_hello)
[![Java CI with Maven](https://github.com/mrmlb94/hello/actions/workflows/maven.yml/badge.svg)](https://github.com/mrmlb94/hello/actions/workflows/maven.yml)
[![codecov](https://codecov.io/github/mrmlb94/hello/branch/main/graph/badge.svg?token=UZPZ0UC5J3)](https://codecov.io/github/mrmlb94/hello)

## Introduction

This project is a Java web application that provides user management and a quiz system. It enables users to register, log in, participate in quizzes, and track their results. Built with **Spring Boot**, it follows modern software development practices including **Test-Driven Development (TDD), CI/CD automation, and SonarQube integration** to ensure maintainability and quality.

## Technologies and Frameworks

- **Spring Boot (v3.2.4)** – RESTful API development
- **Spring Data JPA** – Database interaction using PostgreSQL
- **Thymeleaf** – Java-based template engine for frontend
- **JUnit 5 & Mockito** – Unit testing
- **Selenium WebDriver** – End-to-end (E2E) testing
- **Testcontainers** – Database-driven integration testing
- **SonarQube Cloud** – Static Code Analysis & Technical Debt Monitoring
- **Docker & Docker Compose** – Containerized deployment
- **GitHub Actions (CI/CD)** – Automated testing & deployment

## Running the Application

### Prerequisites

- **Java 17 or higher**
- **Maven**
- **Docker & Docker Compose**

### Steps to Run

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/mrmlb94/hello
    cd hello
    ```

2. **Run the Application using Docker**:
    ```bash
    docker-compose up --build
    ```

3. **Access the Application**:
    - Open a web browser and navigate to `http://localhost:8081`

## Testing

- **Run unit tests**:
    ```bash
    mvn test
    ```
- **Run integration tests**:
    ```bash
    mvn verify
    ```

## CI/CD and Code Quality

The project integrates **GitHub Actions** for CI/CD, ensuring automated builds, tests, and deployments. **SonarQube Cloud** enforces static code analysis, achieving **100% code coverage and 0 technical debt**.

## Conclusion

This project is built with scalability, maintainability, and security in mind. It leverages best practices in software development and is **fully CI/CD-ready, Dockerized, and rigorously tested** for production deployment.

