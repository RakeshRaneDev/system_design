# 🧃 Vending Machine – Low Level Design (LLD)

## 1. Overview
A **Vending Machine** allows users to purchase items automatically by inserting money and selecting a product.  
The machine handles item selection, payment validation, change calculation, and item dispensing.

---

## 2. Functional Requirements

### Core Features
1. Display all available items with their price and stock.
2. Accept money (coins, notes, or digital payment).
3. Validate item selection and payment.
4. Dispense the selected item.
5. Return balance if any.
6. Allow transaction cancellation and refund.
7. Provide admin operations for refilling and maintenance.

---

## 3. Non-Functional Requirements

| Attribute        | Description |
|------------------|-------------|
| **Reliability** | Handle invalid inputs, failed payments gracefully. |
| **Concurrency** | Prevent race conditions during simultaneous access. |
| **Extensibility** | Easy to add new items or payment methods. |
| **Maintainability** | Modular class design for easier updates. |
| **Security** | Protect admin operations. |
| **Performance** | Quick response to user inputs. |

---

## 4. Class Design

### 🧩 Class Diagram 

```mermaid
classDiagram
    class VendingMachine {
        - Inventory inventory
        - PaymentProcessor paymentProcessor
        - Display display
        - VendingMachineState state
        + selectItem(code)
        + insertMoney(amount)
        + dispenseItem()
        + cancelTransaction()
    }

    class Item {
        - String id
        - String name
        - double price
        - int quantity
    }

    class Inventory {
        - Map<Item, Integer> stock
        + addItem(item, count)
        + removeItem(item)
        + getItem(code)
        + isAvailable(item)
    }

    class PaymentProcessor {
        - List<Double> acceptedCoins
        - double balanceInserted
        + acceptPayment(amount)
        + isPaymentSufficient(itemPrice)
        + calculateChange(itemPrice)
        + returnChange()
    }

    class CoinDispenser {
        - Map<Double, Integer> coinInventory
        + dispenseChange(amount)
        + refillCoins()
    }

    class Display {
        + showMessage(message)
        + showItems(inventory)
    }

    class Admin {
        + refillItem(item, count)
        + updatePrice(item, newPrice)
        + viewSalesReport()
    }

    class VendingMachineState {
        <<interface>>
        + insertMoney(amount)
        + selectItem(code)
        + dispenseItem()
        + cancel()
    }

    class IdleState
    class HasMoneyState
    class ItemSelectedState
    class DispensingState
    class OutOfStockState

    %% Relationships
    VendingMachine --> Inventory
    VendingMachine --> PaymentProcessor
    VendingMachine --> Display
    VendingMachine --> VendingMachineState
    PaymentProcessor --> CoinDispenser
    Inventory --> Item
    VendingMachineState <|-- IdleState
    VendingMachineState <|-- HasMoneyState
    VendingMachineState <|-- ItemSelectedState
    VendingMachineState <|-- DispensingState
    VendingMachineState <|-- OutOfStockState