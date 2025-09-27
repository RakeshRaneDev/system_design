## Expectations from the interviewee
   In a typical parking lot system, there are several components each with specific constraints and requirements. The following section provides an overview 
of some major expectations the interviewer will want an interviewee to discuss in more detail during the interview.

### Payment flexibility
One of the most significant attributes of the parking lot system is the payment structure that it provides to its customers. An interviewer would expect you to ask questions like these:

How are customers able to pay at different exit points (i.e., either at the automated exit panel or to the parking agent) and by different methods (cash, credit, coupon)?

If there are multiple floors in the parking lot, how will the system keep track of the customer having already paid on a particular floor rather than at the exit?

### Parking spot type
Another topic of discussion that an interviewer would expect you to be aware of is the different parking spot types—handicapped, compact, large, and motorcycle—regarding which you can ask the following questions:

How will the parking capacity of each lot be considered?

What happens when a lot becomes full?

How can one keep track of the free parking spots on each floor if there are multiple floors in the parking lot?

How will the division of the parking spots be carried out among the four different parking spot types in the lot?

### Vehicle types
Similar to the parking spot, an interviewer would also expect you to discuss the different vehicle types—car, truck, van, motorcycle—which can have the following set of questions:

How will capacity be allocated for different vehicle types?

If the parking spot of any vehicle type is booked, can a vehicle of another type park in the designated parking spot?

### Pricing
We touched upon the payment structure offered by the parking lot system. Now, the pricing model needs to be clarified from the interviewer, and therefore you may ask questions like these:
How will pricing be handled? Should we accommodate having different rates for each hour? For example, customers will have to pay
$4 for the first hour,
$3.5 for the second and third hours, and
$2.5 for all the subsequent hours.

Will the pricing be the same for the different vehicle types?

### Design approach
We are going to design this parking lot system using the bottom-up design approach. For this purpose, we will follow the steps below:

Identify and design the smallest components first, like, the vehicle and parking spot types.

Use these small components to design bigger components, for example, the payment system at the exit.

Repeat the steps above until we design the whole system like the parking lot.

### Design pattern
During an interview, it is always a good practice to discuss the design patterns that a parking lot system falls under. Stating the design patterns gives the interviewer a positive impression and shows that the interviewee is well-versed in the advanced concepts of object-oriented design.


Try to answer the following question. If you are not familiar with design patterns, don’t worry! You can learn about them by asking questions like, “Define design patterns.”

## Requirements for the Parking Lot Design
Learn about all requirements of the parking lot problem.

In this lesson, let’s list down the requirements of the parking lot system. This is a very crucial step since requirements define the scope of a problem, so getting them right from the interviewer and understanding them well will make the design of the rest of the system smooth and easy.

We’ll use the notational convention to identify each requirement with a unique label "Rn", where "R" is short for Requirement and "n" is a natural number.

### Requirements collection
Let’s define the requirements for the parking lot problem:

R1: The parking lot should have the capacity to park 40,000 vehicles.

R2: The four different types of parking spots are handicapped, compact, large, and motorcycle.

R3: The parking lot should have multiple entrance and exit points.




R4: Four types of vehicles should be allowed to park in the parking lot, which are as follows:

Car
Truck
Van
Motorcycle



R5: The parking lot should have a display board that shows free parking spots for each parking spot type.

R6: The system should not allow more vehicles in the parking lot if the maximum capacity (40,000) is reached.

R7: If the parking lot is completely occupied, the system should show a message on the entrance and on the parking lot display board.




R8: Customers should be able to collect a parking ticket from the entrance and pay at the exit.

R9: The customer can pay for the ticket either with an automated exit panel or pay the parking agent at the exit.

R10: The payment should be calculated at an hourly rate.

R11: Payment can be made using either a credit/debit card or cash.




### Use Case Diagram for the Parking Lot
Learn how to define use cases and create the corresponding use case diagram for the parking lot system.

Let’s build the use case diagram of the parking lot system and understand the relationship between its different components.

First, let’s define the different elements of our parking lot, followed by the complete use case diagram of the system.

System
Our system is a "parking lot."

Actors
Here are the main actors of our parking lot system.

Primary actors
Customer: This actor can park the vehicle in the allocated parking space according to the vehicle type and pay for the parking before exit.

Parking agent: The parking agent will assist the customer and perform all the tasks that a customer can do, such as paying the parking ticket on behalf of the customer.

Secondary actors
Admin: This can add, remove, or update a spot, agent, entry/exit panels, and view/update accounts.

System: This is responsible for giving details of parking spot availability and assigning a parking spot to a vehicle.

Use Cases
In this section, we will define the use cases for the parking lot. We have listed down the use cases according to their respective interactions with a particular actor.

Note: You will see some use cases occurring multiple times because they are shared among different actors in the system.

Admin
Add spot: To add a parking spot

Add agent: To add a new agent

Add/modify rate: To add/modify hourly rate

Add entry/exit panel: To add and update exit/entry panel at each entry/exit

Update account: To update account details and payment information

Login/Logout: To login/logout to/from agent or admin account

View account: To view account details like payment status or unpaid amount

Customer
Take ticket: To take a ticket at the entrance, that contains information regarding the vehicle and its entrance time

Scan ticket: To scan the ticket at the exit and get the parking fee

Pay ticket: To pay the parking fee at the exit panel via cash or a credit card

Cash: To pay the parking fee via cash

Credit card: To pay the parking fee via credit card

Park vehicle: To park the vehicle at the assigned destination

Parking agent
Update account: To update account details and payment information

Login/Logout: To log in/log out to/from the agent or admin account

View account: To view account details like payment status or unpaid amount

Take ticket: To take a ticket at the entrance, that contains information regarding the vehicle and its entrance time

Scan ticket: To scan the ticket at the exit and get the parking fee

Pay ticket: To pay the parking fee at the exit panel via cash or a credit card

Cash: To pay the parking fee via cash

Credit card: To pay the parking fee via credit card

Park vehicle: To park the vehicle at the assigned destination

System
Assigning parking spots to vehicles: To check the vehicle type and associate a free spot according to it

Remove spot: To remove a parking spot if it is not available for parking

Show full: To display the status of the parking lot as full

Show available: To show the details of available parking spots

Relationships
This section describes the relationships between and among actors and their use cases.

Generalization
The “Parking agent” has a generalization relationship with the “Customer” since the parking agent can perform all those tasks that a customer can perform.

“Cash” and “Credit card” use cases are used for payments. Hence, both have a generalization relationship with the “Pay ticket” use case.

Associations
The table below shows the interactions between actors and their corresponding use cases.

Admin

Customer

Parking Agent

System



Add spot

Take ticket

Update account

Assigning parking spots to vehicles

Add agent

Scan ticket

Login/Logout

Remove spot

Add/modify rate

Pay ticket

View account

Show full

Add entry/exit panel

Cash

Take ticket

Show available

Update account

Credit card

Scan ticket



Login/Logout

Park vehicle

Pay ticket

View account



Cash



Credit card

Park vehicle

Include
The “Scan ticket” has an include relationship with the “Pay ticket” since it is necessary to scan a ticket and get the total payable fee of parking a vehicle before the actual payment.

Use case diagram
Here is the use case diagram of the parking lot system:


The use case diagram of parking lot system

The use case diagram of parking lot system
In the next lesson, we will discuss the class diagram with a detailed explanation of all classes and their relationship with each other.
