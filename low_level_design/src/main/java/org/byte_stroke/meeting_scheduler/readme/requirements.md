# Getting Ready: The Meeting Scheduler Problem
Understand the meeting scheduler design and learn the questions to further simplify this problem.

## Problem definition
A meeting scheduler software enables organizations to efficiently arrange and manage meetings for multiple participants, ensuring optimal use of meeting rooms and everyone’s time. The system helps find a suitable time and location by checking participants’ availability, room capacity, and preferences. Organizers and participants can book, update, or cancel meetings; add or remove attendees; and receive real-time notifications for all meeting changes. When participants respond to invites or remove themselves from a meeting, their calendars are automatically updated to reflect their participation status.
In this LLD interview case study, your focus will be on:
* Assigning available meeting rooms based on room capacity, schedule, and participant count
* Determining optimal meeting times by analyzing the availability of all required attendees
* Managing the full meeting life cycle: creation, updates, cancellations, and participant management
* Sending notifications for all meeting-related events, and updating calendars automatically when meetings are accepted, declined, or modified
* Tracking each participant’s invite/response status in the meeting record, and handling changes such as removals, cancellations, and overlapping bookings

> This system model applies to any organization or platform that requires collaborative meeting management, from startups to enterprise-level operations.

## Expectations from the interviewee
It is important to narrow down the components you will include in your design of a meeting scheduler. The following section provides an overview of some of the main expectations that the interviewer will want to hear you discuss in more detail during the interview.

### Room assignment
A meeting scheduler is responsible for assigning a meeting room to a scheduled meeting. Make sure to ask the following questions from the interviewer to figure out how this assignment works:
* How does the system determine available rooms?
* How important is the capacity of a room when assigning a room for a meeting?

### Availability of attendees
There are multiple attendees in a meeting, and all attendees have different schedules. You may ask the following questions to understand how the scheduler works:
* How does the system check the availability of the attendees?
* How does the system access the meeting information of all attendees?

## Design approach
We will design this Meeting Scheduler system using the bottom-up design approach. For this purpose, we will follow the steps below:
* First, we’ll identify the simple core entities such as MeetingRoom, Interval, Participant, and Calendar, defining their responsibilities and relationships.
* Next, we’ll model how meetings are created, how the scheduler checks room and participant availability, assigns suitable rooms, and sends notifications for bookings, cancellations, and updates.
* We’ll ensure the design gracefully handles conflicts, last-minute updates, recurring meetings, and edge cases like overlapping events or room shortages.
* This approach will incorporate SOLID principles and design patterns, ensuring the system is scalable, maintainable, and adaptable to future needs.
Diagrams and sample code will illustrate the main workflows, class structures, and collaboration between entities.


## Requirements for the Meeting Scheduler
Learn about all requirements of the meeting scheduler.
This lesson outlines the functional and operational requirements for the Meeting Scheduler. Clearly identifying and understanding these requirements is essential to defining the system’s scope and ensuring a robust, user-friendly design.
We’ll use the notational convention to identify each requirement with a unique label “Rn,” where “R” is short for Requirement and “n” is a natural number.
The requirements for the meeting scheduler design problem are defined below:
* R1: The system must support a configurable number of meeting rooms, allowing organizations to add or manage rooms as needed.
* R2: Each meeting room must have a defined capacity, ensuring only meetings within the room’s limits can be scheduled.
* R3: Meeting rooms can be booked for specific time intervals, provided they are available and not already reserved for overlapping times.
* R4: When a meeting is scheduled, updated, or canceled, notifications must be sent promptly to all invited participants.
* R5: Invited participants receive meeting invites regardless of their current availability. The system must track each participant’s invitation response status (accepted, declined, pending) within the meeting record.
* R6: Every user must have access to a personal calendar to view, schedule, or cancel meetings, and to track all meeting invitations and responses.
* R7: The organizer must be able to add or remove participants after a meeting has been scheduled, and appropriate notifications must be sent for any changes.
* R8: The system must handle booking conflicts and overlapping meetings, providing clear feedback if a room or participant is booked for the requested time.
* R9: The organizer should be able to edit meeting details (such as title, agenda, room, time, and participant list), and all updates should trigger notifications.
* R10: The system should support cancellation of meetings by the organizer or authorized participants, notifying all invitees of the cancellation.
* R11: If a participant declines an invite or withdraws from a meeting, the meeting must be removed from their calendar, and their response status updated in the meeting record. The meeting should remain active for other participants unless canceled by the organizer.
* R12: Any updates to a meeting (e.g., time, room, or participants) should automatically update all relevant calendars and trigger notifications.
