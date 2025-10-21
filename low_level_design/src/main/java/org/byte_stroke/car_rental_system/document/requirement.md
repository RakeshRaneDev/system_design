# Getting Ready: The Car Rental System
Understand the car rental system problem and learn the questions to further simplify this problem.
A car rental system enables customers to rent vehicles for short periods, ranging from a few hours to several weeks. Such systems are commonly operated by companies with multiple offices across cities, often near airports or commercial centers. The platform allows customers to reserve, pick up, and return vehicles at different locations, depending on availability and convenience.
Each branch manages its fleet, consisting of various vehicle types and models, and tracks reservations, customer information, payments, and the logistical aspects of vehicle movement between locations. Customers can search for available vehicles by location, type, or features and reserve or cancel rentals subject to company policies. The system supports multiple payment methods and handles cases such as late returns, reservation modifications, and optional services (e.g., driver assignment and roadside assistance).
This scenario presents several design challenges, including distributed inventory management, concurrent reservations, flexible pick-up and drop-off options, dynamic pricing, and ensuring a seamless and reliable customer experience across all locations.

In this LLD interview case study, your focus will be on:
* Managing vehicle inventory and supporting multiple vehicle types and features
* Handling reservations, cancellations, and modifications across branches
* Facilitating vehicle search, pick-up, and drop-off at different locations
* Tracking customer information, rental history, and payments
* Supporting optional services and flexible payment methods
* Ensuring system reliability, data consistency, and scalability

> This system model can be adapted for any rental business managing distributed assets and flexible reservations.

## Expectations from the interviewee
The car rental system consists of multiple components. Each component has its own functionality and constraints. This section provides an overview of some of the main expectations that the interviewer will want to hear you discuss in more detail during the interview.

### Vehicle types
An interviewer would expect you to discuss the different vehicle types, and ask the following questions:
* What types of vehicles will that system support?
* How can we identify the specific vehicle?

### Search interface
Members will use the application and add location and the reservation date. They will receive several options to select the vehicle. Therefore, an interviewer would expect you to ask questions listed below:
* Is it possible to search a vehicle using its name or type?
* Can we search for a vehicle by its model number?

### Services
An interviewer would also expect you to discuss the services of the car rental system and may ask the following questions:
* Does a car rental system assign a driver to its customer?
* Does a car rental system provide roadside assistance to its customer?Examples include towing, tire changes, fuel delivery, battery jump-start, or lockout services.

### Reservation cancelation
There will be many duplicate instances in our system. The interviewer expects you to ask questions listed below:
* Can the member be able to cancel a reservation?
* Which member is allowed to request a vehicle reservation cancelation and when?

### Payment flexibility
One of the car rental system’s most significant attributes is its customer payment structure. The payment depends on the vehicle type and time stamp. Therefore, an interviewer would expect you to ask questions listed below:
* How can customers pay at different branch locations and by different methods (cash, credit, or cheque)?
* If there are multiple branches of the car rental system, how will the system keep track of the customer having already paid at a particular branch?

## Design approach
We’ll design this car rental system using the bottom-up design approach. For this purpose, we will follow the steps below:
* Identify core components: Start with basic entities like Vehicle, Customer, Reservation, Payment, etc.
* Compose subsystems: Build larger modules for inventory management, reservation management, and payment processing.
* Integrate subsystems: Assemble the complete application, ensuring the design is modular and adheres to SOLID principles.
* Document assumptions: Where requirements are unclear, clearly state and justify your assumptions.
This design approach will address concurrency, edge cases, and follow SOLID principles to ensure scalability and maintainability. Later on, diagrams and code will be used to illustrate major workflows and class structures.


## Requirements for the Car Rental System
Look at the requirements for our car rental system.
In this lesson, we outline the functional and operational requirements for the car rental system. Clearly identifying and understanding requirements is essential to defining the system’s scope and ensuring a robust, user-friendly design.
We’ll use the notational convention to identify each requirement with a unique label “Rn,” where “R” is short for Requirement and “n” is a natural number.

### Requirement collection
The set of requirements for the car rental system is listed below:
* R1: The system supports two main user roles: Customers and Receptionists.
* R2: The system manages multiple types of vehicles, including cars, trucks, vans, and motorcycles.
* R3: Each vehicle type may have multiple subtypes, such as:
  * Cars: Economy, Luxury, Standard, Compact, Intermediate, Full size, Premium
  * Vans: Passenger, Cargo
  * Motorcycles: Standard, Cruiser, Touring, Sports, Off-road, Dual purpose
  * Trucks: Light-duty, Medium-duty, Heavy-duty
* R4: The system must record every reservation, including the customer details and the date/time a vehicle is issued.
* R5: The system can track and report the number of vehicles each customer has rented, including rental history and active reservations.
* R6: Customers can cancel their reservations at any time before the pickup date, subject to company policies.
* R7: The system maintains a vehicle log to track all significant events related to each vehicle (e.g., maintenance, repairs, accidents, assignments, or status changes).
* R8: Customers can add equipment to their reservations, such as a ski rack, child seat, or navigation system.
* R9: Customers can add extra services to their reservations, including a driver, Wi-Fi, or roadside assistance.
* R10: If a vehicle is not returned by the due date, the system must notify the customer and automatically generate a fine according to company policy.
* R11: Users can search for vehicles by type, model, features, or availability at specific locations and dates.
* R12: The system must support the management of multiple branches in different cities and locations
* R13: Each branch must maintain a record of parking stalls for vehicles at that location, including current status (occupied, available, reserved).
* R14: The system should securely process payments, refunds, and fines using multiple payment methods (cash, card, online).
* R15: The Customer shall be able to make payment for the car rental in both scenarios: when making the reservation and when returning the vehicle. The system should validate both.

We’ve identified our requirements for the problem. In the next lesson, we’ll define different use cases of our car rental system.
