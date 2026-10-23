# Domain events

Business facts that Nexo services publish to Kafka. Each event has exactly one owner
(the service that publishes it). Other services only listen.

Conventions:

- Names are `Noun` + past-tense verb: `TransferCompleted`, never `CompleteTransfer`.
- Events describe something that already happened and changed the system.
  A rejected request (insufficient funds, invalid data) is an error response, not an event.
- One fact, one event. `TransferCompleted` is published once; listeners derive
  what they need (e.g. Notifications sends one message to the sender and one to the receiver).
- Every event carries `eventId`, `occurredAt`, `version` and the IDs needed by listeners.
- Events are published with the transactional outbox pattern (Stage 2).

## 1. Onboarding and identity

| Event | Owner | Listeners | Triggered by | Key data |
| --- | --- | --- | --- | --- |
| `CustomerRegistered` | Customers | Notifications, Audit | Customer signs up | customerId, email, phone |
| `EmailVerified` | Customers | Audit | Customer enters email code | customerId |
| `PhoneVerified` | Customers | Audit | Customer enters SMS/WhatsApp code | customerId |
| `IdentityDocumentsSubmitted` | Customers | Audit | Customer uploads ID and selfie | customerId, verificationId |
| `IdentitySentToReview` | Customers | Notifications, Audit | KYC provider returns a doubtful score | customerId, verificationId, score |
| `IdentityApproved` | Customers | Accounts, Notifications, Audit | KYC provider or agent approves | customerId, limitTier |
| `IdentityRejected` | Customers | Notifications, Audit | KYC provider or agent rejects | customerId, reason |

Policy: **when `IdentityApproved`, open an MXN account** (Accounts).

## 2. Money: deposits and transfers

| Event | Owner | Listeners | Triggered by | Key data |
| --- | --- | --- | --- | --- |
| `AccountOpened` | Accounts | Notifications, Audit | Policy on `IdentityApproved` | accountId, customerId, currency, clabe |
| `FundsDeposited` | Accounts | Notifications, Audit, Fraud | Customer makes a simulated deposit | accountId, amount, currency |
| `TransferCompleted` | Accounts | Notifications, Audit, Fraud | Transfer passes risk rules, or is released | transferId, fromAccountId, toAccountId, amount, currency |
| `TransferHeld` | Accounts | Notifications, Audit, Fraud | Synchronous risk rule flags the transfer; money does not move | transferId, fromAccountId, toAccountId, amount, ruleCode |
| `TransferReleased` | Accounts | Audit | Fraud analyst releases a held transfer; followed by `TransferCompleted` | transferId, analystId |
| `TransferRejected` | Accounts | Notifications, Audit | Analyst rejects, or funds are no longer available on release | transferId, reason (`REJECTED_BY_ANALYST`, `INSUFFICIENT_FUNDS`) |

Not events: insufficient funds, daily limit exceeded or frozen account on a new transfer.
Accounts answers with a `ProblemDetail` error and nothing changes.

## 3. Security

| Event | Owner | Listeners | Triggered by | Key data |
| --- | --- | --- | --- | --- |
| `NewDeviceSignedIn` | Customers | Notifications, Audit, Fraud | Login from an unknown device (Keycloak event) | customerId, deviceId, location |
| `FraudFreezeRequested` | Fraud | Accounts, Audit | Window rule detects severe risk | accountId, alertId, reason |
| `AccountFrozen` | Accounts | Notifications, Audit | Customer freezes from the app, or policy on `FraudFreezeRequested` | accountId, frozenBy (`CUSTOMER`, `FRAUD`) |
| `AccountUnfrozen` | Accounts | Notifications, Audit | Customer passes step-up selfie, or supervisor unfreezes | accountId, unfrozenBy |
| `LimitsChanged` | Accounts | Notifications, Audit | Supervisor changes daily limits | accountId, oldLimit, newLimit, changedBy |

Security notifications are always sent, regardless of customer preferences.

## 4. Product (outline, refined in Stage 3)

| Event | Owner | Listeners |
| --- | --- | --- |
| `CurrencyConverted` | Accounts | Notifications, Audit |
| `PaymentRequestCreated` | Accounts | Notifications |
| `PaymentRequestPaid` | Accounts | Notifications, Audit |
| `ScheduledPaymentDue` | Accounts | Notifications |
| `BudgetThresholdReached` | Accounts | Notifications |

## 5. Back office (outline, refined in Stage 3)

| Event | Owner | Listeners |
| --- | --- | --- |
| `FraudAlertRaised` | Fraud | Audit |
| `RefundRequested` | Accounts | Audit |
| `RefundApproved` | Accounts | Notifications, Audit |
| `CustomerRecordViewed` | Audit (internal) | none |

## 6. Statements and account closure (outline, refined in Stage 4)

| Event | Owner | Listeners |
| --- | --- | --- |
| `StatementGenerated` | Statements | Notifications |
| `AccountClosureRequested` | Accounts | Customers, Notifications, Audit |
| `AccountClosed` | Accounts | Customers, Notifications, Audit |
| `CustomerDeactivated` | Customers | Audit |
