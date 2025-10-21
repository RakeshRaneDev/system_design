# Code for the Meeting Scheduler

## Enumeration
The following code defines the enumeration and custom data type used in the Blackjack game.
RSVPStatus: We need to create an enumeration to keep track of the suit of the states of the meetings, i.e., accepted, rejected or pending.
```
from enum import Enum

class RSVPStatus(Enum):
    ACCEPTED = "ACCEPTED"
    REJECTED = "REJECTED"
    PENDING = "PENDING"
```

## User
The User class refers to a participant taking part in a meeting. A user can either accept or reject an invitation. The definition of this class is given below:
```
from Calendar import Calendar
from RSVPStatus import RSVPStatus

class User:
    def __init__(self, name: str, email: str):
        self.name = name
        self.email = email
        self.calendar = Calendar()

    def respond_invitation(self, meeting: 'Meeting', response: str):
        meeting.update_participant_status(self, RSVPStatus(response))
        print(f"  · {self.name} responded: {response}")
        if response == RSVPStatus.REJECTED.value:
            self.calendar.remove_meeting(meeting)
        elif response == RSVPStatus.ACCEPTED.value:
            self.calendar.add_meeting(meeting)

    def view_meetings(self) -> list['Meeting']:
        return self.calendar.get_meetings()

    def get_name(self) -> str:
        return self.name

    def get_calendar(self) -> Calendar:
        return self.calendar
```

## Interval
The Interval class denotes the meeting interval (the start and end time).
```
from datetime import datetime

class Interval:
    def __init__(self, start_time: datetime, end_time: datetime):
        self.start_time = start_time
        self.end_time = end_time

    def get_start_time(self) -> datetime:
        return self.start_time

    def get_end_time(self) -> datetime:
        return self.end_time

    # Check if intervals overlap
    def overlaps(self, other: 'Interval') -> bool:
        return not (self.end_time <= other.start_time or self.start_time >= other.end_time)
```

## MeetingRoom
The MeetingRoom class will represent the meeting rooms, each having a specific capacity, a boolean to check if a room is available, and a list of intervals for which the room is booked. The definition of the class is provided below:
```
from Interval import Interval

class MeetingRoom:
    def __init__(self, id: int, capacity: int):
        self.id = id
        self.capacity = capacity
        self.booked_intervals: list[Interval] = []

    def is_available_for(self, interval: Interval, request_capacity: int) -> bool:
        if request_capacity > self.capacity:
            return False
        return all(not iv.overlaps(interval) for iv in self.booked_intervals)

    def book_interval(self, interval: Interval):
        self.booked_intervals.append(interval)

    def release_interval(self, interval: Interval):
        self.booked_intervals = [
            iv for iv in self.booked_intervals
            if iv.get_start_time() != interval.get_start_time() or iv.get_end_time() != interval.get_end_time()
        ]

    def get_id(self) -> int:
        return self.id

    def get_capacity(self) -> int:
        return self.capacity

    def __str__(self) -> str:
        return f"Room {self.id} (cap: {self.capacity})"
```

## Calendar
The Calendar class contains a list of meetings to keep track of all the scheduled meetings. The definition of this class is provided below:

```
class Calendar:
    def __init__(self):
        self.meetings: list['Meeting'] = []

    def add_meeting(self, meeting: 'Meeting'):
        if meeting not in self.meetings:
            self.meetings.append(meeting)

    def remove_meeting(self, meeting: 'Meeting'):
        if meeting in self.meetings:
            self.meetings.remove(meeting)

    def get_meetings(self) -> list['Meeting']:
        return self.meetings
```



## Meeting
The Meeting class outlines the meeting details such as the number and list of participants, meeting time interval, and meeting room. It also has the option to add more participants. The definition of this class is shown below:
```
from typing import List, Dict
from Interval import Interval
from MeetingRoom import MeetingRoom
from RSVPStatus import RSVPStatus

class Meeting:
    _next_id = 1

    def __init__(self, participants: List['User'], interval: Interval, room: MeetingRoom, subject: str):
        self.id = Meeting._next_id
        Meeting._next_id += 1

        self.participant_status: Dict['User', RSVPStatus] = {user: RSVPStatus.PENDING for user in participants}
        self.interval = interval
        self.room = room
        self.subject = subject

    def add_participants(self, participants: List['User']):
        for user in participants:
            if user not in self.participant_status:
                self.participant_status[user] = RSVPStatus.PENDING

    def update_participant_status(self, user: 'User', status: RSVPStatus):
        if user in self.participant_status:
            self.participant_status[user] = status

    def get_accepted_participants(self) -> List['User']:
        return [user for user, status in self.participant_status.items() if status == RSVPStatus.ACCEPTED]

    def get_pending_participants(self) -> List['User']:
        return [user for user, status in self.participant_status.items() if status == RSVPStatus.PENDING]

    def get_rejected_participants(self) -> List['User']:
        return [user for user, status in self.participant_status.items() if status == RSVPStatus.REJECTED]

    def get_interval(self) -> Interval:
        return self.interval

    def get_room(self) -> MeetingRoom:
        return self.room

    def get_subject(self) -> str:
        return self.subject
```

## MeetingScheduler
The MeetingScheduler class is the main class of the meeting scheduler and contains the organizer, which is responsible for scheduling and canceling a meeting as well as booking or releasing a room. It also checks if any meeting rooms are available for a meeting. In addition, there will be only one instance of the scheduler in the meeting scheduler. Therefore, the MeetingScheduler class will be a Singleton class to ensure that only one instance for the scheduler is created in the entire system.
The definition of this class is shown below:
```
from Meeting import Meeting
from Notification import Notification
from datetime import datetime
import random

class MeetingScheduler:
    def __init__(self, organizer: 'User', rooms: list['MeetingRoom']):
        self.organizer = organizer
        self.calendar = organizer.get_calendar()
        self.rooms = rooms

    def schedule_meeting(self, users: list['User'], interval: 'Interval', subject: str) -> 'Meeting | None':
        room = self.check_rooms_availability(len(users), interval)
        if room is None:
            print("✗ No room available for the interval.")
            return None

        self.book_room(room, interval)
        meeting = Meeting(users, interval, room, subject)

        for user in users:
            user.get_calendar().add_meeting(meeting)
        self.organizer.get_calendar().add_meeting(meeting)

        notification = Notification(1, f"Meeting Invitation: {subject}", datetime.now())
        for user in users:
            notification.send_invite(user, meeting)
            response = random.choice(["ACCEPTED", "REJECTED"])
            user.respond_invitation(meeting, response)

        print("✔ Meeting scheduled successfully!\n")
        return meeting

    def cancel_meeting(self, meeting: Meeting) -> bool:
        self.release_room(meeting.get_room(), meeting.get_interval())
        for user in meeting.get_accepted_participants():
            user.get_calendar().remove_meeting(meeting)
            notification = Notification(2, f"Meeting Cancelled: {meeting.get_subject()}", datetime.now())
            notification.send_cancel_notification(user, meeting)

        print("✔ Meeting cancelled!\n")
        return True

    def check_rooms_availability(self, capacity: int, interval: 'Interval') -> 'MeetingRoom | None':
        for room in self.rooms:
            if room.is_available_for(interval, capacity):
                return room
        return None

    def book_room(self, room: 'MeetingRoom', interval: 'Interval') -> bool:
        room.book_interval(interval)
        return True

    def release_room(self, room: 'MeetingRoom', interval: 'Interval') -> bool:
        room.release_interval(interval)
        return True
```

## Notification
The Notification class is responsible for sending notifications to users about any new meetings or cancelations. The definition of this class is provided below:

```
from datetime import datetime

class Notification:
    def __init__(self, notification_id: int, content: str, creation_date: datetime):
        self.notification_id = notification_id
        self.content = content
        self.creation_date = creation_date

    def send_invite(self, user: 'User', meeting: 'Meeting'):
        print(f"  - Notification sent to {user.get_name()} for meeting: {meeting.get_subject()}")

    def send_cancel_notification(self, user: 'User', meeting: 'Meeting'):
        print(f"  - Cancellation sent to {user.get_name()} for meeting: {meeting.get_subject()}")
```


## Executable code: Meeting scheduler
Below is a fully self-contained, runnable program in Java, C#, C++, Python, and JavaScript that demonstrates the core workflows of the Meeting scheduler. The main driver code of the system resides in the Driver.java, Driver.cs, Driver.py, Driver.cpp, and Driver.js for all respective languages. You can click on the “Run” button to execute the codes.
What does this code show
System initialization: Sets up meeting rooms, users, and the meeting scheduler.
* Scenario 1 (Schedule a meeting): The organizer schedules a meeting with participants, the system checks room availability, books the room, adds the meeting to user calendars, and sends invitation notifications. Each participant randomly accepts or rejects the invite.
* Scenario 2 (Schedule another meeting): Demonstrates scheduling another meeting, with the same invitation and response workflow.
* Scenario 3 (Cancel a meeting): The organizer cancels the meeting, which releases the room, removes the meeting from all calendars, and sends cancellation notifications to participants.
```
from MeetingRoom import MeetingRoom
from User import User
from MeetingScheduler import MeetingScheduler
from Interval import Interval
from datetime import datetime

def header(title: str):
    print("\n==============================")
    print(f"▶ {title}")
    print("==============================\n")

def arrow(msg: str):
    print(f"→ {msg}")

def get_interval(year, month, day, start_hour, start_min, end_hour, end_min) -> Interval:
    start = datetime(year, month, day, start_hour, start_min, 0)
    end = datetime(year, month, day, end_hour, end_min, 0)
    return Interval(start, end)

def main():
    # Set up rooms
    roomA = MeetingRoom(1, 4)
    roomB = MeetingRoom(2, 8)
    rooms = [roomA, roomB]

    # Set up users
    alice = User("Alice", "alice@email.com")
    bob = User("Bob", "bob@email.com")
    charlie = User("Charlie", "charlie@email.com")
    participants = [alice, bob, charlie]

    # Set up scheduler
    scheduler = MeetingScheduler(alice, rooms)

    # Scenario 1
    header("Scenario 1: Schedule a Meeting (Random Accept/Reject)")
    arrow("Scheduling meeting \"Design Review\" for Alice, Bob, Charlie...")
    interval1 = get_interval(2025, 7, 10, 10, 0, 11, 0)
    meeting1 = scheduler.schedule_meeting(participants, interval1, "Design Review")

    # Scenario 2
    header("Scenario 2: Schedule Another Meeting (Random Accept/Reject)")
    arrow("Scheduling meeting \"Sprint Planning\" for Alice, Bob, Charlie...")
    interval2 = get_interval(2025, 7, 10, 12, 0, 13, 0)
    meeting2 = scheduler.schedule_meeting(participants, interval2, "Sprint Planning")

    # Scenario 3
    header("Scenario 3: Cancel Meeting")
    arrow("Cancelling meeting \"Design Review\"...")
    if meeting1 is not None:
        scheduler.cancel_meeting(meeting1)

if __name__ == "__main__":
    main()
```
