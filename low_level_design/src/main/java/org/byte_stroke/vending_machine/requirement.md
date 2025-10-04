# Getting Ready: Vending Machine
Understand the vending machine problem and learn the questions to further simplify this problem.

## Problem definition
A vending machine is an automated self-service unit that provides users with products such as snacks, beverages, and chocolates. The machine contains multiple racks with products placed in slots. Users interact with the machine by inserting money, selecting the product they wish to buy, and receiving the selected item if the transaction is valid. The vending machine manages product dispensing based on the selection and payment, handles change, and maintains the state of its inventory.

In this LLD interview case study, your focus will be on:

* Managing the inventory of products using racks and slots within a vending machine.

* Handling user interactions for inserting money, selecting products, and dispensing items.

* Processing payments securely, including calculating and returning change for cash transactions.

* Managing different machine states (idle, accepting money, dispensing).

## Expectations from the interviewee
Although the vending machine problem is a simpler design problem asked in interviews, the interviewer still has some expectations. The following provides an overview of what the interviewer wants to hear you discuss in more detail during the interview.

### States of the vending machine
An interviewer would also expect you to discuss the different states of the vending machine. You may ask the following set of questions:

* What function do the vending machines perform? Alternatively, how many different states can the vending machines have?

* After inserting money into the machine, what does the system do?

* Who presses the vending machine button, and what happens after pressing it?

* What does the dispense function do?

* If the vending machine is in a dispense state, is it possible to insert money?

* If you are in NoMoneyInsertedState and try to select a product without paying money, would you be able to select a product?

### Money handling
One of the most significant attributes of the vending machine system is how it receives, calculates, and returns money. You may ask the interviewer the questions listed below:

* What should the system do if we pay less money than the product price?

* What should the system do if we pay more money than the product price?

* Can the credit card be used to input money or can only cash be used?

### Design approach
We will design this vending machine system using a bottom-up approach:

* First, we’ll identify simple core entities like Product, Rack, Slot, and Payment, and define their responsibilities.

* Next, we’ll model inventory and transaction handling, including product management, payment processing, and change return.

* Finally, we’ll design the overall VendingMachine as a state-driven system, ensuring that our design is modular and extensible by following SOLID principles.

This approach will also consider edge cases, concurrency, and future enhancements such as digital payments or real-time inventory updates. Diagrams and code will be used to illustrate the design in later lessons.

## Requirements for the Vending Machine
Look at the requirements of the vending machine.

In this lesson, we outline the functional and operational requirements for the Vending Machine system. Clearly identifying and understanding requirements is essential to define the system’s scope and ensure a robust, user-friendly design.

We’ll use the notational convention to identify each requirement with a unique label “Rn,” where “R” is short for Requirement and “n” is a natural number.

### Requirement collection
The requirements for the vending machine problem are defined below:
* R1: The vending machine stores and manages different types of products, each placed at a unique slot within the machine.
* R2: The vending machine can be in one of these three states:
  * NoMoneyInsertedState: There is no money inserted into the machine.
  * MoneyInsertedState: Money is inserted into the machine.
  * DispenseState: The machine gives out the product.
* R3: The system has two primary actors: Customer and Operator.
* R4: The Admin can add new products to the machine or remove products.
* R5: The system should allow the users to select a product they want to purchase from the machine by specifying the rack number.
* R6: Users can insert money (cash) into the machine.
* R7: The system should be able to calculate the money inserted into the machine.
* R8: The system should check whether the user inserted the exact amount required for the specific product into the machine.
* R9: If the amount exceeds the product price, the system should change the user back and dispense the product.
* R10: If the amount exceeds the product price, the system should display an error message and return the money.
We’ve identified our requirements for the problem, and in the next lesson, we will define the class diagram of our vending machine system.
