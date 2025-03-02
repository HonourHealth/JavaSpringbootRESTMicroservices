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

## Tech Stack

### Backend
- Java Spring Boot
- Spring Data JPA
- MySQL Database
- RESTful API design

### Frontend
- React
- Vite
- Modern JavaScript (ES6+)
- CSS for styling

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