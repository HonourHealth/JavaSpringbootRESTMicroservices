# **Microservices Architecture with Spring Boot, Spring Cloud, and Eureka**
## **Overview**
This project is a **microservices-based architecture** that consists of multiple services, each with a clearly defined responsibility. It takes full advantage of **Spring Cloud**, **Eureka**, **Resilience4j**, and **Spring Cloud Gateway** for routing and scaling.
The architecture includes the following services:

- **Service Registry**: Eureka Server for service discovery.
- **Department Service**: A service to manage departments.
- **Employee Service**: A service to manage employees, integrated with Department and Organization services.
- **Organization Service**: A service to manage organizations.
- **API Gateway**: A gateway to route requests to appropriate microservices.
- **Config Server**: A centralized configuration server.

The architecture demonstrates:
- Service-to-service communication using Feign clients.
- Circuit breaker and retry mechanisms using Resilience4j.
- Externalized configuration and dynamic refresh using Spring Cloud Config with Spring Cloud Bus and RabbitMQ.
- Service discovery using Eureka.
- API Gateway request routing.
- Tracing and monitoring using Micrometer and Zipkin.
- Persistence with Spring Data JPA and MySQL.

## **Modules**
### **1. Config Server**
- **Purpose**: Centralized configuration management for all microservices. Retrieves configuration from a remote Git repository.
- **Tech Stack**: Spring Cloud Config Server, Eureka Client
- **Default Port**: `8888`
- **Configuration Remote URL**: 
    - Retrieves configurations from a remote Git repository. 
    - Dynamic refresh using Spring Cloud Bus (RabbitMQ).
    - Simplifies environment-based configuration management.

``` properties
  spring.cloud.config.server.git.uri=https://github.com/HonourHealth/config-server-repo
  spring.cloud.config.server.git.default-label=main
```
### **2. Service Registry**
- **Purpose**: Implement service discovery using Eureka Server. All microservices register themselves with this service.
- **Dependencies**:
    - `spring-cloud-starter-netflix-eureka-server`
- **Default Port**: `8761`
### **3. Department Service**
- **Purpose**: CRUD operations for managing departments in the system.
- **Features**:
    - Create and retrieve departments.
    - Fetch department details via department code using HTTP requests.
- **Dependencies**:
    - Spring Data JPA (MySQL Persistence)
    - Feign Client integration for remote calls.
    - Eureka Client.
- **Endpoints**:
    - `POST /api/departments` - Add a department.
    - `GET /api/departments/{department-code}` - Fetch department details.
- **Default Port**: `8080`

### **4. Employee Service**
- **Purpose**: CRUD operations for managing employees with integrated department and organization details.
- **Features**:
    - Employee creation and retrieval.
    - Fetch an employee by ID, with related department and organization data.
    - Uses Feign Client for inter-service communication.
    - Resilience4j for retry and circuit breaker.
- **Dependencies**:
    - Feign Client for service-to-service calls.
    - Resilience4j for handling failures.
    - Spring Data JPA for persistence.
    - Eureka Client.
- **Endpoints**:
    - `POST /api/employees` - Add an employee.
    - `GET /api/employees/{id}` - Fetch employee details by ID.
- **Default Port**: `8081`

### **5. Organization Service**
- **Purpose**: CRUD operations for managing organizations in the system.
- **Features**:
    - Create and fetch an organization using HTTP requests.
- **Dependencies**:
    - Spring Data JPA for persistence.
    - Feign integration for remote service calls.
    - Eureka Client participation.
- **Endpoints**:
    - `POST /api/organizations` - Add an organization.
    - `GET /api/organizations/{organization_code}` - Fetch organization details.
- **Default Port**: `8083`

### **6. API Gateway**
- **Purpose**: Central point for routing requests to backend microservices using Spring Cloud Gateway.
- **Features**:
    - Routing rules configured in `application.properties`.
    - Supports path predicates for Employee, Department, and Organization services.
    - Global CORS configuration.

### **Technologies Used**
- **Framework**:
    - Spring Boot (3.4.x), Spring Cloud
- **Interservice Communication**:
    - Feign Client
    - Eureka Service Discovery
- **Resilience**:
    - Resilience4j for Circuit Breaker and Retry mechanisms.
- **Service Gateway**:
    - Spring Cloud Gateway for dynamic routing.
- **Configuration Management**:
    - Spring Cloud Config Server for centralized configuration, backed by a Git repository.
- **Database**:
    - MySQL for data persistence.
- **Tracing & Monitoring**:
    - Micrometer for application metrics.
    - Zipkin for distributed tracing.
- **Containerization**:
    - Docker
- **Build Tool**:
    - Maven for dependency management and packaging.

## **Service-to-Service Communication**
The communication between services is facilitated using **OpenFeign**, which simplifies RESTful web service invocation. For example:
### Fetching Department Details in Employee Service:
``` java
@FeignClient(name = "DEPARTMENT-SERVICE")
public interface APIClientDepService {
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
}
```
### Fetching Organization Details in Employee Service:
``` java
@FeignClient(name = "ORGANIZATION-SERVICE")
public interface APIClientOrgService {
    @GetMapping("api/organizations/{organization_code}")
    OrganizationDto getOrganization(@PathVariable("organization_code") String organizationCode);
}
```
These Feign clients simplify and standardize inter-service communication while leveraging **Eureka** for dynamic service discovery.

## **Dynamic Configuration with RabbitMQ**
The project uses **Spring Cloud Bus** with **RabbitMQ** to propagate dynamic configuration changes from the **Config Server** to all microservices without manual restarts.
- **Steps**:
    1. Config Server pushes changes to RabbitMQ when values in its linked Git repository are updated.
    2. RabbitMQ broadcasts these updates to all services listening on the Spring Cloud Bus.
    3. The microservices automatically refresh their context without requiring a restart.

#### **RabbitMQ Docker Setup**:
``` bash
docker run --rm -it -p 5672:5672 rabbitmq:4.0.5
```

## **Resilience4j Integration**
The project uses Resilience4j for circuit breaker and retry functionality in service-to-service calls. Here’s an example implementation in the **EmployeeServiceImpl** class:
### Retry Mechanism:
``` java
@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
public APIResponseDto getEmployeeById(Long employeeId) {
    LOGGER.info("Fetching employee details with retry...");
    // Logic for fetching employee, department, and organization details
}
```
### Fallback Method for Circuit Breaker:
``` java
public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
    LOGGER.info("Fallback method invoked due to an error...");
    // Returning default department data
    DepartmentDto departmentDto = new DepartmentDto("Fallback Dept", "Default Dept Desc", "DEFAULT");
    // Construct APIResponseDto with fallback data
    return new APIResponseDto(employeeDto, departmentDto);
}
```
The retry and fallback configurations are defined in `application.properties` of the employee-service

## **Central Configuration with Config Server**
All services retrieve their configuration from a centralized Git repository configured in the Config Server. The repository URI is defined in the **application.properties** of the Config Server:
``` properties
spring.cloud.config.server.git.uri=https://github.com/HonourHealth/config-server-repo
spring.cloud.config.server.git.default-label=main
```
Services use the following line to fetch configuration from the Config Server:
``` properties
spring.config.import=optional:configserver:http://localhost:8888
```
This allows for externalizing configurations, making it easier to manage environments like `dev`, `qa`, and `prod`.
## **Service Discovery with Eureka**
- The **Service Registry** is implemented using Eureka.
- All services (Employee, Department, Organization, Config Server, API Gateway) register themselves with Eureka using the following configuration:
``` properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
```
Eureka helps in dynamic service discovery and load balancing.
## **API Gateway Configuration**
The **API Gateway** routes requests to respective microservices using predefined paths. Example configurations for routing:
``` properties
### Employee Service Route ###
spring.cloud.gateway.routes[0].id=EMPLOYEE-SERVICE
spring.cloud.gateway.routes[0].uri=lb://EMPLOYEE-SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/employees/**
```
``` properties
### Routes for Department Service ###
spring.cloud.gateway.routes[1].id=DEPARTMENT-SERVICE
spring.cloud.gateway.routes[1].uri=lb://DEPARTMENT-SERVICE
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/departments/**
```
``` properties
### Routes for Organization Service ###
spring.cloud.gateway.routes[2].id=ORGANIZATION-SERVICE
spring.cloud.gateway.routes[2].uri=lb://ORGANIZATION-SERVICE
spring.cloud.gateway.routes[2].predicates[0]=Path=/api/organizations/**
```
The API Gateway also enables CORS configurations globally:
``` properties
spring.cloud.gateway.globalcors.corsConfigurations.[/**].allowedOrigins=*
spring.cloud.gateway.globalcors.corsConfigurations.[/**].allowedMethods=GET,POST,PUT,DELETE
```
## **Database Schema**
### **Employee Table**

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| first_name | VARCHAR(255) |  |
| last_name | VARCHAR(255) |  |
| email | VARCHAR(255) | UNIQUE, NOT NULL |
| department_code | VARCHAR(255) |  |
| organization_code | VARCHAR(255) |  |
### **Department Table**

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| department_name | VARCHAR(255) |  |
| department_code | VARCHAR(255) |  |
| department_description | VARCHAR(255) |  |
### **Organization Table**

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | PRIMARY KEY |
| organization_name | VARCHAR(255) | NOT NULL |
| organization_description | VARCHAR(255) |  |
| organization_code | VARCHAR(255) | UNIQUE, NOT NULL |
| created_date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
## **How to Run**
### Prerequisites
- JDK 21+
- Maven installed.
- MySQL Server running (Update datasource URLs in configurations).
- RabbitMQ setup (for Spring Cloud Bus).
- Docker installed

### Steps
1. **Start the Service Registry**

2. **Start the Config Server**

3. **Run Docker** 

4. **Start the Microservices**: Start each service (Employee, Department, Organization)

5. **Start the API Gateway**

6. **Test the Application**

    - Use the following endpoints to verify that each service is working correctly:
        - Service Registry (Eureka Dashboard): `http://localhost:8761`
        - API Gateway Routes:
            - Employee Service: `http://localhost:9191/api/employees`
            - Department Service: `http://localhost:9191/api/departments`
            - Organization Service: `http://localhost:9191/api/organizations`

## **Monitoring & Tracing**
- **Micrometer Metrics**:
    - Metrics can be viewed via Actuator endpoints like:
    ``` 
    /actuator/health
    /actuator/metrics
    ```
- **Zipkin (Distributed Tracing)**:
    - Ensure Zipkin Server is running. (either run it on docker or as a jar file in terminal)
    - Trace logs will flow via Micrometer -> Zipkin.

    ``` 
    docker run --rm -it --name zipkin -p 9411:9411 openzipkin/zipkin
    ```
    or if it is installed as a jar file inside of the project folder
    ``` 
    java -jar zipkin-server-versionnumber-exec.jar
    ```

## **Back-End Testing**
Unit tests can be run through:
- Your IDE's test runner (Right-click on the test file/folder and select 'Run Tests')
- Or using Maven command

# **React Front-End**
This project is a React-based front-end application that interacts with the microservices architecture through an API Gateway. It handles employee, department, and organization management using modern UI practices.
## **Prerequisites**
Before running the front-end, ensure the following are installed:
- **Node.js** (version >= 18.x)
- **npm** (comes with Node.js)

> Note: Back-end services (microservices, API Gateway, Eureka Registry, Config Server) should be running before starting the front-end application.

## **Technology Stack**
- **Framework**: React (18.0.0)
- **HTTP Client**: Axios (1.7.9)
- **Styling**: Bootstrap (5.3.3)

## **Folder Structure Overview**
Key folders of the front-end app:
``` 
src
├── components                 # Reusable components go here
│   ├── EmployeeComponent.js   # Component specifically for Employee management
├── services                   # Service files for API integration
│   ├── employeeService.js     # (Example) Service for employee-related API calls
├── App.js                     # Main app component linking everything together
├── App.css                    # Styles specific to App.js
├── index.js                   # Entry point for the application
├── index.css                  # Global styling for the application
├── logo.svg                   # Logo file used in the project
├── reportWebVitals.js         # Performance testing file
├── setupTests.js              # Jest test setup
```
## **Configuration**
By default, the front-end communicates with the **API Gateway** running at:
``` bash
http://localhost:9191/api
```

### **API Endpoints**
The following endpoints are expected to be available from the API Gateway:
- `/api/employees`: Fetch and manage employee data
- `/api/departments`: Fetch and manage department data
- `/api/organizations`: Fetch and manage organization data

## **Key Features**
### 1. View Employee Details
- Fetch employee information.

### 2. View Department Details
- Fetch department information.

### 3. View Organization Details
- Fetch Organization information.

### 4. Integration with API Gateway
- All data flows through the centralized API Gateway for seamless service-to-service communication.

## **Styling**
### **Using Bootstrap**
The front-end UI uses Bootstrap classes for responsive design and pre-styled components.

Bootstrap is included via NPM imports:
```javascript
import "bootstrap/dist/css/bootstrap.min.css";
```
Additional custom styles can be placed in `App.css` or other related CSS files.

## **How to Run the Application**
1. **Navigate to the Front-End Project Folder**
    ``` bash
    cd react-front-end
    ```
2. **Install Dependencies**
    ``` bash
    npm install
    ```
3. **Run the Application**
    ``` bash
    npm start
    ```
4. Open the app in your browser at:
    ``` 
    http://localhost:3000
    ```

## **Front-End Testing**
Run the available front-end tests with coverage using:
```bash
npm run test:coverage
```

Coverage reports can be found at:

- coverage/lcov-report/index.html
