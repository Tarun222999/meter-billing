# Stage 1, Phase 8 — End-to-end verification and first AWS deployment

## Outcome and prerequisite

After Phases 1–7 are reviewed, prove the complete synchronous workflow locally, then perform the first AWS deployment. Stage 1 closes only after deployed verification and review.

## Decisions at phase entry

- Review actual AWS options and costs at this time; agree on budget, environment uptime, region, and services before provisioning paid resources.
- Select application/database hosting, image/artifact storage, secrets, networking, HTTPS, and log/health collection.
- Decide migration execution, compatible release sequencing, CI/CD approach, and application rollback/database recovery procedures.
- Use synthetic customer data. Do not select Kafka or later-stage infrastructure yet.

## Small implementation slices

1. **Local release audit:** Run the full suite and exercise staff onboarding, subscription assignment, trusted ingestion/retry, customer reporting, and invoice finalization through HTTP. Include two customers and denied access.
2. **Operability:** Add/document health readiness, useful request correlation, logs, API contracts, environment settings, and failure diagnosis. Ensure credentials and internal error details are not exposed.
3. **Reproducible artifact:** Package and containerize the application. Run it with external PostgreSQL and configuration; verify startup, graceful shutdown, and persistence across restart.
4. **Deployment design:** Compare suitable AWS arrangements, record cost and operational trade-offs, and agree on the concrete first environment.
5. **Release pipeline and migrations:** Establish repeatable build/test/artifact/deployment steps. Verify migrations on a populated test database and document recovery before applying the release.
6. **First deployment:** Provision the agreed resources, deploy the synchronous backend, and run authenticated smoke tests against the actual environment.
7. **Recovery and ownership:** Rehearse a bounded failure/recovery exercise, verify the backup/restore procedure in a safe environment, and complete one small requirement change chosen with the learner through test, review, and redeployment.

## Concepts to learn

- Unit, HTTP, persistence, security, and full-workflow test coverage
- External configuration, health versus readiness, structured logs, and basic metrics
- Docker images, immutable artifacts, graceful termination, and startup failures
- AWS identity/permissions, networking, managed versus self-managed trade-offs, and cost controls
- CI/CD, migration ordering, compatible releases, application rollback versus database recovery
- Operational runbooks, smoke tests, and debugging from deployed evidence

## Acceptance criteria and evidence

- A fresh checkout has documented setup/test/run commands; fresh schema creation and supported schema upgrade work.
- The full synchronous journey passes locally with automated regression coverage and synthetic data.
- The deployed API enforces authentication/tenant boundaries and persists data across application restart.
- Build and deployment steps are repeatable; secrets are external and health/log access is appropriately restricted.
- A release record identifies artifact, migration versions, smoke-test results, and known limitations.
- Recovery steps distinguish application rollback from database restoration or a forward migration; backup recovery has evidence, not only a checkbox.
- The learner can diagnose a demonstrated failure and implement/explain a bounded change independently.
- AWS resources, expected costs, shutdown/cleanup instructions, and chosen operational policies are documented.

## Review questions

- If a release fails after a migration, can the previous application still run safely?
- How do you distinguish bad configuration, database unavailability, and an application bug?
- What evidence shows an invoice is correct and access is isolated in the deployed environment?

## Completion and handoff

Record Stage 1 completion only after deployed checks and outstanding reviews are resolved. Revisit Stage 2 in the roadmap and design its Kafka phases together. Kafka, CDC, Redis, Elasticsearch, service extraction, and optional Cassandra remain future work.
