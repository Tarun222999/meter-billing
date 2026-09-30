# Stage 1, Phase 3 — Login, permissions, and tenant isolation

## Outcome and prerequisite

After Phase 2 review, CloudBox staff can perform authorized customer administration and customer employees can access only their own permitted data.

## Decisions before implementation

- Define the minimum role/permission matrix for CloudBox staff and customer employees.
- Define account provisioning and initial administrator setup. Public signup and invitation/email workflows are not assumed.
- Choose session or token authentication and explain credential lifetime, logout/revocation, and CSRF implications. JWT is not mandatory.
- Decide whether a person can belong to multiple tenants initially and how an active tenant is authorized.

## Small implementation slices

1. **Identity model:** Persist users and the required customer association/membership. Establish credentials without logging or storing plaintext passwords.
2. **Login and lifecycle:** Implement authentication, failed-login behavior, and the chosen logout/expiry semantics. Document how test accounts are provisioned.
3. **Permission enforcement:** Protect the existing customer-management endpoints according to the agreed matrix; handle unauthenticated and unauthorized calls consistently.
4. **Tenant isolation:** Restrict customer-facing reads and lists by trusted identity/membership. Test Acme and Northstar users attempting to cross boundaries using resource IDs and request parameters.
5. **Security regression review:** Test role escalation attempts, expired/revoked authentication where applicable, and sensitive-data exposure. Document the policy future endpoints must follow.

## Concepts to learn

- Authentication versus authorization; security filters and security context
- Password hashing, credential lifecycle, and session/token trade-offs
- Endpoint permissions and resource ownership checks
- Tenant-aware queries; why an untrusted customer ID is not authorization
- HTTP authentication failures, access denials, CSRF, and CORS as distinct concerns
- Security integration tests and safe configuration of credentials

## Acceptance criteria and evidence

- Valid credentials authenticate; invalid credentials fail without exposing sensitive details.
- Only permitted CloudBox staff can perform customer-administration operations.
- Acme employees cannot read or modify Northstar resources through detail or list routes.
- Clients cannot grant themselves roles or tenant membership through extra request fields.
- Passwords and authentication secrets do not appear in responses, logs, or source control.
- Automated tests exercise both allowed and denied access, not only mocked principals on happy paths.

## Review questions

- Where is the authenticated tenant obtained and checked?
- Can a query return another tenant's rows before filtering?
- How does logout affect the chosen authentication mechanism?

## Completion and exclusions

Review the permission matrix against every existing endpoint. Subsequent phases must add equivalent authorization tests. No external identity provider, MFA, public signup, or API-key ingestion unless later explicitly scoped. No AWS deployment yet.
