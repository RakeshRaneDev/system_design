# Getting Ready: The ATM System
Understand the ATM design problem and learn the questions to simplify this problem further.

## Problem definition
An Automated Teller Machine (ATM) is a self-service terminal that enables bank customers to perform financial transactions without interacting with a human teller or visiting a physical bank branch. 
Through ATMs, users can perform essential banking operations such as depositing cash, withdrawing money, checking account balances, and transferring funds between accounts. ATMs are typically placed in accessible public locations like banks, malls, airports, and convenience stores to provide round-the-clock banking services.

In this LLD interview case study, your focus will be on:

* Enabling secure card-based access and PIN verification for users.
* Supporting core transactions: cash withdrawal, deposit, balance inquiry, and fund transfer.
* Managing cash inventory within the ATM, including withdrawal limits and handling insufficient funds.
* Ensuring a seamless and secure user experience through well-coordinated hardware and software components.

> Note: The design can be adapted for modern ATMs with features like contactless cards, mobile integrations, or 
> advanced security (e.g., biometrics), but we will focus on the core ATM functionalities.

## Expectations from the interviewee
During the interview, you are expected to discuss key aspects of the ATM system’s design, clarifying requirements, handling edge cases, and reasoning about hardware-software interactions. Typical areas to cover include:

### ATM components
To better understand an ATM system, you may ask the interviewer the following questions:
* What are the components of an ATM?
* Is the ATM necessarily placed inside a room?
* Does an ATM have a fingerprint scanner?

### ATM features
Different ATMs may vary in terms of features which is why it is important to clear the following questions from the interviewer:
* What is the withdrawal limit of an ATM?
* Can we check our account balance using an ATM?
* Can we set a PIN using an ATM?

### ATM processing
The interviewer would expect you to ask a question regarding the processing of transactions using an ATM. Therefore, you may ask the following questions:
* What happens when the amount entered by the user for withdrawal is greater than the user's account balance?
* What happens when the amount entered by the user for withdrawal is greater than the ATM's cash limit?
* What happens when the amount entered by the user exceeds the total cash present in the ATM?
* Can the ATM be used for online transactions?

## Design approach
We will design this ATM system using a bottom-up approach, following these steps:
* First, we’ll identify and model the core hardware components such as CardReader, Keypad, Screen, CashDispenser, and Printer, defining their responsibilities and interactions.
* Next, we’ll compose these components into larger entities representing the ATM’s operational state, transaction workflows, and the overall ATM machine.
* We’ll model how the ATM interacts with users for authentication, processes various transactions, manages its cash inventory, and ensures secure and reliable operations.
* This approach will allow us to address edge cases, hardware failures, and incorporate SOLID design principles for modularity and maintainability. We’ll use diagrams and code examples to illustrate the design at each step.

## Requirements for the ATM System
Learn about all requirements of the ATM design.
In this lesson, we outline the functional and operational requirements for the ATM System. Identifying and understanding requirements is essential to define the system’s scope and ensure a robust, user-friendly design.
We’ll use the notational convention to identify each requirement with a unique label, “Rn,” where “R” is short for Requirement, and “n” is a natural number.

### Requirement collection
* R1: Each user has a single account at the bank that they can access by inserting their card into the ATM.
* R2: The ATM system consists of the following main components to facilitate user interaction and transaction processing:
  * Card Reader: Reads the user’s ATM card.
  * Keypad: The user can enter their PIN and transaction details.
  * Screen: Displays prompts, messages, and transaction information.
  * Cash Dispenser: Dispenses cash to the user for withdrawal transactions.
  * Printer: Prints transaction receipts for the user.
  * Network Infrastructure: Securely connects the ATM to the bank’s central system to process transactions and access account information.
* R3: The ATM must authenticate users by verifying the PIN entered, ensuring that only authorized customers can access account services.
* R4: All transaction options become available only after successfully authentication the card and PIN.
* R5: Users may have either or both of the following account types:
  * Current account
  * Savings account
* R6: The ATM must allow the following operations for each account type:
  * Balance Inquiry
  * Cash Withdrawal
  * Funds Transfer
* R7: After completing a transaction, the user should have the option to perform another transaction or end their session and retrieve their card.