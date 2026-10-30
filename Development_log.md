Portfolio Website --- Development Log
Record meaningful implementation milestones and decisions here. Add
dated entries as work progresses. Dates below are intentionally not
assigned where the exact date or commit hash has not been verified.


Project setup
- Created a Spring Boot portfolio backend using Java 21 and Maven.
- Configured PostgreSQL as the persistence layer.
- Local database name: portfolio_db.
- Application name: portfolio.
- API base path: /api/v1.


Backend implementation
Project domain model
Implemented a Project JPA entity with: - Generated ID. - Required
title and description. - Optional GitHub and live-demo URLs. - Featured
and published flags. - Creation timestamp.


Persistence layer
- Added ProjectRepository, extending JpaRepository<Project, Long>.
- Added findByPublishedTrue() for public project retrieval.
DTO layer
- Added ProjectRequest for create/update input.
- Added ProjectResponse for API output.
- Kept persistence entities separate from API request/response
  objects.


Service layer
Implemented operations for: - Listing published projects. - Listing all
projects, including drafts. - Retrieving a published project by ID. -
Creating projects. - Updating projects. - Deleting projects. - Mapping
entities to response DTOs.

The detail lookup filters out unpublished projects and returns 404 for
unpublished or missing records.
REST API
Implemented endpoints under /api/v1/projects: - GET / --- published
projects. - GET /all --- all projects, including drafts. - GET /{id}
--- published project by ID. - POST / --- create. - PUT /{id} ---
update. - DELETE /{id} --- delete.

Implemented response statuses include 201 for create and 204 for delete.
Validation and exception handling
- Added Jakarta Bean Validation for required title/description and
  maximum lengths.
- Added basic URL-prefix validation for GitHub and live URLs.
- Added GlobalExceptionHandler using @RestControllerAdvice.
- Handles validation errors as 400 responses with field-level details.
- Handles ResponseStatusException with structured JSON.
- Developer manually confirmed invalid request validation returns 400
  and missing project lookup returns 404.



Automated testing setup
- Added spring-boot-starter-test with Maven test scope, alongside
  focused Spring Boot test starters.
- Created ProjectServiceTest using JUnit 5 and Mockito.
- Corrected a source-folder mistake: test source was initially placed
  under src/main/java; moved it to src/test/java. This resolved
  the missing JUnit/Mockito package compilation errors.
- Developer confirmed successful test runs after moving the test file.



Service tests reported as passing
1. getAllProjects_shouldReturnPublishedProjects
2. getProjectById_shouldThrowExceptionForUnpublishedProject
3. getProjectById_shouldReturnPublishedProject



Proposed test --- completion unconfirmed
- getAllProjectsIncludingDrafts_shouldReturnAllProjects
Verify this test's status before marking it complete.
Security and production notes
- GET /api/v1/projects/all currently has no authentication and can
  expose unpublished drafts. Secure it with admin authorization or
  remove it before deployment.
- Keep database credentials out of Git. Use environment variables or
  deployment secrets.
- Configure CORS when the frontend origin is known.
- Consider schema migrations instead of relying on Hibernate
  ddl-auto=update in production.
- The URL regex currently checks only that a non-empty value starts
  with http:// or https://; it is not a full URI validator.


Current next steps
1. Confirm and finish the getAllProjectsIncludingDrafts unit test.
2. Complete service tests for create, update, and delete behaviour.
3. Add controller tests and selected integration tests.
4. Secure or remove the drafts endpoint.
5. Document the API and local setup.
6. Externalize secrets and prepare production configuration.
7. Deploy the backend and verify it.
8. Begin the React + TypeScript + Vite + Tailwind frontend.


## YYYY-MM-DD — Short milestone title

### Work completed
- ...

### Tests / verification
- Command or manual test:
- Result:

### Decisions
- ...

### Issues / follow-ups
- ...

### Git
- Commit: