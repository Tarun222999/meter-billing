# Stage 1, Phase 5 — Trusted usage ingestion and deduplication

## Outcome and prerequisite

After Phase 4 review, CloudBox's product can report API-call consumption for an authorized customer. Successful synchronous acceptance means the event is committed to PostgreSQL.

Acme is the billed customer, not the reporter of its own billable usage. A producer credential authenticates CloudBox's reporting system; its permitted customer scope must be enforced.

## Decisions before implementation

- Define event fields, positive integral quantity bounds, occurrence time, and receipt time.
- Define API-key provisioning, secure storage, scope, revocation, and administrative ownership.
- Define event-ID uniqueness scope and which fields determine an exact retry versus a conflict.
- Agree on responses for accepted, duplicate, conflicting, unauthorized, and invalid input.
- Define future/late timestamp policy and subscription eligibility for historical events. Define how closed billing periods will be handled in Phase 7.

## Small implementation slices

1. **Trusted producer:** Provision and validate an ingestion credential; reject invalid, revoked, or insufficiently scoped keys. Human login must not automatically grant producer access.
2. **Single event:** Validate the payload, customer, metric, subscription, and time rules; commit a valid event and return its agreed outcome.
3. **Retries:** Return an idempotent outcome for an exact retry and reject conflicting reuse of the same identity. Document validation order when state has changed since original acceptance.
4. **Concurrent submissions:** Enforce deduplication in PostgreSQL and test simultaneous matching and conflicting submissions.
5. **Failure and restart:** Demonstrate that rejection leaves no accepted event, accepted events survive restart, and retry after a lost response does not create extra usage.

## Concepts to learn

- Machine authentication versus human authentication; API-key lifecycle
- Idempotency, database uniqueness, conflict handling, and race conditions
- Transaction commit versus response delivery
- Event occurrence time versus ingestion time
- DTO validation, database integration tests, and concurrent request tests
- Request correlation and logs that omit credentials and sensitive payloads

## Acceptance criteria and evidence

- Only an authorized CloudBox producer can report for the specified customer.
- Unknown customers, unsupported metrics, invalid quantities/timestamps, and ineligible subscriptions are rejected according to documented rules.
- Exact retries create one accepted event; conflicting retries cannot overwrite it.
- Deduplication survives restarts and concurrent requests; no in-memory lock is the sole correctness guarantee.
- A successful acceptance is backed by committed data; failed transactions leave no partial accepted record.
- API keys are not exposed through list/read endpoints or logs; revoked credentials fail.

## Review questions

- What happens when the database commits but the client never receives the response?
- Why is checking whether an event exists insufficient under concurrency?
- How do we know the producer is allowed to report for the customer in the payload?

## Completion and exclusions

Review tests for trust boundaries and durable idempotency. Keep synchronous processing; no Kafka, outbox, CDC, batching, or Redis deduplication. Carry the late-usage decision forward to invoice finalization.
