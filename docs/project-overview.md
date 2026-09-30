# Usage Metering and Billing Engine

## Learning outcome

Build the ability to implement, test, debug, modify, deploy, and explain an end-to-end Spring Boot backend independently. The application provides evidence of that ability and supports interview preparation. The human owns implementation; agents guide and review.

The completed sibling `in-memory-metering` project is the domain-learning reference. This is a separate project. Application implementation has not started here.

## Product direction

Agreed Stage 1 model: one SaaS company (CloudBox) uses this engine to manage and bill its business customers (such as Acme). Each business customer is a tenant. Customer employees can log in to view their own subscription, usage, and invoices. CloudBox staff manage customers and pricing; their exact permissions will be designed during the relevant phase.

This is not currently scoped as a billing platform serving multiple independent SaaS providers. Confirm the exact actors and onboarding flow during scope design.

Example journey: CloudBox registers Acme and assigns a pricing plan; CloudBox's product reports Acme's API consumption; the engine validates and persists it once; authorized users inspect monthly totals; an authorized actor previews and finalizes an itemized invoice. We build the billing engine, not CloudBox's API product. Acme does not report its own billable consumption. Ingestion credentials must authorize the trusted CloudBox reporting system and its permitted customer scope; credential design remains open.

## Agreed Stage 1 scope — complete synchronous backend

- Identity and access: login, customer-account provisioning, tenant membership, permissions, and tenant isolation. Public self-registration is not yet agreed; design the provisioning flow during its phase.
- Plans: administrator-managed metric pricing and permitted customer visibility.
- Subscriptions: customer-plan association and explicit lifecycle rules.
- Ingestion: API-key authentication, validation, durable usage storage, exact-retry handling, and conflicting-duplicate rejection.
- Reporting: tenant-scoped monthly summaries and paginated event history.
- Billing: monthly previews, itemized finalized invoices, preserved pricing details, and protection against duplicate finalization.
- Foundations: PostgreSQL, Flyway, DTO validation, consistent errors, API documentation, automated tests, external configuration, logging, and health checks.
- Deployment: first AWS deployment after the complete synchronous backend is tested.

This is the first usable milestone of the same evolving project, delivered through small learning phases. Requests complete their business work before returning success; asynchronous Kafka workflows belong to Stage 2. Endpoint contracts, schemas, and detailed business rules will be designed in the relevant phases.

## Agreed Stage 1 boundaries

- Monthly billing with one currency. Select the currency and billing time zone during detailed design.
- One initial metric: API-call count. Other metrics are extensions; storage snapshots and distinct active-user counts require separate aggregation semantics.
- One active subscription per customer.
- No mid-month plan changes or proration in the first version.
- No taxes, discounts, payment collection, or frontend.
- Define money rounding and invoice snapshots explicitly rather than copying demo assumptions without review.

## Stage 1 completion criteria

- Users authenticate and can access only permitted tenant resources.
- Authorized actors can establish subscriptions and manage applicable plans.
- Application usage submissions are validated, persisted, and safe to retry.
- Reports and invoices reconcile with accepted usage and pricing rules.
- Restarting the application preserves data.
- Tests cover important failures, tenant boundaries, and concurrent duplicate submissions.
- A fresh PostgreSQL database is reproducible through migrations.
- The first deployed workflow is verified and failures can be investigated through logs.
- The learner can trace a request through security, controller, service, and database, and independently implement a bounded requirement change.

Deployment is the final checkpoint of this stage. Local functional completion precedes selection and setup of AWS infrastructure.

## Full journey and detailed plans

See [roadmap.md](roadmap.md) for Kafka, outbox/CDC, Redis, Elasticsearch, microservice extraction, and optional Cassandra. See [phases/README.md](phases/README.md) for how detailed learning phases will be created.
