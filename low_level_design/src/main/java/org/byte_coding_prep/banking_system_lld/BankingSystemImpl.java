package org.byte_coding_prep.banking_system_lld;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class BankingSystemImpl implements BankingSystem {
    private final Map<String, Account> accounts = new HashMap<>();
    private final List<ScheduledPayment> scheduledPayments = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private int paymentCounter = 0;

    public BankingSystemImpl() {}

    // Create Account
    @Override
    public boolean createAccount(int timestamp, String accountId) {
        lock.lock();
        try {
            processPayments(timestamp);

            if (accounts.containsKey(accountId)) return false;

            accounts.put(accountId, new Account(accountId, timestamp));
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    // Deposit
    public Integer deposit(int timestamp, String accountId, int amount) {
        lock.lock();
        try {
            processPayments(timestamp);

            Account acc = accounts.get(accountId);
            if (acc == null) return null;

            acc.balance += amount;
            acc.balanceLog.put(timestamp, acc.balance);

            return acc.balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    // Transfer
    public Integer transfer(int timestamp, String sourceId, String targetId, int amount) {
        lock.lock();
        try {
            processPayments(timestamp);

            Account src = accounts.get(sourceId);
            Account tgt = accounts.get(targetId);

            if (src == null || tgt == null) return null;
            if (sourceId.equals(targetId)) return null;
            if (src.balance < amount) return null;

            src.balance -= amount;
            src.outgoing += amount;
            src.balanceLog.put(timestamp, src.balance);

            tgt.balance += amount;
            tgt.balanceLog.put(timestamp, tgt.balance);

            return src.balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    // Top Spenders
    public List<String> topSpenders(int timestamp, int n) {
        lock.lock();
        try {
            processPayments(timestamp);

            List<AccountSummary> list = new ArrayList<>();
            for (Account acc : accounts.values()) {
                list.add(new AccountSummary(acc.accountId, acc.outgoing));
            }

            list.sort(new ByOutgoing());

            List<String> result = new ArrayList<>();
            for (int i = 0; i < Math.min(n, list.size()); i++) {
                AccountSummary s = list.get(i);
                result.add(s.accountId + "(" + s.outgoing + ")");
            }

            return result;
        } finally {
            lock.unlock();
        }
    }

    // Schedule Payment
    public String schedulePayment(int timestamp, String accountId, int amount, int delay) {
        lock.lock();
        try {
            processPayments(timestamp);

            if (!accounts.containsKey(accountId)) return null;

            paymentCounter++;
            String paymentId = "payment" + paymentCounter;
            int execTime = timestamp + delay;

            scheduledPayments.add(new ScheduledPayment(paymentId, accountId, amount, execTime));

            return paymentId;
        } finally {
            lock.unlock();
        }
    }

    // Cancel Payment
    public boolean cancelPayment(int timestamp, String accountId, String paymentId) {
        lock.lock();
        try {
            processPayments(timestamp);

            Iterator<ScheduledPayment> it = scheduledPayments.iterator();
            while (it.hasNext()) {
                ScheduledPayment p = it.next();
                if (p.id.equals(paymentId)) {
                    if (!p.accountId.equals(accountId)) return false;
                    it.remove();
                    return true;
                }
            }

            return false;
        } finally {
            lock.unlock();
        }
    }

    // Merge Accounts
    public boolean mergeAccounts(int timestamp, String id1, String id2) {
        lock.lock();
        try {
            processPayments(timestamp);

            if (id1.equals(id2)) return false;

            Account a1 = accounts.get(id1);
            Account a2 = accounts.get(id2);

            if (a1 == null || a2 == null) return false;

            a1.balance += a2.balance;
            a1.outgoing += a2.outgoing;
            a1.balanceLog.put(timestamp, a1.balance);

            for (ScheduledPayment p : scheduledPayments) {
                if (p.accountId.equals(id2)) {
                    p.accountId = id1;
                }
            }

            accounts.remove(id2);
            return true;
        } finally {
            lock.unlock();
        }
    }

    // Get Balance at a given timestamp
    public Integer getBalance(int timestamp, String accountId, int timeAt) {
        lock.lock();
        try {
            processPayments(timestamp);

            Account acc = accounts.get(accountId);
            if (acc == null || acc.createdAt > timeAt) return null;

            int closest = -1;

            for (Integer t : acc.balanceLog.keySet()) {
                if (t <= timeAt && (closest == -1 || t > closest)) {
                    closest = t;
                }
            }

            if (closest != -1) {
                return acc.balanceLog.get(closest);
            }

            return null;
        } finally {
            lock.unlock();
        }
    }

    // Internal scheduled payments processor
    private void processPayments(int currentTimestamp) {
        if (scheduledPayments.isEmpty()) return;

        List<ScheduledPayment> remaining = new ArrayList<>();

        for (ScheduledPayment p : scheduledPayments) {
            if (p.executeAt <= currentTimestamp) {
                Account acc = accounts.get(p.accountId);

                if (acc != null && acc.balance >= p.amount) {
                    acc.balance -= p.amount;
                    acc.outgoing += p.amount;
                    acc.balanceLog.put(currentTimestamp, acc.balance);
                }
            } else {
                remaining.add(p);
            }
        }

        scheduledPayments.clear();
        scheduledPayments.addAll(remaining);
    }
}
