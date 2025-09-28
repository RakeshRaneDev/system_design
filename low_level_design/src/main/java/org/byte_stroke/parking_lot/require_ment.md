# Getting Ready: Parking Lot
Make an object-oriented design for a multi-entrance and exit parking lot system.

## Problem definition
A parking lot is a designated area for parking vehicles, commonly found in venues like shopping malls, sports stadiums, and office buildings. It consists of a fixed number of parking spots allocated for different types of vehicles. 
Each spot is charged based on the duration a vehicle remains parked. 
Parking time is tracked using a parking ticket issued at the entrance.
Upon exit, the customer can pay using either an automated exit panel or through a parking agent, with a credit/debit card or cash as accepted payment methods.

In this LLD interview case study, your focus will be on:
* Managing parking for various vehicle types (car, van, truck, motorcycle, etc.) across potentially multiple floors and zones.
* Allocating and tracking parking spot availability by type (e.g., compact, large, handicapped, motorcycle).
* Issuing and managing parking tickets for tracking entry, parking duration, and exit.
* Handling flexible payments at automated exit panels or via human agents, supporting multiple payment methods (cash, card, coupon).
* Implementing and enforcing pricing models that may vary by hour, spot type, or vehicle type.
* Ensuring real-time tracking of spot occupancy and enabling efficient entry/exit flows, especially during busy periods.
* Accommodating special considerations like handicapped spots and overflow situations.
> ! This parking lot system model can be adapted for various venues (malls, stadiums, offices, airports) 
> and is extensible to different business rules (such as prebooking, dynamic pricing, or loyalty programs).

![parking_lot.png](images%2Fparking_lot.png)

## Expectations from the interviewee
In a typical parking lot system, there are several components each with specific constraints and requirements. 
The following section provides an overview of some major expectations the interviewer will want an interviewee to discuss in more detail during the interview.

### Payment flexibility
One of the most significant attributes of the parking lot system is the payment structure that it provides to its customers. 
An interviewer would expect you to ask questions like these:
* How are customers able to pay at different exit points (i.e., either at the automated exit panel or to the parking agent) and by different methods (cash, credit, coupon)?
* If there are multiple floors in the parking lot, how will the system keep track of the customer having already paid on a particular floor rather than at the exit?

### Parking spot type
Another topic of discussion that an interviewer would expect you to be aware of is the different parking spot types—handicapped, compact, large, 
and motorcycle—regarding which you can ask the following questions:
* How will the parking capacity of each lot be considered?
* What happens when a lot becomes full?
* How can one keep track of the free parking spots on each floor if there are multiple floors in the parking lot?
* How will the division of the parking spots be carried out among the four different parking spot types in the lot?

### Vehicle types
Similar to the parking spot, an interviewer would also expect you to discuss the different vehicle types—car, truck, van, motorcycle—which can have the following set of questions:
* How will capacity be allocated for different vehicle types?
* If the parking spot of any vehicle type is booked, can a vehicle of another type park in the designated parking spot?

### Pricing
We touched upon the payment structure offered by the parking lot system. Now, the pricing model 
needs to be clarified from the interviewer, and therefore you may ask questions like these:
* How will pricing be handled? Should we accommodate having different rates for each hour? For example, customers will have to pay
  $4, $4 for the first hour, $3.5, $3.5 for the second and third hours, and $2.5, $2.5 for all the subsequent hours.
* Will the pricing be the same for the different vehicle types?

## Design approach
We will design this parking lot system using a bottom-up design approach. For this purpose, we will follow the steps below:
* First, we’ll identify the core entities such as Vehicle, ParkingSpot, ParkingTicket, and Payment, and define their primary responsibilities.
* Next, we’ll model how vehicles are assigned to appropriate parking spots based on type and availability, how entry/exit is managed, and how parking duration is tracked.
* We’ll design the payment system to support different payment methods and flexible pricing rules, ensuring secure and accurate fee calculation.
* We’ll ensure the system supports scalability (multiple floors, high traffic), real-time updates on availability, and follows SOLID principles for maintainability.

## Design pattern
During an interview, it is always a good practice to discuss the design patterns that a parking lot system falls under. Stating the design patterns gives the interviewer a positive impression 
and shows that the interviewee is well-versed in the advanced concepts of object-oriented design.
* **Singleton:** Useful for managing the payment processor and central ticket tracking to ensure only one instance controls critical operations across floors and exits.
* **Strategy:** Allows for applying different pricing models based on time parked and vehicle type.
* **Factory Method:** Helps create various parking spot and vehicle type objects dynamically without changing client code.
* **Observer (Optional):** Can notify parking agents or automated panels when spots become available or full.
* **Proxy (Optional):** Can manage access to payment gateways or ticket validation, adding security and caching if needed.

## Requirements for the Parking Lot Design
we outline the functional and operational requirements for the parking lot system. Clearly identifying and understanding 
requirements is essential to defining the system’s scope and ensuring a robust, user-friendly design.
We’ll use the notational convention to identify each requirement 
with a unique label “Rn,” where “R” is short for Requirement and “n” is a natural number. 
### Requirements collection
* R1: The parking lot must support a total capacity of up to 40,000 vehicles.
* R2: The parking lot must support multiple types of parking spots:
  * Accessible (for individuals with disabilities)
  * Compact
  * Large
  * Motorcycle
* R3: The parking lot should provide multiple entrance and exit points to support efficient traffic flow.
* R4: The system must support parking for four types of vehicles: cars, trucks, vans, and motorcycles.
* R5: A display board at each entrance and on every floor should show the current number of available parking spots for each parking spot type.
* R6: The system must not allow more vehicles to enter once the parking lot reaches its maximum capacity. When the parking lot is fully occupied, a clear message should be shown at each entrance and on all parking lot display boards.
* R7: When the parking lot is fully occupied, a clear message should be shown at each entrance and on all parking lot display boards.
* R8: Customers must be issued a parking ticket at entry, which will be used to track parking time and calculate payment at exit.
* R9: Customers should be able to pay for parking at the automated exit panel.
* R10: The parking lot system must support configurable pricing rates based on vehicle type and/or parking spot type and different rates for different parking durations (e.g., first hour, subsequent hours).
* R11: Payments must be accepted via credit/debit card and cash at all payment points.



