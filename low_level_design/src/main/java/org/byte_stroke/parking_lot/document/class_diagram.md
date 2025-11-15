# Class Diagram for the Parking Lot
Learn to create a class diagram for the parking lot system using the bottom-up approach.

In this lesson, we will identify, design, and explain the classes and abstract classes and their relationships that 
form the backbone of our parking lot system. We’ll use SOLID principles, extensibility, and object-oriented design best practices.

## Components of a parking lot system
As our requirements outline, the system involves various entities and their interactions. We will use a bottom-up approach:
* First, design the smallest, most fundamental entities.
* Next, combine these to form more complex system components.
* Finally, bring everything together in the main ParkingLot class, responsible for coordination.

### Vehicle
Our parking lot system should have a vehicle object according to the requirements. The vehicle can be a car, a truck, a van, or a motorcycle. There are two ways to represent a vehicle in our system:
#### Enumeration vs. abstract class
The enumeration class creates a user-defined data type with the four vehicle types as values.
This approach is not proficient for object-oriented design because if we want to add one more vehicle type later in our system, 
we would need to update the code in multiple places, violating the Open/Closed principle of the SOLID design principle. 
The Open/Closed principle states that classes can be extended but not modified. Therefore, it is recommended not to use the enumeration data type as it is not a scalable approach.
> Note: Using enums isn’t prohibited, but it is not recommended. Later, we will use the PaymentStatus enum in our parking lot design, as it won’t require further modifications.

An abstract class cannot instantiate an object and can only be used as a base class. The abstract class for Vehicle is the best approach. It allows us to create derived child classes for the Vehicle class. 
It can also be extended easily in case the vehicle type changes.
![class_diagram.png](..%2Fimages%2Fclass_diagram.png)

img - The class diagram of Vehicle and its derived classes

Click to see the relevant requirements: R1 , R4 , R6 ,R10

### Parking spot
Similar to the Vehicle class, the ParkingSpot should also be an abstract class. There are four types of parking spots: handicapped, compact, large, and motorcycle. These classes can be derived from the parking spot abstract class.
![parking spot'.png](..%2Fimages%2Fparking%20spot%27.png)

### Account
Similar to the Vehicle and ParkingSpot classes, Account should also be an abstract class. The Admin class is derived from this abstract class.
![account.png](..%2Fimages%2Faccount.png)

### Display board
This class represents the free parking spot types and the number of empty slots.
![display board.png](..%2Fimages%2Fdisplay%20board.png)

### Entrance and exit
The Entrance class is responsible for generating the parking ticket whenever a vehicle arrives. It contains the ID attribute, since there are multiple entrances to the parking lot. It also has the getTicket() method.
The Exit class is responsible for validating the parking ticket’s payment status before allowing the vehicle to exit the parking lot. It contains the ID attribute, since there are multiple exits to the parking lot. It also has the validateTicket() method.
![Entrance and Exit classes.png](..%2Fimages%2FEntrance%20and%20Exit%20classes.png)

### Parking ticket
The ParkingTicket class is one of the central classes of the system. It keeps track of the entrance and exit times of the vehicles, the amount, and the payment status.
![Parking Ticket class.png](..%2Fimages%2FParking%20Ticket%20class.png)

### Payment
The Payment class will be an abstract class and will have two child classes, card and cash, since these are two payment methods of the parking lot system.
![Payment class.png](..%2Fimages%2FPayment%20class.png)

### Parking rate#
The ParkingRate class is responsible for calculating the final payment based on the time spent in the parking lot.
![ParkingRate class.png](..%2Fimages%2FParkingRate%20class.png)

### Parking lot
Now, we will discuss the design of the whole ParkingLot system class. This parking lot system is composed of smaller objects we have already designed, like entrance/exit, parking spots, parking rates, etc.

### The enumerations and custom data types
The following provides an overview of the enumerations and custom data types used in this problem:
* **PaymentStatus:** We need to create an enumeration to keep track of the payment status of the parking ticket, whether it is paid, unpaid, canceled, refunded, and so on.
* **AccountStatus:**  We need to create an enumeration to keep track of the status of the account, whether it is active, canceled, closed, and so on.
* **TicketStatus:**  We need to create an enumeration to keep track of the current status of a parking ticket, whether it is issued, in use, paid, validated, canceled, refunded, and so on.
![parking_lot_system.png](..%2Fimages%2Fparking_lot_system.png)


### Address
We also need to create a custom data type, Address, that will store the location of the parking lot.
![Address custom data type.png](..%2Fimages%2FAddress%20custom%20data%20type.png)

### Person
The Person class is used to store information related to a person like a name, street address, country, etc.
![Person class custom data type.png](..%2Fimages%2FPerson%20class%20custom%20data%20type.png)

## Relationship between the classes
Now, we’ll discuss the relationships between the classes we have defined above in our parking lot system.

### Association
Association represents a loose relationship between two classes, where one class refers to another to use its functionality or store a reference. Associations typically do not imply ownership, and the associated object may exist independently of the referring object.
![The association relationship between classes.png](images%2FThe%20association%20relationship%20between%20classes.png)
In the parking lot system, association relationships include:
* Each ParkingSpot is associated with a Vehicle object. When a vehicle is parked, the spot keeps a reference to the vehicle. This allows the parking spot to track which vehicle is currently occupying it. However, the vehicle does not directly reference its spot.
* A Vehicle holds an association to its current ParkingTicket. When a vehicle enters the lot, it receives a ticket, and this association allows for tracking and processing of parking sessions.
* The ParkingTicket class maintains references to the Entrance, Exit, and Vehicle involved in the parking session, allowing the system to trace the entry and exit points and the vehicle details for any given ticket.
* The DisplayBoard and ParkingSpot should have an association relationship. The DisplayBoard is responsible for showing the availability/status of multiple ParkingSpot objects—it maintains a reference (typically a list or map) to all spots it displays.

### Composition
Composition is a strong form of association that implies ownership and a whole-part relationship. When a composite object is destroyed, its components are destroyed as well. In other words, the composed objects do not exist independently of their parent.
In our parking lot system, composition relationships are seen in:
* The ParkingLot class is composed of its key components, including all instances of Entrance, Exit, ParkingSpot, DisplayBoard, ParkingRate, and all current ParkingTicket objects. The parking lot is responsible for the creation, management, and destruction of these components, which do not exist outside the context of the parking lot.
* Each ParkingTicket is composed of a Payment object. The payment is created and managed by the ticket, representing the financial transaction for that specific parking session. The payment does not exist independently outside the ticket.

![The composition relationship between classes.png](images%2FThe%20composition%20relationship%20between%20classes.png)

### Inheritance
Inheritance, also known as generalization, is a relationship where one class (the child or subclass) inherits behavior and attributes from another class (the parent or superclass). This allows for code reuse and logical grouping of shared functionality, while enabling polymorphic behavior.
In the parking lot system, inheritance relationships are structured as follows:
* The abstract Vehicle class serves as the general blueprint for different types of vehicles, with Car, Truck, Van, and Motorcycle as its concrete subclasses. Each subclass can extend or override the behavior defined in Vehicle.
* Similarly, the abstract ParkingSpot class defines the core properties and behaviors for a parking spot. The specific spot types—AccessibleSpot, CompactSpot, LargeSpot, and MotorcycleSpot—are implemented as subclasses, each potentially providing specialized logic for assigning vehicles.
* The Payment abstract class provides the basic structure for payment operations, while Cash and CreditCard classes implement the details for each payment method.
> Note: We have already discussed the inheritance relationship between classes in the component section above.

## Class diagram of the parking lot system
In this section, we outline the multiplicity (cardinality) relationships between the main classes in our parking lot system. 
For each relationship, we explain the allowed number of instances on each side and the real-world or design rationale behind the connection. Understanding these relationships is key to modeling how different entities interact and collaborate to support key workflows in the system.
![class_diagram.png](images%2Fclass_diagram.png)

Here is the complete class diagram for our parking lot system:
![parking_lot_system.png](images%2Fparking_lot_system.png)
## Design pattern
Our parking lot system employs several standard object-oriented design patterns to enhance its flexibility and maintainability:
* Singleton pattern: The ParkingLot class is implemented as a Singleton. This ensures that only one instance of the parking lot system exists throughout the application's lifecycle, centralizing the management of all lot resources.
* Factory and Abstract Factory patterns: The creation of different types of parking spots, vehicles, or payments can leverage the Factory and Abstract Factory patterns. These patterns make it easy to introduce new types of spots, vehicles, or payment methods in the future, simply by extending the factory logic, without changing the core business logic or system structure.

### Additional requirements
The interviewer can introduce some additional requirements in the parking lot system, or they can ask some follow-up questions. Let’s see some examples of additional requirements:

* **Parking floor:**  The parking lot should have multiple floors where customers can park their cars. The class diagram provided below shows the relationship of ParkingFloor with other classes:
![Relationship of the ParkingFloor class with other classes.png](images%2FRelationship%20of%20the%20ParkingFloor%20class%20with%20other%20classes.png)

* **Electric:**  The parking lot should have some parking spots specified for electric cars. These spots should have an electric panel through which customers can pay and charge their vehicles. The class diagram provided below shows the relationship of Electric and ElectricPanel with other classes:
![Electric and ElectricPanel class.png](images%2FElectric%20and%20ElectricPanel%20class.png)