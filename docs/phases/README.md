# Learning phase documents

Stage 1 is divided into the eight phases below. All are currently unstarted. See [the full roadmap](../roadmap.md) for later stages and [progress](../progress.md) for the authoritative current task.

## Stage 1 — Complete synchronous backend

1. [Spring Boot foundations and HTTP](stage-01-phase-01-spring-foundations.md)
2. [PostgreSQL, migrations, and customers](stage-01-phase-02-persistence-customers.md)
3. [Login, permissions, and tenant isolation](stage-01-phase-03-security-tenancy.md)
4. [Pricing plans and subscriptions](stage-01-phase-04-plans-subscriptions.md)
5. [Trusted usage ingestion and deduplication](stage-01-phase-05-usage-ingestion.md)
6. [Monthly reporting](stage-01-phase-06-usage-reporting.md)
7. [Invoice previews and finalization](stage-01-phase-07-invoices.md)
8. [End-to-end verification and first AWS deployment](stage-01-phase-08-verification-deployment.md)

## How to use these files

Work on one numbered slice at a time, not an entire phase at once. Before a slice, resolve only the design questions needed for it and record decisions. The learner implements and tests; the agent reviews; the learner addresses findings before advancing. Each phase's checks are cumulative and must remain green in later phases.

Each phase lists observable requirements, not mandatory classes or a supplied implementation. Endpoint paths, schemas, and detailed policies are design work for the learner with guidance. A phase file's existence does not indicate completion.

Security arrives in Phase 3: the earlier HTTP work is a local-only learning increment and is not ready for external exposure. The first AWS deployment is in Phase 8, after synchronous functionality and tests are complete. A local PostgreSQL container in Phase 2 is not a cloud deployment.

Each phase will have its own Markdown file. Each task within it should describe:

- The business capability and why it is needed.
- Required observable behavior and constraints.
- Acceptance criteria, including relevant failure cases.
- Concepts to understand and reasoning questions for review.
- Prerequisites and explicit exclusions.
- Completion evidence and any applicable deployment checkpoint.

Provide enough detail for the learner to know what to build, while leaving implementation choices to them. Do not embed complete production solutions. Later stages receive detailed phase files when their design is discussed.
