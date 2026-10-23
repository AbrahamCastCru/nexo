# Nexo

**A cross-border digital wallet built with Spring Boot microservices, Angular and Ionic.**

Nexo is a full-stack fintech platform for the US–Mexico border region. Customers hold
balances in MXN and USD, send money instantly, pay and get paid with QR codes, and track
their spending, while an internal back office reviews identities, investigates fraud
alerts and audits every action. It runs on simulated money, like a fintech sandbox,
but every part is designed the way a real bank would build it.

> 🚧 **Status:** in active development. See the [roadmap](docs/roadmap.md) for progress.

## What makes it interesting

- **Money that always adds up.** A double-entry ledger in PostgreSQL where balances are
  derived from immutable entries, never edited.
- **No double spending.** Idempotency keys, ordered pessimistic locking and concurrency
  tests that fire hundreds of simultaneous transfers at the same account.
- **Event-driven microservices.** Accounts, Customers, Notifications, Fraud, Audit,
  FX and Statements services communicating through Kafka with the transactional
  outbox pattern.
- **Real identity verification.** KYC with document checks, liveness and face match
  through a pluggable provider (hexagonal architecture), plus step-up biometric
  re-authentication for high-risk actions.
- **Notifications where users actually are.** WhatsApp, email, push and in-app alerts
  for transfers, onboarding and security events.
- **Production-grade operations.** Kubernetes on AWS provisioned with Terraform,
  per-service CI/CD, distributed tracing, Grafana dashboards, load and chaos testing.

## Applications

| App | Users | Stack |
| --- | --- | --- |
| Mobile wallet | Customers | Ionic, Angular, Capacitor |
| Back office | Support agents, fraud analysts, supervisors | Angular |
| Backend | — | Java 21, Spring Boot, Spring Cloud Gateway |

## Tech stack

**Backend:** Java 21 · Spring Boot · Spring Security · Spring Data JPA · Flyway · Kafka · Resilience4j
**Data:** PostgreSQL · Redis · Amazon S3
**Identity:** Keycloak (OIDC) · Didit (KYC and biometrics)
**Frontend:** Angular · Ionic · Capacitor
**Infrastructure:** Docker · Kubernetes · Helm · Terraform · AWS (EKS, RDS, ElastiCache, SES, CloudFront)
**Quality and operations:** JUnit 5 · Testcontainers · k6 · GitHub Actions · OpenTelemetry · Prometheus · Grafana