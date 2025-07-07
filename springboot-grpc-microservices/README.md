# gRPC Trading Platform - Spring Boot Microservices

A comprehensive microservices-based trading platform built with Spring Boot 3.5.0 and gRPC, demonstrating modern microservice architecture patterns, inter-service communication, and real-time data streaming.

## 🏗️ Architecture Overview

This project implements a distributed trading platform using microservices architecture with gRPC for inter-service communication. The system consists of multiple services that handle user management, stock trading, and data aggregation.

### System Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Aggregator     │    │  User Service   │    │  Stock Service  │
│  Service        │◄──►│  (Port 6565)    │    │  (Port 7575)    │
│  (Port 8080)    │    │                 │    │                 │
│  REST API       │    │  gRPC Server    │    │  gRPC Server    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                      │                      │
         │                      │                      │
         ▼                      ▼                      ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Web Interface  │    │  H2 Database    │    │  Price Updates  │
│  (Static HTML)  │    │  (In-Memory)    │    │  (Streaming)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🚀 Services

### 1. Proto Module
- **Purpose**: Shared protocol buffer definitions
- **Location**: `proto/`
- **Contains**:
    - Common data types (Ticker enum)
    - User service definitions
    - Stock service definitions
- **Generated Code**: Automatically generates Java classes for gRPC communication

### 2. User Service
- **Port**: 6565 (gRPC)
- **Database**: H2 in-memory database
- **Features**:
    - User management and authentication
    - Portfolio management
    - Stock trading operations (buy/sell)
    - Balance management
- **Key APIs**:
    - `GetUserInformation`: Retrieve user profile and holdings
    - `StockTrade`: Execute buy/sell orders

### 3. Aggregator Service
- **Port**: 8080 (HTTP REST)
- **Type**: API Gateway and aggregation layer
- **Features**:
    - REST API endpoints for web clients
    - Aggregates data from multiple microservices
    - Real-time price update subscriptions
    - Protocol buffer to JSON conversion
- **Web Interface**: Serves static HTML at `http://localhost:8080`

### 4. Stock Service
- **Port**: 7575 (gRPC)
- **Type**: Market data provider
- **Features**:
    - Real-time stock price feeds
    - Historical price data
    - Streaming price updates
- **Key APIs**:
    - `GetStockPrice`: Get current price for a ticker
    - `GetPriceUpdates`: Subscribe to real-time price streams

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Framework** | Spring Boot | 3.5.0 |
| **Language** | Java | 21 |
| **Communication** | gRPC | 1.58.0 |
| **Protocol Buffers** | Protobuf | 3.25.1 |
| **Database** | H2 Database | (In-memory) |
| **Build Tool** | Maven | Latest |
| **gRPC Spring** | grpc-spring-boot-starter | 3.1.0.RELEASE |

## 📋 Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **Git**

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd springboot-grpc-microservices
```

### 2. Build All Services
```bash
mvn clean install
```

### 3. Start Services (⚠️ **IMPORTANT**: Follow this exact order)

> **Note**: Services must be started in this specific order for the system to work properly. The aggregator service depends on the stock service, and some components depend on all services being available.

#### Step 1: Start Stock Service (FIRST)
```bash
cd stock-service
java -jar stock-service.jar
```
*Service will start on port 7575*

#### Step 2: Start Aggregator Service (SECOND)
```bash
cd aggregator-service
mvn spring-boot:run
```
*Service will start on port 8080*

#### Step 3: Start User Service (THIRD)
```bash
cd user-service
mvn spring-boot:run
```
*Service will start on port 6565*

### 4. Access the Application
- **Web Interface**: http://localhost:8080
- **REST API**: http://localhost:8080/*

## 📊 API Documentation

### Aggregator Service REST Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/user/{id}` | Get user information | `/user/1` |
| POST | `/trade` | Execute trade | `/trade` |

### Example API Requests

#### Get User Information
```bash
curl -X GET http://localhost:8080/user/1
```

#### Execute Stock Trade
```bash
curl -X POST http://localhost:8080/trade \
  -H "Content-Type: application/json" \
  -d '{
    "action": "SELL",
    "price": 10,
    "quantity": 2,
    "ticker": "APPLE",
    "user_id": 1
}'
```

## 🗄️ Database Schema

### Customer Table
```sql
CREATE TABLE customer (
    id int AUTO_INCREMENT primary key,
    name VARCHAR(50),
    balance int
);
```

### Portfolio Item Table
```sql
CREATE TABLE portfolio_item (
    id int AUTO_INCREMENT primary key,
    customer_id int,
    ticker VARCHAR(10),
    quantity int,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);
```

## 🔧 Configuration

### Service Ports
- **User Service**: 6565 (gRPC)
- **Stock Service**: 7575 (gRPC)
- **Aggregator Service**: 8080 (HTTP)

### gRPC Configuration
```properties
# User Service
grpc.server.port=6565

# Aggregator Service Client Configuration
grpc.client.user-service.address=static://localhost:6565
grpc.client.user-service.negotiationType=PLAINTEXT
grpc.client.stock-service.address=static://localhost:7575
grpc.client.stock-service.negotiationType=PLAINTEXT
```

## 🎯 Supported Stock Tickers

| Ticker | Company |
|--------|---------|
| APPLE | Apple Inc. |
| GOOGLE | Alphabet Inc. |
| AMAZON | Amazon.com Inc. |
| MICROSOFT | Microsoft Corp. |

## 🔧 Development

### Building Individual Services
```bash
# Build proto definitions
cd proto && mvn clean install

# Build user service
cd user-service && mvn clean install

# Build aggregator service  
cd aggregator-service && mvn clean install
```

### Running Tests
```bash
mvn test
```

### Protocol Buffer Compilation
Protocol buffers are automatically compiled during the Maven build process using the `protobuf-maven-plugin`.

## 🚨 Troubleshooting

### Common Issues

#### Port Already in Use
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (Windows)
taskkill /PID <process_id> /F

# Kill process (Linux/Mac)
kill -9 <process_id>
```

#### Service Dependencies
- The **aggregator service** depends on the proto module
- Build the **proto module** first before building other services
- Services can run independently but full functionality requires all services

#### gRPC Connection Issues
- Ensure all services are running on correct ports
- Check firewall settings
- Verify gRPC client configuration in `application.properties`

## 📁 Project Structure

```
springboot-grpc-microservices/
├── pom.xml                          # Parent POM
├── README.md                        # This file
├── proto/                           # Protocol Buffer definitions
│   ├── src/main/proto/
│   │   ├── common/common.proto      # Shared types
│   │   ├── user-service.proto       # User service definitions
│   │   └── stock-service.proto      # Stock service definitions
│   └── pom.xml
├── user-service/                    # User management microservice
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data.sql                 # Database initialization
│   └── pom.xml
├── aggregator-service/              # API Gateway & aggregation
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── static/index.html        # Web interface
│   └── pom.xml
└── stock-service/                   # Stock market data
    └── stock-service.jar            # Prebuilt service
```

## 🔗 Related Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [gRPC](https://grpc.io/)
- [Protocol Buffers](https://developers.google.com/protocol-buffers)
- [Maven](https://maven.apache.org/)
- [H2 Database](https://h2database.com/)

**Note**: This is a demonstration project showcasing microservices architecture with gRPC. For production use, consider adding authentication, monitoring, logging, and proper error handling.