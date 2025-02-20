# Spring Boot Kafka Microservices Project

## Overview
This project demonstrates a microservices architecture using Spring Boot and Apache Kafka for event-driven communication. The system processes orders through multiple services, showcasing asynchronous communication patterns.

## Architecture
The project consists of the following components:
- **Order Service**: Handles order creation and publishes events to Kafka
- **Stock Service**: Consumes order events and manages inventory
- **Email Service**: Consumes order events and handles email notifications
- **Base Domains**: Shared DTOs between services
- **Kafka**: Message broker for asynchronous communication

## Prerequisites
- Java 21 or higher
- Maven
- Apache Kafka

## Initial Setup
### 1. Start Zookeeper Service
- Open a terminal in your Kafka installation directory and run:
    ```bash
    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
    ```

### 2. Start Kafka Service
- Open another terminal in your Kafka installation directory and run:
    ```bash
    .\bin\windows\kafka-server-start.bat .\config\server.properties
    ```

**Important:** Ensure both Zookeeper and Kafka services are running before starting the application.


## Technical Stack
- Spring Boot
- Apache Kafka
- Project Lombok
- SLF4J
- Spring Kafka

## Configuration

The application uses the following configurations:

1. Kafka topic configuration in KafkaTopicConfig.java
2. Topic name specified in application.properties
3. Kafka producer configuration for order events
4. Kafka consumer configurations for stock and email services

## API Endpoints
#### Order Service
```yaml
POST /api/v1/orders - Create a new order
{
  "name": "Product Name",
  "qty": 1,
  "price": 100.00
}
```

## Event Flow
1. Client submits an order through REST API
2. Order Service generates a unique UUID for the order
3. Order event is created with "PENDING" status
4. Event is published to Kafka topic
5. Stock Service consumes the event and updates inventory
6. Email Service consumes the event and sends notifications
7. Confirmation message returned to client

## Running the Application
1. Ensure Zookeeper and Kafka services are running (see Initial Setup section)
2. Build the project:
    ```bash
    mvn clean install
    ```
3. Run each service in separate terminals:
    ```bash
    cd order-service
    mvn spring-boot:run
    
    cd stock-service
    mvn spring-boot:run
    
    cd email-service
    mvn spring-boot:run
    ```

## Features
- Asynchronous order processing
- Event-driven architecture
- Microservices communication via Kafka
- UUID-based order tracking
- Stock management (to do)
- Email notifications (to do)
- Logging with SLF4J