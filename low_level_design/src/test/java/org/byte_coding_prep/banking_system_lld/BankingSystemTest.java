package org.byte_coding_prep.banking_system_lld;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class BankingSystemTest {

    private BankingSystem banking;

    @BeforeEach
    void setup() {
        banking = new BankingSystemImpl(); // Replace with your implementation
    }

    // ---------------------------------------------------------------
    // 1. CREATE ACCOUNT TESTS
    // ---------------------------------------------------------------
    @Test
    void testCreateAccountSuccess() {
        assertTrue(banking.createAccount(1, "A1"));
    }

    @Test
    void testCreateAccountDuplicate() {
        banking.createAccount(1, "A1");
        assertFalse(banking.createAccount(2, "A1"));  // duplicate
    }

    @Test
    void testCreateAccountNullId() {
        assertFalse(banking.createAccount(1, null));
    }

    @Test
    void testCreateAccountEmptyId() {
        assertFalse(banking.createAccount(1, ""));
    }

    // ---------------------------------------------------------------
    // 2. DEPOSIT TESTS
    // ---------------------------------------------------------------
    @Test
    void testDepositToNewAccount() {
        banking.createAccount(1, "A1");
        Integer balance = banking.deposit(2, "A1", 100);
        assertEquals(100, balance);
    }

    @Test
    void testDepositMultipleTimes() {
        banking.createAccount(1, "A1");
        banking.deposit(2, "A1", 100);
        Integer balance = banking.deposit(3, "A1", 50);
        assertEquals(150, balance);
    }

    @Test
    void testDepositToUnknownAccount() {
        assertNull(banking.deposit(1, "X", 100));
    }

    @Test
    void testDepositNegativeAmount() {
        banking.createAccount(1, "A1");
        assertNull(banking.deposit(2, "A1", -10));
    }

    @Test
    void testDepositTimestampOrdering() {
        banking.createAccount(1, "A1");
        banking.deposit(10, "A1", 100);
        Integer balance = banking.deposit(5, "A1", 50); // out of order?
        // Depending on design, might ignore or return previous balance
        assertNotNull(balance);
    }

    // ---------------------------------------------------------------
    // 3. TRANSFER TESTS
    // ---------------------------------------------------------------
    @Test
    void testTransferSuccess() {
        banking.createAccount(1, "A1");
        banking.createAccount(1, "A2");
        banking.deposit(2, "A1", 200);

        Integer balance = banking.transfer(3, "A1", "A2", 100);
        assertEquals(100, balance);
    }

    @Test
    void testTransferInsufficientBalance() {
        banking.createAccount(1, "A1");
        banking.createAccount(1, "A2");
        banking.deposit(2, "A1", 50);

        Integer result = banking.transfer(3, "A1", "A2", 100);
        assertNull(result);
    }

    @Test
    void testTransferToUnknownAccount() {
        banking.createAccount(1, "A1");
        assertNull(banking.transfer(2, "A1", "X", 50));
    }

    @Test
    void testTransferNegativeAmount() {
        banking.createAccount(1, "A1");
        banking.createAccount(1, "A2");
        assertNull(banking.transfer(2, "A1", "A2", -10));
    }

    @Test
    void testTransferSameAccount() {
        banking.createAccount(1, "A1");
        assertNull(banking.transfer(2, "A1", "A1", 100));
    }

    // ---------------------------------------------------------------
    // 4. TOP SPENDERS TESTS
    // ---------------------------------------------------------------
    @Test
    void testTopSpendersBasic() {
        banking.createAccount(1, "A1");
        banking.createAccount(1, "A2");
        banking.createAccount(1, "A3");

        banking.deposit(2, "A1", 100);
        banking.deposit(2, "A2", 200);

        banking.transfer(3, "A2", "A1", 50);   // A2 spends 50

        List<String> result = banking.topSpenders(4, 2);

        assertEquals(List.of("A2", "A1"), result);
    }

    @Test
    void testTopSpendersNMoreThanAccounts() {
        banking.createAccount(1, "A1");
        List<String> result = banking.topSpenders(2, 10);
        assertTrue(result.contains("A1"));
    }

    @Test
    void testTopSpendersNoAccounts() {
        List<String> result = banking.topSpenders(1, 5);
        assertTrue(result.isEmpty());
    }

    // ---------------------------------------------------------------
    // 5. SCHEDULE PAYMENT TESTS
    // ---------------------------------------------------------------
    @Test
    void testSchedulePaymentSuccess() {
        banking.createAccount(1, "A1");
        banking.deposit(2, "A1", 500);

        String id = banking.schedulePayment(3, "A1", 100, 10);
        assertNotNull(id);
    }

    @Test
    void testSchedulePaymentInsufficientBalance() {
        banking.createAccount(1, "A1");

        String id = banking.schedulePayment(2, "A1", 300, 10);
        assertNull(id);
    }

    @Test
    void testSchedulePaymentBadAmount() {
        banking.createAccount(1, "A1");
        assertNull(banking.schedulePayment(3, "A1", -10, 10));
    }

    @Test
    void testSchedulePaymentAccountNotFound() {
        assertNull(banking.schedulePayment(1, "X", 100, 10));
    }

    // ---------------------------------------------------------------
    // 6. CANCEL PAYMENT TESTS
    // ---------------------------------------------------------------
    @Test
    void testCancelPaymentSuccess() {
        banking.createAccount(1, "A1");
        banking.deposit(2, "A1", 500);

        String pid = banking.schedulePayment(3, "A1", 100, 10);
        assertTrue(banking.cancelPayment(4, "A1", pid));
    }

    @Test
    void testCancelPaymentInvalidId() {
        banking.createAccount(1, "A1");
        assertFalse(banking.cancelPayment(2, "A1", "XYZ"));
    }

    @Test
    void testCancelPaymentUnknownAccount() {
        assertFalse(banking.cancelPayment(1, "X", "p1"));
    }

    // ---------------------------------------------------------------
    // 7. MERGE ACCOUNTS TESTS
    // ---------------------------------------------------------------
    @Test
    void testMergeSuccess() {
        banking.createAccount(1, "A1");
        banking.createAccount(1, "A2");

        banking.deposit(2, "A1", 100);
        banking.deposit(2, "A2", 200);

        assertTrue(banking.mergeAccounts(3, "A1", "A2"));
        assertEquals(300, banking.getBalance(4, "A1", 4));
    }

    @Test
    void testMergeUnknownAccount() {
        banking.createAccount(1, "A1");
        assertFalse(banking.mergeAccounts(2, "A1", "X"));
    }

    @Test
    void testMergeSameAccount() {
        banking.createAccount(1, "A1");
        assertFalse(banking.mergeAccounts(2, "A1", "A1"));
    }

    // ---------------------------------------------------------------
    // 8. GET BALANCE TESTS
    // ---------------------------------------------------------------
    @Test
    void testGetBalanceBasic() {
        banking.createAccount(1, "A1");
        banking.deposit(2, "A1", 100);
        assertEquals(100, banking.getBalance(3, "A1", 3));
    }

    @Test
    void testGetBalanceUnknownAccount() {
        assertNull(banking.getBalance(1, "X", 1));
    }

    @Test
    void testGetBalanceBeforeTimestamp() {
        banking.createAccount(10, "A1");
        assertEquals(0, banking.getBalance(5, "A1", 5));
    }

    @Test
    void testGetBalanceFutureTimestamp() {
        banking.createAccount(1, "A1");
        banking.deposit(2, "A1", 100);
        assertEquals(100, banking.getBalance(50, "A1", 50));
    }

    // ---------------------------------------------------------------
    // 9. CONCURRENCY TESTS (if implementation is thread-safe)
    // ---------------------------------------------------------------
    @Test
    void testConcurrentDeposits() throws Exception {
        banking.createAccount(1, "A1");

        Runnable task = () -> banking.deposit(2, "A1", 10);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(task));
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        // Expect exactly 1000 deposited (100 * 10)
        assertEquals(1000, banking.getBalance(10, "A1", 10));
    }
}

