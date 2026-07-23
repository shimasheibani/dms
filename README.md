# DMS — Document Management System

A Spring Boot REST API for managing documents and controlling user access to them. Supports uploading, retrieving, updating, and deleting documents, with JWT-based authentication and role-based access control.

## Features

- JWT-based authentication (register / login)
- Document upload with file-type validation (PDF, Word, PNG, JPEG)
- Document CRUD (create, list, retrieve, update, delete)
- User CRUD with role-based access (`ADMIN`, `User`, `VISITOR`)
- Document status tracking (`updated`, `deleted`)
- Per-user document listing

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java, Spring Boot 3.4.3, Spring Security, Hibernate |
| Auth | JWT (jjwt) |
| Database | MySQL |
| Mapping | ModelMapper |
| Testing | JUnit 5, H2 (in-memory) |

## Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL

### Configuration

Set these environment variables (see `src/main/resources/application.properties`), or copy `.env.example` to `.env` and fill it in:

```
DB_USERNAME=root
DB_PASSWORD=your-db-password
JWT_SECRET=a-long-random-secret
GITLAB_TOKEN=only-needed-if-the-gitlab-integration-is-used
```

### Run

```bash
mvn spring-boot:run
```

API is available at `http://localhost:8081`.

### Run tests

```bash
mvn test
```

## License

MIT
