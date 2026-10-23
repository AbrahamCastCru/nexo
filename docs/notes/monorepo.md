# Monorepo vs polyrepo

## Context 
- **Nexo** is a multi-service platform combining frontend, backend, and mobile applications, raising the architectural question of whether to consolidate code in a monorepo or distribute it across individual polyrepos.

## Gains
- **Atomic Commits & Refactoring:** API contract changes and their corresponding consumer updates (e.g., backend and mobile) happen in a single commit, preventing version mismatch.

- **Centralized Infrastructure & Contracts:** Single source of truth for the root compose.yaml, unified CI workflows, and central OpenAPI specs.

## Losses
- **CI/CD Overhead:** Naive pipelines trigger full builds and tests for all services on every commit, wasting compute time and slowing down feedback loops.

- **Boundary Decay:** Physical proximity tempts developers to introduce direct imports across service boundaries instead of keeping domains isolated.

- **Granular Access Control:** Platform limitations make it difficult to restrict directory-level permissions to specific teams if external contributors are involved.

## Decision

- **Choice:** Adopt a monorepo setup to maximize developer velocity, atomic refactoring, and contract visibility.

- **Mitigation:** Use path filters in GitHub Actions to build and test only the affected services, treat each backend service as an independent Maven project, and enforce strict module isolation (using tools like ArchUnit where applicable) to prevent cross-service imports.