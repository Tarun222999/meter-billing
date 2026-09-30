# Stage 1, Phase 4 — Pricing plans and subscriptions

## Outcome and prerequisite

After Phase 3 review, authorized CloudBox staff can define API-call pricing and assign a plan to a customer. Customer employees can view their own subscription.

## Decisions before implementation

- Select the single currency and billing time zone; define unit-price precision and final amount rounding.
- Define plan identity and how prices are preserved when new prices are introduced. Referenced historical prices must not silently change.
- Define subscription statuses, legal transitions, and effective dates. One active subscription per customer; no mid-month plan changes.
- Decide initial-month/cancellation billing behavior without proration, and what event time qualifies under a subscription. Distinguish current status from historical eligibility.

## Small implementation slices

1. **Pricing plan:** Persist and expose a plan with an included API-call allowance and per-unit price. Validate nonnegative values and allowed precision.
2. **Subscription creation:** Assign a customer to an applicable plan and enforce required references and one-active-subscription behavior.
3. **Lifecycle:** Implement only the agreed transitions and effective-date rules. Reject invalid transitions without partial writes.
4. **Authorized views:** Expose the customer's own subscription and relevant pricing; restrict administrative changes.
5. **Consistency checks:** Test competing subscription creation and the policy protecting already-applicable prices. Record the required database guarantees.

## Concepts to learn

- Relational associations, foreign keys, fetching, and avoiding unintended N+1 queries
- Domain invariants, immutable pricing/versioning choices, and effective dates
- BigDecimal mapped to PostgreSQL numeric types
- Transactional service methods, rollback, Spring transaction proxies, and self-invocation pitfalls
- Uniqueness under concurrency and locking strategies when needed

## Acceptance criteria and evidence

- Valid plans and subscriptions persist and are accessible only to permitted callers.
- Negative prices/allowances, missing references, and invalid transitions fail clearly.
- Two concurrent requests cannot establish two active subscriptions for a customer.
- Existing pricing agreements are not silently rewritten by subsequent plan administration.
- Effective-date and no-mid-month-change rules have boundary tests.
- Selected pricing, time, and lifecycle policies are recorded for ingestion and billing.

## Review questions

- Which invariant needs a database guarantee rather than a read-before-write check?
- Can editing a plan change the cost of usage already incurred?
- What happens if a transaction fails after its first write?

## Completion and exclusions

Review domain rules, migrations, queries, authorization, and concurrency tests. No multiple currencies, proration, discounts, payments, or Kafka. Actual usage and invoice calculations follow later.
