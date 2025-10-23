package org.byte_stroke.atm_system.educative;

public class ATM {

    private static ATM atmObject = new ATM();
    private  ATMState currentAtmState;
    private ATMStatus atmStatus;

    private int atmBalance;
    private int numberOfHundredDollarsBills;
    private int numberOfFiftyDollarsBills;
    private int numberOfTenDollarsBills;

    private CardReader cardReader;
    private KeyPad keyPad;
    private Screen screen;
    private Printer printer;
    private CashDispenser cashDispenser;



    // session variable;

    private User activeUser;
    private AtmCard insertedCard;
    private  boolean authenticated;

    private ATM(){
        this.currentAtmState = new IdleState();
        this.atmStatus = ATMStatus.IDLE;
        this.cardReader = new CardReader();
        this.keyPad = new KeyPad();
        this.screen = new Screen();
        this.printer  = new Printer();
        this.cashDispenser = new CashDispenser();
    }

    public  static ATM getInstance(){
        return  atmObject;
    }

    public void displayCurrentAtmState(){
        System.out.println("[ATM State: " + atmStatus);
    }

    public void initializeATM(int atmBalance, int noOfHundred, int noOfFifty, int noOfTen){
        this.currentAtmState = new IdleState();
        this.atmBalance = atmBalance;
        this.numberOfHundredDollarsBills = noOfHundred;
        this.numberOfFiftyDollarsBills = noOfFifty;
        this.numberOfTenDollarsBills = noOfTen;
        this.atmStatus = ATMStatus.IDLE;
        this.activeUser = null;
        this.insertedCard = null;
        this.authenticated = false;
    }


    public ATMState getCurrentAtmState() {
        return currentAtmState;
    }

    public void setCurrentAtmState(ATMState currentAtmState) {
        this.currentAtmState = currentAtmState;
    }

    public ATMStatus getAtmStatus() {
        return atmStatus;
    }

    public void setAtmStatus(ATMStatus atmStatus) {
        this.atmStatus = atmStatus;
    }

    public int getAtmBalance() {
        return atmBalance;
    }

    public void setAtmBalance(int atmBalance) {
        this.atmBalance = atmBalance;
    }

    public int getNumberOfHundredDollarsBills() {
        return numberOfHundredDollarsBills;
    }

    public void setNumberOfHundredDollarsBills(int numberOfHundredDollarsBills) {
        this.numberOfHundredDollarsBills = numberOfHundredDollarsBills;
    }

    public int getNumberOfFiftyDollarsBills() {
        return numberOfFiftyDollarsBills;
    }

    public void setNumberOfFiftyDollarsBills(int numberOfFiftyDollarsBills) {
        this.numberOfFiftyDollarsBills = numberOfFiftyDollarsBills;
    }

    public int getNumberOfTenDollarsBills() {
        return numberOfTenDollarsBills;
    }

    public void setNumberOfTenDollarsBills(int numberOfTenDollarsBills) {
        this.numberOfTenDollarsBills = numberOfTenDollarsBills;
    }

    public CardReader getCardReader() {
        return cardReader;
    }


    public KeyPad getKeyPad() {
        return keyPad;
    }


    public Screen getScreen() {
        return screen;
    }


    public Printer getPrinter() {
        return printer;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public AtmCard getInsertedCard() {
        return insertedCard;
    }

    public void setInsertedCard(AtmCard insertedCard) {
        this.insertedCard = insertedCard;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
