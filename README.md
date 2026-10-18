# Meeting Room Booking System

A RESTful API developed with **Java and Spring Boot** to manage meeting rooms and users in a corporate environment, with plans to implement a meeting reservation system.

## About the Project

The Meeting Room Booking System is a backend project designed to support companies that need a centralized system for managing meeting rooms and scheduling meetings.

The application allows users to register and log in, while meeting rooms can be registered and managed through API endpoints. Each room stores information such as its room number, capacity, and projector availability.

The main goal is to develop a structured REST API that handles data persistence, input validation, business rules, and error handling. The project will also include a reservation system that prevents overlapping bookings for the same room.

This project is being developed incrementally, with a focus on backend development, RESTful API design, and good programming practices.

## Technologies Used

- **Java** — Main programming language.
- **Spring Boot** — Application development and dependency management.
- **Spring Web** — REST endpoints and HTTP request handling.
- **Spring Data JPA** — Database access and persistence.
- **MySQL** — Relational database.
- **Lombok** — Reduces boilerplate code.
- **Bean Validation** — Request data validation.
- **Maven** — Dependency and project management.
- **Postman** — API endpoint testing.
- **Git and GitHub** — Version control and project hosting.


## Room Management

Each meeting room contains information such as:

- **ID:** Unique identifier generated for the room.
- **Room number:** The room's identifying number, such as 201 or 203.
- **Capacity:** Maximum number of people the room can accommodate.
- **Projector availability:** Indicates whether the room has a projector.

The API supports creating rooms, searching for them by room number, and deleting a room by providing its ID and room number.

## API Endpoints

Base URL (local development):

`http://localhost:8080`

### Users

| Method | Endpoint | Description |
|---|---|---|
| POST | `/users/create` | Register a new user |
| POST | `/users/login` | Authenticate a user |
| DELETE | `/users/delete` | Delete a user using its ID |

### Meeting Rooms

| Method | Endpoint | Description |
|---|---|---|
| POST | `/rooms/create` | Register a meeting room |
| GET | `/rooms/search` | Search for a room by room number |
| DELETE | `/rooms/delete` | Delete a room using its ID and room number |

**Note:** The endpoints and request formats may evolve as the project develops.

## Example Requests

### Create a User

`POST /users/create`

```json
{
  "name": "Caio Feitosa",
  "email": "caiofeitosa@example.com",
  "password": "your_password"
}
```

### Log In

`POST /users/login`

```json
{
  "email": "caiofeitosa@example.com",
  "password": "your_password"
}
```

### Create a Meeting Room

`POST /rooms/create`

```json
{
  "roomNumber": 201,
  "capacity": 10,
  "hasProjector": true
}
```

### Delete a Meeting Room

`DELETE /rooms/delete`

```json
{
  "id": 2,
  "roomNumber": 201
}
```

These examples assume the DTO fields match the current implementation. Adapt them if your request classes use different field names or validation rules.

## Error Handling and Validation

The API uses request validation and centralized exception handling to improve consistency when processing invalid requests or business rule violations.

Depending on the situation, the API can return HTTP status codes such as:

- `400 Bad Request` — Invalid request data.
- `401 Unauthorized` — Invalid login credentials.
- `404 Not Found` — Requested resource does not exist.
- `409 Conflict` — Conflict with an existing resource or business rule.

## Getting Started

### Prerequisites

Make sure you have installed:

- Java version compatible with the project configuration.
- Maven.
- MySQL Server.
- An API client such as Postman.

### Installation

1. Clone the repository:

   ```bash
   git clone <https://github.com/caiofeitosaa/meeting_room_booking_system>
   ```

2. Navigate to the project directory:

   ```bash
   cd meeting_room_booking_system
   ```

3. Configure your MySQL database and application properties.

4. Configure the required database credentials using your local environment or development configuration.

5. Run the application using your IDE or Maven.

6. Once the application is running, use Postman to send requests to the endpoints listed above.

## Project Structure

The application follows a layered structure to separate responsibilities:

- `controller` — Handles HTTP requests and exposes API endpoints.
- `service` — Contains business logic.
- `repository` — Handles database access through Spring Data JPA.
- `entities` — Represents database entities.
- `dtos` — Defines request and response data structures.
- `exception_handling` — Centralizes custom exceptions and error responses.

## Future Improvements

As development progresses, the project will focus on reservation management, time conflict detection, access control, and automated testing.

Additional improvements may include API documentation, password security enhancements, and more comprehensive integration tests.

## Author

Developed as a personal backend project to practice Java, Spring Boot, REST API development, relational database integration, and software engineering principles.
