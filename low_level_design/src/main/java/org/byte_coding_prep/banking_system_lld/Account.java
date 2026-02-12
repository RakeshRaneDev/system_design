package org.byte_coding_prep.banking_system_lld;

import java.util.HashMap;
import java.util.Map;


public class Account {
    public final String accountId;
    public int balance;
    public int outgoing;
    public final int createdAt;
    public final Map<Integer, Integer> balanceLog = new HashMap<>();


    public Account(String accountId, int createdAt) {
        this.accountId = accountId;
        this.balance = 0;
        this.outgoing = 0;
        this.createdAt = createdAt;
    }
}