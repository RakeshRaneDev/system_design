# Class Diagram for the Elevator System

Learn to create a class diagram for the elevator system problem using the bottom-up approach.
In this lesson, we’ll identify and design the classes, abstract classes, and interfaces based on the requirements we previously gathered from the interviewer in our elevator system.

## Components of an elevator system
As mentioned, we will design the elevator system using a bottom-up approach. Therefore, we will first identify and design classes for the smaller components like Button, Door, and Floor. Then, we will create the larger components—ElevatorCar, Building, and finally the overall ElevatorSystem—composed of or aggregates the smaller ones.

## Button
Button is an abstract class. There can be four types of buttons: the door button, the elevator button, the hall button, and the emergency stop button. The status of the button determines whether it is pressed or unpressed. We can press the button or check its status through the Button class.
The ElevatorButton subclass is inherited from the Button class and represents the buttons inside the elevator. When the elevator button is pressed, it specifies the destination floor of the elevator car or where the passenger wants to go.
Similar to ElevatorButton, HallButton is also a subclass of the Button class. This class represents the buttons that are outside the elevator. This class used the enumeration Direction to specify whether the button is for going up or down. The hall button has two important pieces of information, the floor from where the button is pressed and the direction in which the passenger wants to move.
Furthermore, there’s an EmergencyButton class that also inherits the behavior of the abstract Button class. This class responds to the passengers call when they press the emergency button. It alerts the support team to take prompt action and refrains other passengers from using the elevator car.
The UML representation of these classes is shown below:
![ Button and its derived classes.png](images%2F%20Button%20and%20its%20derived%20classes.png)

## Elevator panel and hall panel
ElevatorPanel is a class which is used to represent the complete grid of buttons inside the elevator. In the elevator panel, we will have a list of buttons for selecting the destination floor of the elevator, two buttons for closing and opening the elevator, and an emergency stop button.
While the HallPanel class represents the buttons that are outside the elevators. The hall panel consists of only two buttons: up and down.
Both the elevator panel and the hall panel are used to take input from the passenger. The representation of both classes is given below:
![HallPanel and ElevatorPanel classes.png](images%2FHallPanel%20and%20ElevatorPanel%20classes.png)

> Number of buttons in the elevator panel = Number of floors + 3
> Number of buttons in the hall panel = 2

## Display
Every elevator has a display to represent the current floor number and direction (up or down) of an elevator. It also gives information about the capacity of the elevator. So we will use the Display class which represents this information. The Display class consists of floor number, capacity, and direction. It has separate methods for both elevator display and hall display. The showElevatorDisplay() will display all of the class attributes. The Display class also has an update() method that changes the floor number, the capacity, and the direction of the elevator car.
The class representation is provided below:
![Display class.png](images%2FDisplay%20class.png)

## Door
The Door class symbolizes the door of an elevator. This class has a reference to enum DoorState which depicts that the status of the door can be either open or closed. A simple representation of the Door class is provided below:
![Door class.png](images%2FDoor%20class.png)

## Elevator car
ElevatorCar is the class that expresses the elevators of the building. Each elevator has a unique ID. This class consists of an enumeration named state that tells the present state of the elevator. Moreover, in every elevator car, there is a door, an elevator panel, and a display. The elevator car can start moving or stop on any floor. The UML expression of the class is as follows:
![ElevatorCar class.png](images%2FElevatorCar%20class.png)

## Floor
The Floor class represents the floors of a building. Each floor consists of a number of hall panels to call the lift and has displays to indicate the current floor and direction of the lift since there is a separate panel and display for each elevator. Moreover, we will have getFloorNumber(), getPanel(), and getDisplay() functions to check if the floor is at the bottom or top, and update the panels and displays respectively. The down button will be disabled if the floor is at the lowest of the building and the up button will be disabled if the floor is topmost.
The model of the Floor class is shown below:
![Floor class.png](images%2FFloor%20class.png)

## Building
The Building class represents an actual building that consists of the number of floors and elevators. This class is represented below:
![Building class.png](images%2FBuilding%20class.png)

## Elevator system
ElevatorSystem is the main functional class of the whole elevator control system. The elevator system has a display of each elevator and monitors the elevator cars. The elevator system has a dispatcher to select the best elevator car. Moreover, the system takes control of the elevator doors. The UML representation of a class is given below:
![ElevatorSystem class.png](images%2FElevatorSystem%20class.png)

## Enumerations
Enumeration is generally a data type in which only a specific set of constants can be stored. Following is the list of enumerations required in the elevator system:
* ElevatorState: It describes the state of an elevator, which could be idle, up, or down, or maintenance.
* Direction: When the elevator is not in an idle state, it describes the direction of the motion of an elevator which could be up or down.
* DoorState: When the elevator is in an idle state, it describes the status of a door of an elevator that could be open or close.
![Enums in the elevator system.png](images%2FEnums%20in%20the%20elevator%20system.png)

## Relationship between the classes
Now, we’ll discuss the relationships between the classes we have defined above in our elevator system.

### Aggregation
Aggregation is a “has-a” relationship where the container (whole) can exist independently of the contained (part). The part can also exist independently, and is often shared or referenced. The class diagram has the following aggregation relationships:

* ElevatorSystem has an aggregation relationship with Building.
  * The ElevatorSystem is the high-level controller and contains a Building instance (which has floors and elevator cars). If the system is reset, the Building object can be reused or replaced.
* Building aggregates Floor and ElevatorCar.
  * The Building holds multiple floors and elevator cars. If the Building is destroyed, floors and cars might still be conceptual entities (e.g., for reporting or migration).
![The association relationship between classes.png](images%2FThe%20association%20relationship%20between%20classes.png)

### Composition
Composition is a strong “part-of” relationship where the part cannot exist without the whole. If the whole is destroyed, its parts are destroyed too. The class diagram has the following composition relationships.
* ElevatorCar is composed of Door, Display, and ElevatorPanel.
  * Each ElevatorCar owns its Door, Display, and Panel—these have no meaningful existence outside the car. If the car is decommissioned, so are its door, display, and panel.
* Floor is composed of HallPanel and Display.
  * Each floor has a unique HallPanel and Display, which do not make sense independently of their floor.
* HallPanel contains HallButtons.
  * A HallPanel comprises up/down buttons specific to it; these buttons are not shared with other panels.
* ElevatorPanel contains ElevatorButtons, DoorButtons, and an EmergencyButton.
  * These buttons exist only as part of the elevator’s control panel. They are not shared across panels or elevators.
![The composition relationships between classes.png](images%2FThe%20composition%20relationships%20between%20classes.png)

### Inheritance
Inheritance is an “is-a” relationship. Subclasses share a contract and behavior with the superclass, and can be used wherever the superclass is expected. The following classes show an inheritance relationship:
* ElevatorButton, HallButton, DoorButton, and EmergencyButton all extend the abstract Button class.
  * All buttons share common features (pressed state, press/reset actions), but each has specialized behavior and context. Inheriting from a base Button reduces duplication and supports polymorphism if needed.

## Class diagram of the elevator system
In this section, we outline the multiplicity (cardinality) relationships between the main classes in our Elevator system. For each relationship, we explain the allowed number of instances on each side and the real-world or design rationale behind the connection. Understanding these relationships is key to modeling how different entities interact and collaborate to support key workflows in the system.
![class mapping.png](images%2Fclass%20mapping.png)

Here’s the complete class diagram for our elevator system:
![class diagram of elevator system.png](images%2Fclass%20diagram%20of%20elevator%20system.png)


## Design pattern
Several design patterns are well-suited to the elevator control system:
* Strategy pattern: The dispatching logic—how the system assigns elevator cars to requests—can vary based on building size, traffic patterns, or custom policies. By using the Strategy pattern, you can define multiple dispatch algorithms (e.g., nearest-car, least-busy, or time-based scheduling) and swap them at runtime depending on building requirements.
* State pattern: Elevators naturally transition between states such as idle, moving up, moving down, maintenance, or emergency. The State pattern allows each elevator to delegate its behavior to an object representing its current state. This leads to cleaner, more maintainable code: state transitions are explicit, and each state handles only its relevant actions (such as door logic or input handling).
* Delegation: Instead of implementing all behaviors in a single class, delegation enables objects (like the elevator system or elevator cars) to delegate certain responsibilities—such as dispatching, state management, or display updates—to specialized helper classes or state objects. This promotes separation of concerns and greater flexibility in modifying or extending system behavior.
These patterns help make the elevator system modular, extensible, and easy to adapt to different building requirements or future enhancements.
