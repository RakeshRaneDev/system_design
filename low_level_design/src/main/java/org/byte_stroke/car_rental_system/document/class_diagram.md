# Class Diagram for the Car Rental System

Now, we’ll create the class diagram for the car rental system on the basis of the given requirements. In the class diagram, we will first identify classes (concrete, abstract, or associated) and interfaces for the system. Then, we will determine the relationship between them, according to the requirements in the previous lesson.

## Components of a car rental system
As mentioned earlier, we’ll design the car rental system using a bottom-up approach.

### Address and person
The Address class is a reusable value object that represents a physical address within the car rental system. This class standardizes the way addresses are stored and referenced for customers, staff, and branches. It contains attributes such as street address, city, state/province, postal/zip code, and country.
The Person class is an abstract base class that encapsulates common personal information for all individuals in the system, including customers, receptionists, and workers. This class centralizes attributes and methods shared by all people in the system, promoting code reuse and maintainability. The class representation of Address and Person is given below:
No change in the class representation of these classes.
![Address and Person classes.png](..%2Fimages%2FAddress%20and%20Person%20classes.png)

### Account
The Account class represents the login and system access credentials for a person. It is an abstract class because only specific user roles (Customer, Receptionist, Worker) actually log into the system. The Account class manages authentication, authorization, and account state. This class has members like account ID, password, the status of an account, etc.
The Customer class represents the customers who can register, log in, make vehicle reservations, manage their bookings, make payments, and receive notifications, while the Receptionist is responsible for managing vehicles, reservations, and branch operations. Receptionists inherit all Customer privileges, plus the ability to administer inventory and operations for their branch.
The class representation of Account and its subclasses is given below:
![the Account class.png](..%2Fimages%2Fthe%20Account%20class.png)

### Driver
Since we are designing the car rental problem, we will have a Driver class. 
A customer can request an additional driver at the time of reservation. 
The class diagram is shown below:
![Driver class.png](..%2Fimages%2FDriver%20class.png)

### Vehicle
Our car rental system should have a vehicle object according to the requirements. The vehicle can be of four types: a car, truck, van, and motorcycle. 
For this purpose, we’ll create Vehicle as an abstract class and Car, Truck, Van, and Motorcycle as its subclasses, as shown in the figure below:
![Vehicle and its derived classes.png](..%2Fimages%2FVehicle%20and%20its%20derived%20classes.png)

### Equipment
Equipment is an abstract class that stores information about different types of equipment that can be added to the reservation. For simplicity, we’ll assume three types of equipment, i.e., navigation, child seat, and ski rack. The class diagram for Equipment and its subclasses is as follows:
![Equipment and its derived classes.png](..%2Fimages%2FEquipment%20and%20its%20derived%20classes.png)

### Service
Service is an abstract class that represents the services provided to the customers along with the vehicle. While reserving a vehicle, the customers can add a service to their reservation. Every service has its fixed cost. We have three types of services, i.e., driver, roadside assistance, and Wi-Fi. The UML diagram of Service, along with its subclasses DriverService, RoadsideAssistance, and Wi-Fi, is given below:
![Service and its derived classes.png](..%2Fimages%2FService%20and%20its%20derived%20classes.png)


### Notification
Notification is an abstract class responsible for sending notifications to customers. Every notification has an ID, creation date, and content in it. The notification can either be an SMS notification or an email notification. The SMSNotification class requires the phone number of the customer to send a notification, while EmailNotification is sent to the email address of the customer. The relationship diagram of these classes is shown below:
![Notification and its derived classes.png](..%2Fimages%2FNotification%20and%20its%20derived%20classes.png)

### Parking stall
Each car rental location has parking stalls where the vehicles are parked. Each parking stall is identified by its ID and its location is specified by a location identifier. The representation of the ParkingStall class is shown below:
![ParkingStall class.png](..%2Fimages%2FParkingStall%20class.png)


### Vehicle log
VehicleLog is a class that is used to keep track of all the events related to a vehicle. Every vehicle log has its ID, log type, description, and creation date, as shown here:
![VehicleLog class.png](..%2Fimages%2FVehicleLog%20class.png)

### Vehicle reservation
Vehicle reservation is one of the most important requirements of the car rental system. To fulfill this functionality, we have a VehicleReservation class. This class is responsible for managing the vehicle reservation status of vehicles. The customer can add any equipment or service at the time of reservation as well. The UML representation of the class is shown below:
![VehicleReservation class.png](..%2Fimages%2FVehicleReservation%20class.png)


### Payment
The Payment class will be an abstract class and will have two child classes: CreditCard and Cash. These represent the two payment methods in the car rental system. The representation of these classes is given below:
![Payment and its child classes.png](..%2Fimages%2FPayment%20and%20its%20child%20classes.png)
### Fine
The system needs the Fine class to calculate the fine on the vehicle reservation in case the customer returns the vehicle after the due date, the fuel in the vehicle is less than the limit value, or there is any damage to the vehicle. The representation of this class is given below:
![Fine class.png](..%2Fimages%2FFine%20class.png)


### Search interface and vehicle inventory class
Search is one of the most important functionalities of the system. It is the interface that allows the user to search for any vehicle and return the list of vehicles upon searching by any of the following methods:
* Search car by its type
* Search car by its model
The VehicleCatalog is a class where the search function is implemented. In each catalog, the vehicles are sorted according to one of the given search techniques, i.e., either the vehicle type or model. The following UML diagram shows this relationship:
![Search interface and VehicleCatalog.png](..%2Fimages%2FSearch%20interface%20and%20VehicleCatalog.png)


### Car rental system and branch
CarRentalSystem is the main class of the car rental system and is the central part of the design. There can be multiple branches and locations of the car rental system. The CarRentalBranch class will represent each of these branches. The class representation is as follows:
![CarRentalSystem and CarRentalBranch classes.png](..%2Fimages%2FCarRentalSystem%20and%20CarRentalBranch%20classes.png)


### Enumerations
The list of enumerations required in the car rental system is provided below:

* **VehicleStatus:** The vehicle status describes the status of the particular vehicle for the user, whether it is available, reserved, lost, or being serviced.

* **AccountStatus:**  The account status tells about the user account status, i.e., active, closed, canceled, banned, or blocked.
* **ReservationStatus:** The reservation status tells about the reservation state of any vehicle, whether it is in an active state, pending state, confirmed state, completed state, or canceled state.
* **PaymentStatus:** The payment status checks if the customer’s payment falls in any of the following stages: unpaid, pending, completed, canceled, or refunded.
* **VanType:** The van type specifies that the van can only be of two types, i.e, passenger or cargo.ifies that the van can only be of two types, i.e, passenger or cargo.
* **CarType:** The car type tells about the different types of cars, whether it is economy, compact, intermediate, standard, full size, premium, or luxury.
* **MotorcycleType:** Similar to the car type, the motorcycle type tells about the different types of motorcycles, whether it is standard, cruiser, touring, sports, off-road, or dual purpose.
* **TruckType:** The truck type specifies that the truck can be of three types, i.e, light-duty, medium-duty, or heavy-duty.
* **VehicleLogType:** The vehicle log type describes the type of a particular log of a vehicle, whether it is an accident, fueling, cleaning service, oil change, repair, or other.

These enumerations can be represented using the following class diagram:
![Enums in the car rental system.png](..%2Fimages%2FEnums%20in%20the%20car%20rental%20system.png)


## Relationship between the classes
Now, let’s discuss the relationships between the main classes in our Car Rental System. Understanding these relationships helps us design for maintainability, extensibility, and clarity.
### Association
Association represents a “uses” or “knows about” relationship between two classes. The class diagram will have the following association relationships:

* **One-way association:**
    * The Account class should have one way association with the VehicleReservation class.
    * The VehicleReservation class will have a one-way association with the Vehicle class. This means they reference Vehicle (e.g., by storing a vehicle ID or a reference), but Vehicle does not reference them back.
    * The Fine class has a one-way association with Payment, as a fine is calculated based on or linked to a specific payment, but payment does not necessarily reference a fine.
![The one-way association relationship between classes.png](..%2Fimages%2FThe%20one-way%20association%20relationship%20between%20classes.png)

* **Two-way association:**
* The VehicleReservation class has a two-way association with both Payment and Notification. A reservation is linked to payment(s) and notifications (for confirmation, reminders, etc.), and payments/notifications can also reference the associated reservation if needed.
![The two-way association relationship between classes.png](..%2Fimages%2FThe%20two-way%20association%20relationship%20between%20classes.png)


### Composition
Composition represents a strong “has-a” relationship, where the lifetime of the part is controlled by the whole. The class diagram will have the following composition relationships:
* The CarRentalBranch class is composed of multiple ParkingStall instances. If the branch is deleted, its parking stalls cease to exist.
* The Vehicle class is composed of VehicleLog entries. Logs exist only as long as the vehicle exists.
![composition relationship between classes.png](..%2Fimages%2Fcomposition%20relationship%20between%20classes.png)


### Aggregation
Aggregation is a weaker “has-a” relationship, where the part can exist independently of the whole. The following classes show an aggregation relationship:
* The CarRentalSystem class aggregates multiple CarRentalBranch instances, meaning branches can exist independently and could potentially be shared or moved between systems.
* The ParkingStall and VehicleCatalog classes aggregate Vehicle instances; a vehicle can be reassigned or exist elsewhere.
* The VehicleReservation class aggregates multiple Equipment and Service instances; equipment and services can be reused across reservations or elsewhere in the system.
![aggregation relationship between classes.png](..%2Fimages%2Faggregation%20relationship%20between%20classes.png)


### Inheritance
Inheritance represents an “is-a” relationship. The following classes show an inheritance relationship:
* The Receptionist and Customer classes extend the abstract Account class, inheriting its fields and methods.
* The Account class extends the Person class, inheriting personal information.
* The Car, Truck, Van, and Motorcycle classes extend the Vehicle class, each representing a specialized vehicle type.
* The DriverService, RoadsideAssistance, and WiFi classes extend the abstract Service class.
* The Navigation, ChildSeat, and SkiRack classes extend the abstract Equipment class.
* The SmsNotification and EmailNotification classes extend the abstract Notification class.
* The Cash and CreditCard classes extend the abstract Payment class.
* The VehicleCatalog class implements the Search interface, enabling searching vehicles by type or model.



## Class diagram of the car rental system
In this section, we outline the multiplicity (cardinality) relationships between the main classes in our car rental system. For each relationship, we explain the allowed number of instances on each side and the real-world or design rationale behind the connection. Understanding these relationships is key to modeling how different entities interact and collaborate to support key workflows in the system.

![class_map.png](..%2Fimages%2Fclass_map.png)

Here’s the class diagram of the car rental system:

![class_diagram.png](..%2Fimages%2Fclass_diagram.png)

Design pattern
To promote extensibility and adhere to design principles like SRP (Single Responsibility principle) and OCP (Open/Closed principle), we can use the Decorator pattern for dynamic fee calculation and feature extension:

* DiscountDecorator: Applies discounts to all types of vehicles.

* PeakSeasonDecorator: Adjusts pricing during peak demand.

* DamageFineDecorator: Calculates fines for returned vehicles with damage.

* FuelFineDecorator: Calculates fines for vehicles returned with a partially filled fuel tank.

These decorators enable you to add or modify price-related behaviors without changing the underlying vehicle, reservation, or payment classes. Other decorators can be introduced as system needs evolve.
Similarly, we can make several other decorators according to the system needs. These decorator fulfill the SRP and OCP design principles.
