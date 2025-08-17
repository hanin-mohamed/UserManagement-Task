
# User Management API (Spring Boot 3)

This project is a **RESTful API** for managing users, with **JWT authentication**, **DTO + MapStruct** for data transformation, **validation** for input checks, and **Swagger** for API documentation. It follows a **clean architecture** pattern , **H2** in development.

## Project Description

The **User Management API** allows for:
- **CRUD operations** for user records.
- **Authentication** using JWT with **BCrypt** encryption for secure login and registration.
- **Role-based access control** using JWT tokens.
- API documentation via **Swagger** for easy testing and integration.
- **DTOs** for clean data exchange between client and server, with **MapStruct** used for fast transformation between **Entity** and **DTO**.
- Validation of inputs on the DTO layer to ensure correct and safe data handling.

### Key Features
- **User Registration/Login** with JWT token generation.
- **Employee Management**: Add, Update, Delete, and View employee records with pagination and sorting.
- **JWT Authentication** for secure and stateless sessions.
- **MapStruct** for automated entity-to-DTO transformations.
- **API Documentation** using **Swagger/OpenAPI**.
- **Unit Testing** for core functionality, ensuring the reliability of the API.

---

## Technologies Stack

- **Backend**: 
  - Java 17
  - Spring Boot 3.x
  - Spring Security for JWT authentication
  - MapStruct for DTO mapping
  - H2 Database (Dev)
  - Swagger for API documentation

- **Development Tools**:
  - Maven for build management
  - Lombok for reducing boilerplate code
  - JPA (Hibernate) for ORM
  - Spring Data JPA for database interactions

- **Security**:
  - BCrypt for password hashing
  - JWT for secure stateless authentication

- **API Documentation**:
  - Springdoc OpenAPI for Swagger UI
---


## API Documentation (Swagger)

- **Swagger UI**: `/swagger-ui/index.html`
- **OpenAPI JSON**: `/v3/api-docs`


---

## Security

- **JWT**: Signed with **HS256** and contains `sub=username` and `roles`.
- **BCrypt**: Passwords are hashed and stored securely.
- **Stateless Authentication**: No sessions stored on the server.
- **CORS**: Configurable through environment variables.

---

## Best Practices

- **DTO + MapStruct**: Used for separating the Entity layer from API responses and ensuring efficient data transformation.
- **NullValuePropertyMappingStrategy.IGNORE**: Prevents `null` from overwriting existing data during updates.
- **Swagger/OpenAPI**: Auto-generates API documentation for easy testing and integration.
- **Error Handling**: Consistent error responses with clear messages.

---

**Build & Run**

```bash
git clone https://github.com/hanin-mohamed/UserManagement-Task.git
cd UserManagement-Task
mvn clean package -DskipTests
mvn spring-boot:run
```

**Swagger**

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
