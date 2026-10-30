# Personal Portfolio Website

A full-stack personal portfolio website to present my software
engineering projects, technical skills, and professional profile.

## Project status

The backend is under development. Core project-management APIs are
implemented. Automated testing, security hardening, documentation, and
deployment remain in progress. Frontend development has not started.

## Planned technology stack

**Backend:** Java 21, Spring Boot 4.1.2, Maven, Spring Data JPA,
PostgreSQL, Jakarta Bean Validation, JUnit 5, Mockito.

**Frontend:** React, TypeScript, Vite, Tailwind CSS.

## Current backend capabilities

-   Create, read, update, and delete portfolio projects.
-   Return published projects through the public API.
-   Retrieve an individual project only when it is published.
-   Retrieve all projects, including drafts, through an endpoint that
    must be secured before deployment.
-   Validate project titles, descriptions, and URL prefixes.
-   Return structured JSON errors for validation failures and not-found
    conditions.

## API overview

Local base URL: `http://localhost:8080/api/v1/projects`

  -------------------------------------------------------------------------
  Method                  Endpoint                  Purpose
  ----------------------- ------------------------- -----------------------
  GET                     `/api/v1/projects`        List published projects

  GET                     `/api/v1/projects/all`    List all projects,
                                                    including drafts
                                                    (currently unprotected)

  GET                     `/api/v1/projects/{id}`   Get a published project
                                                    by ID

  POST                    `/api/v1/projects`        Create a project

  PUT                     `/api/v1/projects/{id}`   Update a project

  DELETE                  `/api/v1/projects/{id}`   Delete a project
  -------------------------------------------------------------------------

## Run locally

1.  Install Java 21 and PostgreSQL.
2.  Create a PostgreSQL database named `portfolio_db`.
3.  Configure the database connection using local environment variables
    or uncommitted configuration.
4.  From the backend project directory, run:

``` powershell
.\mvnw.cmd spring-boot:run
```

The API should be available at `http://localhost:8080`.

## Security and deployment notes

-   Never commit database passwords or other secrets.
-   The `/api/v1/projects/all` endpoint currently exposes drafts without
    authentication. Protect it with admin authentication or remove it
    before public deployment.
-   Configure CORS deliberately when the frontend is introduced.
-   Use environment-based configuration for database credentials and
    deployment settings.
-   Complete automated tests before considering the backend
    production-ready.

## Planned repository structure

``` text
portfolio/
├── backend/
├── frontend/
├── README.md
├── PORTFOLIO_PROJECT_CONTEXT.md
└── DEVELOPMENT_LOG.md
```

Adjust this to match the actual repository layout.

## Development approach

Build and verify the backend first, then implement the frontend against
the established API contract.
