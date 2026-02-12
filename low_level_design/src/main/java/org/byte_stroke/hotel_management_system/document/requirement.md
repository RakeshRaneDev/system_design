# Getting Ready: The Hotel Management System
Understand the hotel management system problem and learn the questions to further simplify this problem.

## Problem definition
The hotel management system is a software used to manage all hotel activities efficiently and smoothly. Almost all popular hotels have an online management system to digitize room bookings, manage staff, and perform other necessary hotel management features. 
This system makes the notification and payment process flexible and automated. 
The hotel usually has a fixed number of rooms, and the booking system has information about all rooms present and their availability.

For the hotel manager, manually controlling all the hotel activities is difficult. With the help of an online hotel management system, the manager can keep track of rooms, customers, and workers through a single portal. 
The manager can also post available rooms to the system and generate bills. 
The system also allows customers to search for and book a room of their choice and cost range. The system provides complete information about rooms to the customers so they can view the available rooms and book them online. 
The guest is charged based on the time the hotel room is booked. In this way, the system facilitates both customers and the manager of the hotel.

## Expectations from the interviewee
Numerous components are present in the hotel management system, each with specific constraints and requirements. The following provides an overview of some of the main things the interviewer will want to hear you discuss in more detail during the interview.

### Room booking
Booking a room is an essential part of the hotel management system. The system must ensure no two users can book the same room for overlapping time slots. The interviewer expects you to ask questions to identify how the system will work in such situations:
- How will the system ensure multiple users do not book the same room?
- What type of users are allowed to book a room in the hotel?
- Can users book a room in advance?

### Payment handling
One of the hotel management system’s most significant attributes is its payment structure for its customers. This can vary, so the interviewer would expect you to ask the questions listed below:
- What payment methods can the customer use (for example, credit card or cash)?
- How is the payment performed? Does the customer pay online or through a receptionist at the hotel?
- Will the customer be able to pay in advance for a room booking, or is a just-in-time(JIT) payment system available?

### Price variance
We touched upon the payment methods of the hotel management system. Now, the pricing model needs to be clarified by the interviewer. Therefore, you may ask the questions listed below:
- How will the booking price be calculated? Or which factors affect the price of a room in the hotel?
- How does the location and size of the room affect its price?
- How does the booking duration affect the payment?

### Booking cancellation
There will be many duplicate instances in our system. The interviewer expects you to ask the questions provided below:
- Can the user cancel a room booking?
- Which type of users are allowed to request a room booking cancellation?

## Requirements for the Hotel Management System
Learn about all the requirements of the hotel management system problem.

In this lesson, we’ll list the requirements of the hotel management system. This is a crucial step since requirements define the scope of a problem. Getting them right from the interviewer and understanding them well will make the design of the rest of the system smooth and easy.

We’ll use the notational convention to identify each requirement with a unique label “Rn,” where “R” is short for Requirement and “n” is a natural number.

### Requirement collection
The requirements for the hotel management system are defined below:

- R1: The system can have four types of accounts, such as housekeeper, receptionist, and guest.
- R2: The rooms can be of different styles, such as standard, deluxe, family suite, or business suite.
- R3: The system should allow guests to search for and book any available room.
- R4: During room booking, the user will enter the check-in date and the duration of the stay. The user would also have to give some advance payment.
- R5: The customer can cancel the booking; a full refund will be provided if the booking is canceled before 24 hours of check-in time.
- R6: The system should notify customers about the booking status or other information.
- R7: The system should log and manage all the cleanup tasks.
- R8: The system should allow the customer to add services of their choice, such as room service, food or kitchen service, or amenities.
- R9: Every room should have its specific key, and there can be a master key that opens a specific set of rooms.
- R10: A hotel can have multiple branches.



