# Portfolio Project Context and Handover

> Bring this file into a new ChatGPT conversation to continue the
> project. Read it before proposing code changes. Update it whenever
> implementation decisions or project status change.

## 1. Project goal

Build a polished, responsive, full-stack personal portfolio website for
software engineering job applications. It should present projects,
technical skills, and professional information. The backend is being
developed first; the frontend follows after the backend is tested,
secured, documented, and deployable.

## 2. Developer environment and preferences

-   OS/editor: Windows and Visual Studio Code.
-   Java: 21 (Temurin/OpenJDK).
-   Database: PostgreSQL 18, local port 5432.
-   Build: Maven Wrapper.
-   API testing: Thunder Client in VS Code.
-   Known local project path:
    `C:/Users/dipay/Desktop/Dipayan/portfolio/`.
-   Preferred workflow: incremental, one manageable task at a time.
-   Explain what is being built, why it is needed, how it works, and
    relevant interview concepts.
-   Avoid large code dumps. Give a focused change, then wait for
    implementation and verification.
-   Do not assume a task is complete until the developer confirms it.

## 3. Technology stack

**Backend:** Java 21, Spring Boot 4.1.2, Maven, Spring Data JPA,
PostgreSQL 18, Jakarta Bean Validation, JUnit 5, Mockito.

**Planned frontend:** React, TypeScript, Vite, Tailwind CSS.

## 4. Backend setup

Local database name: `portfolio_db`. PostgreSQL port: `5432`.
Application name: `portfolio`. Local API base: `http://localhost:8080`.

Run the application on Windows:

``` powershell
.\mvnw.cmd spring-boot:run
```

Run tests:

``` powershell
.\mvnw.cmd clean test
```

Known configuration shape (password intentionally omitted):

``` properties
spring.application.name=portfolio
spring.datasource.url=jdbc:postgresql://localhost:5432/portfolio_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

The actual local properties may differ. Never ask the developer to share
their database password. Externalize credentials before committing or
deploying.

## 5. Backend package structure

Known Java source layout:

``` text
src/main/java/com/dipayan/portfolio/
├── PortfolioApplication.java
└── project/
    ├── Project.java
    ├── ProjectRepository.java
    ├── ProjectService.java
    ├── ProjectController.java
    ├── GlobalExceptionHandler.java
    └── dto/
        ├── ProjectRequest.java
        └── ProjectResponse.java
```

Test source must be under `src/test/java`, not `src/main/java`:

``` text
src/test/java/com/dipayan/portfolio/project/
└── ProjectServiceTest.java
```

A previous compilation issue occurred because `ProjectServiceTest.java`
was initially placed in `src/main/java`. Moving it to `src/test/java`
resolved the missing JUnit/Mockito package errors.

## 6. Project entity

`Project` maps to the `projects` table and has these known fields:

  Field           Type / behaviour
  --------------- ------------------------------------------
  `id`            `Long`, generated identity
  `title`         Required, max 100 characters
  `description`   Required, max 2000 characters
  `githubUrl`     Optional
  `liveUrl`       Optional
  `featured`      Boolean, defaults to `false`
  `published`     Boolean, defaults to `true`
  `createdAt`     `LocalDateTime`, initialized on creation

The entity has a no-argument constructor and getters/setters.

## 7. Repository

`ProjectRepository` extends `JpaRepository<Project, Long>` and includes:

``` java
List<Project> findByPublishedTrue();
```

Inherited methods such as `findAll()`, `findById()`, `save()`, and
delete operations are also used.

## 8. DTOs and validation

`ProjectRequest` includes: - `title`: `@NotBlank`, maximum 100
characters. - `description`: `@NotBlank`, maximum 2000 characters. -
`githubUrl`: optional; current regex allows an empty string or a value
beginning with `http://` or `https://`. - `liveUrl`: same basic
URL-prefix validation. - `featured`: boolean, default false. -
`published`: boolean, default true.

The URL regex is a basic prefix check, not comprehensive URI validation.
The controller uses `@Valid` for request-body validation.

`ProjectResponse` exposes `id`, `title`, `description`, `githubUrl`,
`liveUrl`, `featured`, `published`, and `createdAt`.

## 9. Service behaviour

Known service methods: - `getAllProjects()` calls
`findByPublishedTrue()` and maps entities to response DTOs. -
`getAllProjectsIncludingDrafts()` calls `findAll()` and maps all
entities to response DTOs. - `getProjectById(Long id)` finds by ID,
filters to published projects, and throws `ResponseStatusException` with
404 when missing or unpublished. - Create, update, and delete operations
are implemented. - Missing IDs in relevant operations produce 404
responses. - Entity/DTO mapping is handled by service helper methods.

## 10. API contract

Base path: `/api/v1/projects`

  -------------------------------------------------------------------------
  Method                  Path                      Behaviour
  ----------------------- ------------------------- -----------------------
  GET                     `/api/v1/projects`        List published projects

  GET                     `/api/v1/projects/all`    List all projects,
                                                    including drafts;
                                                    currently not protected

  GET                     `/api/v1/projects/{id}`   Return a published
                                                    project; drafts and
                                                    missing IDs return 404

  POST                    `/api/v1/projects`        Create a project;
                                                    returns 201

  PUT                     `/api/v1/projects/{id}`   Update a project

  DELETE                  `/api/v1/projects/{id}`   Delete a project;
                                                    returns 204
  -------------------------------------------------------------------------

Example request body (adapt to the actual DTO as needed):

``` json
{
  "title": "Portfolio Website",
  "description": "A full-stack personal portfolio project.",
  "githubUrl": "https://github.com/your-username/portfolio",
  "liveUrl": "",
  "featured": true,
  "published": true
}
```

Do not assume IDs from previous manual tests remain stable. Query the
API/database for current values.

## 11. Exception handling

`GlobalExceptionHandler` is a `@RestControllerAdvice` and handles: -
`MethodArgumentNotValidException`: HTTP 400 with structured JSON
including timestamp, status, error, message, field errors, and path. -
`ResponseStatusException`: structured JSON with timestamp, status,
reason phrase, message, and path.

The developer manually confirmed invalid request validation returns 400
and a missing project lookup returns 404.

## 12. Automated testing status

The Maven POM includes `spring-boot-starter-test` with test scope, as
well as focused Spring Boot test starters for JPA, Validation, and Web
MVC.

`ProjectServiceTest.java` is located at
`src/test/java/com/dipayan/portfolio/project/` and uses JUnit 5 and
Mockito: - `@ExtendWith(MockitoExtension.class)` -
`@Mock ProjectRepository` - `@InjectMocks ProjectService`

The developer confirmed successful test runs after correcting the test
source folder.

Tests reported as added and passing: 1.
`getAllProjects_shouldReturnPublishedProjects` 2.
`getProjectById_shouldThrowExceptionForUnpublishedProject` 3.
`getProjectById_shouldReturnPublishedProject`

A fourth test was proposed but completion is **not confirmed**: -
`getAllProjectsIncludingDrafts_shouldReturnAllProjects`

First confirm its status and run `.\mvnw.cmd clean test`.

Planned additional tests: - Project creation and DTO mapping. - Update
existing project. - Update missing project. - Delete existing project. -
Delete missing project. - Controller tests for HTTP status, JSON
payloads, and validation. - Integration tests where useful.

## 13. Known security and production tasks

-   `GET /api/v1/projects/all` currently has no authentication and
    exposes drafts. Protect it with admin authorization or remove it
    before deployment.
-   Keep database credentials out of Git.
-   Configure CORS when the frontend origin is known.
-   Consider stronger URI validation if needed.
-   Review production use of `spring.jpa.hibernate.ddl-auto=update`;
    consider schema migrations for deployment.

## 14. Backend roadmap

1.  Finish service unit tests.
2.  Add controller tests and selected integration tests.
3.  Secure or remove the drafts endpoint.
4.  Add API documentation (OpenAPI, if appropriate).
5.  Improve README and local setup instructions.
6.  Externalize secrets and production configuration.
7.  Deploy backend and PostgreSQL database.
8.  Verify deployed API behaviour.

## 15. Frontend roadmap (not started)

Planned stack: React + TypeScript + Vite + Tailwind CSS.

Potential sections: - Home / hero with professional introduction. -
About. - Skills / technologies. - Projects listing populated from
`GET /api/v1/projects`. - Project detail view or modal. - GitHub and
live-demo links. - Contact section. - Responsive navigation and mobile
layout.

Frontend principles: - Consume the existing API; do not duplicate
project data. - Display only published projects using public
endpoints. - Keep API base URL configurable through frontend environment
variables. - Handle loading, empty, and error states. - Do not build
admin UI unless backend authentication/authorization is implemented. -
Confirm the API contract and deployment URL before integration.

## 16. New-chat handover prompt

Attach this file and say:

> I'm continuing my personal portfolio website project. Read
> `PORTFOLIO_PROJECT_CONTEXT.md` completely before proposing changes.
> Continue from the existing implementation; do not restart the project
> or assume unconfirmed tasks are complete. I prefer incremental
> guidance: one manageable task, explanation, implementation, and
> verification at a time. First establish the current backend status,
> then help me finish it. Once the backend is complete, guide me through
> the React + TypeScript + Vite + Tailwind frontend using the existing
> API contract.

## 17. Update policy

Update this file whenever an endpoint, DTO, dependency, test result,
security decision, deployment setting, or frontend architecture changes.
If uncertain, ask the developer to inspect current source files instead
of relying only on this handover.
