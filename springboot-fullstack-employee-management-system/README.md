# Employee Management System

A full-stack web application for managing employee data, built with Spring Boot backend and React frontend.

## Project Overview

This Employee Management System provides a comprehensive solution for organizations to manage their employee records. The application features a RESTful API built with Spring Boot on the backend and a responsive user interface developed with React and Vite.

## Features

- Employee CRUD operations
- Responsive design
- RESTful API architecture
- Data persistence with MySQL database
- Modern React frontend with Vite for fast development
- Comprehensive unit tests for both the back-end and the front-end

## Tech Stack

### Backend
- Java Spring Boot
- Spring Data JPA
- MySQL Database
- RESTful API design
- Mockito
- Junit 5

### Frontend
- React
- Vite
- Modern JavaScript (ES6+)
- CSS for styling
- React Testing Library
- Jest

## Getting Started

### Prerequisites

- Java 21 or higher
- Node.js and npm
- MySQL

### Backend Setup

1. Clone the repository

2. Navigate to the directory
    ```
    cd springboot-fullstack-employee-management-system/
    ```
3. Configure the database connection in application.properties
4. Build and run the Spring Boot application


### Frontend Setup
1. Navigate to the frontend directory
    ```bash
    cd springboot-fullstack-employee-management-system/ems-front-end
    ```

2. Install dependencies
    ```bash
    npm install
    ```

3. Start the development server
    ```bash
    npm run dev
    ``` 
4. Open your browser and navigate to http://localhost:3000

### API Documentation
The backend provides the following RESTful endpoints:
- GET /api/employees - Retrieve all employees
- GET /api/employees/{id} - Retrieve a specific employee
- POST /api/employees - Create a new employee
- PUT /api/employees/{id} - Update an existing employee
- DELETE /api/employees/{id} - Delete an employee


## Unit Testing

This project includes unit tests for both the backend (Spring Boot) and frontend (React) components.

### Backend Testing (Spring Boot)

The backend uses JUnit 5 and Mockito for unit and integration testing.

#### Running Backend Tests

**Using IntelliJ IDEA (Recommended):**

1. Right-click on the test directory or specific test class in the Project panel
2. Select "Run Tests" or "Run [TestName]"
3. View test results in the Run tool window

**Using Maven:**

To run the backend tests from the command line, navigate to the backend directory and execute:

```bash
./mvnw test
```

For generating a test coverage report:

```bash
./mvnw test jacoco:report
```

### Frontend Testing (React)

The frontend uses Jest and React Testing Library for unit and component testing.

#### Running Frontend Tests
To run the frontend tests, navigate to the frontend directory and execute:
```bash
npm test
```

For generating a test coverage report:
```bash
npm run test:coverage
```

The coverage report will be displayed in the console and saved to the coverage directory.
