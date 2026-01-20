# Spring Boot Modular Architecture Demo

A production-ready demonstration of **modular architecture** principles in Spring Boot, showcasing how to build scalable, maintainable applications with independent, loosely-coupled modules.

## 🎯 Overview

This project demonstrates how to structure a Spring Boot application using **Maven multi-module architecture**. It separates concerns into independent modules that communicate through well-defined interfaces and events, promoting:

- **Loose Coupling**: Modules depend on abstractions, not implementations
- **High Cohesion**: Each module has a single, clear responsibility
- **Scalability**: Modules can be developed, tested, and deployed independently
- **Maintainability**: Changes in one module don't cascade to others

## 🏗️ Architecture

### Module Dependency Graph

```
┌─────────────────┐
│  Common Module  │ ← Foundation (no dependencies)
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌────────┐  ┌─────────┐
│  User  │  │  Order  │ ← Feature modules
│ Module │◄─┤ Module  │
└────────┘  └─────────┘
    │           │
    └─────┬─────┘
          ▼
   ┌──────────────┐
   │ Application  │ ← Aggregator
   └──────────────┘
```

### Communication Patterns

1. **Interface-Based** (Synchronous)
    - Order → User: Direct API calls via `UserService` interface
    - Used for: User validation, data retrieval

2. **Event-Driven** (Asynchronous)
    - User → Order: Publishes `UserCreatedEvent`
    - Used for: Notifications, side effects

## 📁 Project Structure

```
modular-demo/
├── pom.xml                        # Parent POM
├── common-module/                 # Shared utilities
│   ├── dto/                       # API response models
│   ├── exception/                 # Global exception handling
│   └── event/                     # Cross-module events
├── user-module/                   # User management
│   ├── api/                       # Public interface (UserService)
│   ├── service/                   # Implementation (UserServiceImpl)
│   ├── controller/                # REST endpoints
│   ├── repository/                # Data access
│   └── model/                     # Domain entities
├── order-module/                  # Order management
│   ├── service/                   # Business logic
│   ├── controller/                # REST endpoints
│   ├── repository/                # Data access
│   ├── listener/                  # Event handlers
│   └── model/                     # Domain entities
└── application/                   # Main application
    ├── src/main/java/             # Entry point
    └── src/main/resources/        # Configuration (application.yml)
```

## 🔧 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/theshamkhi/ModularMonolith.git
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
cd application
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

### 4. Access H2 Console (Optional)

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave empty)*

## 🌐 API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/username/{username}` | Get user by username |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Order Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| GET | `/api/orders/user/{userId}` | Get orders by user |
| POST | `/api/orders` | Create new order |
| PATCH | `/api/orders/{id}/status?status={status}` | Update order status |

## 📝 Example Requests

### Create a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "active": true
  }'
```

### Create an Order

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productName": "Laptop",
    "quantity": 2,
    "totalPrice": 2499.99,
    "status": "PENDING"
  }'
```

### Get User Orders

```bash
curl http://localhost:8080/api/orders/user/1
```

## ✨ Key Features

### 1. **Modular Design**
- Each module is an independent Maven project
- Clear separation of concerns
- Minimal inter-module dependencies

### 2. **Loose Coupling**
- Modules communicate via interfaces
- Order module uses `UserService` interface, not implementation
- Easy to swap implementations or add new modules

### 3. **Event-Driven Architecture**
- Asynchronous communication via Spring Events
- `UserCreatedEvent` demonstrates decoupled notifications
- Multiple listeners can react to same event

### 4. **Global Exception Handling**
- Centralized error handling in Common module
- Consistent API responses across all modules
- Custom exceptions (`ResourceNotFoundException`)

### 5. **DTOs for Data Transfer**
- Separate DTOs from entities
- Clean API contracts
- Version-safe data exchange

## 🎓 Architecture Principles

### Dependency Rules

✅ **Allowed:**
- User → Common (uses shared utilities)
- Order → Common (uses shared utilities)
- Order → User **Interface** (loose coupling)
- Application → All modules (aggregator)

❌ **Forbidden:**
- Common → User/Order (foundation can't depend on features)
- User → Order (would create circular dependency)
- Order → User **Implementation** (tight coupling)

### Design Patterns Used

1. **Dependency Inversion Principle (DIP)**
    - High-level modules depend on abstractions
    - `OrderService` depends on `UserService` interface

2. **Repository Pattern**
    - Data access abstraction
    - Spring Data JPA repositories

3. **Observer Pattern**
    - Event publishing and listening
    - `UserCreatedEvent` → `UserEventListener`

4. **DTO Pattern**
    - Separation of domain models and API contracts

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Run Tests for Specific Module

```bash
cd user-module
mvn test
```

```

## 📦 Building for Production

### Create Executable JAR

```bash
mvn clean package
```

The executable JAR will be in `application/target/application-1.0.0.jar`

### Run the JAR

```bash
java -jar application/target/application-1.0.0.jar
```

## 🛠️ Technology Stack

- **Spring Boot 3.2.0**
- **Spring Data JPA** (Data access)
- **H2 Database** (In-memory database)
- **Lombok** (Boilerplate reduction)
- **Maven** (Build tool)
- **Java 17** (LTS version)

## 📚 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Multi-Module Projects](https://maven.apache.org/guides/mini/guide-multiple-modules.html)
- [Domain-Driven Design](https://martinfowler.com/tags/domain%20driven%20design.html)
- [Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle)
- 
---

⭐ **Star this repository** if you found it helpful!