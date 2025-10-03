# Code of Elevator System

## Enumerations
First of all, we will define all the enumerations required in the elevator system. According to the class diagram, there are three enumerations used in the system i.e., ElevatorState, Direction and DoorState. The code to implement these enumerations is as follows:
> Note: JavaScript does not support enumerations, so we will be using the Object.freeze() method as an alternative that freezes an object and prevents further modifications.

```
class Direction(Enum):
    UP = 1
    DOWN = 2
    IDLE = 3

class ElevatorState(Enum):
    IDLE = 1
    UP = 2
    DOWN = 3
    MAINTENANCE = 4

class DoorState(Enum):
    OPEN = 1
    CLOSED = 2
```
## Button
This section contains the implementation of a Button class and its subclasses which are HallButton and the ElevatorButton. The Button class has a pure virtual function isPressed() in it. The code to implement this relationship is given below:
```
class Button(ABC):
    def __init__(self):
        self.pressed = False

    def press_down(self):
        pass

    def reset(self):
        pass

    @abstractmethod
    def is_pressed(self):
```
## Elevator panel and hall panel
ElevatorPanel and the HallPanel are classes which use the instance of ElevatorButotn and HallButton respectively.

The code to implement these classes is provided below:
```
=class ElevatorPanel:
    def __init__(self, num_floors):
        self.floor_buttons = []
        self.open_button = None
        self.close_button = None
        self.emergency_button = None

    def get_floor_buttons(self):
        return self.floor_buttons

    def get_open_button(self):
        return self.open_button

    def get_close_button(self):
        return self.close_button

    def get_emergency_button(self):
        return self.emergency_button

    def enter_emergency(self):
        pass

    def exit_emergency(self):
        pass

class HallPanel:
    def __init__(self, floor_number, top_floor):
        self.up = None
        self.down = None

    def get_up_button(self):
        return self.up

    def get_down_button(self):
        return self.down
```
## Display
This component shows the implementation of the Display class. This class is responsible for showing the display inside and outside of the elevator cars. The code to implement this class is shown below:
```
class Display:
    def __init__(self):
        self.floor = 0
        self.load = 0
        self.direction = None
        self.state = None
        self.maintenance = False
        self.overloaded = False

    def update(self, f, direction, load, state, overloaded, maintenance):
        pass

    def show_elevator_display(self, car_id):
        pass
```

## Elevator car
This section contains the definition of the ElevatorCar class. An elevator car contains the instance of Door, Display, and ElevatorPanel. The implementation of this class is represented below:
```
class ElevatorCar:
    def __init__(self, id, num_floors):
        self.id = id
        self.current_floor = 0
        self.state = None
        self.door = Door()
        self.display = Display()
        self.panel = None
        self.request_queue = deque()
        self.load = 0
        self.overloaded = False
        self.maintenance = False

    def get_id(self): return 0
    def get_current_floor(self): return 0
    def get_state(self): return None
    def get_panel(self): return None
    def is_in_maintenance(self): return False
    def is_overloaded(self): return False
    def register_request(self, floor): pass
    def move(self): pass
    def stop(self): pass
    def enter_maintenance(self): pass
    def exit_maintenance(self): pass
    def emergency_stop(self): pass
    def add_load(self, kg): pass
    def remove_load(self, kg): pass
    def get_display(self): return None
    def get_door(self): return None
```

## Door and floor
This section contains the code for the Door and Floor classes. In the Door class, the enumeration DoorState is used and the Floor class contains the instances of Display and HallPanel. The implementation of this class is given below:
```
class Door:
    def __init__(self):
        self.state = None

    def open(self): pass
    def close(self): pass
    def is_open(self): return False
    def get_state(self): return None


class Floor:
    def __init__(self, floor_number, num_panels):
        self.floor_number = floor_number
        self.panels = []
        self.display = None

    def get_floor_number(self): return 0
    def get_panels(self): return None
    def get_panel(self, index): return None
    def get_display(self): return None
```

## Elevator system and building
The final class of an elevator system is the ElevatorSystem class which will be a Singleton class, which means that the entire system will have only one instance of this class. Moreover, there is a Building class that contains the instances of Floor and ElevatorCar. The implementation of these Singleton classes are provided below:
```
class ElevatorSystem:
    _system = None

    def __init__(self, floors, cars):
        self.building = None

    @staticmethod
    def get_instance(floors, cars): return None
    def get_cars(self): return None
    def get_building(self): return None
    def call_elevator(self, floor_num, direction): pass
    def get_nearest_idle_car(self, floor): return None
    def dispatcher(self): pass
    def monitoring(self): pass


class Building:
    def __init__(self, num_floors, num_cars, num_panels, num_displays_per_floor):
        self.floors = []
        self.cars = []

    def get_floors(self): return None
    def get_cars(self): return None
```

## Executable code: Elevator system
Below is a fully self-contained, runnable program in Java, C#, C++, Python, and JavaScript that demonstrates the core workflows of the Elevator System. The main driver code of the system resides in the Driver.java, Driver.cs, Driver.py, Driver.cpp, and Driver.js for all respective languages. You can click on the “Run” button to execute the codes.
### What does this code show
* System initialization: Creates a building with configurable numbers of floors, elevator cars, hall panels, and displays.
* Scenario 1 (Maintenance mode): Puts an elevator into maintenance, handles a passenger request, and demonstrates how the system dispatches elevators accordingly.
* Scenario 2 (Randomized elevator positions): Randomizes elevator locations and processes a new passenger request, showing how the nearest elevator is assigned and how car positions update.
* Status monitoring: After each action, the system outputs the current status of all elevators and relevant floor displays.

```
import random

from ElevatorSystem import ElevatorSystem
from Direction import Direction

def run_call(system, floor, dir):
    print(f"Passenger calls lift on floor {floor} ({dir.name})")
    nearest = system.get_nearest_idle_car(floor)
    if nearest is None:
        print("No idle elevator available right now.")
        return
    print(f"→ Nearest elevator is {nearest.get_id()+1} at floor {nearest.get_current_floor()}. Lift going {dir.name}.")
    system.call_elevator(floor, dir)
    system.dispatcher()
    print("\n[Status after dispatch]")
    system.monitoring()
    print('-' * 100)

def main():
    num_floors = 13
    num_cars = 3
    num_panels = 1   # Number of HallPanels per floor
    num_displays = 3 # Number of Displays per floor

    system = ElevatorSystem.get_instance(num_floors, num_cars, num_panels, num_displays)

    # SCENARIO 1
    print("=== Scenario 1: Elevator 3 in maintenance, passenger calls elevator from floor 7 ===\n")
    system.monitoring()
    print()

    car3 = system.get_cars()[2]
    car3.enter_maintenance()
    print()
    system.monitoring()
    print()

    run_call(system, 7, Direction.UP)

    car3.exit_maintenance()
    print("\n--- Resetting maintenance for all elevators ---\n")
    system.monitoring()
    print()

    print("=== Scenario 2: Random positions, passenger calls elevator from ground (0) to top (12) ===")

    for car in system.get_cars():
        random_floor = random.randint(0, num_floors - 1)
        print(f"\n== Setting random position for Elevator {car.get_id()+1} ==")
        print(f"→ Teleporting Elevator {car.get_id()+1} to floor {random_floor}")
        car.register_request(random_floor)
        car.move()

    print("\nElevator positions after random repositioning:")
    for car in system.get_cars():
        print(f"Elevator {car.get_id()+1} ► Floor: {car.get_current_floor()} | State: {car.get_state().name}")
    print()

    run_call(system, 0, Direction.UP)

if __name__ == "__main__":
    main()
```
