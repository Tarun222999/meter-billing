# Stage 1, Phase 6 — Monthly usage reporting

## Outcome and prerequisite

After Phase 5 review, authorized users can inspect accepted API usage and monthly totals for their customer.

## Decisions before implementation

- Define month selection using the agreed billing zone, including empty-month behavior.
- Define allowed event filters, ordering, page limits, and access for CloudBox staff.
- Decide which query supports each endpoint and what indexes it needs.

## Small implementation slices

1. **Event history:** Expose a tenant-scoped, paginated list of accepted events with stable ordering and bounded filters.
2. **Monthly summary:** Calculate API-call totals for a selected customer/month using occurrence time.
3. **Boundaries and isolation:** Test empty months, month/year transitions, zone conversion, and multiple customers with overlapping timestamps.
4. **Query review:** Inspect representative SQL and its query plan. Add a justified index through Flyway; avoid loading all stored events into application memory just to sum them.

## Concepts to learn

- SQL aggregation and repository projections
- Inclusive start/exclusive end ranges and conversion from billing month to instants
- Pagination, tie-break ordering, and validation of filter inputs
- Tenant predicates, query indexes, and reading EXPLAIN output
- Numeric aggregation bounds and deterministic fixture data
- Distinguishing usage quantities from monetary charges

## Acceptance criteria and evidence

- Summaries reconcile with accepted events; duplicate attempts do not inflate totals.
- Month boundaries use the explicit billing zone, not the machine default.
- Empty periods and invalid filters have documented responses.
- History and summary endpoints cannot expose another customer's information.
- Pagination is bounded and stable for unchanged data, including equal event timestamps.
- A representative query and index rationale are recorded; tests cover time and tenant boundaries.

## Review questions

- Why use an exclusive end boundary instead of the last second of a month?
- Does an index match the actual filter and ordering pattern?
- How can a correct-looking summary still leak another tenant's data?

## Completion and exclusions

Review reporting correctness, API permissions, query behavior, and tests. No Elasticsearch, materialized asynchronous aggregates, caching, or invoice amounts in this phase.
