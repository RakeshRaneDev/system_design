# 🅿️ Parking Lot System – Detailed Requirements

## 📘 Overview

The **Parking Lot System** is designed to manage parking spaces efficiently in a multi-level parking facility.  
It should handle vehicle entry and exit, parking spot allocation, payments, and availability tracking.  
The system must also be **concurrent-safe**, allowing multiple users and attendants to interact simultaneously without conflicts.

---

## 🎯 Goals

- Efficiently manage and allocate parking spots.
- Support multiple types of vehicles and spot sizes.
- Handle concurrency safely (multiple cars entering/exiting).
- Allow users to select the **closest available spot** based on defined criteria.
- Generate parking tickets and calculate fees accurately.
- Maintain real-time availability across all levels.

---

## 🧩 Core Functional Requirements

### 1. Parking Lot Configuration
- The parking lot consists of **multiple floors (levels)**.
- Each floor contains **multiple parking spots**, categorized as:
    - `Compact`
    - `Large`
    - `Handicapped`
    - `Electric Vehicle (EV)`
- Each spot has a **unique ID**, `floorNumber + spotNumber`.

### 2. Vehicle Types
The system supports different vehicle categories:
- `Motorcycle`
- `Car`
- `Truck`
- `Electric Car`

Each vehicle type can only park in specific spot types:
| Vehicle Type | Allowed Spot Types |
|---------------|--------------------|
| Motorcycle    | Compact, Large, Handicapped |
| Car           | Compact, Large |
| Truck         | Large |
| Electric Car  | EV, Large |

### 3. Vehicle Entry Process
- On arrival, the vehicle requests entry.
- System checks:
    - Vehicle type
    - Availability of compatible spot
- If a spot is available:
    - Allocate the **nearest** spot (based on defined proximity rules)
    - Generate a **parking ticket**
    - Mark spot as **occupied**

If no spot is available → user is informed and queued (optional extension).

### 4. Vehicle Exit Process
- User provides ticket at exit.
- System calculates **parking duration**.
- Applies **fee policy**:
    - Flat rate per hour or dynamic rate based on duration.
- Updates:
    - Spot marked **available**
    - Ticket marked **closed**
    - Payment recorded

---

## 🧮 Fee Calculation Rules

Basic Example (configurable per implementation):

| Vehicle Type | Base Fee (₹) | Per Hour Fee (₹) |
|---------------|---------------|------------------|
| Motorcycle    | 10            | 5                |
| Car           | 20            | 10               |
| Truck         | 30            | 15               |
| Electric Car  | 25            | 10               |

---

## 🚗 Spot Selection Strategy (Closest Spot Allocation)

The system should allocate the **closest available parking spot** using one of the following strategies:

1. **Default Strategy (Level-based Proximity):**
    - Choose the spot on the **lowest floor**.
    - Within that floor, choose the **nearest available spot** to the entrance (smallest spot ID).

2. **EV Optimization Strategy:**
    - Prefer EV spots first for electric cars.
    - If none available, fallback to large spots.

3. **Handicapped Spot Priority:**
    - If a user is tagged as handicapped, allocate nearest `Handicapped` spot if available.

4. **Custom Strategy Support (Extension):**
    - Implement strategy pattern for selection:
        - Example: Distance-based, Time-of-day based, or Price-based selection.

---

## 🔐 Concurrency Requirements

The system must be **thread-safe** and handle **simultaneous operations** gracefully.

### Concurrency Scenarios
1. **Multiple cars entering at the same time**
    - Prevent double-booking of a single spot.
    - Synchronize spot allocation per floor or global lock (using `ReentrantLock` or similar mechanism).

2. **Multiple cars leaving simultaneously**
    - Ensure consistent update of available spots count.
    - Ticket close and spot release should be atomic operations.

3. **Concurrent Reads**
    - Allow multiple read operations (e.g., checking availability) without blocking.

4. **Concurrent Writes**
    - Writes (allocation or release) should lock only affected floor/spot to minimize contention.

5. **Deadlock Prevention**
    - Design locking hierarchy (e.g., always lock floors in ascending order).

### Example Thread-Safe Approaches
- Use `ConcurrentHashMap<Integer, Spot>` for spot management.
- Use fine-grained locks per floor (`ReentrantLock` or `StampedLock`).
- Use atomic variables (`AtomicInteger`) for counters (e.g., total available spots).

---

## 🧱 Key System Components

| Component | Responsibility |
|------------|----------------|
| `ParkingLot` | Singleton class that manages all floors, entry/exit points |
| `Floor` | Holds and manages multiple parking spots |
| `ParkingSpot` | Represents individual parking spaces with state (`available`, `occupied`) |
| `Vehicle` | Base class for different vehicle types |
| `Ticket` | Holds ticket ID, vehicle info, entry time, exit time, and fee |
| `PaymentProcessor` | Calculates fee and processes payment |
| `SpotAllocator` | Strategy pattern implementation for spot selection |
| `ConcurrencyManager` | Manages locks and synchronization between threads |

---

## 🧰 Non-Functional Requirements

- **Scalability:** Should support hundreds of spots and floors.
- **Thread Safety:** All entry/exit operations must be safe under concurrent load.
- **Extensibility:** Easy to add new vehicle or spot types.
- **Performance:** Allocation and release must occur in O(log n) or better.
- **Fault Tolerance:** If allocation fails mid-process, system must rollback to consistent state.

---

## 📈 Example Flow (Sequence)

**Scenario: Car enters and finds a spot**

1. `EntryGate` receives request with vehicle details.
2. `ParkingLot` checks availability via `SpotAllocator`.
3. `SpotAllocator` picks the nearest compatible spot.
4. Lock spot → mark as occupied → issue ticket.
5. Unlock floor → return ticket.

**Scenario: Car exits**

1. User provides ticket ID.
2. `PaymentProcessor` computes fee.
3. Spot marked as available (atomic operation).
4. Ticket marked as closed.

---

## 🧩 Optional Extensions

- **Reservation System**: Book spots in advance.
- **Mobile App Integration**: View spot availability and reserve remotely.
- **License Plate Recognition**: Auto-detect vehicle entry/exit.
- **Dynamic Pricing**: Increase rates during peak hours.
- **Real-Time Notifications**: Notify users of spot availability.

---

## ✅ Summary

This Parking Lot System should:
- Support **multi-level** and **multi-type** spots.
- Be **thread-safe** for concurrent access.
- Allow **customizable spot allocation strategies**.
- Be **modular, extensible, and performant**.

It serves as a strong **Low-Level Design (LLD)** example covering:
- OOP principles
- Design patterns (Singleton, Strategy, Factory)
- Concurrency control
- Real-world system modeling

---

# 🅿️ Parking Lot System – Detailed Requirements (with Class Diagram)

## 📘 Overview

The **Parking Lot System** manages vehicle parking in a multi-floor facility.  
It supports multiple vehicle types, concurrent access, payment handling, and dynamic spot selection strategies.

---

## 🎯 Goals

- Efficient parking spot allocation.
- Multi-vehicle, multi-floor support.
- Concurrency-safe operations.
- Customizable spot selection (nearest, EV-first, etc.).
- Accurate ticketing and fee management.

---

## 🧩 Core Functional Requirements

### Parking Lot Configuration
- Multiple floors; each floor contains several parking spots.
- Spot types: **Compact**, **Large**, **Handicapped**, **EV**.
- Each spot has a unique ID (`floorNumber + spotNumber`).

### Vehicle Types
- `Motorcycle`, `Car`, `Truck`, `ElectricCar`
- Each can occupy specific spot types (see mapping below):

| Vehicle Type | Allowed Spot Types |
|---------------|--------------------|
| Motorcycle    | Compact, Large, Handicapped |
| Car           | Compact, Large |
| Truck         | Large |
| Electric Car  | EV, Large |

---

## 🚗 Spot Selection Strategy

Spot allocation should pick the **closest available spot** using a configurable strategy:

1. **Default (Nearest Spot First)** – choose lowest floor, smallest spot number.
2. **EV-first Strategy** – prefer EV spots for electric cars.
3. **Handicapped Priority** – assign Handicapped spot for eligible users.
4. **Custom Strategies** via Strategy Pattern (distance, time, price, etc.)

---

## 🔐 Concurrency Requirements

1. **Thread-Safe Access**:
    - Multiple vehicles can enter/exit simultaneously.
    - Prevent race conditions on spot allocation/release.

2. **Synchronization**:
    - Use locks (`ReentrantLock`, `StampedLock`) per floor or spot.
    - Atomic variables for counts (e.g., available spots).

3. **Deadlock Prevention**:
    - Lock ordering by floor number.
    - Release locks in consistent order.

4. **Concurrent Reads/Writes**:
    - Allow multiple concurrent reads (availability).
    - Write (allocation/release) operations must be atomic.

---

## 🧱 Key Classes and Relationships

### 📋 Class Responsibilities

| Class | Description |
|--------|-------------|
| **ParkingLot** | Singleton managing all floors, entry/exit points, and spot allocation. |
| **Floor** | Contains multiple parking spots and manages availability. |
| **ParkingSpot** | Represents an individual parking space (available/occupied). |
| **Vehicle** | Base class for vehicles; subtypes include Car, Truck, ElectricCar. |
| **Ticket** | Records parking session details (vehicle, spot, entry/exit time, fee). |
| **PaymentProcessor** | Calculates and processes parking fee. |
| **SpotAllocator** | Strategy pattern interface for spot selection logic. |
| **ConcurrencyManager** | Manages locks and thread safety across system. |

---

## 🧮 Fee Calculation Rules

| Vehicle Type | Base Fee (₹) | Per Hour (₹) |
|---------------|---------------|---------------|
| Motorcycle | 10 | 5 |
| Car | 20 | 10 |
| Truck | 30 | 15 |
| Electric Car | 25 | 10 |

---

## 🧭 Sequence Example: Vehicle Entry Flow

1. `EntryGate` receives vehicle details.
2. `ParkingLot` queries `SpotAllocator` for nearest compatible spot.
3. `SpotAllocator` selects available spot (thread-safe).
4. Spot is locked → marked as occupied → `Ticket` issued.
5. Lock released → response returned.

---

## ⚙️ System Relationships Summary

1. ParkingLot → Floor: Composition (ParkingLot owns Floors)
2. Floor → ParkingSpot: Composition
3. ParkingSpot → Vehicle: Association (occupies)
4. ParkingLot → SpotAllocator: Strategy pattern (runtime configurable)
5. Vehicle → Ticket: Association (1:1 per session)
6. ParkingLot → ConcurrencyManager: Coordination for locks
7. PaymentProcessor → Ticket: Fee computation

## 🧰 Non-Functional Requirements

- Scalable: Handle hundreds of concurrent entries/exits.
- Thread-safe: Use locks to ensure atomic operations.
- Extensible: Add new vehicle/spot types easily.
- Performant: Allocation O(log N) or better.
- Reliable: Rollback in case of partial failure.

## 🚀 Future Enhancements
- Reservation System
- License Plate Recognition
- Dynamic Pricing
- Mobile Integration
- Admin Dashboard

## 🪢 Mermaid UML Class Diagram

```mermaid
classDiagram
    %% Core Entities
    class ParkingLot {
        -List<Floor> floors
        -SpotAllocator allocator
        -Map<String, Ticket> activeTickets
        +getInstance() ParkingLot
        +assignSpot(Vehicle): Ticket
        +releaseSpot(Ticket): void
    }

    class Floor {
        -int floorNumber
        -List<ParkingSpot> spots
        +getAvailableSpot(Vehicle): ParkingSpot
        +releaseSpot(spotId): void
    }

    class ParkingSpot {
        -String id
        -SpotType type
        -boolean isAvailable
        +assignVehicle(Vehicle): void
        +removeVehicle(): void
    }

    class Vehicle {
        <<abstract>>
        -String licensePlate
        -VehicleType type
    }

    class Car {
    }

    class Truck {
    }

    class ElectricCar {
        -int batteryLevel
    }

    class Ticket {
        -String ticketId
        -Vehicle vehicle
        -ParkingSpot spot
        -LocalDateTime entryTime
        -LocalDateTime exitTime
        -double amount
        +calculateDuration(): long
    }

    class PaymentProcessor {
        +calculateFee(Vehicle, long): double
        +processPayment(Ticket): boolean
    }

    class SpotAllocator {
        <<interface>>
        +findSpot(ParkingLot, Vehicle): ParkingSpot
    }

    class NearestSpotAllocator {
        +findSpot(ParkingLot, Vehicle): ParkingSpot
    }

    class EVSpotAllocator {
        +findSpot(ParkingLot, Vehicle): ParkingSpot
    }

    class ConcurrencyManager {
        -Map<Integer, Lock> floorLocks
        +lockFloor(int): void
        +unlockFloor(int): void
    }

    %% Relationships
    ParkingLot --> Floor : contains >
    Floor --> ParkingSpot : manages >
    ParkingSpot --> Vehicle : occupiedBy >
    ParkingLot --> SpotAllocator : uses >
    ParkingLot --> Ticket : creates >
    ParkingLot --> PaymentProcessor : interacts >
    Vehicle <|-- Car
    Vehicle <|-- Truck
    Vehicle <|-- ElectricCar
    SpotAllocator <|-- NearestSpotAllocator
    SpotAllocator <|-- EVSpotAllocator
    ParkingLot --> ConcurrencyManager : coordinates >


