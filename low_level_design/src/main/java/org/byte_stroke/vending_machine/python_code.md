# Code for the Vending Machine
Write the object-oriented code to implement the design of the vending machine problem.
We’ve reviewed different aspects of the vending machine problem and observed the attributes attached to the problem using various UML diagrams. Let us now explore the more practical side of things, where we will work on implementing the vending machine using multiple languages. This is usually the last step in an object-oriented design interview process.
We have chosen the following languages to write the skeleton code of the different classes present in the vending machine:

## Vending machine classes
This section will provide the skeleton code of the classes designed in the class diagram lesson.
> Note: For simplicity, we are not defining getter and setter functions. The reader can assume that all class attributes are private, accessed through their respective public getter methods, and modified only through their public method functions.

### Enumerations
The following code defines the enumeration used in the vending machine system:
```
from enum import Enum

class ProductType(Enum):
    CHOCOLATE = 1
    SNACK = 2
    BEVERAGE = 3
    OTHER = 4
```

### State
The State interface declares the two user-facing operations whose behavior varies by the machine’s current mode. Each concrete state class implements these methods, but leaves their bodies empty here as placeholders for the real logic in VendingMachine.
```
# ===== State Interface =====
class State(ABC):
    @abstractmethod
    def insert_money(self, vm, amount):
        pass

    @abstractmethod
    def select_product(self, vm, rack_number):
        pass

# ===== NoMoneyInsertedState =====
class NoMoneyInsertedState(State):
    def insert_money(self, vm, amount):
        # to be implemented: record amount and switch to MoneyInsertedState
        pass

    def select_product(self, vm, rack_number):
        # to be implemented: reject selection when no money
        pass

# ===== MoneyInsertedState =====
class MoneyInsertedState(State):
    def insert_money(self, vm, amount):
        # to be implemented: add to current balance
        pass

    def select_product(self, vm, rack_number):
        # to be implemented: check availability and payment, then dispense or refund
        pass

# ===== DispenseState =====
class DispenseState(State):
    def insert_money(self, vm, amount):
        # to be implemented: defer until dispensing completes
        pass

    def select_product(self, vm, rack_number):
        # to be implemented: defer until dispensing completes
        pass
```


### Product, rack, and inventory
At the heart of our vending-machine model are three classes that work together to represent what’s in each slot and where:
* Product encapsulates the details of an item for sale—its unique ID, display name, price, and category (chocolate, snack, beverage, or other).
* Rack represents a single slot in the machine. It knows which Product it holds and how many units remain. When you load or dispense stock, you do so by interacting with the appropriate rack.
* Inventory serves as the lookup service for every rack. It maintains a map from rack numbers to Rack instances, so when the machine needs to check availability or list its contents, it simply asks the Inventory for rack # N (or for a list of all racks).
Together, these classes cleanly separate “what the items are” (Product), “where they live” (Rack), and “how to find them” (Inventory).
The definition of these classes is provided below:

```
# ===== Product =====
class Product:
    def __init__(self, id, name, price, type_): pass
    def get_id(self): pass
    def get_name(self): pass
    def get_price(self): pass
    def get_type(self): pass

# ===== Rack =====
class Rack:
    def __init__(self, rack_number): pass
    def get_rack_number(self): pass
    def is_empty(self): pass
    def load_product(self, product, qty): pass
    def peek_product(self): pass
    def dispense_one(self): pass

# ===== Inventory =====
class Inventory:
    def __init__(self): pass
    def add_rack(self, rack): pass
    def get_rack(self, rack_number): pass
    def all_racks(self): pass
```

### Vending machine
The VendingMachine class is the central controller of our system. Implemented as a Singleton, it encapsulates:

* Three State instances (No-Money, Money-Inserted, Dispense) which it creates up front and between which it switches.
* currentState – the active State object.
* currentAmount – running total of cash the user has put in.
* selectedRack – the rack number the user has picked (–1 if none).
* inventory – the Inventory instance that owns all Rack objects.
All user calls (insertMoney and selectProduct) are simply delegated to the currentState. The machine itself handles the actual dispensing/refunding and resets back to the No-Money state when each transaction completes. Administrative methods (addRack/loadProduct) sit on the context here, not in the State interface.
The definition of this class is given below:

```
class VendingMachine:
    _instance = None

    def __init__(self):
        self._no_money_state = None  # NoMoneyInsertedState()
        self._money_inserted_state = None  # MoneyInsertedState()
        self._dispense_state = None  # DispenseState()

        self._current_state = self._no_money_state
        self._current_amount = 0.0
        self._selected_rack = -1
        self._inventory = None  # Inventory()

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = VendingMachine()
        return cls._instance

    def get_no_money_state(self): return self._no_money_state
    def get_money_inserted_state(self): return self._money_inserted_state
    def get_dispense_state(self): return self._dispense_state

    def set_state(self, state): pass
    def get_current_amount(self): return self._current_amount
    def add_to_current_amount(self, amt): pass
    def get_inventory(self): return self._inventory
    def set_selected_rack(self, rack): pass

    def insert_money(self, amount):
        self._current_state.insert_money(self, amount)

    def select_product(self, rack_number):
        self._current_state.select_product(self, rack_number)

    def dispense_product(self): pass
    def refund(self): pass
    def add_rack(self, rack): pass
    def load_product(self, rack_number, product, qty): pass
    def show_inventory(self): pass
```
## Executable Code: Vending Machine
Below is a fully self-contained, runnable program in Java, C#, C++, Python, and JavaScript that demonstrates the core workflows of the Vending Machine system. The main driver code of the system resides in the Driver.java, Driver.cs, Driver.py, Driver.cpp, and Driver.js for all respective languages. You can click on the “Run” button to execute the codes.
What does this code show
* System initialization: Sets up the vending machine with racks, products, and their quantities.
* Scenario 1 (Customer buys a product): The customer inserts money, selects a product using the rack number, and either receives the product and change (if any) or an error message (if payment is insufficient).
* Scenario 2 (Operator adds/removes a product): The operator restocks a product or removes a product from the machine.
* Scenario 3 (Handling edge cases): Demonstrates behaviors such as underpayment, exact payment, and overpayment, as well as selecting an empty rack.

```
from VendingMachine import VendingMachine
from Rack import Rack
from Product import Product
from ProductType import ProductType

if __name__ == "__main__":
    vm = VendingMachine.getInstance()

    vm.addRack(Rack(1))
    vm.addRack(Rack(2))
    vm.addRack(Rack(3))

    choc = Product(101, "Chocolate Bar", 1.50, ProductType.CHOCOLATE)
    snack = Product(102, "Potato Chips", 2.00, ProductType.SNACK)
    bev = Product(103, "Soda Can", 2.50, ProductType.BEVERAGE)

    vm.loadProduct(1, choc, 5)
    vm.loadProduct(2, snack, 3)
    vm.loadProduct(3, bev, 2)

    vm.showInventory()

    print("========== Scenario 1: Exact Payment ==========")
    vm.insertMoney(1.50)
    vm.selectProduct(1)

    print("========== Scenario 2: Overpayment & Change ==========")
    vm.insertMoney(3.00)
    vm.selectProduct(2)

    print("========== Scenario 3: Underpayment & Refund ==========")
    vm.insertMoney(1.00)
    vm.selectProduct(3)

    print("========== Scenario 4: Deplete Rack 3 & Retry ==========")
    vm.insertMoney(5.00)
    vm.selectProduct(3)
    vm.insertMoney(2.50)
    vm.selectProduct(3)
    vm.insertMoney(2.50)
    vm.selectProduct(3)

    vm.showInventory()
