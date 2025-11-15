# Python Code for the Parking Lot

Write the code for the classes that we have designed in different languages.
We’ve reviewed the different aspects of the parking lot system and observed the attributes attached to the problem using various UML diagrams. Now, let’s explore the more practical side of things, where we will work on implementing the parking lot system using multiple languages. This is usually the last step in an object-oriented design interview process.

## Enumerations and custom data type

First, we will define all the enumerations required in the parking lot. 
According to the class diagram, two enumerations are used in the system, i.e., PaymentStatus and AccountStatus. The code to implement these enumerations and custom data types is as follows:

```
from enum import Enum, auto

class PaymentStatus(Enum):
    COMPLETED = auto()
    FAILED = auto()
    PENDING = auto()
    UNPAID = auto()
    REFUNDED = auto()

class AccountStatus(Enum):
    ACTIVE = auto()
    CLOSED = auto()
    CANCELED = auto()
    BLACKLISTED = auto()
    NONE = auto()

class TicketStatus(Enum):
    ISSUED = auto()
    IN_USE = auto()
    PAID = auto()
    VALIDATED = auto()
    CANCELED = auto()
    REFUNDED = auto()

class Person:
    def __init__(self):
        self.name = None
        self.address = None
        self.phone = None
        self.email = None

class Address:
    def __init__(self):
        self.zip_code = None
        self.street = None
        self.city = None
        self.state = None
        self.country = None
```
## Parking spots
The first section of the parking lot system that we will work on is the ParkingSpot class, 
which will act as a base class for four different types of parking spots: accessible, compact, large, and motorcycle. This will have an instance of the Vehicle class. The definition of the ParkingSpot class and the classes being derived from it are given below:
```
class ParkingSpot:
    def __init__(self):
        self.id = None
        self.is_free = None
        self.vehicle = None  # type: Vehicle

    def assign_vehicle(self, vehicle):
        pass

    def remove_vehicle(self):
        pass

class Handicapped(ParkingSpot):
    def assign_vehicle(self, vehicle):
        pass

class Compact(ParkingSpot):
    def assign_vehicle(self, vehicle):
        pass

class Large(ParkingSpot):
    def assign_vehicle(self, vehicle):
        pass

class MotorcycleSpot(ParkingSpot):
    def assign_vehicle(self, vehicle):
        pass
```

### Vehicle
Vehicle will be another abstract class, which serves as a parent for four different types of vehicles: car, truck, van, and motor cycle. 
The definition of the Vehicle and its child classes are given below:

```
class Vehicle:
    def __init__(self):
        self.license_no = None
        self.ticket = None  # type: ParkingTicket

    def assign_ticket(self, ticket):
        pass

class Car(Vehicle):
    def assign_ticket(self, ticket):
        pass

class Van(Vehicle):
    def assign_ticket(self, ticket):
        pass

class Truck(Vehicle):
    def assign_ticket(self, ticket):
        pass

class Motorcycle(Vehicle):
    def assign_ticket(self, ticket):
        pass
```
## Account
The Account class will be an abstract class, which will have the actors Admin, as its child class. 
The definition of these classes is given below:

```
class Account:
    def __init__(self):
        self.user_name = None
        self.password = None
        self.person = None
        self.status = None  # type: AccountStatus

    def reset_password(self):
        pass

class Admin(Account):
    def add_parking_spot(self, spot):
        pass

    def add_display_board(self, board):
        pass

    def add_entrance(self, entrance):
        pass

    def add_exit(self, exit):
        pass

    def reset_password(self):
        pass
```
## Display board and parking rate
This section contains the DisplayBoard and ParkingRate classes that only have the composition class with the ParkingLot class. 
This relationship is highlighted in the ParkingLot class. The definition of these classes is given below:

```
class DisplayBoard:
    def __init__(self):
        self.id = None
        self.parking_spots = None  # dict: str -> list of ParkingSpot

    def add_parking_spot(self, spot_type, spots):
        pass

    def show_free_slot(self):
        pass

class ParkingRate:
    def __init__(self):
        self.hours = None
        self.rate = None

    def calculate(self, duration, vehicle, spot):
        pass
```

## Entrance and exit
This section contains the Entrance and Exit classes, both of which are associated with the ParkingTicket class. 
The definition of the Entrance and Exit classes is given below:
```
class Entrance:
    def __init__(self):
        self.id = None

    def get_ticket(self, vehicle):
        pass

class Exit:
    def __init__(self):
        self.id = None

    def validate_ticket(self, ticket):
        pass
```

## Parking ticket
The definition of the ParkingTicket class can be found below. 
This contains instances of the Vehicle, Payment, Entrance and Exit classes:

```
class ParkingTicket:
    def __init__(self):
        self.ticket_no = None
        self.entry_time = None
        self.exit_time = None
        self.amount = None
        self.status = None  # type: TicketStatus

        self.vehicle = None
        self.payment = None  # type: Payment
        self.entrance = None
        self.exit_ins = None
```

## Payment
The Payment class is another abstract class, with the Cash and CreditCard classes as its child. This takes the PaymentStatus enumeration and the dateTime data type to keep track of the payment status and time. 
The definition of this class is given below:

```
class Payment:
    def __init__(self):
        self.amount = None
        self.status = None  # type: PaymentStatus
        self.timestamp = None

    def initiate_transaction(self):
        pass

class Cash(Payment):
    def initiate_transaction(self):
        pass

class CreditCard(Payment):
    def initiate_transaction(self):
        pass
```

## Parking lot
The final class of the parking lot system is the ParkingLot class which will be a Singleton class, meaning the entire system will only have one instance of this class. 
The definition of this class is given below:

```
class ParkingLot:
    _instance = None

    def __init__(self):
        self.id = None
        self.name = None
        self.address = None
        self.parking_rate = None

        self.entrances = None       # dict: str -> Entrance
        self.exits = None           # dict: str -> Exit
        self.spots = None           # dict: int -> ParkingSpot
        self.tickets = None         # dict: str -> ParkingTicket
        self.display_boards = None  # list of DisplayBoard

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = ParkingLot()
        return cls._instance

    def add_entrance(self, entrance):
        pass

    def add_exit(self, exit):
        pass

    def add_parking_spot(self, spot):
        pass

    def add_display_board(self, board):
        pass

    def get_parking_ticket(self, vehicle):
        pass

    def is_full(self, spot_type):
        pass
```

## Executable code: Parking lot system
What this code shows
System initialization (creates a lot, spots, entrances, exits, display boards)
Scenario 1: Customer parks and receives a ticket.
Scenario 2: Customer exits, system calculates fee, payment is processed.
Scenario 3: Another customer enters, parks, and a new customer is rejected when the lot is full.

```
import time
from ParkingLot import ParkingLot
from Handicapped import Handicapped
from Compact import Compact
from Large import Large
from MotorcycleSpot import MotorcycleSpot
from DisplayBoard import DisplayBoard
from Entrance import Entrance
from Exit import Exit
from Car import Car
from Van import Van
from Motorcycle import Motorcycle
from Truck import Truck

def main():
    print("\n====================== PARKING LOT SYSTEM DEMO ======================\n")

    lot = ParkingLot.get_instance()
    lot.add_spot(Handicapped(1))
    lot.add_spot(Compact(2))
    lot.add_spot(Large(3))
    lot.add_spot(MotorcycleSpot(4))

    board = DisplayBoard(1)
    lot.add_display_board(board)

    entrance = Entrance(1)
    exit_panel = Exit(1)

    # SCENARIO 1: CUSTOMER ENTERS, PARKS
    print("\n→→→ SCENARIO 1: Customer enters and parks a car\n")
    car = Car("KA-01-HH-1234")
    print(f"-> Car {car.license_no} arrives at entrance")
    ticket1 = entrance.get_ticket(car)
    print("-> Updating display board after parking:")
    board.update(lot.get_all_spots())
    board.show_free_slot()

    # SCENARIO 2: CUSTOMER EXITS AND PAYS
    print("\n→→→ SCENARIO 2: Customer exits and pays\n")
    print(f"-> Car {car.license_no} proceeds to exit panel")
    time.sleep(1.5)
    exit_panel.validate_ticket(ticket1)
    print("-> Updating display board after exit:")
    board.update(lot.get_all_spots())
    board.show_free_slot()

    # SCENARIO 3: FILLING LOT AND REJECTING ENTRY IF FULL
    print("\n→→→ SCENARIO 3: Multiple customers attempt to enter; lot may become full\n")
    van = Van("KA-01-HH-9999")
    motorcycle = Motorcycle("KA-02-XX-3333")
    truck = Truck("KA-04-AA-9998")
    car2 = Car("DL-09-YY-1234")

    print(f"-> Van {van.license_no} arrives at entrance")
    ticket2 = entrance.get_ticket(van)
    print(f"-> Motorcycle {motorcycle.license_no} arrives at entrance")
    ticket3 = entrance.get_ticket(motorcycle)
    print(f"-> Truck {truck.license_no} arrives at entrance")
    ticket4 = entrance.get_ticket(truck)
    print(f"-> Car {car2.license_no} arrives at entrance")
    ticket5 = entrance.get_ticket(car2)

    print("-> Updating display board after several parkings:")
    board.update(lot.get_all_spots())
    board.show_free_slot()

    # Try to park another car (lot may now be full)
    car3 = Car("UP-01-CC-1001")
    print(f"-> Car {car3.license_no} attempts to park (should be denied if lot is full):")
    ticket6 = entrance.get_ticket(car3)

    board.update(lot.get_all_spots())
    board.show_free_slot()

    print("\n====================== END OF DEMONSTRATION ======================\n")

if __name__ == "__main__":
    main()
```
