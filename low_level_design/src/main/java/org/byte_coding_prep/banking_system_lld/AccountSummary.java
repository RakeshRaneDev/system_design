package org.byte_coding_prep.banking_system_lld;

public class AccountSummary {
    String accountId;
    int outgoing;

    AccountSummary(String accountId, int outgoing) {
        this.accountId = accountId;
        this.outgoing = outgoing;
    }

}
