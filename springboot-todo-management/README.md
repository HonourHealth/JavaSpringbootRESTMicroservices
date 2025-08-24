# Todo Management Application

A full-stack Todo Management application built with Spring Boot (backend) and React (frontend). This application provides user authentication, role-based authorization, and CRUD operations for managing todos.

## 🏗️ Architecture Overview

- **Backend**: Spring Boot 3.4.4 with Spring Security, JWT authentication, and MySQL database
- **Frontend**: React with Vite, Bootstrap, and Axios for API communication
- **Database**: MySQL for data persistence
- **Authentication**: JWT (JSON Web Token) based authentication
- **Authorization**: Role-based access control (ADMIN/USER roles)

## 📋 Features

### Authentication & Authorization
- User registration and login
- JWT token-based authentication
- Role-based access control (ADMIN/USER)
- Secure password encoding with BCrypt
- CORS configuration for cross-origin requests

### Todo Management
- Create, read, update, and delete todos
- Mark todos as complete/incomplete
- Role-based permissions:
  - **ADMIN**: Full CRUD operations
  - **USER**: Read and update completion status only

### Frontend Features
- Responsive UI with Bootstrap
- Protected routes with authentication guards
- Real-time token validation
- User-friendly error handling
- Dynamic navigation based on authentication status

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.4.4
- **Security**: Spring Security with JWT
- **Database**: MySQL with Spring Data JPA
- **ORM**: Hibernate
- **Build Tool**: Maven
- **Java Version**: 21
- **Dependencies**:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - MySQL Connector
  - JWT (io.jsonwebtoken)
  - ModelMapper
  - Lombok

### Frontend
- **Framework**: React 19.1.0
- **Build Tool**: Vite
- **Styling**: Bootstrap 5.3.7
- **HTTP Client**: Axios 1.10.0
- **Routing**: React Router DOM 7.7.0
- **Development**: ESLint for code quality

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Node.js 16+ and npm
- MySQL 8.0+
- Maven 3.6+

### Database Setup

1. **Create MySQL Database**:
   ```sql
   CREATE DATABASE todo_management;
   ```
   **Note**: While Hibernate automatically creates all tables and relationships, you must manually create the database itself.

2. **Configure Database Connection**:
   Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/todo_management
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Automatic Schema Creation**:
   With `spring.jpa.hibernate.ddl-auto=update`, Hibernate will automatically:
   - Create all tables (`users`, `todos`, `roles`, `user_roles`)
   - Establish foreign key relationships
   - Update schema when entities change

4. **Initialize Roles** (Required - Manual Step):
   The application requires roles to be present in the database:
   
   **Insert manually after first run**:
   ```sql
   INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
   INSERT INTO roles (name) VALUES ('ROLE_USER');
   ```
### Backend Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd springboot-todo-management
   ```

2. **Install dependencies and run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **The backend will start on**: `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd todo-ui
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start development server**:
   ```bash
   npm run dev
   ```

4. **The frontend will start on**: `http://localhost:3000`

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "name1",
  "username": "username1",
  "email": "email1@example.com",
  "password": "password1"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "usernameOrEmail": "admin",
  "password": "admin"
}
```

**Response**:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "role": "ROLE_USER"
}
```

### Todo Endpoints

All todo endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Get All Todos
```http
GET /api/todos
```

#### Get Todo by ID
```http
GET /api/todos/{id}
```

#### Create Todo (ADMIN only)
```http
POST /api/todos
Content-Type: application/json

{
  "title": "Learn Spring Boot",
  "description": "Complete the Spring Boot tutorial",
  "completed": false
}
```

#### Update Todo (ADMIN only)
```http
PUT /api/todos/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "description": "Updated description",
  "completed": true
}
```

#### Delete Todo (ADMIN only)
```http
DELETE /api/todos/{id}
```

#### Mark Todo as Complete
```http
PATCH /api/todos/{id}/complete
```

#### Mark Todo as Incomplete
```http
PATCH /api/todos/{id}/incomplete
```

## 🏛️ Project Structure

### Backend Structure
```
src/main/java/com/example/springboot_todo_management/
├── SpringbootTodoManagementApplication.java  # Main application class
├── config/
│   └── SpringSecurityConfig.java             # Security configuration
├── controller/
│   ├── AuthController.java                   # Authentication endpoints
│   └── TodoController.java                   # Todo CRUD endpoints
├── dto/
│   ├── JwtAuthResponse.java                  # JWT response DTO
│   ├── LoginDto.java                         # Login request DTO
│   ├── RegisterDto.java                      # Registration request DTO
│   └── TodoDto.java                          # Todo data transfer object
├── entity/
│   ├── Role.java                             # Role entity
│   ├── Todo.java                             # Todo entity
│   └── User.java                             # User entity
├── exception/
│   ├── ErrorDetails.java                     # Error response structure
│   ├── GlobalExceptionHandler.java           # Global exception handling
│   ├── ResourceNotFoundException.java        # Custom exception
│   └── TodoAPIException.java                 # Custom API exception
├── repository/
│   ├── RoleRepository.java                   # Role data access
│   ├── TodoRepository.java                   # Todo data access
│   └── UserRepository.java                   # User data access
├── security/
│   ├── CustomUserDetailsService.java         # User details service
│   ├── JwtAuthenticationEntryPoint.java      # JWT entry point
│   ├── JwtAuthenticationFilter.java          # JWT filter
│   └── JwtTokenProvider.java                 # JWT utility
├── service/
│   ├── AuthService.java                      # Auth service interface
│   ├── TodoService.java                      # Todo service interface
│   └── impl/
│       ├── AuthServiceImpl.java              # Auth service implementation
│       └── TodoServiceImpl.java              # Todo service implementation
└── utils/
    └── PasswordEncoderImpl.java              # Password encoding utility
```

### Frontend Structure
```
todo-ui/src/
├── App.jsx                                   # Main app component
├── App.css                                   # Global styles
├── main.jsx                                  # Application entry point
├── components/
│   ├── FooterComponent.jsx                   # Footer component
│   ├── HeaderComponent.jsx                   # Navigation header
│   ├── ListTodoComponent.jsx                 # Todo list display
│   ├── LoginComponent.jsx                    # Login form
│   ├── RegisterComponent.jsx                 # Registration form
│   └── TodoComponent.jsx                     # Todo create/edit form
└── services/
    ├── AuthService.js                        # Authentication API calls
    └── TodoService.js                        # Todo API calls
```

## 🔒 Security Features

### JWT Authentication
- Stateless authentication using JSON Web Tokens
- Token expiration: 7 days (configurable)
- Secure token generation with HMAC SHA-256

### Password Security
- BCrypt password hashing
- Strong password encoding before storage

### CORS Configuration
- Configured to allow requests from frontend origin
- Supports preflight requests
- Allows credentials for authenticated requests

### Role-Based Authorization
- Method-level security with `@PreAuthorize`
- Different access levels for ADMIN and USER roles
- Protected endpoints based on user roles

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  username VARCHAR(255) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);
```

### Roles Table
```sql
CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);
```

### Todos Table
```sql
CREATE TABLE todos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  completed BOOLEAN DEFAULT FALSE
);
```

### User-Roles Junction Table
```sql
CREATE TABLE user_roles (
  user_id BIGINT,
  role_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);
```

## 🎯 Usage Examples

### 1. Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Admin User",
    "username": "admin",
    "email": "admin@example.com",
    "password": "admin123"
  }'
```

### 2. Login and Get Token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "admin123"
  }'
```

### 3. Create a Todo (with JWT token)
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "title": "Complete project",
    "description": "Finish the todo management application",
    "completed": false
  }'
```

## 🔧 Configuration

### Backend Configuration (`application.properties`)
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/todo_management
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
app.jwt.secret=your-secret-key
app.jwt.expiration-milliseconds=604800000
```

### Frontend Configuration
The frontend is configured to connect to the backend at `http://localhost:8080`. Update the base URLs in the service files if your backend runs on a different port.

## 🚨 Troubleshooting

### Common Issues

1. **401 Unauthorized on Login**:
   - Ensure the database is running and accessible
   - Verify user credentials exist in the database
   - Check if roles are properly assigned to users

2. **CORS Errors**:
   - Verify CORS configuration in `SpringSecurityConfig.java`
   - Ensure frontend origin is allowed

3. **JWT Token Issues**:
   - Check token expiration
   - Verify token is properly included in Authorization header
   - Ensure JWT secret is consistent

4. **Database Connection Issues**:
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists
