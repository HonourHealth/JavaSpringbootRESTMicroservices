# JavaSpringbootRESTMicroservices

A collection of Java Spring Boot REST Microservice projects demonstrating various architectural patterns, technologies, and best practices for building modern, scalable, resilient microservice and web applications.

## Project Summaries

### springboot-microservices
A comprehensive microservices-based architecture that consists of multiple services showcasing:
- Service Registry (Eureka)
- Department, Employee, and Organization microservices
- API Gateway for routing
- Config Server for centralized configuration
- Resilience4j for circuit breaking and retry mechanisms
- Distributed tracing with Micrometer and Zipkin

### springboot-kafka-microservices
A microservices architecture using Kafka for event-driven communication between services:
- Order Service: Creates orders and publishes events
- Stock Service: Manages inventory based on order events
- Email Service: Handles notifications based on order events
The system processes orders through multiple services, showcasing asynchronous communication patterns.

### springboot-rabbitmq-microservices
A project demonstrating microservices communication using RabbitMQ message broker, implementing asynchronous messaging patterns between services.

### springboot-kafka-wikimedia-project
A real-time data processing application with two modules:
- kafka-producer-wikimedia: Streams real-time changes from Wikimedia's EventStreams API
- kafka-consumer-database: Persists Kafka messages to MySQL database

### springboot-fullstack-employee-management-system
A full-stack application with Spring Boot backend and frontend for employee management, featuring CRUD operations, RESTful API design, and MySQL database integration.

### springboot-restful-webservices
A backend-focused project implementing RESTful web services with Spring Boot, featuring Mockito and JUnit for comprehensive unit testing.

### springboot-rabbitmq-tutorial
A tutorial project covering RabbitMQ integration with Spring Boot, including exchanges, queues, bindings, and message handling patterns.

### springboot-kafka-tutorial
A basic tutorial project demonstrating Kafka integration with Spring Boot, including producer and consumer implementations with essential configurations.

### banking-app
A Spring Boot application demonstrating core banking functionalities with RESTful APIs, including account management, transactions, and basic banking operations.

## Getting Started

Each project contains its own README with detailed setup instructions. Navigate to the specific project directory for more information on running and configuring each application.