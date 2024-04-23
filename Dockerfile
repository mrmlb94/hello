# Use an official OpenJDK image with a matching Java version
FROM openjdk:21-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests


# Specify the entry point
ENTRYPOINT ["java","-jar","target/hello-0.0.1-SNAPSHOT.jar"]
