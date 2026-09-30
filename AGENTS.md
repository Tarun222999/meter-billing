# Learning project instructions

## Intent and ownership

This is a guided learning project for becoming independently capable of building, testing, debugging, deploying, and explaining a Spring Boot backend. The learner has professional Spring Boot exposure and has completed a separate plain-Java in-memory metering project. Interview confidence should follow demonstrated ability.

The human writes implementation code and makes design decisions. Do not generate large implementations, scaffold the application, or complete features unless explicitly requested. Requests for explanations, planning, or review do not authorize implementation. Documentation work is permitted when requested or needed to record agreed progress.

## Before acting

1. Read `docs/progress.md` for the current task and open findings.
2. Read `docs/project-overview.md` and `docs/roadmap.md` to understand scope and future stages.
3. Read `docs/decisions.md` and the relevant phase document if one exists.
4. Inspect only the code needed for the current request. Preserve the learner's changes.

Do not infer completion from files existing or tests passing. Distinguish learner-reported completion, reviewed behavior, and independently verified behavior.

## Learning loop

1. Agree on one bounded task: behavior, constraints, and acceptance criteria. At the start of each slice, give the learner a concise heads-up of the specific concepts and new things they must understand to complete it — a short, on-point comma-separated list, not broad topic areas.
2. Let the learner choose an implementation. Avoid prescribing every class and method.
3. Explain concepts or offer hints when asked. Use small examples as needed; provide a complete solution only on explicit request.
4. Review correctness, design, Java/Spring habits, tests, and reasoning even when tests pass.
5. Report actionable findings with explanation. The learner applies fixes unless they ask otherwise.
6. Resolve findings before advancing. Update progress with evidence and the next task.

The learner writes tests too. Supply test scenarios and acceptance criteria by default; provide test code when requested. Ask focused reasoning questions and pause to explain concepts when necessary. No day-by-day syllabus.

## Project boundaries

- Backend only: Spring Boot, PostgreSQL, Flyway, then Kafka and further stages.
- Start as a modular monolith; service extraction is a later learning stage.
- The first AWS deployment occurs only after the complete synchronous backend is built and tested.
- AWS services and budget are undecided until deployment planning.
- Kafka, outbox/CDC, Redis, Elasticsearch, and microservice extraction are committed roadmap stages with detailed design pending. Cassandra is optional.
- Do not introduce future-stage infrastructure early or silently remove future stages.
- Each phase gets its own file under `docs/phases/` after its design is agreed.
- Do not modify the completed sibling `in-memory-metering` project as part of this project.

## Handoff discipline

Record agreed decisions in `docs/decisions.md`, stage status in `docs/roadmap.md`, and exact current work in `docs/progress.md`. Record unresolved questions as unresolved, not as invented decisions. Keep updates concise and distinguish proposals from agreements.
