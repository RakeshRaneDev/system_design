# Car rental system classes
This section will provide the skeleton code of the classes designed in the class diagram lesson.

> Note: For simplicity, we are not defining getter and setter functions. The reader can assume that all class attributes are private, accessed through their respective public getter methods, and modified only through their public methods function.

## Enumerations
First, we will define all the enumerations required in the car rental system. According to the class diagram, seven enumerations are used in the system, i.e., VehicleStatus, AccountStatus, ReservationStatus, PaymentStatus, VanType, CarType, and VehicleLogType. The code to implement these enumerations is as follows:
> Note: JavaScript does not support enumerations, so we will use the Object.freeze() method as an alternative that freezes an object and prevents further modifications.
```
from enum import Enum

class MotorcycleType(Enum):
    STANDARD = 1
    CRUISER = 2
    TOURING = 3
    SPORTS = 4
    OFF_ROAD = 5
    DUAL_PURPOSE = 6

class AccountStatus(Enum):
    ACTIVE = 1
    CLOSED = 2
    CANCELED = 3
    BLACKLISTED = 4
    BLOCKED = 5

class ReservationStatus(Enum):
    ACTIVE = 1
    PENDING = 2
    CONFIRMED = 3
    COMPLETED = 4
    CANCELED = 5

class PaymentStatus(Enum):
    UNPAID = 1
    PENDING = 2
    COMPLETED = 3
    CANCELED = 4
    REFUNDED = 5

class VanType(Enum):
    PASSENGER = 1
    CARGO = 2

class CarType(Enum):
    ECONOMY = 1
    COMPACT = 2
    INTERMEDIATE = 3
    STANDARD = 4
    FULL_SIZE = 5
    PREMIUM = 6
    LUXURY = 7

class TruckType(Enum):
    LIGHT_DUTY = 1
    MEDIUM_DUTY = 2
    HEAVY_DUTY = 3

class VehicleLogType(Enum):
    ACCIDENT = 1
    FUELING = 2
    CLEANING_SERVICE = 3
    OIL_CHANGE = 4
    REPAIR = 5
    OTHER = 6
```
## Address, person, and driver
This section contains the Address, Person, and Driver classes, where the first two classes are used as a custom data type. The implementation of these classes is shown below:
```
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private int zipCode;
    private String country;

    public Address(String street, String city, String state, int zip, String country) {
        this.streetAddress = street;
        this.city = city;
        this.state = state;
        this.zipCode = zip;
        this.country = country;
    }
    // Getters...
}

public abstract class Person {
    private String name;
    private Address address;
    private String email;
    private String phoneNumber;

    // Setters & getters...
}

public class Driver extends Person {
    private int driverId;
    // Setters/getters...
}
```

## Account
Account is an abstract class that represents the various people or actors that can interact with the system. There are two types of accounts: receptionist and customer. The implementation of Account and its subclasses is shown below:
```
public abstract class Account extends Person {
    private String accountId;
    private String password;
    private AccountStatus status;

    public Account() {}
    // Optional: full constructor
    // Setters/getters...
    public abstract boolean resetPassword();
}

public class Customer extends Account {
    private String licenseNumber;
    private Date licenseExpiry;

    // Setters/getters...
    @Override
    public boolean resetPassword() {
        this.setPassword(java.util.UUID.randomUUID().toString());
        return true;
    }
}

public class Receptionist extends Account {
    private Date dateJoined;
    private List<Customer> customerList = new ArrayList<>();
    // Add/search/remove customer methods...
    @Override
    public boolean resetPassword() {
        this.setPassword(java.util.UUID.randomUUID().toString());
        return true;
    }
}
```


## Vehicle
Vehicle will be another abstract class, which serves as a parent for four different types of vehicles: Car, Van, Truck, and MotorCycle. The definition of the Vehicle and its child classes is given below:
```
import java.util.*;
public abstract class Vehicle {
    private String vehicleId;
    private String licensePlateNumber;
    private int passengerCapacity;
    private VehicleStatus status;
    private String model;
    private int manufacturingYear;
    private List<VehicleLog> log = new ArrayList<>();
    // Setters/getters, reserveVehicle, returnVehicle
}

public class Car extends Vehicle { private CarType carType; /* Setters/getters... */ }
public class Van extends Vehicle { private VanType vanType; /* Setters/getters... */ }
public class Truck extends Vehicle { private TruckType truckType; /* Setters/getters... */ }
public class Motorcycle extends Vehicle { private MotorcycleType motorcycleType; /* Setters/getters... */ }
```


## Equipment
Equipment is an abstract class, and this section represents different equipment: Navigation, ChildSeat, and SkiRack added in the reservation. The code to implement these classes is shown below:
```
public abstract class Equipment {
    private int equipmentId;
    private int price;
    // Setters/getters...
}

public class Navigation extends Equipment { /* Extend if needed */ }
public class ChildSeat extends Equipment { /* Extend if needed */ }
public class SkiRack extends Equipment { /* Extend if needed */ }
```

## Service
Service is an abstract class, and this section represents different services: DriverService, RoadsideAssistance, and Wi-Fi added to the reservation. The code to implement these classes is shown below:
```
public abstract class Service {
    private int serviceId;
    private int price;
    // Setters/getters...
}

public class DriverService extends Service {
    private int driverId;
    // Setters/getters...
}
public class RoadsideAssistance extends Service { /* Extend if needed */ }
public class WiFi extends Service { /* Extend if needed */ }
```

## Payment
The Payment class is another abstract class, with the Cash and CreditCard classes as its child. This takes in the PaymentStatus enum to keep track of the payment status. The definition of this class is provided below:

```
import java.util.*;
public abstract class Payment {
    private double amount;
    private Date timestamp;
    private PaymentStatus status;
    // Setters/getters...
    public abstract boolean makePayment();
}

public class Cash extends Payment {
    @Override
    public boolean makePayment() { setStatus(PaymentStatus.COMPLETED); return true; }
}

public class CreditCard extends Payment {
    private String nameOnCard;
    private String cardNumber;
    private String billingAddress;
    private int code;
    // Setters...
    @Override
    public boolean makePayment() { setStatus(PaymentStatus.COMPLETED); return true; }
}
```

## Vehicle log and vehicle reservation
VehicleLog is a class responsible for keeping track of all the events related to a vehicle. VehicleReservation is a class responsible for managing the reservation of vehicles. The implementation of this class is given below:
```
import java.util.*;
public class VehicleLog {
    private int logId;
    private VehicleLogType logType;
    private String description;
    private Date creationDate;
    // Constructor, getters...
}

public class VehicleReservation {
    private int reservationId;
    private String customerId;
    private String vehicleId;
    private Date creationDate;
    private ReservationStatus status;
    private Date dueDate;
    private Date returnDate;
    private String pickupLocation;
    private String returnLocation;
    private List<Equipment> equipments = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    // Setters/getters, addEquipment(), addService(), etc.
}
```


## Notification
The Notification class is another abstract class responsible for sending notifications, with the SMSNotification and EmailNotification classes as its child. The implementation of this class is shown below:

```
import java.util.*;
public abstract class Notification {
    private int notificationId;
    private Date createdOn;
    private String content;
    public void setContent(String c) { content = c; }
    public String getContent() { return content; }
    public abstract void sendNotification(Account account);
}

public class SmsNotification extends Notification {
    @Override
    public void sendNotification(Account account) {
        System.out.println("SMS to " + account.getName() + ": " + getContent());
    }
}

public class EmailNotification extends Notification {
    @Override
    public void sendNotification(Account account) {
        System.out.println("Email to " + account.getName() + ": " + getContent());
    }
}
```
## Parking stall and fine
ParkingStall is a class used to locate vehicles in the car rental branch while the Fine class represents the fine applied on payment. The implementation of these classes is given below:
```
public class ParkingStall {
    private int stallId;
    private String locationIdentifier;
    // Constructor, getters...
}


public class Fine {
    private double amount;
    private String reason;
    public void setAmount(double a) { amount = a; }
    public double getAmount() { return amount; }
    public void setReason(String r) { reason = r; }
    public String getReason() { return reason; }
    public double calculateFine() { return amount; }
}
```


## Search interface and vehicle catalog
Search is an interface and the VehicleCatalog class is used to implement the search interface to help in vehicle searching. The code to perform this function is presented below:
```
import java.util.*;
public interface Search {
    List<Vehicle> searchByType(String type);
    List<Vehicle> searchByModel(String model);
}

public class VehicleCatalog implements Search {
    private HashMap<String, List<Vehicle>> vehicleTypes = new HashMap<>();
    private HashMap<String, List<Vehicle>> vehicleModels = new HashMap<>();
    // addVehicle(), searchByType(), searchByModel()
}
```


## Car rental system and car rental branch
The CarRentalSystem class is the base class of the system that is used to represent the whole car rental system (or the top-level classes of the system). CarRentalBranch represents the single branch of the system. The implementation of these classes is given below:
```
import java.util.*;
public class CarRentalBranch {
    private String name;
    private Address address;
    private List<ParkingStall> stalls;
    // Constructor, getters...
}

public class CarRentalSystem {
    // Singleton pattern
    private String name;
    private List<CarRentalBranch> branches = new ArrayList<>();
    private static CarRentalSystem system = null;
    private CarRentalSystem() {}
    public static CarRentalSystem getInstance() {
        if (system == null) system = new CarRentalSystem();
        return system;
    }
    // addNewBranch(), getBranches()...
}
```
## Executable code: Car rental system
Below is a fully self-contained, runnable program in Java, C#, C++, Python, and JavaScript that demonstrates the core workflows of the Car Rental system. The main driver code of the system resides in the Driver.java, Driver.cs, Driver.py, Driver.cpp, and Driver.js for all respective languages. You can click on the “Run” button to execute the codes.

What this code shows
System Initialization: Sets up branches, adds vehicles to the inventory, and registers a customer.
* Scenario 1 (Vehicle search): The customer searches the inventory for available vehicles.
* Scenario 2 (Reservation and add-ons): The customer makes a reservation, adds equipment (Child Seat), and an extra service (Driver).
* Scenario 3 (Payment): The customer completes payment for the reservation.
* Scenario 4 (Notification): The customer receives an email notification for reservation confirmation.
* Scenario 5 (Pickup and return): The customer picks up and returns the vehicle.
* Scenario 6 (Fine calculation): The system checks for overdue return and applies a fine if needed.
* Scenario 7 (Reservation history): Displays all reservations made by the customer.

```
from abc import ABC, abstractmethod
from Person import Person
from AccountStatus import AccountStatus

class Account(Person, ABC):
    def __init__(self):
        super().__init__()
        self._account_id = ""
        self._password = ""
        self._status = AccountStatus.ACTIVE

    def get_account_id(self): return self._account_id
    def set_account_id(self, account_id): self._account_id = account_id

    def get_status(self): return self._status
    def set_status(self, status): self._status = status

    def set_password(self, password): self._password = password
    def get_password(self): return self._password

    @abstractmethod
    def reset_password(self): pass
```
