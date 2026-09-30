# Stage 1, Phase 2 — PostgreSQL, migrations, and customers

## Outcome and prerequisite

After Phase 1 review, allow local creation and retrieval of persistent business customers. These endpoints acquire authorization in Phase 3 before external exposure.

## Decisions before implementation

- Define customer identity, required fields, uniqueness, and which fields can change.
- Design create/read/update/list contracts, pagination limits, and error responses.
- Choose a reproducible local PostgreSQL setup and isolated PostgreSQL test environment. Testcontainers is a candidate to evaluate.

## Small implementation slices

1. **Database baseline:** Configure PostgreSQL and Flyway. Write the first schema migration and validate the entity mapping against it. Use environment configuration for credentials.
2. **Create and retrieve:** Persist a valid customer and retrieve it by ID. Separate HTTP payloads from persistence entities; inspect the SQL and database rows.
3. **Validation and errors:** Reject malformed input and invalid customer data. Give missing resources and conflicts consistent responses without leaking SQL or stack traces.
4. **Update and list:** Update an agreed field and implement bounded pagination with deterministic sorting. Decide how conflicting updates are handled.
5. **Evolve an existing database:** Make one small, justified schema change with a new migration. Verify both a fresh database and an upgrade containing customer data.

## Concepts to learn

- SQL types, primary keys, uniqueness, nullability, and database constraints
- JPA entities, repositories, persistence context, dirty checking, and transaction boundaries
- HTTP DTOs, Bean Validation, exception translation, and centralized error handling
- Flyway version history; schema validation instead of automatic schema updates
- Connection configuration, SQL inspection, pagination, and basic indexes
- Unit tests versus real PostgreSQL integration tests; isolated test data

## Acceptance criteria and evidence

- A customer survives an application restart and is retrievable through HTTP.
- Invalid input and missing IDs have documented, tested responses.
- Database constraints enforce selected invariants even when application pre-checks race.
- Lists have bounded page sizes and stable ordering; update behavior is tested.
- Migrations work from empty and previous populated schema states without unintended data loss.
- Integration tests use PostgreSQL for behavior depending on PostgreSQL semantics.
- Commands and sample HTTP requests are documented without committed secrets.

## Review questions

- Why are Java validation and database constraints both needed?
- When does a write commit, and what happens on failure?
- Why not return a JPA entity directly as the API contract?

## Completion and exclusions

Review SQL, migration behavior, API design, and tests before Phase 3. No cloud deployment, billing model, or messaging. Do not add database tables for future phases speculatively.
