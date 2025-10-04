# Class Diagram for the Vending Machine
Learn to create a class diagram for the vending machine using the bottom-up approach.
In this lesson, we use a bottom-up approach to identify and design the classes and interfaces for our Vending Machine system. The focus is clear responsibility separation, applying the State design pattern, and modeling relationships that reflect real-world constraints. Let’s explore each component and how they work together to realize our requirements.

## Components of a vending machine
As mentioned, we should design the vending machine using a bottom-up approach.

### State Pattern and State
To manage the Vending Machine’s changing behavior depending on its current mode, we use the State design pattern. This pattern encapsulates the machine’s different operational “states” as separate classes, each handling user actions in its way.

### State interface
We define a State interface (or abstract base class), which exposes the two main user-facing actions:
* insertMoney(amount): For inserting cash into the machine.
* selectProduct(rackNumber): For selecting a product to purchase.
The machine is in exactly one of the following three concrete states at any moment. Each state provides its logic for these actions:
* NoMoneyInsertedState
  * insertMoney: Records the inserted amount and transitions the machine to the MoneyInsertedState.
  * selectProduct: Rejects the request with a message (e.g., “Please insert money first”).
* MoneyInsertedState
  * insertMoney: Adds to the current balance.
  * selectProduct:
  * Checks if the selected product is available and compares the current balance to the product price:
    * Underpaid: Returns all inserted money and resets to NoMoneyInsertedState.
    * Exact payment: Dispenses the selected product and transitions to DispenseState.
    * Overpayment: Dispenses the product, returns the change, and transitions to DispenseState.
* DispenseState
  * insertMoney: Does not accept any new actions; prompts the user to wait (e.g., “Please wait, dispensing in progress…”).
  * selectProduct: Same as above; defers all actions while dispensing.
  * After the product (and any change) is delivered, the machine resets to NoMoneyInsertedState.

![State interface and its subclasses.png](images%2FState%20interface%20and%20its%20subclasses.png)

Every state implements some functions, as shown below:
> Note: According to the implementation of the State design pattern, all functions will be available in each state. However, it is not necessary that every function has a meaningful definition for that particular state.

## Product
The Product class contains the details of a particular product available in the vending machine. Each product has name, id, price, and its type associated with it as presented below:
![Product class.png](images%2FProduct%20class.png)

## Rack
The Rack class is used to identify the location of the products in the vending machine. Every rack has a specific rackNumber as an identifier, a product it has and the quantity of the product. The class representation is provided below:
![Rack class.png](images%2FRack%20class.png)

## Inventory
The Inventory class manages a collection of racks inside the vending machine. Internally, it keeps a map from each rack number to its corresponding Rack object. It provides three core operations:
* addRack(Rack rack) – register a new rack in the machine
* getRack(int rackNumber) – look up and return the rack for the given position (or null if none exists)
* allRacks() – retrieve every rack currently in the machine
This design makes it easy to load, dispense, and inspect products by rack number without exposing any lower-level list or count fields.
The class representation is provided below:
![Inventory class.png](images%2FInventory%20class.png)

## VendingMachine
The VendingMachine class is the central context in our State‐pattern design. It is a Singleton (only one instance exists) and holds:
* Current State (State)
* Current Balance (the total cash inserted so far)
* Inventory (the collection of racks and their contents)

All external calls—insertMoney(amount) and selectProduct(rackNumber)—simply delegate to whatever State object is active. The machine itself handles:
* State transitions (No-Money → Money-Inserted → Dispense → No-Money)
* Actual dispensing/refunding logic (once the state says it’s OK to dispense)
* Admin operations (loading racks, collecting cash)—kept entirely outside the State interface.
By pushing only the two user actions into the State subclasses, and keeping everything else in the context, we get a clear separation of “what changes when” vs. “how things actually happen.” The class representation is provided below:
![VendingMachine class.png](images%2FVendingMachine%20class.png)

## Enumeration
The enumeration required to design the vending machine system is provided below:

### ProductType
We need to create an enumeration to keep track of the type of product, whether it is a chocolate, snack, beverage, or other.
![Product class.png](images%2FProduct%20class.png)

## Relationship between the classes
Now, we’ll discuss the relationships between the classes we have defined above in our vending machine.

## Composition
We use composition to show strong “owns‐a” relationships wherever one object cannot meaningfully exist without its container:
### VendingMachine ▶ Inventory
* The machine contains exactly one Inventory instance.
* If the machine is torn down, its inventory goes with it.
### Inventory ▶ Rack
* The inventory manages a collection of racks (one per slot).
* Each Rack lives and dies inside its Inventory.
### Rack ▶ Product
* A rack always holds a specific Product (plus a quantity).
* You cannot have a rack without knowing what product it contains.
Visually, each arrow is a filled-diamond at the container end:
![The composition relationship between classes.png](images%2FThe%20composition%20relationship%20between%20classes.png)


## Aggregation
The class diagram has the following aggregation relationships:
* The VendingMachine class contains the State interface.

![The aggregation relationship between classes.png](images%2FThe%20aggregation%20relationship%20between%20classes.png)

## Association
Besides the strong ownership shown by our composition arrows, we also have simple “uses-a” links between classes. In UML these are unfilled‐arrow or line associations with no diamond:
* VendingMachine → Inventory
The machine holds one Inventory object and calls its lookup methods to find racks.
* VendingMachine → State
The machine keeps a reference to its current State (No-Money, Money-Inserted, or Dispense) and delegates insertMoney/selectProduct to it.
* Inventory → Rack
Internally, Inventory stores many Rack instances (one per slot) and looks them up by rack number.
* Rack → Product
Each rack refers to exactly one Product (plus a quantity) when it’s loaded.
Visually, you’d draw each of these as a straight line (or arrow) from the “user” class to the “used” class, with multiplicities as needed.
![The association relationship between classes.png](images%2FThe%20association%20relationship%20between%20classes.png)

## Inheritance
Inheritance in object-oriented design allows classes to share contracts (interfaces/abstract classes) or behaviors (concrete classes). Our three concrete state classes—NoMoneyInsertedState, MoneyInsertedState, and DispenseState—all implement the common State interface. Referring to the UML under the “State pattern and State” section, this is shown with a solid line and a hollow triangle pointing to the superclass or the State interface.
* State defines the two key operations (insertMoney and selectProduct) without prescribing how they work.
Each concrete state class inherits that contract and provides its own behavior for those methods, according to the vending machine’s current mode.
This arrangement lets the VendingMachine treat all states uniformly (polymorphism), while each subclass specializes the response to user actions in exactly the way its particular state requires.

## Class diagram for the vending machine
In this section, we outline the multiplicity (cardinality) relationships between the main classes in our Vending Machine system. For each relationship, we explain the allowed number of instances on each side and the real-world or design rationale behind the connection. Understanding these relationships is key to modeling how different entities interact and collaborate to support key workflows in the system.
![class_mapping.png](images%2Fclass_mapping.png)
Here is the complete class diagram for our vending machine:
![class_diagram.png](images%2Fclass_diagram.png)

## Design Patterns 
The primary design pattern used in the vending machine system is the State Design Pattern.

### State Design Pattern:
* Purpose: To manage the vending machine’s changing behavior depending on its current mode.
* How it works: The machine’s operational states (NoMoneyInsertedState, MoneyInsertedState, DispenseState) are encapsulated as separate classes implementing a common State interface.
* Benefits: Avoids complex conditional logic (if/else or switch statements), making the code easier to maintain and extend.
* Each state class implements the same two user-facing actions: insertMoney(amount) and selectProduct(rackNumber), but with behavior specific to that state.
No other explicit design patterns are mentioned in this lesson, but the use of Singleton is implied for the VendingMachine class to ensure only one instance exists.
