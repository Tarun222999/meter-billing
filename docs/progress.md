# Progress

**Current stage:** Stage 1 — complete synchronous backend; phase plan documented.

**Current phase:** Phase 1 (Spring Boot foundations and HTTP) complete and verified. Phase 2 (PostgreSQL, migrations, and customers) started: slice 1 (database baseline) decisions agreed 2026-09-30; implementation not yet written.

**Implementation status:** Spring Boot 4.1.1 app under `backend/` (base package `com.cloudbox.metering`). All four Phase 1 slices verified. `mvnw test` passes (3 tests, 0 failures). `GET /api/v1/health` returns `200` + `application/json`; its values are externalized via a `HealthProperties` `@ConfigurationProperties` record registered by `@ConfigurationPropertiesScan`, injected into `HealthService` by constructor. No database or security yet.

## Agreed

- Human implements; agents explain, provide bounded tasks, and review before progression.
- Full roadmap is persisted, including future stages awaiting detailed design.
- PostgreSQL is the primary database; Flyway manages versioned SQL migrations.
- Begin as a modular monolith; Kafka precedes deliberate service extraction.
- First AWS deployment follows completion and testing of the synchronous backend.
- Choose AWS services and budget when preparing that deployment.

## Current task

Phase 2, slice 1 (database baseline): decisions agreed 2026-09-30 (Docker Compose for local PostgreSQL, Testcontainers for integration tests, `BIGINT IDENTITY` customer PK, initial `customers` schema and email uniqueness — see `decisions.md` §18). Remaining work: the learner adds the datasource and Flyway configuration with externalized credentials, writes `V1` creating `customers`, maps the entity, and validates the mapping against the migrated schema with a PostgreSQL-backed integration test. Prerequisite: Docker Desktop daemon running (installed but stopped as of 2026-09-30). Phase 1 reviewed (done).

## Next task

Review Phase 2 slice 1 (migration, mapping validation, DB-backed integration test) before the create/retrieve slice.

## Open reviews

Slice 1 (Phase 1) reviewed and verified on 2026-09-24: layout, POM coordinates, passing test, and real startup all confirmed; learner explained build config and entry point.

Slice 2 (Phase 1) reviewed and verified on 2026-09-26: `GET /api/v1/health` returns the agreed contract; `@WebMvcTest` asserts status, content type, and all three JSON fields; full suite green (2 tests). Findings raised and resolved during review: missing static imports, path/fields drifting from the contract, missing content-type assertion, a value typo the test caught, and bare `@WebMvcTest` pinned to the controller. Learner correctly explained slice-vs-full-context testing and the `Accept` vs response `Content-Type` distinction.

Slice 3 (Phase 1) reviewed and verified on 2026-09-26: `HealthService` bean added; `HealthController` takes it via constructor injection into a `final` field (no field `@Autowired`, no `new`). `@WebMvcTest` uses `@MockitoBean` and stubs it. Findings raised and resolved during review: an unstubbed mock returning `null` (empty body / no content type), the Java `assert` keyword used instead of `assertEquals`, an unused import, and a stub that duplicated the real values so it couldn't prove delegation. Final: 3 tests green.

Slice 4 (Phase 1) reviewed and verified on 2026-09-29: health values externalized via `HealthProperties` (`@ConfigurationProperties` record) registered with `@ConfigurationPropertiesScan` and constructor-injected into `HealthService`; `@Value` fields removed. Override verified end-to-end (command-line `--metering.health.service=... --metering.health.status=...` changed the HTTP response; unset properties fell back to `application.properties`). Deliberate failure reproduced and evidenced: a second instance died with `Web server failed to start. Port 8080 was already in use.` Findings resolved during review: incomplete `@ConfigurationProperties` class (uninitialized `final` fields), `@EnableConfigurationProperties` misplaced on the properties record instead of a configuration class, and `@Configuration` on a `final` record causing a CGLIB "Cannot subclass final class" context-startup failure. Final: 3 tests green.

Phase 1 (Spring Boot foundations and HTTP) is complete: all four slices reviewed and verified. The learner can explain the request path, bean creation and injection, slice vs full-context testing, and configuration precedence.

Open, minor, non-blocking:

- POM hygiene: empty `<url/>` and blank `<licenses>/<developers>/<scm>` blocks remain (`<name>`/`<description>` now filled).

Resolved 2026-09-24: `JAVA_HOME` was unset and only JDK 18 was installed. Temurin 25 is now installed and `JAVA_HOME` is set at both User and Machine scope to `C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot`; a fresh shell resolves `java -version` to OpenJDK 25.0.4.1 LTS.

No other implementation review exists for this project. The plain-Java sibling is learner-completed; do not reopen it automatically.

## Latest checkpoint

2026-09-30: Phase 2 slice 1 (database baseline) scoped and its decisions agreed, recorded in `decisions.md` §18. Local PostgreSQL runs via Docker Compose; integration tests use Testcontainers (H2 rejected); `customers` PK is `BIGINT GENERATED ALWAYS AS IDENTITY`; initial columns are `id`, `name VARCHAR(255) NOT NULL`, `email VARCHAR(320) NOT NULL UNIQUE`, `created_at TIMESTAMPTZ NOT NULL DEFAULT now()` (DB default, mapped read-only); app lowercases email before writes; credentials externalized. Tooling verified on this machine: Docker CLI 29.x and Compose v5.5.1 present, but the Docker Desktop daemon was not running. No Phase 2 implementation code written yet.

2026-09-29: Phase 1 complete and verified (all four slices). Slice 4 externalized the health values into a `HealthProperties` `@ConfigurationProperties` record (registered via `@ConfigurationPropertiesScan`, constructor-injected into `HealthService`). Override verified over HTTP (command-line args changed the response; unspecified values fell back to `application.properties`). Deliberate startup failure reproduced and captured: `Web server failed to start. Port 8080 was already in use.` Full suite green: 3 tests, 0 failures. Phase 2 (PostgreSQL, Flyway, customers) is next; its first slice is the database baseline.

2026-09-26: Phase 1 slice 3 complete and verified. Added `HealthService` (`@Service`, currently a plain unit under test) and switched `HealthController` to constructor injection of it (final field). `@WebMvcTest` mocks it with `@MockitoBean` and stubs values different from the real ones so the test proves delegation. Added `HealthServiceTest` (plain JUnit). Full suite green: 3 tests, 0 failures. Slice 4 (externalized configuration and a diagnosed failure) is next; it is the last Phase 1 slice.

2026-09-26: Phase 1 slice 2 complete and verified. `GET /api/v1/health` implemented in package `com.cloudbox.metering.health` (`HealthController` + `HealthResponse` record), returning `{"service":"service-metering","version":"0.0.1","status":"UP"}`. Verified by `@WebMvcTest(HealthController.class)` (status, content type, all JSON fields) and by a real HTTP request against the packaged jar (200, `application/json`). Full suite green: 2 tests, 0 failures. Slice 3 (Spring-managed collaborator via constructor injection) is next.

2026-09-24: Phase 1 slice 1 complete and verified. Learner generated the project with Spring Initializr (Java 25, Spring Boot 4.1.1, Maven) and flattened it into `backend/`. Verified independently: `mvnw test` passes (1 test, 0 failures) and `mvnw spring-boot:run` starts Tomcat 11.0.24 on port 8080 on Java 25.0.4.1. Learner explained the parent POM, starters, `spring-boot-maven-plugin`, and `@SpringBootApplication`/`main`. Environment gotcha recorded: `JAVA_HOME` was unset and only JDK 18 was installed; Temurin 25 was installed and `JAVA_HOME` set per-terminal. Slice 2 (`GET /api/v1/health`) is next.

2026-09-24: Phase 1 setup decisions agreed with the learner: Java 25 (LTS), Spring Boot 4.1.x, Maven, project under `backend/`, base package `com.cloudbox.metering`. Versions verified against the official Spring Boot system-requirements page (4.1 supports Java 17–26; 3.x is EOL). Endpoint `GET /api/v1/health` agreed for slice 2. Learner to implement slice 1 next; no application code written yet.

2026-09-21: Created eight individual Stage 1 phase guides and a linked index. Each contains bounded slices, learning concepts, required design decisions, acceptance criteria, review questions, and exclusions. All phases remain unstarted. AWS selection/deployment remains in Phase 8; later roadmap stages remain awaiting detailed design.

2026-09-21: Learner agreed to Stage 1 as the first complete synchronous milestone: customer registration, plans/subscriptions, trusted product-reported API usage, monthly summaries, login/isolation, and stable finalized invoices. One metric, one currency, one active subscription per customer, no mid-month plan changes, and no payment collection. Corrected the earlier customer-self-reporting description. Future stages remain planned; none have been implemented or deployed.
