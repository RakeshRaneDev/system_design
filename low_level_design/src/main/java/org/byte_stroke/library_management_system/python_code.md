# Code of Library Management System
Write the code for the designed classes in different languages.
We’ve gone over the different aspects of the library management system and observed the attributes attached to the problem using various UML diagrams. Let us now explore the more practical side of things, where we will work on implementing the library management system using multiple languages. This is usually the last step in an object-oriented design interview process.
We have chosen the following languages to write the skeleton code of the different classes present in the library management system:

## bLibrary management
This section will provide the skeleton code of the classes designed in the class diagram lesson.

> Note: For simplicity, we are not defining getter and setter functions. The reader can assume that all class attributes are private, accessed through their respective public getter methods, and modified only through their public method functions.

## Enumerations
First, we will define all the enumerations required in the library management system. According to the class diagram, four enumerations are used in the system: BookFormat, BookStatus, ReservationStatus, and AccountStatus. The code to implement these enumerations is as follows:`

```
class BookFormat(Enum):
    HARDCOVER = 1
    PAPERBACK = 2
    AUDIOBOOK = 3
    EBOOK = 4
    NEWSPAPER = 5
    MAGAZINE = 6
    JOURNAL = 7

class BookStatus(Enum):
    AVAILABLE = 1
    RESERVED = 2
    LOANED = 3
    LOST = 4

class ReservationStatus(Enum):
    WAITING = 1
    PENDING = 2
    CANCELED = 3
    NONE = 4

class AccountStatus(Enum):
    ACTIVE = 1
    CLOSED = 2
    CANCELED = 3
    BLOCKLISTED = 4
    NONE = 5
```

## Address and person
This section contains the code for Address and Person classes where the Person class is composed of an Address class. The implementation of these classes can be found below:
```
class Address:
    def __init__(self, street_address, city, state, zip_code, country):
        self.street_address = street_address
        self.city = city
        self.state = state
        self.zip_code = zip_code
        self.country = country

class Person:
    def __init__(self, name, address, email, phone):
        self.name = name
        self.address = address
        self.email = email
        self.phone = phone
```

## User
The User is an abstract class that represents the various people or actors that can interact with the system. Since there are two types of users, the librarian and the library member, the user can either be a Librarian or a Member. The implementation of the mentioned classes is shown below:
```
class User(ABC):
    def __init__(self, id, password, person, card):
        self.id = id
        self.password = password
        self.status = AccountStatus.ACTIVE
        self.person = person
        self.card = card

    @abstractmethod
    def reset_password(self):
        pass

class Librarian(User):
    def add_book_item(self, book_item: BookItem):
        pass

    def block_member(self, member: Member):
        pass

    def un_block_member(self, member: Member):
        pass

    def reset_password(self):
        pass

class Member(User):
    def __init__(self, id, password, person, card):
        super().__init__(id, password, person, card)
        self.date_of_membership = datetime.now()
        self.total_books_checked_out = 0

    def reserve_book_item(self, book_item: BookItem):
        pass

    def checkout_book_item(self, book_item: BookItem):
        pass

    def return_book_item(self, book_item: BookItem):
        pass

    def renew_book_item(self, book_item: BookItem):
        pass

    def reset_password(self):
        pass

    def _increment_total_books_checked_out(self):
        pass

    def _check_for_fine(self, book_item_id):
        pass
```
## Book reservation, book lending and fine
This component shows the implementation of BookReservation, BookLending, and Fine classes. These classes will be responsible for managing reservations against books, managing reservations, and calculating fine on books. The code is shown below:

```
class BookReservation:
    def __init__(self, item_id, member_id):
        self.item_id = item_id
        self.creation_date = datetime.now()
        self.status = ReservationStatus.PENDING
        self.member_id = member_id

    @staticmethod
    def fetch_reservation_details(book_item_id):
        pass

class BookLending:
    def __init__(self, item_id, member_id):
        self.item_id = item_id
        self.creation_date = datetime.now()
        self.due_date = None
        self.return_date = None
        self.member_id = member_id
        self.book_reservation = None
        self.user = None

    @staticmethod
    def lend_book(book_item_id, member_id):
        pass

    @staticmethod
    def fetch_lending_details(book_item_id):
        pass

class Fine:
    def __init__(self, book_item_id, member_id):
        self.creation_date = datetime.now()
        self.book_item_id = book_item_id
        self.member_id = member_id

    @staticmethod
    def collect_fine(member_id, days):
        pass
```
## Book and rack
The Book is an abstract class and BookItem represents each copy of the book. For example, if there are two copies of the same book then there would only be one Book object and two BookItem objects. The code to implement these classes is as follows:
```
class Book:
    def __init__(self, isbn, title, subject, publisher, language,
                 number_of_pages, book_format, authors):
        self.isbn = isbn
        self.title = title
        self.subject = subject
        self.publisher = publisher
        self.language = language
        self.number_of_pages = number_of_pages
        self.book_format = book_format  # BookFormat enum
        self.authors = authors  # list of Author

class BookItem:
    def __init__(self, id, book, placed_at, price, date_of_purchase, publication_date):
        self.id = id
        self.book = book
        self.is_reference_only = False
        self.borrowed = None
        self.due_date = None
        self.price = price
        self.status = BookStatus.AVAILABLE
        self.date_of_purchase = date_of_purchase
        self.publication_date = publication_date
        self.placed_at = placed_at

    def checkout(self, member_id):
        pass

    def set_placed_at(self, rack: Rack):
        self.placed_at = rack

    def set_added_by(self, librarian):
        pass

    def get_book(self):
        return self.book

class Rack:
    def __init__(self, number, location_identifier):
        self.number = number
        self.location_identifier = location_identifier
        self.book_items = []

    def add_book_item(self, book_item: BookItem):
        pass
```
## Notification
The Notification class is another abstract class responsible for sending notifications to the users, with the PostalNotification and EmailNotification classes as its child classes. The implementation of this class can be found below:
```
class Notification(ABC):
    def __init__(self, notification_id, content):
        self.notification_id = notification_id
        self.creation_date = datetime.now()
        self.content = content
        self.book_lending = None
        self.book_reservation = None

    @abstractmethod
    def send_notification(self):
        pass

class PostalNotification(Notification):
    def __init__(self, notification_id, content, address):
        super().__init__(notification_id, content)
        self.address = address

    def send_notification(self):
        pass

class EmailNotification(Notification):
    def __init__(self, notification_id, content, email):
        super().__init__(notification_id, content)
        self.email = email

    def send_notification(self):
        pass
```

## Search and catalog
The Search is an interface used in the efficient searching of library books by various methods, and the Catalog class is used to implement the search interface to help in book searching. The code to perform this functionality is presented below:
```
class Search(ABC):
    @abstractmethod
    def search_by_title(self, title):
        pass

    @abstractmethod
    def search_by_author(self, author):
        pass

    @abstractmethod
    def search_by_subject(self, subject):
        pass

    @abstractmethod
    def search_by_publication_date(self, pub_date):
        pass

class Catalog(Search):
    def __init__(self):
        self.book_titles = defaultdict(list)
        self.book_authors = defaultdict(list)
        self.book_subjects = defaultdict(list)
        self.book_publication_dates = defaultdict(list)

    def search_by_title(self, query):
        pass

    def search_by_author(self, query):
        pass

    def search_by_subject(self, query):
        pass

    def search_by_publication_date(self, query):
        pass
```

## Library
The final class of LMS is the Library class which will be a Singleton class, meaning the entire system will have only one instance of this class. The implementation of this class can be found below:

```
class Library:
    _instance = None

    def __init__(self):
        if Library._instance is not None:
            raise Exception("This class is a singleton!")
        self.name = None
        self.address = None
        self.catalog = Catalog()
        Library._instance = self

    @staticmethod
    def get_instance():
        if Library._instance is None:
            Library()
        return Library._instance

    def get_address(self):
        pass
```

## Executable code: Library management system
Below is a fully self-contained, runnable program in Java, C#, C++, Python, and JavaScript that demonstrates the core workflows of the Library Management System. The main driver code of the system resides in the Driver.java, Driver.cs, Driver.py, Driver.cpp, and Driver.js for all respective languages. You can click on the “Run” button to execute the codes.

### What does this code show
* System initialization: Sets up books, copies, members, a librarian, and the catalog.
* Scenario 1 (Member searches and reserves a book): The member searches for a book, reserves it, and checks out the book.
* Scenario 2 (Member returns the book (on time and late)): The member returns the book, and the system calculates if a fine is due.
* Scenario 3 (Book renewal): The member renews a borrowed book.
* Notifications: Overdue and reservation notifications are simulated.

```
from datetime import datetime, timedelta
from Address import Address
from Author import Author
from Book import Book
from BookFormat import BookFormat
from Rack import Rack
from BookItem import BookItem
from Library import Library
from LibraryCard import LibraryCard
from Person import Person
from Member import Member
from Librarian import Librarian
from EmailNotification import EmailNotification
from PostalNotification import PostalNotification

if __name__ == "__main__":
    try:
        print("\n========== SYSTEM INITIALIZATION ==========\n")
        lib_address = Address("1 Main St", "Springfield", "State", 12345, "Country")
        library = Library.get_instance("Springfield Public Library", lib_address)

        # Authors
        author1 = Author("Jane Austen", lib_address, "austen@email.com", "123456", "Famous novelist")
        author2 = Author("Mark Twain", lib_address, "twain@email.com", "234567", "Another novelist")

        # Books and BookItems
        pub_date1 = datetime.strptime("2000-01-01", "%Y-%m-%d")
        pub_date2 = datetime.strptime("2005-05-20", "%Y-%m-%d")
        book1 = Book("ISBN123", "Pride and Prejudice", "Novel", "Publisher A",
                     pub_date1, "English", 300, BookFormat.HARDCOVER, [author1])
        book2 = Book("ISBN456", "Adventures of Huckleberry Finn", "Adventure", "Publisher B",
                     pub_date2, "English", 250, BookFormat.PAPERBACK, [author2])
        rack1 = Rack(1, "A1")
        rack2 = Rack(2, "B2")
        book_item1 = BookItem("BI001", book1, rack1, 30.0, datetime.strptime("2020-01-01", "%Y-%m-%d"), book1.publication_date)
        book_item2 = BookItem("BI002", book2, rack2, 25.0, datetime.strptime("2021-06-15", "%Y-%m-%d"), book2.publication_date)
        library.catalog.add_book_item(book_item1)
        library.catalog.add_book_item(book_item2)

        # Users
        card_member = LibraryCard("CARD1001", datetime.now())
        person_member = Person("Alice", lib_address, "alice@email.com", "345678")
        member = Member("MEM001", "pass", person_member, card_member)

        card_librarian = LibraryCard("CARD2001", datetime.now())
        person_librarian = Person("Libby", lib_address, "libby@email.com", "456789")
        librarian = Librarian("LIB001", "pass", person_librarian, card_librarian)

        # SCENARIO 1: Search and Reserve
        print("\n------------------------------")
        print(">>> SCENARIO 1: Member searches and reserves a book")
        print("------------------------------\n")

        found_books = library.catalog.search_by_title("Pride and Prejudice")
        if found_books:
            book_item = found_books[0]
            print(f"-> Member [{member.person.name}] attempts to reserve book item [{book_item.id}]")
            member.reserve_book_item(book_item)
            print(f"-> Member [{member.person.name}] checks out reserved book item [{book_item.id}]")
            member.checkout_book_item(book_item)
        else:
            print("Book not found.")

        # SCENARIO 2: Return Book (Late Return)
        print("\n------------------------------")
        print(">>> SCENARIO 2: Member returns the book late")
        print("------------------------------\n")
        book_item1.due_date = datetime.now() - timedelta(days=3)
        print(f"-> Member [{member.person.name}] returns book item [{book_item1.id}] after due date.")
        member.return_book_item(book_item1)

        # SCENARIO 3: Renew Book
        print("\n------------------------------")
        print(">>> SCENARIO 3: Member renews a book")
        print("------------------------------\n")
        print(f"-> Member [{member.person.name}] checks out the book again.")
        member.checkout_book_item(book_item1)
        print(f"-> Member [{member.person.name}] renews the book.")
        member.renew_book_item(book_item1)

        # NOTIFICATIONS
        print("\n------------------------------")
        print(">>> NOTIFICATIONS")
        print("------------------------------\n")
        email_notification = EmailNotification("N001", "Your book is overdue!", member.person.email)
        email_notification.send_notification()

        postal_notification = PostalNotification("N002", "Please return your book!", member.person.address)
        postal_notification.send_notification()

        print("\n========== END OF DEMO ==========\n")

    except Exception as ex:
        print("Exception occurred:", ex)
```


























