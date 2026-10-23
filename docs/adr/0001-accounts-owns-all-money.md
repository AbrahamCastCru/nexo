# ADR-001: Accounts service owns all money movements

- Status: Accepted
- Date: 2026-10-23

## Context

Nexo moves money between customer accounts: deposits, transfers, currency conversions,
refunds and scheduled payments. A transfer has two sides: a debit to the sender and a
credit to the receiver. If one side is written and the other is not, money is created
or destroyed.

Microservices give each service its own database, so a write that spans two services
cannot run in a single database transaction.

## Decision

All balances, ledger entries and money movements live in the Accounts service and its
PostgreSQL database. No other service changes balances. Other services ask Accounts to
move money (REST) or react to its events (Kafka).

## Alternatives considered

- **Separate Ledger and Transfers services.** Each transfer would need a distributed
  transaction or a saga with compensations between two databases. A failure between the
  debit and the credit leaves money in an inconsistent state until compensation runs,
  and every money feature would pay that complexity.
- **Each service updates its own balances** (e.g. FX converts, Payments pays). Balance
  rules would be duplicated across services and the system total could no longer be
  checked in one place.

## Consequences

- Debit and credit of a transfer are written in one local ACID transaction, with
  row locks taken in a fixed order to prevent double spending and deadlocks.
- Double-entry bookkeeping is enforced in one place: the sum of all ledger entries is
  always zero, and a nightly job can verify it.
- Accounts becomes the most critical service. It needs the strongest test coverage,
  concurrency tests and its own scaling (Stage 5).
- Accounts must stay focused on money. Customer identity, notifications, fraud analysis
  and statements live in other services and communicate through events.
