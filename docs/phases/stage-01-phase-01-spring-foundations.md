# Stage 1, Phase 1 — Spring Boot foundations and HTTP

## Outcome and prerequisite

Start from the agreed planning documents. Produce a locally running Spring Boot application with one small HTTP endpoint and repeatable tests. Understand what Spring does when the process starts.

## Decisions before implementation

- Select a supported Java/Spring Boot combination and build tool, checking official documentation at setup time. Earlier chat version suggestions are not pinned requirements.
- Choose a simple informational endpoint, its JSON response, and its URL.
- Agree on an initial package layout inside the modular monolith without creating empty future modules.

## Small implementation slices

1. **Reproducible startup:** Generate the minimum project, run its test, and start it. Explain the build configuration and application entry point. Preserve the existing planning files when generating the project.
2. **One HTTP response:** Implement the agreed endpoint with a stable JSON contract. Exercise it through an HTTP client and an automated web-layer test.
3. **Spring-managed collaboration:** Add a small meaningful collaborator for the endpoint and use constructor injection. Explain how this differs from wiring objects in the plain-Java `Main`.
4. **Configuration and diagnosis:** Make a small setting configurable; demonstrate an override. Deliberately reproduce a startup or configuration failure, diagnose it, and restore a passing build.

## Concepts to learn

- Spring versus Spring Boot; application context, beans, component scanning, and auto-configuration
- Annotations, constructor injection, bean lifecycle and singleton scope at a practical level
- Embedded web server, request mapping, HTTP status, headers, and JSON serialization
- Controller responsibilities and response DTOs; Java records where useful
- Configuration properties, environment overrides, and startup logs
- Plain unit tests, focused HTTP tests, and full-context startup tests

## Acceptance criteria and evidence

- Documented commands test and start the app from its project directory.
- The endpoint returns the agreed status, content type, and JSON fields.
- Tests exercise the HTTP contract and the collaborator's behavior where applicable.
- Configuration can change without editing production source.
- The learner can identify a failing startup's cause from logs and explain the request path.

## Review questions

- What creates the controller and its dependencies? What happens if a dependency cannot be found?
- Which behavior comes from Java, Spring, and Spring Boot respectively?
- Why should a singleton bean not hold mutable per-request customer state?

## Completion and exclusions

Review the implementation and resolve findings before Phase 2. Record commands and evidence in progress. No database, security implementation, business billing, Kafka, or AWS deployment yet.
