# Assignment 1 — Employee Management System

A classic full-stack CRUD assignment: a Spring Boot REST API backed by an
in-memory H2 database, with a React frontend for listing, searching,
filtering, creating, editing, and deleting employees.

## What's verified vs. hand-written

- **Frontend (`/frontend`)** — built with Vite, `npm install` and `npm run build`
  were both run successfully in the environment that produced this project.
  It's a working, compiling React app.
- **Backend (`/backend`)** — complete, standard Spring Boot 3 source code
  (Maven project). It could not be compiled in the environment used to write
  this assignment, because that environment has no network access to Maven
  Central. The code follows conventional, well-tested Spring Boot patterns
  (Spring Data JPA, Bean Validation, `@RestControllerAdvice` for error
  handling) and should build cleanly with `mvn spring-boot:run` on a machine
  with normal internet access. Double-check it builds on your end before
  treating it as final.

## Running it

**Backend** (requires JDK 21 and Maven):
```bash
cd backend
mvn spring-boot:run
```
Starts on `http://localhost:8080`. On first run, five sample employees are
seeded automatically. The H2 console is available at
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:employeedb`).

**Frontend** (requires Node.js):
```bash
cd frontend
npm install
npm run dev
```
Starts on `http://localhost:5173` and expects the backend on port 8080.

## API reference

| Method | Endpoint                | Description                                  |
|--------|--------------------------|-----------------------------------------------|
| GET    | `/api/employees`         | List employees — supports `page`, `size`, `sortBy`, `direction`, `search` (by name), `department` (filter) |
| GET    | `/api/employees/{id}`    | Get a single employee                         |
| POST   | `/api/employees`         | Create an employee                            |
| PUT    | `/api/employees/{id}`    | Update an employee                            |
| DELETE | `/api/employees/{id}`    | Delete an employee                            |

## Design notes

- **DTOs separate from the entity** — `EmployeeRequest`/`EmployeeResponse`
  keep the API contract independent of the persistence model, so the
  database schema can evolve without automatically changing the public API.
- **Validation at the DTO layer** via Bean Validation (`@NotBlank`, `@Email`,
  `@Positive`, etc.), with a `@RestControllerAdvice` translating validation
  failures into a consistent `{ fieldErrors: {...} }` response the frontend
  reads directly to show inline field errors.
- **Duplicate email handling** — checked explicitly in the service layer
  and surfaced as a `409 Conflict`, not left to a raw database constraint
  violation leaking an unfriendly SQL error to the client.
- **Pagination and dynamic search/filter** at the repository level using
  Spring Data derived query methods and `Pageable`, rather than loading
  everything and filtering in memory.
- **Test coverage** — a service-layer test class covers create, duplicate
  rejection, not-found handling, update, and delete, using `@Transactional`
  tests that roll back automatically so each test starts clean.

## What I'd add with more time

- Sorting/column-header click-to-sort in the UI (the API already supports
  `sortBy`/`direction`, just not wired to the table headers yet).
- Optimistic UI updates instead of a full reload after each mutation.
- Integration tests using `@SpringBootTest` + `MockMvc` against the actual
  HTTP layer, on top of the existing service-layer unit tests.
