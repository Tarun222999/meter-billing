# Stage 1, Phase 7 — Invoice previews and finalization

## Outcome and prerequisite

After Phase 6 review, authorized actors can preview monthly charges and finalize a durable itemized invoice. Customers can retrieve only their own invoices.

## Decisions before implementation

- Define who can preview and finalize, eligible billing periods, and treatment of partial subscription months under the no-proration boundary.
- Define finalization identity, repeated-request outcomes, zero-usage behavior, and invoice identifiers. This is not a legal/tax compliance exercise.
- Specify allowance application, unit-price precision, line rounding, currency scale, and total calculation.
- Define how pricing applicable to the month is selected and preserved.
- Resolve late-arriving usage and concurrent ingestion/finalization. A minimal policy may close the month to new usage; select and document a policy before implementation. Never silently omit accepted usage or mutate an issued amount.

## Small implementation slices

1. **Pure calculation:** Calculate included usage, billable quantity, line amount, and total using the agreed rules. Write focused monetary boundary tests.
2. **Preview:** Combine authorized monthly usage with applicable pricing without creating a finalized invoice. Document that a preview can change while its inputs remain open.
3. **Finalization:** Atomically persist invoice identity, usage/pricing snapshot, lines, currency, and total. Preserve them from later source changes.
4. **Repeat and race handling:** Test repeated and concurrent finalization plus ingestion racing with finalization under the selected closure policy.
5. **Retrieval:** Provide tenant-scoped invoice detail and paginated history, with readable calculation inputs and authorization tests.

## Concepts to learn

- Pure domain calculations composed by transactional application services
- Monetary rounding boundaries and database precision
- Snapshot data versus mutable references
- Uniqueness, isolation, locks/versioning as needed, and transaction consistency
- Idempotent commands, stable historical records, and lifecycle rules
- End-to-end tests linking ingestion, reporting, and billing

## Acceptance criteria and evidence

- Below, at, and above allowance calculations are correct; no floating-point money.
- Line amounts and totals follow the documented rounding policy and total equals the sum of lines.
- Preview has no finalization side effect.
- Finalized invoices preserve quantities and pricing even if source pricing changes later.
- Concurrent/repeated finalization produces one logical invoice for the agreed billing identity.
- The late-usage and concurrency policy prevents accepted usage from silently disappearing from billing.
- Failure during finalization does not leave an incomplete invoice.
- Customer users cannot access another customer's invoice or perform staff-only operations.

## Review questions

- What must be copied into the invoice instead of looked up when reading it later?
- How can a database transaction still allow a race if the isolation/locking strategy is wrong?
- What happens to an event arriving just as the month is finalized?

## Completion and exclusions

Review financial boundaries, SQL guarantees, authorization, and end-to-end evidence before Phase 8. No payments, tax, credit notes, proration, PDF output, or asynchronous generation. Refund/correction workflows require a later explicit scope decision.
