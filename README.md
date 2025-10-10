# Gateway Booking Information System API

### Overview
The Gateway Booking Information System (Gateway API) is a RESTful microservice designed to handle core booking logic for flights and hotels. Built on Spring Boot 3, this project was developed incrementally to showcase proficiency in modern Java and enterprise architectural patterns, including DTO-driven development, asynchronous processing, and JWT-based security.

The API allows users to manage flights, hotels, and process combined bookings, ensuring transactional integrity and high performance through concurrent payment simulation.

### Core Features & Demonstrated Architectural Concepts
| Concept | Implementation Details |
| :---: | :--- |
| Data Transfer Objects (DTOs) | Implemented DTOs for every entity (`FlightDTO`, `HotelDTO`, `BookingResponseDTO`) to decouple the API from the JPA entities. This prevents `LazyInitializationException` and enhances data security. |
| Transactional Integrity | Used `@Transactional` for methods in `FlightService` and `BookingService` to ensure availability checks (decrementing seats/rooms) and booking creation are atomic. |
| Concurrency & Asynchronicity | Used a Java `ExecutorService` (fixed thread pool) in `BookingService` to process payment simulation asynchronously, preventing API blocking and providing an immediate `PENDING` response. |
| File I/O & Logging | Implemented a utility class with basic Java I/O (`FileWriter`, `PrintWriter`) to log the outcome of the asynchronous payment processing to a `bookings.log` file. |
| JWT Security | Implemented token-based authentication using JSON Web Tokens (JWT). Includes custom `JwtUtil` and `wtRequestFilter` for token generation, validation, and setting the `SecurityContext`. |
| API Security | Configured Spring Security 6 with a stateless session policy, protecting all core endpoints (`/flights`, `/bookings`) while exposing `/users` and `/authenticate` publicly. |
| Exception Handling & Validation | Used `@ControllerAdvice` (`GlobalExceptionHandler`) to provide consistent error responses (e.g., 400 Bad Request, 404 Not Found) for business logic and bean validation (`@Valid`). |
| External Configuration | Leveraged Spring Profiles (`dev`, `prod`) and the `@Value` annotation to externalize critical settings (e.g., JWT secret key, database connection, and development tools) for robust deployment. |

### Getting Started

#### Prerequisites
- Java Development Kit (JDK) 17+

- Apache Maven

#### Build & Run
1. Clone the repository:
```bash
git clone https://github.com/samnjue/gateway
cd gateway
```

2. Build the project (downloads dependencies):
```bash
./mvnw clean install
```

3. Run the application (using the default dev profile with H2 in-memory DB):
```bash
java -jar target/gateway.jar
```

### API Usage and Testing
The API uses JWT Bearer Token authentication for all core resources. The first step is to authenticate.

#### 1. Obtain a JWT Token
Use the default in-memory credentials (`admin`/`password`) to log in.
| Step | Endpoint | Headers | Body |
| :---: | :---: | :---: | :---: |
| Login | `POST` `/api/v1/authenticate` | `Content-Type:` `application/json` | `{"username": "admin"`, `"password": "password"}` |

#### 2. Test Protected Endpoints
Use the token received from the step above in the `Authorization` header.
| Endpoint | Method | Concept Demonstrated | Notes |
| :---: | :---: | :---: | :---: |
| `/api/v1/flights` | `POST` | Validation | Requires a valid `FlightDTO` (e.g., price >0.00). |
| `/api/v1/bookings` | `POST` | Concurrency, Transactions | Returns status `PENDING` immediately. Payment processing and logging run on a background thread. |
| `/api/v1/bookings/user/1` | `GET` | DTO Decoupling (LAZY Fix) | Successfully retrieves booking details despite `FetchType.LAZY` relationships, confirming DTO implementation works. |
| `/api/v1/hotels/1` | `GET` | JWT Security | Must include `Authorization: Bearer <TOKEN>` or returns 401 Unauthorized. |

#### 3. Test Profile Activation (Deployment Simulation)
To run the application in a production-like environment (disabling the H2 console, using the `validate` DDL-mode):
```bash
java -jar target/gateway.jar --spring.profiles.active=prod
```

### Author
- Sammy Njue
- Built to hone Spring Boot skills, security, and advanced Java concurrency patterns.
