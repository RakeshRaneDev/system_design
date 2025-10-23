package org.byte_stroke.atm_system.educative;

public class ATMApplication {
    public static void main(String[] args) {
        // set up the bank
        Bank bank = new Bank("Sample bank", "SB001");

        BankAccount acc1 = new SavingAccount(1001, 1200);
        BankAccount acc2 = new CurrentAccount(1002, 8000.0);

        AtmCard card1 = new AtmCard("123456", "Rakesh",  123, "12/27");
        AtmCard card2 = new AtmCard("097685", "Ritika",  000, "08/28");

        User user1 = new User(card1, acc1);
        User user2 = new User(card2, acc2);


        // setup atm
        ATM atm = ATM.getInstance();
        atm.initializeATM(20000, 100, 150, 250);

        System.out.println("== scenario 1: Alice - balance inquiry ====");
        atm.setActiveUser(user1);
        atm.getCurrentAtmState().insertCard(atm, user1.getCard());
        atm.getCurrentAtmState().authenticatePin(atm , user1.getCard(), 123);
        atm.getCurrentAtmState().selectOption(atm, TransactionType.BALANCE_INQUIRY);
        atm.getCurrentAtmState().displayBalance(atm, user1.getCard());
        atm.getCurrentAtmState().returnCard(atm);

        System.out.println("== scenario 2: Alice -  with drww ====");
        atm.setActiveUser(user1);
        atm.getCurrentAtmState().insertCard(atm, user1.getCard());
        atm.getCurrentAtmState().authenticatePin(atm , user1.getCard(), 123);
        atm.getCurrentAtmState().selectOption(atm, TransactionType.CASH_WITH_DRAW);
        atm.getCurrentAtmState().cashWithDraw(atm,user1.getCard(), 100 );
        atm.getCurrentAtmState().displayBalance(atm, user1.getCard());
        atm.getCurrentAtmState().returnCard(atm);

        System.out.println("\n == scenario 3: Alice - trasfer ====");
        atm.setActiveUser(user1);
        atm.getCurrentAtmState().insertCard(atm, user1.getCard());
        atm.getCurrentAtmState().authenticatePin(atm , user1.getCard(), 123);
        atm.getCurrentAtmState().selectOption(atm, TransactionType.FUNDS_TRANSFER);
        atm.getCurrentAtmState().transferMoney(atm, user1.getCard(), acc2, 100);
        atm.getCurrentAtmState().displayBalance(atm, user1.getCard());
        atm.getCurrentAtmState().returnCard(atm);

        System.out.println("\n == scenario 3: Alice - change pin ====");
        atm.setActiveUser(user1);
        atm.getCurrentAtmState().insertCard(atm, user1.getCard());
        atm.getCurrentAtmState().authenticatePin(atm , user1.getCard(), 123);
        atm.getCurrentAtmState().selectOption(atm, TransactionType.CHANGE_PIN);
        atm.getCurrentAtmState().pinChange(atm, user1.getCard(), 222);
        atm.getCurrentAtmState().returnCard(atm);


    }
}
