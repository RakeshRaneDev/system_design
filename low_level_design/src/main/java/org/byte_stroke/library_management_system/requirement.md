# Getting Ready: Library Management System

Understand the library management system problem and learn the questions to simplify this problem.
A library management system (LMS) aims to automate all library activities. It is software that helps manage all the primary functions of library management. With the help of a library management system, we can organize, handle, and maintain the records of numerous books and members comprehensively and systematically.
A librarian can use this software to track the number of books in the library and retain several records, including new books, borrowed books with due dates, members who borrowed books, returned books, fines for late returned books, etc. In short, the library management system stores and updates the complete library database.
LMS also supports maintaining the physical library. The user can keep track of a book’s position in the library and search for whether or not the specific book is currently available. Therefore, LMS helps organize and retrieve library data efficiently.

In this LLD interview case study, your focus will be on:
* Efficiently searching and managing books and other resources by various attributes (title, author, category, etc.).
* Handling book borrowing, returning, and reservation workflows.
* Managing user roles (librarian, member) and their permissions.
* Supporting fine calculation and renewal processes for overdue and renewed books.
> Note: This system model can be adapted for academic, public, or private libraries and can support various library policies (such as special collections, inter-library loans, or different user types).

## Expectations from the interviewee
The LMS has multiple components, each with its specific requirements and constraints. Let’s look at some of the main expectations the interviewer will want to hear you discuss in more detail.

### Efficient searching
Searching for books is one of the most crucial functions of LMS. The user must be able to search for any book. Different users may want to search for a book through different methods. Therefore, the interviewer can ask questions like these:
* Would the user be able to search for a book using attributes other than the book name?
* How will the user be able to search for a book by its author name, publication date, etc.?
* How will the user search a specific category of books, like magazines, journals, newspapers, etc.?

### Versatility
Before designing the system, it is mandatory to specify the actors of the system. Hence, the interviewer can ask about the actors of the system as follows:
* Can only a librarian or all library members use the software?

### Book reservation
Another significant feature of LMS is the reservation of the book.
* What is the mechanism of book reservation?
* Can a member reserve a book again if it is already reserved?
* How does the status of the book change when a member returns a book?

### Book renewal
Similar to the book reservation, the interviewer can ask about the book renewal functionality with a question like this:
* What is the mechanism of book renewal if a member wants to hold a book for longer?

### Fine management
There is another question that the interviewer may be interested in asking:
* How is the calculation and deduction of fines handled if the book is returned late?

### Design approach
We will design this Library Management System using the bottom-up design approach. For this purpose, we will follow the steps below:
* First, we’ll identify simple core entities such as Book, Member, and Librarian, and define their responsibilities.
* Next, we’ll model book search, borrowing, return, reservation, renewal, and fine calculation workflows.
* We’ll ensure the system supports role-based access control and maintains accurate transaction and catalog records.

This design approach will address concurrency and scalability and follow SOLID principles to ensure maintainability and extensibility. Later, diagrams and code will illustrate major workflows and class structures.

## Requirements for the Library Management System
Learn about all requirements of the library management system.
In this lesson, we will list the requirements of our library management system. This is a crucial step, as requirements define the scope of a problem. Getting them right from the interviewer and understanding them well will make the system-design process smooth and easy.
We’ll use the notational convention to identify each requirement with a unique label “Rn,” where “R” is short for Requirement and “n” is a natural number.

### Requirement collection
For LMS (library management system), the requirements have been defined below:
* R1: The system should store information about books and members, and maintain a complete log of all book borrowing, return, reservation, and renewal transactions.
* R2: Every book must have a unique identification number and detailed information, including rack/location in the library.
* R3: Each book should include ISBN, title, author name, subject, and publication date.
* R4: A book can have multiple copies; each physical copy is a distinct book item with a unique ID.
* R5: The system must support two types of users: librarian and member, each with a library card and unique card number.
* R6: Every user must have a library card with a unique card number.
* R7: Members can borrow a maximum of 10 books at a time.
* R8: A member can borrow a book for a maximum of 15 days.
* R9: Only one member can reserve each book item at a time.
* R10: The system must record who issued, reserved, or renewed a book item and on which date.
* R11: The system must allow members to renew borrowed books, according to policy limits.
* R12: The system should notify members if a book is not returned by the due date or when a reserved book becomes available.
* R13: If a book is unavailable, a member should be able to reserve it for when it becomes available.
* R14: The system should allow users to search for books by title, author, subject, or publication date.
