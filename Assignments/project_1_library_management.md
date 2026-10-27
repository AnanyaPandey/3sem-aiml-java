# RULES FOR ALL PROJECTS

1. Work in a group of 4. Each person makes ONE main part (one or more classes, as written in your project). Write your name in a comment at the top of your class.
2. Use only Java. Use arrays to store many things (many spots, many books, many accounts). Do not use ArrayList, HashMap, or any database.
3. Your project must use these 5 things. Here is what each one means:
   - **Private variables with getters (encapsulation):** Make your variables private. Give a public method (getter) to read each one. Example: `private String spotId;` and `public String getSpotId()`.
   - **Inheritance:** One parent class, and child classes that extend it. Example: Vehicle is the parent. Car and Bike are children.
   - **Overriding:** The parent has a method. Each child writes its own version of the same method. Example: `getHourlyRate()` gives 20 in Car and 10 in Bike.
   - **Abstract class:** A parent class that you never create objects of directly. You write `abstract` before the class name. Example: `abstract class Vehicle`.
   - **Custom exceptions:** Your own error classes. You make them with `extends Exception`. You use `throw` when something is wrong and `try/catch` to show a nice message.
4. Make a Main class with a menu. The user types a number to choose. If the user types a wrong number, show a message. The program must not crash.
5. **Submit these 4 things:**
   - (a) your code
   - (b) one page diagram with boxes for your classes
   - (c) 5 lines on why you made these classes and what numbers you decided
   - (d) who did what
6. Present for 10 minutes. Every person explains their own class and answers questions on it.

## Tips for working in a group

1. First, sit together and agree on the names of the classes and methods. Write them on paper.
2. Each person writes and tests their own class first, with a small `main` method.
3. Only after that, join all the classes together.
4. If your part needs a class of another person, wait for them or write a simple temporary version to test.
5. Do not copy code from the internet or from AI tools. You will be asked to explain every line of your own class.

------

# PROJECT 1: LIBRARY MANAGEMENT SYSTEM

## What you will make

A library program. The library has books and members. A member can borrow a book and return it. If the member returns it late, the program calculates a fine.

## Starting data (put this in your code when the program starts)

1. 10 books with unique IDs: B101, B102, B103, up to B110. Give each a title and author of your choice.
2. 4 members with unique IDs: M1, M2, M3, M4. Two are students and two are faculty (teachers).

## Rules of the library

1. A book can be kept for 14 days.
2. A student can borrow at most 3 books at one time. Late fine: Rs. 2 per day.
3. A faculty member can borrow at most 5 books at one time. Late fine: Rs. 1 per day.
4. A book that is already borrowed cannot be given to another person.
5. Days are simple numbers. Example: issue on day 5, so the last day (due day) is 19. If returned on day 22, it is 3 days late.

## PERSON 1: Book class and the exceptions

**What this class is for:** One object of this class is ONE book.

**Steps:**

1. Make private variables: `bookId` (example "B101"), `title`, `author`, `isAvailable` (true or false).
2. Make a constructor that takes bookId, title and author. A new book is always available (`isAvailable = true`).
3. Make getters for all variables.
4. Write methods:
   - `markIssued()`: sets isAvailable to false.
   - `markReturned()`: sets isAvailable to true.
5. Write `toString()`: shows ID, title, author and "Available" or "Issued".
6. Make 2 custom exception classes:
   - `BookNotAvailableException`: message "This book is already issued".
   - `LimitExceededException`: message "Member has reached the book limit".

**How to test your part:** Make a book, issue it, check isAvailable, return it.

## PERSON 2: Member, StudentMember and FacultyMember

**What this class is for:** To store member details, and to give different limits and fines for students and faculty.

**Steps:**

1. Make an abstract class `Member`.
2. Private variables: `memberId` (example "M1"), `name`, an array `Book[] borrowedBooks` (size 5), and a counter `borrowedCount` (how many books the member has now).
3. Make a constructor that takes memberId and name.
4. Make getters.
5. Add two abstract methods: `getMaxBooks()` and `getFinePerDay()`.
6. Make child class `StudentMember`: `getMaxBooks()` returns 3, `getFinePerDay()` returns 2.
7. Make child class `FacultyMember`: `getMaxBooks()` returns 5, `getFinePerDay()` returns 1.
8. Write methods in Member:
   - `canBorrow()`: returns true if borrowedCount is less than `getMaxBooks()`.
   - `addBook(Book b)`: puts the book in the array and increases the counter.
   - `removeBook(Book b)`: finds the book in the array and removes it. Shift the other books left so there is no gap. Decrease the counter.
   - `showBorrowedBooks()`: prints all books the member has now.

**How to test your part:** Make one student and one faculty. Print `getMaxBooks()` for both. Same method name, different answers. This is overriding.

## PERSON 3: Transaction class (issue, return and fine)

**What this class is for:** One transaction remembers "this member borrowed this book on this day". It also calculates the fine.

**Steps:**

1. Private variables: `book`, `member`, `issueDay`, `dueDay`, `returnDay`.
2. Make a constructor that takes book, member and issueDay. It sets `dueDay = issueDay + 14`.
3. Make getters.
4. Write `setReturnDay(int day)` to save the return day.
5. Write `calculateFine()`:
   - If returnDay is more than dueDay: late days = returnDay - dueDay. Fine = late days x `member.getFinePerDay()`.
   - Otherwise: fine is 0.
6. Write `printReceipt()`: prints book title, member name, issue day, due day, return day, late days, and fine.

**Example:** A student borrows on day 5. Due day is 19. Returns on day 22. Late days = 3. Fine = 3 x 2 = Rs. 6.

## PERSON 4: Library class and Main

**What this class is for:** This is the main manager. It holds all books, members and transactions.

**Steps:**

1. Make private arrays: `Book[] books` (size 10), `Member[] members` (size 4), `Transaction[] transactions` (size 50, with a counter).
2. In the constructor (or a method), create the 10 books and the 4 members and put them in the arrays.
3. Write `issueBook(String memberId, String bookId, int today)`:
   - Find the member in the array. Find the book in the array.
   - If the book is not available: throw `BookNotAvailableException`.
   - If `member.canBorrow()` is false: throw `LimitExceededException`.
   - Otherwise: call `book.markIssued()`, `member.addBook(book)`, make a new Transaction and save it.
4. Write `returnBook(String memberId, String bookId, int today)`:
   - Find the open transaction for this member and book.
   - Set the return day, call `book.markReturned()` and `member.removeBook(book)`.
   - Print the receipt with the fine.
5. Write `searchByTitle(String text)`: loop through books and print those whose title contains the text.
6. Write `showAllBooks()`: print every book with its status.
7. Main class: show the menu in a loop. Use try/catch to show exception messages nicely.

**Menu:**

```
1. Show all books
2. Issue book
3. Return book
4. Search book by title
5. Show books borrowed by a member
6. Stop program
```

## DEMO: your group must show all 6

1. Issue a book to a student. Show that the book now says "Issued".
2. Try to issue the same book to another member. The "already issued" message must show.
3. Give 3 books to one student. Try a 4th one. The "limit" message must show.
4. Return a book 3 days late. Show the fine (Rs. 6 for a student).
5. Show that a faculty member can borrow 5 books.
6. Search a book by part of its title.