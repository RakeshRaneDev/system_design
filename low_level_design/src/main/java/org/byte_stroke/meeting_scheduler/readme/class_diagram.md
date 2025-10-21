# Class Diagram for the Meeting Scheduler
Learn to create a class diagram for a meeting scheduler using the bottom-up approach.
In this lesson, following a bottom-up approach, we identify and design the main classes, abstract classes, and interfaces for the Meeting Scheduler system. Each class is tied to the requirements and use cases defined earlier, ensuring completeness, traceability, and real-world clarity.

## Components of a meeting scheduler
As mentioned, we’ll design the meeting scheduler using a bottom-up approach.

### User
The User class represents a person who can organize or participate in meetings. It stores personal information (such as name and email), manages their calendar, and can respond to meeting invitations.
The class definition is shown below:
![User class.png](..%2Fimages%2FUser%20class.png)

### Interval
The Interval class contains the start time and end time of a meeting. The visual representation of the Interval class is as follows:
![the Interval class.png](..%2Fimages%2Fthe%20Interval%20class.png)

### MeetingRoom
The MeetingRoom class contains the details of any particular room, such as its capacity and a status, to identify whether it is currently available. It also contains a list of intervals to keep track of when the room is booked for a meeting.
The class diagram of the MeetingRoom class is provided below:
![MeetingRoom class.png](..%2Fimages%2FMeetingRoom%20class.png)


### Meeting
The Meeting class holds all details about a meeting, including the participants, their RSVP statuses, scheduled time, room, and subject.
The class diagram of the Meeting class is provided below:
![the Meeting class.png](..%2Fimages%2Fthe%20Meeting%20class.png)
### Calendar
The Calendar class tracks all meetings for a user. It allows adding or removing meetings based on user actions or meeting status changes.
The class definition is provided below:
![the Calendar class.png](..%2Fimages%2Fthe%20Calendar%20class.png)

### MeetingScheduler
The MeetingScheduler class coordinates the overall scheduling process. It manages the list of meeting rooms, schedules and cancels meetings, checks room availability, books and releases rooms, and triggers notifications.
The visual representation of the MeetingScheduler class is provided below:
![MeetingScheduler class.png](..%2Fimages%2FMeetingScheduler%20class.png)

### Notification
The Notification class will send a notification for an invitation to a user regarding any new meeting. It will also send a cancelation notification to a user as well, in case any meeting gets canceled or is postponed.
The UML representation of the class is shown below:
![Notification class.png](..%2Fimages%2FNotification%20class.png)

### Enumerations
Enumeration is generally a data type in which only a specific set of constants can be stored. Following is the list of enumerations required in the meeting scheduler system:
RSVPStatus (Enum): Tracks the current status of each participant’s response to a meeting invite.

![invatio_status_enum.png](..%2Fimages%2Finvatio_status_enum.png)


## Relationship between the classes
Now, we'll discuss the relationships between the classes we have defined above in the meeting scheduler.

### Association
The class diagram has the following association relationships:
* User and Meeting:
Each User can be an organizer or a participant in multiple Meeting instances. Each Meeting maintains a mapping (Map<User, RSVPStatus>) of all its participants and their current invitation status.
![The association relationship between classes.png](..%2Fimages%2FThe%20association%20relationship%20between%20classes.png)
* Meeting and MeetingRoom:
Each Meeting is assigned to a single MeetingRoom. Each MeetingRoom can host many meetings (at different intervals).

* Notification and User:
Notification objects are sent to users whenever a meeting is scheduled, updated, or canceled.

### Composition
The class diagram has the following composition relationships:
* User and Calendar:
Each User composes exactly one Calendar. If the user is deleted, their calendar is deleted as well.
* Calendar and Meeting:
Each Calendar is composed of multiple Meeting objects (as meetings are accepted or scheduled by the user).
* Meeting and Interval:
Each Meeting is scheduled for a single Interval. Each MeetingRoom keeps a list of all booked Intervals.

![The composition relationship between classes.png](..%2Fimages%2FThe%20composition%20relationship%20between%20classes.png)

### Aggregation
The class diagram has the following aggregation relationships:
* MeetingScheduler and MeetingRoom:
The MeetingScheduler aggregates all available MeetingRoom instances and manages their booking and release.
* MeetingScheduler and Meeting:
The MeetingScheduler aggregates and manages all scheduled Meeting instances for the organization.
* MeetingRoom and Interval:
Each MeetingRoom aggregates a collection of booked Intervals, representing its occupied time slots.

![The aggregation relationship between classes.png](..%2Fimages%2FThe%20aggregation%20relationship%20between%20classes.png)

### Class diagram of the meeting scheduler
In this section, we outline the multiplicity (cardinality) relationships between the main classes in our Meeting Scheduler. For each relationship, we explain the allowed number of instances on each side and the real-world or design rationale behind the connection. Understanding these relationships is key to modeling how different entities interact and collaborate to support key workflows in the system.
![class_map.png](..%2Fimages%2Fclass_map.png)

Here's the complete class diagram for the meeting scheduler:
![class_diagram.png](..%2Fimages%2Fclass_diagram.png)

## Design pattern
In the Meeting Scheduler system, several classic object-oriented design patterns are applied to ensure a robust, scalable, and maintainable design.
* Singleton pattern: The core scheduling functionality is centralized in the MeetingScheduler class, ensuring that there is only one active instance responsible for managing all meetings, rooms, and scheduling operations across the system. This is achieved using the Singleton pattern, which restricts the instantiation of the scheduler to a single, globally accessible object.
* Observer pattern: For user notifications, the system employs the Observer pattern. When a meeting is scheduled, updated, or canceled, all relevant users (participants or organizers) are notified automatically. This decouples the notification logic from the rest of the business workflow and ensures users always receive timely updates.

