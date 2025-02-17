# Spring Boot Kafka Wikimedia Project

A robust real-time data streaming application that captures and processes Wikimedia recent changes using Apache Kafka
and Spring Boot. This project demonstrates the implementation of a distributed streaming architecture with
producer-consumer pattern.

## Architecture Overview

The project consists of two main modules:

- **kafka-producer-wikimedia**: Streams real-time changes from Wikimedia's EventStreams API and publishes them to a
  Kafka topic
- **kafka-consumer-database**: Consumes messages from the Kafka topic and persists them to a MySQL database

## Technologies

- Java 21
- Spring Boot
- Apache Kafka
- MySQL Database
- Spring Data JPA
- Maven
- Wikimedia EventStreams API

## Prerequisites

Before running this application, ensure you have the following installed:

- Java Development Kit (JDK) 21
- Apache Kafka (with Zookeeper)
- MySQL Server
- Apache Maven
- Git

## Installation & Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd spring-boot-kafka-wikimedia
   ```

2. Configure MySQL Database:
   - Open kafka-consumer-database/src/main/resources/application.properties
   - Update the following properties with your MySQL credentials:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Configure Kafka (if needed):
   - Review Kafka settings in both modules' application.properties files
   - Update broker addresses and topic names if necessary

4. Build the project:
   ```bash
   mvn clean install
   ```

## Running the Application

1. Start Kafka and Zookeeper services

   - inside kafka folder run the following:

   ```bash
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   ``` 
   ```bash
   .\bin\windows\kafka-server-start.bat .\config\server.properties
   ```

2. Launch the Producer

3. Launch the Consumer

## Key Features

- Real-time data streaming from Wikimedia changes
- Asynchronous event processing using BackgroundEventSource
- Scalable message processing with Apache Kafka
- Persistent storage using Spring Data JPA
- Spring Boot's auto-configuration for simplified setup