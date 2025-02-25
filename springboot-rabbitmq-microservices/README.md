# Spring Boot RabbitMQ Microservices Project

This repository contains multiple Spring Boot services that communicate asynchronously via RabbitMQ. Each service is independently deployable and has its own codebase. Below is an overview of the modules and instructions on how to set up and run the project locally.

---

## Table of Contents
1. [Project Structure](#project-structure)
2. [Prerequisites](#prerequisites)
3. [Services Overview](#services-overview)
    - [Stock Service](#stock-service)
    - [Order Service](#order-service)
    - [Email Service](#email-service)
4. [Build and Run](#build-and-run)
5. [Configuration](#configuration)
6. [Additional Notes](#additional-notes)

---

## Project Structure

- **stock-service/**
    - Contains logic for handling product stock updates and inventory checks.
    - Uses Spring Boot, AMQP libraries, and a REST controller for any stock-related endpoints.

- **order-service/**
    - Responsible for creating and managing orders, including any communication with RabbitMQ to publish or consume messages about new orders.
    - Uses Spring Boot, AMQP libraries, and REST controllers.

- **email-service/**
    - Sends out transactional emails after certain events, like order placement or changes in stock.
    - Subscribes to specific RabbitMQ queues for email notifications.
    - Also uses Spring Boot and AMQP.

- **pom.xml** (in each microservice)
    - Each service has its own Maven configuration referencing Spring Boot dependencies, Lombok, the RabbitMQ starter, and relevant test libraries.

---

## Prerequisites

- **Java 21**  
  Each microservice requires Java 21 (as configured in the individual `pom.xml` files).

- **Maven**  
  Used to build and manage dependencies for each Spring Boot service.

- **RabbitMQ**  
  You need an instance of RabbitMQ running. By default, each microservice expects RabbitMQ to be accessible locally on the default port. Adjusts settings if needed.

---

## Services Overview

### Stock Service
- Path: `stock-service/`
- Main class: `com.example.stockservice.StockServiceApplication`
- Responsibilities: Inventory management, stock reservations, and confirmations. Publishes or consumes messages when stock is updated.

### Order Service
- Path: `order-service/`
- Main class: `com.example.orderservice.OrderServiceApplication` (typical package name shown as an example)
- Responsibilities: Creating new orders, orchestrating shipping or handling subsequent status changes, and sending events to RabbitMQ about order statuses.

### Email Service
- Path: `email-service/`
- Main class: `com.example.emailservice.EmailServiceApplication` (typical package name shown as an example)
- Responsibilities: Subscribes to relevant RabbitMQ queues and sends out email notifications.

---

## Build and Run

1. **Clone the Repository**  
   Clone the project and navigate to its root folder

2. **Start RabbitMQ**  
   Ensure RabbitMQ is running locally or adjust the broker’s host/port in each application’s configuration.
    ```bash
    docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:4.0.6-management
    ```
3. **Run Each Service**  
   You can start each service by navigating to the individual service folder and running:
   By default, each service should start on a different port, e.g.,
    - Stock Service: port 8081
    - Order Service: port 8082
    - Email Service: port 8083
---

## Configuration

- **Application Properties**  
  Each service can have its own `application.properties` or `application.yml` under `src/main/resources/`.

- **Exchanges, Queues, and Routing**  
  Within each service, you will find bean definitions for queues and exchanges. These are typically declared in a `@Configuration` class. Make sure they are aligned across services (e.g., the queue name for stock updates in Stock Service should match the subscription queue in Order or Email Service if they consume those messages).

---

## Additional Notes

- **Lombok**  
  Lombok is included as an optional dependency in each microservice. Ensure your IDE has Lombok support enabled.

- **CI/CD**  
  If you plan to use a CI system, ensure it can handle multi-module Maven builds. You can extend the build stages in your pipeline to build and test each microservice individually.