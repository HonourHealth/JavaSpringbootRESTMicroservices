# Employee Management System

A full-stack web application for managing employees and departments, built with Spring Boot backend and React frontend.

## Project Overview

This Employee Management System provides a comprehensive solution for organizations to manage their employee and department records. The application features a RESTful API built with Spring Boot on the backend and a responsive user interface developed with React and Vite.

## Features

- Employee CRUD operations (Create, Read, Update, Delete)
- Department CRUD operations
- Department-Employee relationship management
- Responsive design with Bootstrap
- RESTful API architecture
- Data persistence with MySQL database
- Modern React frontend with Vite for fast development
- Comprehensive unit tests with 100% code coverage

## Tech Stack

### Backend
- Java Spring Boot
- Spring Data JPA for data persistence
- MySQL Database
- RESTful API design
- Mockito for mocking in tests
- JUnit 5 for unit testing

### Frontend
- React 19
- React Router v7 for navigation
- Vite for build tooling
- Axios for API communication
- Bootstrap 5 for styling
- React Testing Library and Jest for testing
- ES6+ JavaScript

## Project Structure

```
springboot-fullstack-employee-management-system/
├── src/                               # Backend source code
│   ├── main/
│   │   ├── java/                      # Java source files
│   │   │   └── com/example/ems/       # Main package
│   │   │       ├── controller/        # REST controllers
│   │   │       ├── model/             # Entity classes
│   │   │       ├── repository/        # Data repositories
│   │   │       ├── service/           # Business logic
│   │   │       └── exception/         # Custom exceptions
│   │   └── resources/                 # Configuration files
│   └── test/                          # Backend tests
│       └── java/                      # Test source files
├── ems-front-end/                     # Frontend source code
│   ├── src/
│   │   ├── components/                # React components
│   │   │   ├── DepartmentComponent.jsx    # Department form
│   │   │   ├── EmployeeComponent.jsx      # Employee form
│   │   │   ├── ListDepartmentComponent.jsx # Department list
│   │   │   ├── ListEmployeeComponent.jsx   # Employee list
│   │   │   ├── HeaderComponent.jsx         # Header/navigation
│   │   │   └── FooterComponent.jsx         # Footer
│   │   ├── services/                  # API service calls
│   │   │   ├── DepartmentService.js   # Department API functions
│   │   │   └── EmployeeService.js     # Employee API functions
│   │   ├── App.jsx                    # Main component
│   │   └── main.jsx                   # Entry point
│   ├── tests/                         # Frontend tests
│   │   ├── components/                # Component tests
│   │   └── services/                  # Service tests
│   ├── package.json                   # NPM dependencies
│   └── vite.config.js                 # Vite configuration
└── pom.xml                            # Maven build file
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Node.js 16+ and npm
- MySQL 8+
- Maven 3.8+ (or use the included Maven wrapper)

### Backend Setup

1. Clone the repository

2. Configure the database connection in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ems
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```

3. Create the MySQL database
   ```sql
   CREATE DATABASE ems;
   ```

4. Build and run the Spring Boot application
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend will start on http://localhost:8080

### Frontend Setup

1. Navigate to the frontend directory
   ```bash
   cd ems-front-end
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

## API Documentation

The backend provides the following RESTful endpoints:

### Employee Endpoints
- `GET /api/employees` - Retrieve all employees
- `GET /api/employees/{id}` - Retrieve a specific employee
- `POST /api/employees` - Create a new employee
- `PUT /api/employees/{id}` - Update an existing employee
- `DELETE /api/employees/{id}` - Delete an employee

### Department Endpoints
- `GET /api/departments` - Retrieve all departments
- `GET /api/departments/{id}` - Retrieve a specific department
- `POST /api/departments` - Create a new department
- `PUT /api/departments/{id}` - Update an existing department
- `DELETE /api/departments/{id}` - Delete a department

## Unit Testing

This project includes comprehensive unit tests for both the backend (Spring Boot) and frontend (React) components, with a goal of 100% code coverage.

### Backend Testing (Spring Boot)

The backend uses JUnit 5 and Mockito for unit and integration testing.

#### Running Backend Tests

**Using Maven:**

To run the backend tests from the command line:

```bash
./mvnw test
```

For generating a test coverage report:

```bash
./mvnw test jacoco:report
```

The coverage report will be available in `target/site/jacoco/index.html`.

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

The coverage report will be displayed in the console and saved to the `coverage` directory.