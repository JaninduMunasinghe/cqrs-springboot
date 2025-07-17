# 🚀 CQRS Product Service (Spring Boot + Axon Framework)

A comprehensive beginner friendly **CQRS (Command Query Responsibility Segregation)** implementation built with **Spring Boot**, **Axon Framework**, and **H2 Database**. This project demonstrates how to implement **separated read/write models** using **commands, events, and queries** in a monolithic Java application.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Axon Framework](https://img.shields.io/badge/Axon%20Framework-4.11-blue.svg)](https://axoniq.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📁 Project Structure

```
janindumunasinghe-cqrs-springboot/
├── src/main/java/com/janindu/cqrs/
│   ├── command/                    # Write side (Commands, Aggregates, Events, Controller)
│   │   ├── controller/            # REST controllers for commands
│   │   ├── aggregate/             # Domain aggregates
│   │   ├── commands/              # Command objects
│   │   └── events/                # Event objects
│   ├── query/                     # Read side (Queries, Projection, Controller)
│   │   ├── controller/            # REST controllers for queries
│   │   ├── projection/            # Event handlers for read models
│   │   └── queries/               # Query objects
│   ├── model/                     # Shared models for REST communication
│   ├── data/                      # JPA entities and repository
│   ├── exception/                 # Custom exception handling
│   └── CqrsApplication.java       # Spring Boot main entry point
├── src/main/resources/
│   ├── application.properties     # Application configuration
│   └── data.sql                   # Sample data (optional)
├── pom.xml                        # Maven dependencies
└── README.md                      # This file
```

---

## ⚙️ Technologies Used

- **Java 17** - Programming language
- **Spring Boot 3.x** - Application framework
- **Axon Framework 4.11** - CQRS and Event Sourcing framework
- **H2 Database** - Embedded database for development
- **Maven** - Build tool and dependency management
- **Lombok** - Boilerplate code reduction
- **JPA/Hibernate** - Data persistence layer

---

## ✅ CQRS Architecture Explained

### 🔹 Command Side (Write Operations)
- **Endpoint**: `POST /products`
- **Flow**:
  1. REST Controller receives HTTP request
  2. Uses `CommandGateway` to send a `CreateProductCommand`
  3. Command is handled by a **Product Aggregate** (`@CommandHandler`)
  4. Aggregate validates business rules and applies a `ProductCreatedEvent`
  5. Event is stored in the event store

### 🔹 Query Side (Read Operations)
- **Endpoint**: `GET /products`
- **Flow**:
  1. REST Controller receives HTTP request
  2. Uses `QueryGateway` to send a `GetProductsQuery`
  3. Query is handled by a **Projection** (`@QueryHandler`)
  4. Projection fetches data from the H2 database
  5. Returns the product list as JSON response

### 🔹 Event Handling
- **Event Sourcing Handlers** (`@EventSourcingHandler`) update aggregate state
- **Event Handlers** (`@EventHandler`) in projections update read models
- Events bridge the command and query sides

---

## 🚀 How to Run This Project

### 📌 Prerequisites
- **Java 17+** installed
- **Maven 3.6+** installed
- **Git** for cloning the repository

### ▶️ Steps to Run

```bash
# Clone the repository
git clone https://github.com/<your-username>/janindumunasinghe-cqrs-springboot.git
cd janindumunasinghe-cqrs-springboot

# Build the project
./mvnw clean compile

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8081`

### 🌐 Access Points

- **Application**: http://localhost:8081
- **H2 Console**: http://localhost:8081/h2-console
  - **JDBC URL**: `jdbc:h2:file:~/data/productDB`
  - **Username**: `sa`
  - **Password**: `password`

---

## 🔧 API Endpoints & Examples

### ➕ Create Product

```http
POST /products
Content-Type: application/json

{
  "name": "Gaming Mouse",
  "price": 59.99,
  "quantity": 15
}
```

**Response:**
```json
{
  "message": "Product created successfully",
  "productId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 📦 Get All Products

```http
GET /products
```

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Gaming Mouse",
    "price": 59.99,
    "quantity": 15,
    "createdAt": "2025-01-15T10:30:00Z"
  }
]
```

### 📊 Get Product by ID

```http
GET /products/{productId}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Gaming Mouse",
  "price": 59.99,
  "quantity": 15,
  "createdAt": "2025-01-15T10:30:00Z"
}
```

---

## 🏗️ Key Components

### Command Side Components

```java
@RestController
@RequestMapping("/products")
public class ProductCommandController {
    
    @Autowired
    private CommandGateway commandGateway;
    
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest request) {
        // Send command via CommandGateway
    }
}
```

### Query Side Components

```java
@RestController
@RequestMapping("/products")
public class ProductQueryController {
    
    @Autowired
    private QueryGateway queryGateway;
    
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        // Send query via QueryGateway
    }
}
```

### Aggregate Example

```java
@Aggregate
public class ProductAggregate {
    
    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        // Handle command and apply event
    }
    
    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        // Update aggregate state
    }
}
```

---

## 📚 Learning Goals

- ✅ Understand how **CQRS separates read and write logic**
- ✅ Use **Axon's Command and Query Gateway** to decouple API and business logic
- ✅ Apply **event-driven design** with `@CommandHandler`, `@EventSourcingHandler`, and `@QueryHandler`
- ✅ Store state using **JPA** and project it into a read model
- ✅ Implement **domain aggregates** for business logic encapsulation
- ✅ Handle **cross-cutting concerns** like validation and exception handling

---

## 🛠️ Configuration

### Application Properties

```properties
# Server configuration
server.port=8081

# H2 Database configuration
spring.datasource.url=jdbc:h2:file:~/data/productDB
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Axon configuration
axon.eventhandling.processors.name.mode=tracking
```

---

## 📈 Future Improvements

- [ ] **Event Sourcing** with Axon's Event Store
- [ ] **Product update/delete commands** and events
- [ ] **MongoDB/Redis** for separate read model storage
- [ ] **Microservices architecture** (Product Command Service & Query Service)
- [ ] **Swagger/OpenAPI** documentation
- [ ] **Docker containerization** with docker-compose
- [ ] **Security** with Spring Security
- [ ] **Caching** with Redis for query optimization
- [ ] **Monitoring** with Actuator and Micrometer
- [ ] **Event replay** and **snapshots** for performance
- [ ] **SAGA pattern** for distributed transactions

---


## 🙋‍♂️ Author

**Janindu Munasinghe**

- GitHub: [@janindumunasinghe](https://github.com/JaninduMunasinghe)
- LinkedIn: [Janindu Munasinghe](https://www.linkedin.com/in/janindu-munasinghe/)

---

## ⭐ Support

If this project helped you learn CQRS with Spring Boot & Axon Framework, please consider giving it a ⭐!

---

## 📚 Additional Resources

- [Axon Framework Documentation](https://docs.axoniq.io/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [CQRS Pattern Explanation](https://martinfowler.com/bliki/CQRS.html)
- [Event Sourcing Pattern](https://martinfowler.com/eaaDev/EventSourcing.html)
