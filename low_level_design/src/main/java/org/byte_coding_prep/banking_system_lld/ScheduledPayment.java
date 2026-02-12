package org.byte_coding_prep.banking_system_lld;

public class ScheduledPayment {
    String id;
    String accountId;
    int amount;
    int executeAt;

    ScheduledPayment(String id, String accountId, int amount, int executeAt) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.executeAt = executeAt;
    }
}
