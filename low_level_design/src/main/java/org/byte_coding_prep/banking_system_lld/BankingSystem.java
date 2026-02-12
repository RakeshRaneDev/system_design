package org.byte_coding_prep.banking_system_lld;

import java.util.List;

public interface BankingSystem {
    boolean createAccount(int timestamp, String accountId);
    Integer deposit(int timestamp, String accountId, int amount);
    Integer transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount);
    List<String> topSpenders(int timestamp, int n);
    String schedulePayment(int timestamp, String accountId, int amount, int delay);
    boolean cancelPayment(int timestamp, String accountId, String paymentId);
    boolean mergeAccounts(int timestamp, String accountId1, String accountId2);
    Integer getBalance(int timestamp, String accountId, int timeAt);
}
