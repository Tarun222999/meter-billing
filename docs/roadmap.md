# Full project roadmap

This roadmap preserves the whole learning journey. It is not an implementation specification. Design each stage together before writing its phase files. Every implementation task follows the learning loop in `../AGENTS.md`.

## Stage 1 — Complete synchronous backend

**Status:** Phase 1 (Spring Boot foundations and HTTP) complete and verified; Phase 2 (PostgreSQL, migrations, and customers) in progress. [Eight learning phases documented](phases/README.md). Detailed policy decisions are resolved at the relevant phase.

**Purpose:** Independently build a complete Spring Boot backend with persistent business workflows and secure APIs.

**Expected capabilities:** REST APIs, identity and access, tenant isolation, plan and subscription management, trusted CloudBox product usage ingestion, deduplication, monthly reporting, bill previews, finalized invoices, tests, migrations, and operational visibility. Stage 1 uses API-call count, one currency, one active subscription per customer, and no mid-month plan changes or payment collection. See `project-overview.md` for the agreed boundary.

**Learning goals:** Spring application lifecycle and beans, constructor injection, MVC, JSON and DTOs, validation, error handling, Spring Security, SQL/JPA, transactions, concurrency, Flyway, configuration, unit and integration tests, logging, and health checks.

**Prerequisite:** Completed plain-Java metering foundation.

**Deployment checkpoint:** First AWS deployment only after the synchronous backend is complete and tested. Select AWS services and budget at that point; verify authenticated workflows, migrations, persistence, health, logs, and recovery.

**Open design questions:** Exact role permissions, account provisioning, subscription lifecycle, currency/time-zone selection, billing/rounding rules, invoice finalization and late usage, authentication and trusted-producer credentials, API contracts, module boundaries, and build/runtime versions.

## Stage 2 — Kafka and asynchronous workflows

**Status:** Planned — detailed design pending.

**Purpose:** Move an appropriate usage-processing workflow to asynchronous execution and understand its changed guarantees.

**Expected capabilities:** Producers and consumers, observable processing status, partitioning, retries, duplicate handling, replay, and recovery after interruption.

**Learning goals:** Topics, keys, partitions, consumer groups, offsets, ordering boundaries, acknowledgement/commit timing, delivery semantics, lag, backpressure, poison messages, and eventual consistency.

**Prerequisite:** Working synchronous backend with its first deployment verified.

**Deployment checkpoint:** Deploy the asynchronous workflow; test restarts, duplicate delivery, backlog recovery, and failure visibility.

**Open design questions:** Which workflow changes first, event contracts, partition keys, acceptance/durability guarantees, producer/database failure windows, retry limits, failure destinations, and replay procedure. Do not claim end-to-end exactly-once processing merely because Kafka features are enabled.

## Stage 3 — Transactional outbox and CDC

**Status:** Planned — detailed design pending.

**Purpose:** Address the gap between committing a business change and reliably publishing its event.

**Expected capabilities:** Business data and outbox record written in one PostgreSQL transaction; committed outbox changes delivered to Kafka through CDC, with consumer deduplication.

**Learning goals:** Transactional outbox, CDC, Debezium, replication concepts, snapshots and offsets, connector recovery, schema evolution, retention, and operational monitoring.

**Prerequisite:** Kafka workflow and its failure modes understood.

**Deployment checkpoint:** Deploy connector infrastructure and migration changes; interrupt delivery and demonstrate eventual recovery without lost committed business events or duplicate business effects.

**Open design questions:** Outbox schema, event routing, connector configuration, cleanup, database permissions, replication-slot growth, and recovery procedures. CDC records and intentional domain-event contracts are distinct concerns.

## Stage 4 — Redis

**Status:** Planned — detailed design pending.

**Purpose:** Address a demonstrated caching or API rate-limiting requirement.

**Expected capabilities:** A focused cache and/or rate limiter with explicit expiration and failure behavior.

**Learning goals:** Cache-aside, keys, TTLs, invalidation, stale reads, atomic operations, cache stampedes, and dependency failure handling.

**Prerequisite:** Stable backend plus a concrete access pattern or rate-limiting requirement.

**Deployment checkpoint:** Deploy Redis and its use case; verify expiry, invalidation, limits, and behavior during an outage.

**Open design questions:** What to cache, correctness impact, tenant-scoped keys, stale-data tolerance, eviction, and whether a failed dependency should allow or reject requests. PostgreSQL remains authoritative for billing data.

## Stage 5 — Elasticsearch

**Status:** Planned — detailed design pending.

**Purpose:** Support a concrete search requirement for usage or audit history.

**Expected capabilities:** Search index populated from events, tenant-scoped search/filtering, visible indexing delay, and a rebuild procedure.

**Learning goals:** Mappings, analyzers where relevant, indexing, aggregations, pagination, eventual consistency, duplicate/out-of-order updates, and reindexing.

**Prerequisite:** Stable source data/event contracts and agreed search queries.

**Deployment checkpoint:** Deploy search infrastructure and indexing; verify authorization, delayed updates, recovery, and index rebuilding.

**Open design questions:** Searchable fields, source of truth, index layout, update ordering, retention, and reconciliation. Search results must not become the authority for invoice totals.

## Stage 6 — Microservice extraction

**Status:** Planned — detailed design pending.

**Purpose:** Learn the consequences of independently deploying an existing module.

**Expected capabilities:** One extracted service with clear data ownership, explicit contracts, independent deployment, and traceable interactions.

**Learning goals:** Service boundaries, network failures, timeouts, contract compatibility, distributed consistency, data migration, distributed tracing, and independent releases.

**Prerequisite:** Established module boundaries and an evidence-based reason to extract a component.

**Deployment checkpoint:** Independently deploy and upgrade the service; test unavailable dependencies and mixed compatible versions.

**Open design questions:** Which module, database ownership, synchronous versus asynchronous interactions, authentication between services, migration/cutover, and rollback. No extraction target is selected yet.

## Stage 7 — Optional Cassandra exploration

**Status:** Optional — detailed design pending.

**Purpose:** Compare an alternative usage-history store for defined queries and measured workloads.

**Expected capabilities:** An isolated implementation or projection for customer/time-range usage history, with a comparison against PostgreSQL.

**Learning goals:** Query-driven modeling, partition/clustering keys, time bucketing, denormalization, replication, consistency, retention, and hotspot avoidance.

**Prerequisite:** Explicit interest, query definitions, representative load, and infrastructure budget.

**Deployment checkpoint:** Deploy the experiment and compare correctness, query performance, operational effort, and cost.

**Open design questions:** Whether the stage is worthwhile, data authority, partition size, consistency needs, and retention. Cassandra is not a planned replacement for the core PostgreSQL billing database.

## Stage transition rule

Revisit this document at every transition. Agree on the next stage's scope, acceptance criteria, and deployment checks; then create one file per learning phase. Future-stage entries preserve commitments without authorizing implementation today.
