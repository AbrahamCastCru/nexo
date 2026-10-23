# Open questions

Design decisions to make later. Each one becomes an ADR when decided.

- [ ] **Reserve funds on held transfers?** When a transfer is held for fraud review,
  should the amount be reserved (available balance goes down, like a card authorization
  hold) so that releasing it can never fail for insufficient funds? Decide before Phase 11.
- [ ] **Balance calculation at scale.** Balance is derived from ledger entries. Use a
  running balance column updated in the same transaction, periodic snapshots, or both?
  Decide in Phase 4.
- [ ] **Kafka in AWS.** Amazon MSK or Kafka inside the cluster? Compare cost and operations
  in Phase 18.
