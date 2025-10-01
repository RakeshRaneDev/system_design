# Getting Ready: Elevator System
Understand the elevator system problem and learn the questions to simplify this problem.

## Problem definition
Elevators are essential in multi-story buildings, enabling the efficient movement of people and goods. A typical system includes one or more elevator cars that respond to user requests from call buttons on each floor and destination selections inside the car.
To ensure efficiency and safety, an elevator system must handle requests intelligently, minimize passenger wait times, respect car capacity, and support centralized and decentralized control of multiple cars. The system must also manage concurrent requests, optimize car movements, and provide real-time feedback through displays inside and outside the cars.
Robust safety protocols are critical: in emergencies like power failures or fire alarms, the system may need to ignore inputs and keep doors closed until operation is safe. Handling such scenarios correctly is essential for user protection and reliable operation.
In this LLD interview case study, your focus will be on:

* Multiple elevators coordination: Distributing requests among cars and managing their interaction.
* Request scheduling: Prioritizing and queuing floor requests.
* Capacity management: Preventing overloading.
* Real-time updates: Showing current floor, direction, and occupancy.
* Safety and fault tolerance: Managing emergencies and critical faults.
* User experience: Minimizing wait times, supporting up/down requests, and providing clear information.
> This elevator system case study requires you to design a scalable, reliable, and user-friendly system that can be adapted to different building sizes and requirements.

## Expectations from the interviewee
Numerous components are present in a typical elevator system, each with specific constraints and requirements placed on it. Interviewers look for your ability to break down and design the system, anticipate real-world scenarios, and identify components and interactions. Typical areas of exploration include:

### Multiple elevators
You can also ask the interviewer whether the system should handle multiple elevators or just a single one. For this, you can ask the following questions:
* Can there be multiple elevator cars in the building?
* How could a one-elevator system be different from a multi-elevator system in terms of user wait time and running cost?

### Display
You may want to ask the interviewer about the display of the elevator system:
* How will users request elevators (call panels on each floor, selection panels inside cars)?
* What information is shown on displays inside and outside the elevator cars (current floor, direction, occupied status)?
* How do you keep users informed of system state and updates?

### Optimization
An interviewer would expect you to ask questions about the optimization of the elevator system in terms of wait time, maintenance, throughput, etc. You can ask questions like:
* What strategies can minimize passenger wait times and unnecessary car movements?
* How does the system handle peak hours and concurrent requests?
* How do you optimize for both user experience and operational efficiency (energy use, maintenance)?

### Reliablity and safety
You may want to ask the interviewer about the safety and reliability of the elevator system:
* How does the system respond to faults, emergencies, or overloaded conditions?
* What features ensure passenger safety at all times?

## Design approach
In this case study, we‘ll take a bottom-up design approach:
* Start small: Identify and design the smallest building blocks of the system, such as buttons, doors, and displays.
* Compose larger components: Use these small components to assemble larger modules, such as the elevator car, request panels, and floor subsystems.
* Integrate into the full system: Bring these pieces together to design the complete elevator control system for a building.
* Iterative refinement: At each stage, consider interactions, constraints, and edge cases; iterate and refine based on system requirements.

This approach helps ensure the design is modular, extensible, and robust, and makes it easier to adapt the solution for varying building types and requirements.

## Requirements for the Elevator System
Learn about all requirements of the elevator system.
In this lesson, we outline the functional and operational requirements for the elevator system. Identifying and understanding requirements is essential to defining the system’s scope and ensuring a robust, user-friendly design.
We’ll use the notational convention to identify each requirement with a unique label “Rn”, where “R” is short for Requirement and “n” is a natural number.

## Requirement collection
For the elevator design problem, the requirements are defined below:
* R1: The elevator car system shall support a configurable number of floors (up to 15) and a configurable number of elevator cars (up to 3).
* R2: Each elevator car shall be capable of serving every floor and exist in one of four states: moving up, moving down, maintenance, or idle.
* R3: Elevator doors shall only open when the car is idle and auto-close after a configurable timeout unless the “Open” button is actively held.
* R4: Each floor shall have a panel with Up/Down call buttons, indicator lights, and an external display of the car’s current floor and direction.
* R5: Each elevator car shall have buttons, ground (0) to 15th floor, for every floor, “Open,” “Close,” and an emergency-stop button, plus an internal display showing the current floor, direction, and load status.
* R6: Pressing the emergency-stop button shall immediately halt the car, keep doors closed, and alert building security/operators.
* R7: Each elevator car shall enforce a maximum load of 680 kg, inhibit motion if this limit is exceeded, and emit an audible and visual alarm when overloaded.
* R8: The central controller shall assign the most appropriate car to each floor-call request, aiming to minimize average wait time, and command that car to move accordingly.
* R9: The elevator car system shall support calls from multiple passengers, with each passenger able to go to the same or different floors in the same or opposite direction.
* R10: The system shall support a maintenance state for each car in which:
  * R10a: The car is removed from normal dispatch (no new requests assigned).
  * R10b: All elevator doors remain closed, and panel inputs are ignored.
  * R10c: All UIs display “Maintenance” for that car.
  * R10d: Upon exiting maintenance, the car returns to idle state at its current floor before resuming service.
* R11: All internal and external displays and indicators shall update in real time to reflect each elevator car’s current floor, direction, and operational state (idle, moving, maintenance, or emergency).
* R12: The system shall communicate all operational states, errors, and safety messages to users via audio/visual indicators and displays, ensuring passengers are always aware of the elevator’s status.
