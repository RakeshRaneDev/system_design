# Class Diagram for the ATM System
Learn to create a class diagram for the ATM design using the bottom-up approach.
In this lesson, we’ll design the classes and then identify the relationship between classes according to the requirements for the ATM design problem.

## Components of the ATM system
As mentioned earlier, we’ll design the class diagram for the ATM using a bottom-up approach.

### User
The User class models a bank customer who uses the ATM. It holds the user’s ATMCard and associated BankAccount and initiates ATM sessions and transactions.
![User class.png](..%2Fimages%2FUser%20class.png)


### ATM card
The ATMCard class encapsulates the details required for card-based authentication and authorization. It uniquely identifies a user’s bank card for ATM operations. ATMCard also secures access through PIN verification.
![ATMCard class.png](..%2Fimages%2FATMCard%20class.png)

### Bank account
The BankAccount class represents a user’s account at the bank. Two concrete types extend it: SavingAccount and CurrentAccount.
* SavingAccount: This derived class represents a saving account with a withdrawal limit.
* CurrentAccount: This derived class represents a current/checking account with a withdrawal limit.
![bank_account.png](..%2Fimages%2Fbank_account.png)

### Bank
The Bank class models a financial institution responsible for accounts and cards. It provides account validation and backend transaction processing. It also associates bank code and name with issued cards and accounts.
![Bank class.png](..%2Fimages%2FBank%20class.png)


### Card reader, cash dispenser, keypad, screen, and printer
* CardReader: This class accepts or rejects a card.
* CashDispenser: This class provides the required amount specified by the user in cash.
* Keypad: This class allows the user to enter the PIN.
* Screen: This class represents a screen that displays information upon insertion of the card.
* Printer: This class represents a printer that prints the transaction/withdrawal receipts for the user.

![Keypad, CashDispenser, CardReader, Screen and Printer classes.png](..%2Fimages%2FKeypad%2C%20CashDispenser%2C%20CardReader%2C%20Screen%20and%20Printer%20classes.png)

### ATM state
The ATMState abstract class and its concrete subclasses implement the State Design pattern for the ATM. Each state handles only those operations valid at that point in the session, enforcing a robust and safe workflow.
* IdleState: ATM is waiting for a card. Handles card insertion.
* HasCardState: Card is inserted. Handles PIN entry/authentication and card return.
* SelectionOptionState: User can select an operation (balance inquiry, withdrawal, transfer, change PIN, or cancel).
* BalanceInquiryState: Handles the balance inquiry process and returns the user to operation selection.
* CashWithdrawalState: Handles withdrawal, including cash dispensing and updating balances.
* TransferMoneyState: Handles funds transfers, including validation and updating accounts.
* ChangePinState: Handles the process of updating a card’s PIN.
![ATMState and its derived classes.png](..%2Fimages%2FATMState%20and%20its%20derived%20classes.png)

### ATM
The ATM class models the full automated teller machine, orchestrating every operation. 
It maintains the current state of operation (Idle, HasCard, SelectionOption, transaction states). It also tracks current cash reserves and the count of available bill denominations (hundreds, fifties, tens).

![The ATM class.png](..%2Fimages%2FThe%20ATM%20class.png)

### ATM room
An ATMRoom class has an ATM and may or may not have a user.
![The ATMRoom class.png](..%2Fimages%2FThe%20ATMRoom%20class.png)


### Enumerations and custom data types
The following provides an overview of the enumerations and custom data types used in this problem.

* ATMStatus: This enumeration keeps track of the following states of an ATM:
 Idle , HasCard , OptionSelected , CashWithdrawal , MoneyTransfer ,DisplayBalance ,ChangePin

* TransactionType: This enumeration represents the following transactions:
BalanceInquiry , CashWithdrawal , FundsTransfer , ChangePIN ,Cancel
![Enums in the ATM design.png](..%2Fimages%2FEnums%20in%20the%20ATM%20design.png)

## Relationship between the classes
### Association
The class diagram has the following association relationships:

* User is associated with (has a reference to) these objects but does not “own” their life cycle (they can exist without the user object in code).
  * ATMCard (one-to-one)
  * BankAccount (one-to-one)

* ATM is associated with:
  * Bank (typically for backend operations or validation; not always a direct attribute in your code but conceptually, for real transaction validation)
  * ATMState (the current session state; owned but could be swapped, so this is a weak composition or aggregation)

* User (the current active user during a session; reference, not strong ownership)
  * ATMCard (the currently inserted card in the session)
  * ATMCard is associated with (via the user) a BankAccount (conceptually, not as a direct field).
![association relationship between classes.png](..%2Fimages%2Fassociation%20relationship%20between%20classes.png)
### Composition
The class diagram has the following composition relationships.

* ATM is composed of the following hardware components:
CardReader , CashDispenser , Keypad ,Screen , Printer
* These are usually instantiated and owned by the ATM object. If the ATM is destroyed, these components cease to exist as well. This is a strong (composition) relationship.
![composition relationship between classes.png](..%2Fimages%2Fcomposition%20relationship%20between%20classes.png)

### Inheritance
The following classes show an inheritance relationship:
SavingAccount and CurrentAccount both extend the abstract class BankAccount.
All concrete state classes (IdleState, HasCardState, SelectionOptionState, BalanceInquiryState, CashWithdrawalState, TransferMoneyState, ChangePinState) extend the abstract class ATMState.

## Class diagram for the ATM System
In this section, we outline the multiplicity (cardinality) relationships between the main classes in our ATM system. For each relationship, we explain the allowed number of instances on each side and the real-world or design rationale behind the connection. Understanding these relationships is key to modeling how different entities interact and collaborate to support key workflows in the system.
![class_map.png](..%2Fimages%2Fclass_map.png)

Here’s the complete class diagram for our ATM design:
![class_diagram.png](..%2Fimages%2Fclass_diagram.png)


## Design pattern
The following design patterns have been used in the class diagram:
* The Singleton design pattern: This pattern ensures the existence of a single instance of the ATM at a given moment that can be accessed by multiple users, due to the shared nature of the ATM components.
* The State design pattern: This pattern enables the ATM to alter its behavior based on the internal changes in the machine. This way, an ATM can transition from one state to another, like switching from an idle state to displaying an account balance or money withdrawal state, and as soon as all the operations have been performed, it can switch back to the initial idle state.

The following design patterns can also be used to design ATM:
* The Composite design pattern can be used to combine different components of the ATM along with their functionalities.
* The Builder design pattern allows the same processes for a complex object to have different representations. In the ATM system, it can help separate different kinds of transactions like withdrawals, deposits, etc.
We have completed the class diagram of the ATM system according to the requirements. Now, let's design its sequence diagram in the next lesson.



