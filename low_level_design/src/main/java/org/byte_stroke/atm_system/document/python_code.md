# Code for the ATM System
Write the object-oriented code to implement the design of the ATM problem.

Using various UML diagrams, we’ve covered different aspects of the ATM and observed the attributes attached to the problem. Let’s now explore the more practical side of things, where we will work on implementing the ATM using multiple languages. 
This is usually the last step in an object-oriented design interview process.

## Enumerations
The following code defines the enumeration used in the ATM system.
ATMStatus: This enumeration keeps track of the following states of an ATM:
* Idle
* Card inserted by the user
* Option selected
* Cash withdrawal
* Money transfer
* Display the account balance

```
from enum import Enum

class ATMStatus(Enum):
    Idle = 1
    HasCard = 2
    SelectionOption = 3
    Withdraw = 4
    TransferMoney = 5
    BalanceInquiry = 6
    ChangePin = 7

class TransactionType(Enum):
    BalanceInquiry = 1
    CashWithdrawal = 2
    FundsTransfer = 3
    ChangePIN = 4
    Cancel = 5
```

## User and ATM card
The User class stores the user's ATMcard and bank account, where the ATMCard class holds the card number, customer name, card expiration date, and PIN. The definitions of these classes are provided below:

```
class User:
    def __init__(self, card, account):
        self._card = card
        self._account = account

    def get_card(self):
        return self._card

    def get_account(self):
        return self._account

class ATMCard:
    def __init__(self, card_number, customer_name, expiry, pin):
        self._card_number = card_number
        self._customer_name = customer_name
        self._card_expiry_date = expiry  # Simplified
        self._pin = pin

    def validate_pin(self, entered_pin):
        return self._pin == entered_pin

    def set_pin(self, new_pin):
        self._pin = new_pin

    def get_card_number(self):
        return self._card_number

    def get_customer_name(self):
        return self._customer_name
}
```

## Bank and bank account
The Bank class represents a bank having a name and code. 
The BankAccount class represents a bank account with two child classes: SavingAccount and CurrentAccount. 
These derived classes provide a method for getting the withdrawal limit. The definitions of these classes are provided below:

```
class Bank:
    def __init__(self, name, code):
        self._name = name
        self._bank_code = code

    def get_bank_code(self):
        return self._bank_code

from abc import ABC, abstractmethod

class BankAccount(ABC):
    def __init__(self, account_number, balance):
        self._account_number = account_number
        self._available_balance = balance

    def get_available_balance(self):
        return self._available_balance

    def withdraw(self, amount):
        if amount > 0 and amount <= self._available_balance and amount <= self.get_withdraw_limit():
            self._available_balance -= amount
            return True
        return False

    def transfer(self, to_account, amount):
        if amount > 0 and amount <= self._available_balance and amount <= self.get_withdraw_limit():
            self._available_balance -= amount
            to_account._available_balance += amount
            return True
        return False

    @abstractmethod
    def get_withdraw_limit(self):
        pass

    def get_account_number(self):
        return self._account_number
        
from BankAccount import BankAccount

class SavingAccount(BankAccount):
    def __init__(self, account_number, balance):
        super().__init__(account_number, balance)

    def get_withdraw_limit(self):
        return 1000.0

from BankAccount import BankAccount

class CurrentAccount(BankAccount):
    def __init__(self, account_number, balance):
        super().__init__(account_number, balance)

    def get_withdraw_limit(self):
        return 5000.0
```
## Card reader, card dispenser, printer, screen, and keypad
The CardReader, CashDispenser, Keypad, Screen, and Printer classes compose the ATM and have the following functionalities:
* CardReader: Reads the card inserted by the user.
* CashDispenser: Dispenses cash upon withdrawal request.
* Keypad: Used by the user to enter the PIN for authentication and to input amounts or new PIN.
* Screen: Displays messages.
* Printer: Prints receipts.
The definitions of these classes are provided below:

```
class CardReader:
    def read_card(self, card):
        print("[ATM] Reading card:", card.get_card_number())
        return card is not None

class CashDispenser:
    def dispense_cash(self, amount, atm):
        if amount > 0 and amount <= atm.get_atm_balance():
            # Try to dispense bills in denominations (simple greedy)
            left = amount
            hundreds = min(left // 100, atm.get_no_of_hundred_dollar_bills())
            left -= hundreds * 100
            fifties = min(left // 50, atm.get_no_of_fifty_dollar_bills())
            left -= fifties * 50
            tens = min(left // 10, atm.get_no_of_ten_dollar_bills())
            left -= tens * 10
            if left == 0:
                atm.set_no_of_hundred_dollar_bills(atm.get_no_of_hundred_dollar_bills() - hundreds)
                atm.set_no_of_fifty_dollar_bills(atm.get_no_of_fifty_dollar_bills() - fifties)
                atm.set_no_of_ten_dollar_bills(atm.get_no_of_ten_dollar_bills() - tens)
                atm.set_atm_balance(atm.get_atm_balance() - amount)
                print(f"[ATM] Dispensing ${amount} as: {hundreds}x$100, {fifties}x$50, {tens}x$10.")
                return True
        print("[ATM] Unable to dispense the requested cash. Insufficient bills or balance.")
        return False

class Keypad:
    # For demonstration, values are passed directly in Driver
    def get_input(self, prompt):
        print(prompt, end="")
        return ""  # Not used, see Driver

class Screen:
    def show_message(self, message):
        print("[SCREEN]", message)

class Printer:
    def print_receipt(self, details):
        print("[RECEIPT]", details)

```


## ATM state
ATMState is an abstract class extended by the following concrete classes:
* IdleState (handles card insertion)
* HasCardState (handles PIN authentication)
* SelectionOptionState (handles transaction selection)
* BalanceInquiryState (handles displaying balance)
* CashWithdrawalState (handles cash withdrawal)
* TransferMoneyState (handles funds transfer)
* ChangePinState (handles PIN changes)
All of these derived classes override the methods relevant to their respective states, including returnCard() and exit().
The definitions of these classes are provided below:

```
from abc import ABC

class ATMState(ABC):
    def insert_card(self, atm, card):
        pass

    def authenticate_pin(self, atm, card, pin):
        pass

    def select_operation(self, atm, t_type):
        pass

    def cash_withdrawal(self, atm, card, amount):
        pass

    def display_balance(self, atm, card):
        pass

    def transfer_money(self, atm, card, to_account, amount):
        pass

    def change_pin(self, atm, card, new_pin):
        pass

    def cancel_transaction(self, atm):
        pass

    def return_card(self, atm):
        pass

    def exit(self, atm):
        pass

from ATMState import ATMState
from ATMStatus import ATMStatus

class IdleState(ATMState):
    def insert_card(self, atm, card):
        # Import inside method to break circular import
        from HasCardState import HasCardState

        if atm.get_card_reader().read_card(card):
            atm.set_inserted_card(card)
            atm.set_atm_status(ATMStatus.HasCard)
            atm.set_current_atm_state(HasCardState())
            atm.get_screen().show_message("Please enter your PIN:")
        else:
            atm.get_screen().show_message("Card reading failed.")

from ATMState import ATMState
from ATMStatus import ATMStatus
from SelectionOptionState import SelectionOptionState

class HasCardState(ATMState):
    def authenticate_pin(self, atm, card, pin):
        if card.validate_pin(pin):
            atm.set_authenticated(True)
            atm.set_atm_status(ATMStatus.SelectionOption)
            atm.set_current_atm_state(SelectionOptionState())
            atm.get_screen().show_message("PIN verified. Please select a transaction.")
        else:
            atm.get_screen().show_message("Incorrect PIN. Please try again or cancel.")

    def return_card(self, atm):
        from IdleState import IdleState

        atm.set_inserted_card(None)
        atm.set_authenticated(False)
        atm.set_atm_status(ATMStatus.Idle)
        atm.set_current_atm_state(IdleState())
        atm.get_screen().show_message("Card returned. Thank you.")

from ATMState import ATMState
from ATMStatus import ATMStatus
from TransactionType import TransactionType

class SelectionOptionState(ATMState):
    def select_operation(self, atm, t_type):
        if t_type == TransactionType.BalanceInquiry:
            from BalanceInquiryState import BalanceInquiryState
            atm.set_atm_status(ATMStatus.BalanceInquiry)
            atm.set_current_atm_state(BalanceInquiryState())
        elif t_type == TransactionType.CashWithdrawal:
            from CashWithdrawalState import CashWithdrawalState
            atm.set_atm_status(ATMStatus.Withdraw)
            atm.set_current_atm_state(CashWithdrawalState())
        elif t_type == TransactionType.FundsTransfer:
            from TransferMoneyState import TransferMoneyState
            atm.set_atm_status(ATMStatus.TransferMoney)
            atm.set_current_atm_state(TransferMoneyState())
        elif t_type == TransactionType.ChangePIN:
            from ChangePinState import ChangePinState
            atm.set_atm_status(ATMStatus.ChangePin)
            atm.set_current_atm_state(ChangePinState())
        elif t_type == TransactionType.Cancel:
            atm.get_current_atm_state().return_card(atm)

from ATMState import ATMState
from ATMStatus import ATMStatus
from SelectionOptionState import SelectionOptionState

class CashWithdrawalState(ATMState):
    def cash_withdrawal(self, atm, card, amount):
        acc = atm.get_active_user().get_account()
        acc_limit = acc.get_withdraw_limit()
        if amount <= acc.get_available_balance() and amount <= acc_limit and amount <= atm.get_atm_balance():
            dispensed = atm.get_cash_dispenser().dispense_cash(int(amount), atm)
            if dispensed:
                acc.withdraw(amount)
                atm.get_printer().print_receipt(f"Withdrawn: ${amount} | Remaining balance: ${acc.get_available_balance()}")
                atm.get_screen().show_message("Please collect your cash.")
            else:
                atm.get_screen().show_message("Unable to dispense the requested amount.")
        else:
            atm.get_screen().show_message("Withdrawal denied. Check your balance or limits.")
        # Return to selection state
        atm.set_atm_status(ATMStatus.SelectionOption)
        atm.set_current_atm_state(SelectionOptionState())
        
from ATMState import ATMState
from ATMStatus import ATMStatus
from SelectionOptionState import SelectionOptionState

class TransferMoneyState(ATMState):
    def transfer_money(self, atm, card, to_account, amount):
        from_acc = atm.get_active_user().get_account()
        acc_limit = from_acc.get_withdraw_limit()
        if amount <= from_acc.get_available_balance() and amount <= acc_limit:
            transferred = from_acc.transfer(to_account, amount)
            if transferred:
                atm.get_printer().print_receipt(
                    f"Transferred: ${amount} from Acc#{from_acc.get_account_number()} to Acc#{to_account.get_account_number()} | Your balance: ${from_acc.get_available_balance()}"
                )
                atm.get_screen().show_message("Transfer successful.")
            else:
                atm.get_screen().show_message("Transfer failed.")
        else:
            atm.get_screen().show_message("Transfer denied. Check your balance or limits.")
        # Return to selection state
        atm.set_atm_status(ATMStatus.SelectionOption)
        atm.set_current_atm_state(SelectionOptionState())

from ATMState import ATMState
from ATMStatus import ATMStatus

class BalanceInquiryState(ATMState):
    def display_balance(self, atm, card):
        # Import inside method to avoid circular import
        from SelectionOptionState import SelectionOptionState

        balance = atm.get_active_user().get_account().get_available_balance()
        atm.get_screen().show_message(f"Your current balance is: ${balance}")
        atm.get_printer().print_receipt(f"Balance: ${balance}")
        # Return to selection state
        atm.set_atm_status(ATMStatus.SelectionOption)
        atm.set_current_atm_state(SelectionOptionState())

from ATMState import ATMState
from ATMStatus import ATMStatus
from SelectionOptionState import SelectionOptionState

class ChangePinState(ATMState):
    def change_pin(self, atm, card, new_pin):
        card.set_pin(new_pin)
        atm.get_printer().print_receipt("PIN changed successfully.")
        atm.get_screen().show_message("PIN changed.")
        # Return to selection state
        atm.set_atm_status(ATMStatus.SelectionOption)
        atm.set_current_atm_state(SelectionOptionState())s
```

## ATM
An ATM maintains the following at any given moment:
* A specific operational state, such as Idle, HasCard, SelectionOption, or an active transaction state (e.g., Withdrawal, Transfer, Balance Inquiry, Change PIN)
* Current cash balance
* A limited number of bill denominations: hundred, fifty, and ten dollar bills
It also contains references to all essential hardware components (card reader, cash dispenser, keypad, screen, and printer) and delegates user interactions and transaction processing according to its current state.
The definitions of these classes are provided below:

```
from IdleState import IdleState
from CardReader import CardReader
from CashDispenser import CashDispenser
from Keypad import Keypad
from Screen import Screen
from Printer import Printer
from ATMStatus import ATMStatus

class ATM:
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(ATM, cls).__new__(cls)
            cls._instance._initialized = False
        return cls._instance

    def __init__(self):
        if self._initialized:
            return
        self.current_atm_state = IdleState()
        self.atm_status = ATMStatus.Idle
        self.card_reader = CardReader()
        self.cash_dispenser = CashDispenser()
        self.keypad = Keypad()
        self.screen = Screen()
        self.printer = Printer()

        # Session variables
        self.active_user = None
        self.inserted_card = None
        self.authenticated = False
        self.atm_balance = 0
        self.no_of_hundred_dollar_bills = 0
        self.no_of_fifty_dollar_bills = 0
        self.no_of_ten_dollar_bills = 0

        self._initialized = True

    def display_current_state(self):
        print(f"[ATM] Status: {self.atm_status.name}")

    def initialize_atm(self, atm_balance, no_of_hundred, no_of_fifty, no_of_ten):
        self.atm_balance = atm_balance
        self.no_of_hundred_dollar_bills = no_of_hundred
        self.no_of_fifty_dollar_bills = no_of_fifty
        self.no_of_ten_dollar_bills = no_of_ten
        self.atm_status = ATMStatus.Idle
        self.current_atm_state = IdleState()
        self.active_user = None
        self.inserted_card = None
        self.authenticated = False

    # Getters and setters
    def get_current_atm_state(self):
        return self.current_atm_state

    def set_current_atm_state(self, state):
        self.current_atm_state = state

    def get_atm_status(self):
        return self.atm_status

    def set_atm_status(self, status):
        self.atm_status = status

    def get_atm_balance(self):
        return self.atm_balance

    def set_atm_balance(self, balance):
        self.atm_balance = balance

    def get_no_of_hundred_dollar_bills(self):
        return self.no_of_hundred_dollar_bills

    def set_no_of_hundred_dollar_bills(self, n):
        self.no_of_hundred_dollar_bills = n

    def get_no_of_fifty_dollar_bills(self):
        return self.no_of_fifty_dollar_bills

    def set_no_of_fifty_dollar_bills(self, n):
        self.no_of_fifty_dollar_bills = n

    def get_no_of_ten_dollar_bills(self):
        return self.no_of_ten_dollar_bills

    def set_no_of_ten_dollar_bills(self, n):
        self.no_of_ten_dollar_bills = n

    def get_card_reader(self):
        return self.card_reader

    def get_cash_dispenser(self):
        return self.cash_dispenser

    def get_keypad(self):
        return self.keypad

    def get_screen(self):
        return self.screen

    def get_printer(self):
        return self.printer

    def get_active_user(self):
        return self.active_user

    def set_active_user(self, user):
        self.active_user = user

    def get_inserted_card(self):
        return self.inserted_card

    def set_inserted_card(self, card):
        self.inserted_card = card

    def is_authenticated(self):
        return self.authenticated

    def set_authenticated(self, auth):
        self.authenticated = auth
```

## Executable code: ATM
Below is a fully self-contained, runnable program in Java, C#, C++, Python, and JavaScript that demonstrates the core workflows of the ATM System. The main driver code of the system resides in the Driver.java, Driver.cs, Driver.py, Driver.cpp, and Driver.js for all respective languages. You can click on the “Run” button to execute the codes.
What does this code show
System initialization: Sets up the ATM, users, accounts, cards, and hardware components.
* Scenario 1 (Cardholder checks account balance): The user inserts their card, enters their PIN, and performs a balance inquiry.
* Scenario 2 (Cash withdrawal): The user withdraws cash, and the system dispenses money according to available denominations.
* Scenario 3 (Funds transfer): The user transfers money between accounts and receives a printed receipt.
* Scenario 4 (Change PIN): The user changes their ATM card PIN.
* Scenario 5 (Cancel transaction): The user cancels a transaction after authentication, and the card is returned.

```
from Bank import Bank
from SavingAccount import SavingAccount
from CurrentAccount import CurrentAccount
from ATMCard import ATMCard
from User import User
from ATM import ATM
from TransactionType import TransactionType

def main():
    # Set up bank, accounts, users, cards
    bank = Bank("Sample Bank", "SB001")

    acc1 = SavingAccount(1001, 1200.0)
    acc2 = CurrentAccount(1002, 8000.0)
    card1 = ATMCard("123456", "Alice", "12/27", 1111)
    card2 = ATMCard("654321", "Bob", "08/26", 2222)

    user1 = User(card1, acc1)
    user2 = User(card2, acc2)

    # Set up ATM
    atm = ATM()
    atm.initialize_atm(20000, 100, 40, 50)  # $20,000, 100x$100, 40x$50, 50x$10

    print("=== Scenario 1: Alice - Balance Inquiry ===")
    atm.set_active_user(user1)
    atm.get_current_atm_state().insert_card(atm, user1.get_card())
    atm.get_current_atm_state().authenticate_pin(atm, user1.get_card(), 1111)
    atm.get_current_atm_state().select_operation(atm, TransactionType.BalanceInquiry)
    atm.get_current_atm_state().display_balance(atm, user1.get_card())
    atm.get_current_atm_state().return_card(atm)

    print("\n=== Scenario 2: Alice - Withdraw $500 ===")
    atm.set_active_user(user1)
    atm.get_current_atm_state().insert_card(atm, user1.get_card())
    atm.get_current_atm_state().authenticate_pin(atm, user1.get_card(), 1111)
    atm.get_current_atm_state().select_operation(atm, TransactionType.CashWithdrawal)
    atm.get_current_atm_state().cash_withdrawal(atm, user1.get_card(), 500.0)
    atm.get_current_atm_state().return_card(atm)

    print("\n=== Scenario 3: Bob - Transfer $1000 to Alice ===")
    atm.set_active_user(user2)
    atm.get_current_atm_state().insert_card(atm, user2.get_card())
    atm.get_current_atm_state().authenticate_pin(atm, user2.get_card(), 2222)
    atm.get_current_atm_state().select_operation(atm, TransactionType.FundsTransfer)
    atm.get_current_atm_state().transfer_money(atm, user2.get_card(), acc1, 1000.0)
    atm.get_current_atm_state().return_card(atm)

    print("\n=== Scenario 4: Bob - Change PIN ===")
    atm.set_active_user(user2)
    atm.get_current_atm_state().insert_card(atm, user2.get_card())
    atm.get_current_atm_state().authenticate_pin(atm, user2.get_card(), 2222)
    atm.get_current_atm_state().select_operation(atm, TransactionType.ChangePIN)
    atm.get_current_atm_state().change_pin(atm, user2.get_card(), 9999)  # Bob sets new PIN to 9999
    atm.get_current_atm_state().return_card(atm)

    print("\n=== Scenario 5: Alice - Cancel transaction after PIN ===")
    atm.set_active_user(user1)
    atm.get_current_atm_state().insert_card(atm, user1.get_card())
    atm.get_current_atm_state().authenticate_pin(atm, user1.get_card(), 1111)
    atm.get_current_atm_state().select_operation(atm, TransactionType.Cancel)
    # Card returned, session ends

if __name__ == "__main__":
    main()
```








