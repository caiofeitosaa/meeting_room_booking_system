# Gather — Meeting Room Booking System

**Gather** is a meeting room management and booking system that helps teams register meeting rooms, check availability, and schedule meetings. It combines a responsive web interface with a REST API built using **Java and Spring Boot**.

## About the Project

Gather provides a simple place to manage meeting spaces and bring teams together. Users can create an account, browse room details such as capacity and projector availability, check a room's schedule, and book a meeting.

The web interface is served by Spring Boot and connects to the same API used to manage users, rooms, and reservations. The API handles persistence, input validation, and booking rules, including preventing overlapping reservations for the same room.

Before using the interface to search for rooms or make bookings, create at least one meeting room through the `POST /rooms/create` endpoint.

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

The API supports creating rooms, listing all of them, searching for them by room number, and deleting a room by providing its ID and room number.

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
| GET | `/rooms` | List all registered meeting rooms |
| GET | `/rooms/search` | Search for a room by room number |
| DELETE | `/rooms/delete` | Delete a room using its ID and room number |

### Reservations

| Method | Endpoint | Description |
|---|---|---|
| POST | `/reservation/create` | Create a reservation for an existing user and room |
| GET | `/reservation/search?roomNumber={roomNumber}&date={date}` | Search reservations by room number and date (`YYYY-MM-DD`) |

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

### Create a Reservation

`POST /reservation/create`

```json
{
  "roomNumber": 201,
  "userId": 1,
  "userName": "Caio Feitosa",
  "startDateTime": "2026-10-20T10:08:00",
  "endDateTime": "2026-10-20T11:10:00"
}
```

The provided `userName` must match the name of the existing user identified by `userId`.

### List Meeting Rooms

`GET /rooms`

```json
[
  { "roomNumber": 201, "capacity": 10, "hasProjector": true }
]
```

### Search Reservations

`GET /reservation/search?roomNumber=201&date=2026-10-20`

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
   git clone https://github.com/caiofeitosaa/meeting_room_booking_system
   ```

2. Navigate to the project directory:

   ```bash
   cd meeting_room_booking_system
   ```

3. Configure your MySQL database and application properties.

4. Configure the required database credentials using your local environment or development configuration.

5. Run the application using your IDE or Maven.

6. Once the application is running, use Postman to send requests to the endpoints listed above.

### Web Interface

The application includes a responsive meeting-room booking interface served by Spring Boot. Start the application and open `http://localhost:8080` in a browser. Before testing the interface, create one or more meeting rooms through the `POST /rooms/create` API endpoint; room search and bookings require an existing room. Then create an account or sign in to associate reservations with your user profile, and use the room schedule to check a room's reservations by room number and date.

To book a room, click **Book a room** and then **Show available rooms**. The interface lists every registered room with its capacity and projector availability; select one to fill in the room number, then choose the date and time. The list shows all rooms, not only those free at the chosen time, so a room that is already booked is still rejected by the API with a `409 Conflict`.

## Project Structure

The application follows a layered structure to separate responsibilities:

- `controller` — Handles HTTP requests and exposes API endpoints.
- `service` — Contains business logic.
- `repository` — Handles database access through Spring Data JPA.
- `entities` — Represents database entities.
- `dtos` — Defines request and response data structures.
- `exception_handling` — Centralizes custom exceptions and error responses.

## Future Improvements

As development progresses, the project will focus on access control and automated testing.

Additional improvements may include API documentation, password security enhancements, and more comprehensive integration tests.

## Author

Developed as a personal backend project to practice Java, Spring Boot, REST API development, relational database integration, and software engineering principles.
