# Java OOP Group Projects: Instructions (Projects 1 to 15)

## Projects in this file

1. Library Management: Book, Member, Library, Fine/Transaction
2. Parking Lot: Vehicle (Car, Bike subclasses), ParkingSpot, ParkingLot, Ticket/Payment
3. Bank and ATM: Account (Savings, Current subclasses), Card, ATM, Transaction
4. Elevator System: Elevator, Floor, Request, Controller
5. Restaurant Ordering: MenuItem, Order, Table, Bill
6. Vehicle Rental: Vehicle (abstract), Customer, Rental, Invoice
7. Payroll System: Employee (abstract), Manager/Developer/Intern subclasses, Payroll, Company
8. College System: Student, Professor, Course, Department
9. Tic-Tac-Toe: Board, Player, Game, ComputerPlayer
10. Quiz Application: Question (MCQ and TrueFalse subclasses), Quiz, Player, ScoreBoard
11. Music Player: Song, Playlist, Player, User
12. Barber Shop Simulation (Monte Carlo): Customer, Barber, Shop, Simulation/Statistics
13. Toll Plaza Simulation (Monte Carlo): Vehicle, TollBooth, Plaza, Simulator
14. Snake and Ladder Game: Board, Dice, Player, Game
15. Geometry Shapes Toolkit: Shape (abstract), Circle/Rectangle/Triangle, ShapeCollection, Report

------

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

------

# PROJECT 2: PARKING LOT

## What you will make

A parking lot program. A vehicle comes in. The program finds a free spot, parks the vehicle, and gives a ticket. When the vehicle leaves, the program calculates the money to pay and makes the spot free again.

## Decisions you must make as a group (write them in your 5 lines)

1. How many total parking spots does your lot have? Decide yourselves. Think of a real parking lot.
2. How many spots are for cars and how many for bikes?
3. Every spot must have its own unique ID. Example: C1, C2, C3 for car spots and B1, B2, B3 for bike spots. No two spots can have the same ID.
4. Rate per hour for a car and for a bike.
5. Keep the number of spots small enough that you can fill the whole lot during your demo.
6. Write the total number of spots in only ONE place in your code, so if you change it, you change it only once.

## Rules for the lot

1. A car parks only in a car spot. A bike parks only in a bike spot.
2. Time is a simple number from 0 to 24. Example: entry 9, exit 12 means 3 hours.
3. If the vehicle stays a part of an hour, charge for the full hour. Minimum charge is 1 hour.
4. Amount = hours x rate of that vehicle.

## PERSON 1: Vehicle, Car and Bike

**What this class is for:** To store details of a vehicle, and to give the rate per hour.

**Steps:**

1. Make an abstract class called `Vehicle`.
2. Inside it, make 2 private variables: `vehicleNumber` (example: "CG17AB1234") and `ownerName`.
3. Make a constructor that takes both values and stores them.
4. Make getters: `getVehicleNumber()` and `getOwnerName()`.
5. Add one abstract method: `public abstract int getHourlyRate();` (Abstract means: Vehicle does not write the body. The children must write it.)
6. Make a child class `Car` that extends Vehicle. In it, write `getHourlyRate()` and return your car rate.
7. Make a child class `Bike` that extends Vehicle. In it, write `getHourlyRate()` and return your bike rate.
8. In Vehicle, write a method `toString()` that returns a nice line. Example: "Car CG17AB1234 (owner: Ravi)".
9. Also add a method `getVehicleType()` in Car and Bike that returns "CAR" or "BIKE". Your friends need it later for matching the spot type.

**How to test your part:** In a small main method, make a Car and a Bike, print them, and print their rates.

## PERSON 2: ParkingSpot

**What this class is for:** One object of this class is ONE parking spot.

**Steps:**

1. Make private variables:
   - `spotId`: text, example "C1"
   - `spotType`: text, "CAR" or "BIKE"
   - `parkedVehicle`: stores the Vehicle parked here. It is null (empty) when nobody is parked.
2. Make a constructor that takes spotId and spotType. At the start, parkedVehicle is null (the spot is free).
3. Make getters for spotId, spotType and parkedVehicle.
4. Write these methods:
   - `isFree()`: returns true if parkedVehicle is null, otherwise false.
   - `canFit(Vehicle v)`: returns true if the vehicle type is the same as the spot type. (A car matches "CAR", a bike matches "BIKE".)
   - `park(Vehicle v)`: stores the vehicle in parkedVehicle.
   - `removeVehicle()`: sets parkedVehicle back to null.
5. Write `toString()` that shows the spot ID, type, and either "Free" or the vehicle number.

**How to test your part:** Make one spot, check `isFree()`, park a car, check `isFree()` again, remove it.

## PERSON 3: Ticket and Payment

**What these classes are for:** A ticket is given at entry. The payment is calculated at exit.

**Ticket class steps:**

1. Private variables: `ticketId`, `vehicle`, `spot`, `entryHour`.
2. Ticket IDs must be unique and automatic: T1, T2, T3... Use a static counter variable inside Ticket that increases by 1 for every new ticket. (Static means one shared variable for all tickets.)
3. The constructor takes vehicle, spot and entryHour. It creates the ticket ID by itself.
4. Make getters for everything.
5. `printTicket()`: prints ticket ID, vehicle number, spot ID and entry hour in neat lines.

**Payment class steps:**

1. Make a method `calculateAmount(Ticket ticket, int exitHour)`.
2. Find hours parked = exitHour - entryHour.
3. If hours parked is 0 or less, charge for 1 hour (minimum charge).
4. Amount = hours x `ticket.getVehicle().getHourlyRate()`.
5. Make `printReceipt()`: prints ticket ID, vehicle number, entry hour, exit hour, hours charged and total amount.
6. If exit hour is smaller than entry hour, show a message that exit time is wrong. (You may use an exception for this if you want.)

**Example output of printReceipt():**

```
Ticket: T3
Vehicle: CG17AB1234
Entry: 9   Exit: 12
Hours: 3
Amount to pay: Rs. 60
```

## PERSON 4: ParkingLot and Main

**What this class is for:** This is the main manager. It holds all spots and all tickets and connects everything.

**Steps:**

1. Make private arrays:
   - `ParkingSpot[] spots`: size is the total number of spots your group decided.
   - `Ticket[] tickets`: a bigger array to keep active tickets. Keep a counter for how many are stored.
2. In the constructor, fill the spots array using a loop. Give each spot its unique ID and type. Example: first 5 spots are "C1".."C5" with type CAR, next 3 are "B1".."B3" with type BIKE.
3. Write `parkVehicle(Vehicle v, int entryHour)`:
   - Go through the spots array with a loop.
   - Find the first spot that `isFree()` and `canFit(v)`.
   - If found: park the vehicle, make a new Ticket, store it in the tickets array, print the ticket.
   - If no spot is found: throw `ParkingFullException`.
4. Write `exitVehicle(String ticketId, int exitHour)`:
   - Search the tickets array for this ticket ID.
   - If not found: throw `InvalidTicketException`.
   - If found: call the Payment method to get the amount, print the receipt, free the spot (`removeVehicle()`), and remove the ticket from the array.
5. Write `showAvailableSpots()`: loop through all spots and print the ones that are free.
6. Write `showParkedVehicles()`: loop and print each spot that is not free, with its vehicle.
7. Make the 2 custom exception classes:
   - `ParkingFullException`: message "No free spot for this vehicle type".
   - `InvalidTicketException`: message "Ticket not found".
8. Main class: show the menu in a loop, take the user's choice, and call the methods. Use try/catch to show the exception messages nicely.

**Menu:**

```
1. Park vehicle
2. Exit vehicle
3. Show available spots
4. Show parked vehicles
5. Stop program
```

## DEMO: your group must show all 6

1. Park one car and one bike. Show both tickets.
2. Show the list of available spots.
3. Fill all the car spots. Try to park one more car. The "no free spot" message must show.
4. Exit with a wrong ticket ID. The "ticket not found" message must show.
5. Exit one vehicle and show the receipt with the correct amount. Show it is correct by calculating on paper.
6. Park a new car in the spot that was just made free.

------

# PROJECT 3: BANK AND ATM

## What you will make

A bank program with an ATM. A customer puts a card in the ATM, types a PIN, and then can check balance, deposit money, withdraw money, and see the last 5 transactions.

## Decisions you must make as a group (write them in your 5 lines)

1. How many accounts does your bank have? Make at least 4 (at least 2 savings and 2 current).
2. Each account has a unique account number. Example: SB101, SB102 for savings and CA201, CA202 for current.
3. Opening balance of each account.
4. Minimum balance of a savings account (example: Rs. 500).
5. Maximum cash that the ATM gives in one withdrawal (example: Rs. 10000).
6. Each account has one card. Each card has its own unique card number and a 4-digit PIN.

## Rules of the bank

1. A savings account must always keep the minimum balance. A current account can go down to 0, but not below 0.
2. Deposit and withdraw amount must be more than 0.
3. ATM gives cash only in multiples of 100 (example: 500, 1200 are fine, 150 is not fine).
4. If the customer types a wrong PIN 3 times in a row, the card is blocked. A blocked card cannot be used.
5. A correct PIN makes the wrong-PIN count go back to 0.
6. Every deposit and every withdrawal must be saved as a transaction.

## PERSON 1: Account, SavingsAccount, CurrentAccount and the exceptions

**What this class is for:** To store the money of a customer and to follow the rules of deposit and withdraw.

**Steps:**

1. Make an abstract class `Account`.
2. Private variables: `accountNumber`, `holderName`, `balance`.
3. Make a constructor that takes all three values.
4. Make getters. Do NOT make a setter for balance. Balance must change only through deposit and withdraw. (This is what encapsulation means: nobody can change balance directly.)
5. Add two abstract methods: `getMinimumBalance()` and `getAccountType()`.
6. Make child class `SavingsAccount`: `getMinimumBalance()` returns your savings minimum (example 500). `getAccountType()` returns "SAVINGS".
7. Make child class `CurrentAccount`: `getMinimumBalance()` returns 0. `getAccountType()` returns "CURRENT".
8. Write `deposit(double amount)`:
   - If amount is 0 or less, throw `InvalidAmountException`.
   - Otherwise add the amount to balance.
9. Write `withdraw(double amount)`:
   - If amount is 0 or less, throw `InvalidAmountException`.
   - If (balance - amount) is less than `getMinimumBalance()`, throw `InsufficientBalanceException`.
   - Otherwise subtract the amount from balance.
10. Make 2 custom exception classes:
    - `InvalidAmountException`: message "Amount must be more than 0".
    - `InsufficientBalanceException`: message "Not enough balance".
11. Write `toString()` that shows account number, holder name, type and balance.

**How to test your part:** Make one savings and one current account. Withdraw so that the balance goes below the minimum. Check that savings gives the exception and current does not (when the balance is still above 0).

## PERSON 2: Card and CardBlockedException

**What this class is for:** One card is linked to one account. It checks the PIN and blocks itself after 3 wrong tries.

**Steps:**

1. Private variables: `cardNumber`, `pin` (a number like 4321), `account` (the Account this card belongs to), `wrongAttempts` (starts at 0), `blocked` (starts as false).
2. Make a constructor that takes cardNumber, pin and account.
3. Make getters for cardNumber, account and blocked. **Do NOT make a getter for the PIN.** The PIN must be secret. Only the card itself can check it.
4. Write `verifyPin(int enteredPin)`:
   - If the card is blocked, throw `CardBlockedException`.
   - If enteredPin is the same as pin: set wrongAttempts to 0 and return true.
   - If it is wrong: increase wrongAttempts by 1. If wrongAttempts is now 3, set blocked to true. Return false.
5. Make the custom exception `CardBlockedException`: message "Card is blocked. Please contact the bank".
6. Write `toString()` that shows card number and if it is blocked. Do not show the PIN.

**How to test your part:** Make a card. Try a wrong PIN 3 times. Then try the right PIN. The exception must come.

## PERSON 3: Transaction and TransactionLog

**What these classes are for:** A Transaction remembers one deposit or withdrawal. The TransactionLog keeps all transactions and can print the last 5 of an account.

**Transaction class steps:**

1. Private variables: `transactionId`, `accountNumber`, `type` ("DEPOSIT" or "WITHDRAW"), `amount`, `balanceAfter`.
2. Transaction IDs must be automatic and unique: TX1, TX2, TX3... Use a static counter.
3. Make a constructor that takes accountNumber, type, amount, balanceAfter.
4. Make getters.
5. Write `toString()`: one neat line. Example: `TX7  WITHDRAW  Rs. 500  Balance: Rs. 4500`.

**TransactionLog class steps:**

1. Private variables: `Transaction[] transactions` (size 200) and a counter `count`.
2. `addTransaction(Transaction t)`: store it in the array. If the array is full, print a message.
3. `showLast(String accountNumber, int n)`: go through the array from the END to the start. Print the transactions of this account number. Stop after n transactions. If there are none, print "No transactions".
4. `showAll(String accountNumber)`: print all transactions of this account.

**How to test your part:** Add 7 transactions for one account. Call `showLast(accountNumber, 5)`. Only the last 5 must show, newest first.

## PERSON 4: ATM and Main

**What this class is for:** This is the main manager. It holds all accounts and cards, checks the card and PIN, and does the work for the customer.

**Steps:**

1. Private variables: `Account[] accounts`, `Card[] cards`, `TransactionLog log`, `Card currentCard` (the card that is logged in, null when nobody is logged in), and `atmLimit` (maximum cash in one withdrawal).
2. In the constructor, create your accounts and cards (at least 4 of each) and put them in the arrays. Every card must be linked to its own account.
3. Write `findCard(String cardNumber)`: search the cards array. If not found, return null.
4. Write `login(String cardNumber, int pin)`:
   - Find the card. If not found, print "Card not found" and return false.
   - Call `card.verifyPin(pin)`. (This can throw `CardBlockedException`. Handle it in Main.)
   - If the PIN is right: set `currentCard` and return true.
   - If wrong: print how many tries are used and return false.
5. Write `checkBalance()`: prints the balance of the current card's account.
6. Write `deposit(double amount)`: call `account.deposit(amount)`. Then make a Transaction and add it to the log.
7. Write `withdraw(double amount)`:
   - If amount is not a multiple of 100, print a message and stop.
   - If amount is more than `atmLimit`, print a message and stop.
   - Call `account.withdraw(amount)`. Then make a Transaction and add it to the log.
8. Write `miniStatement()`: call `log.showLast(accountNumber, 5)`.
9. Write `logout()`: set `currentCard` to null.
10. Main class has two menus:
    - First menu: Insert card / Stop program. If the customer chooses "Insert card", ask for card number and PIN, and call `login`.
    - Second menu: shown only after a successful login.
11. Use try/catch to show messages from `CardBlockedException`, `InvalidAmountException` and `InsufficientBalanceException`.

**First menu:**

```
1. Insert card
2. Stop program
```

**Second menu (after login):**

```
1. Check balance
2. Deposit
3. Withdraw
4. Mini statement (last 5)
5. Logout
```

## DEMO: your group must show all 8

1. Login with the correct PIN.
2. Login with a wrong PIN once, then a correct PIN. Show that the wrong count goes back to 0.
3. Type a wrong PIN 3 times. Show that the card is blocked. Try to use the same card again. The blocked message must show.
4. Deposit money and check the balance.
5. Withdraw money and check the balance.
6. Try to withdraw an amount that is not a multiple of 100. The message must show.
7. On a savings account, withdraw so that the balance goes below the minimum. The exception message must show. Then show the current account in a similar case, and explain the difference.
8. Show the mini statement with the last 5 transactions.

------

# PROJECT 4: ELEVATOR SYSTEM

## What you will make

A program for a building with elevators. A person or goods want to go from one floor to another. The program chooses the best elevator, moves it floor by floor, and prints each step.

## Decisions you must make as a group (write them in your 5 lines)

1. How many floors does your building have? Make at least 6 (floor 0 is the Ground floor).
2. How many elevators? Make at least 3: at least 2 passenger elevators and 1 goods elevator.
3. Each elevator has a unique ID. Example: E1, E2, E3.
4. Maximum load of a passenger elevator in kg (example: 600) and of a goods elevator in kg (example: 1500).
5. The floor where each elevator stands when the program starts.

## Rules of the elevator system

1. An elevator moves ONE floor at a time. At each floor, the program prints a line, like: `Elevator E1 is at floor 3`.
2. A request has: from floor, to floor, weight in kg, and type ("PERSON" or "GOODS").
3. A PERSON request must use a passenger elevator. A GOODS request must use a goods elevator.
4. If there are more than one elevator of the correct type, use the nearest one. Nearest means the smallest difference between the elevator's current floor and the request's "from" floor.
5. If the weight is more than the maximum load of the elevator, the request is rejected.
6. A floor number that does not exist (below 0 or above the top floor) is not allowed.
7. "From floor" and "to floor" cannot be the same.

## PERSON 1: Elevator, PassengerElevator, GoodsElevator and the exceptions

**What this class is for:** To store the details of one elevator and to move it.

**Steps:**

1. Make an abstract class `Elevator`.
2. Private variables: `elevatorId`, `currentFloor`, `currentLoad` (in kg, starts at 0).
3. Make a constructor that takes elevatorId and the starting floor.
4. Make getters.
5. Add two abstract methods: `getMaxLoad()` and `getElevatorType()`.
6. Make child class `PassengerElevator`: `getMaxLoad()` returns your passenger limit. `getElevatorType()` returns "PASSENGER".
7. Make child class `GoodsElevator`: `getMaxLoad()` returns your goods limit. `getElevatorType()` returns "GOODS".
8. Write `moveToFloor(int target, int topFloor)`:
   - If target is below 0 or above topFloor, throw `InvalidFloorException`.
   - Use a loop to move one floor at a time. If target is higher, go up. If it is lower, go down.
   - At each floor, update `currentFloor` and print: `Elevator E1 is at floor 3`.
9. Write `load(int weight)`:
   - If (currentLoad + weight) is more than `getMaxLoad()`, throw `OverloadException`.
   - Otherwise add the weight to currentLoad.
10. Write `unload()`: sets currentLoad back to 0.
11. Make 2 custom exception classes:
    - `OverloadException`: message "Weight is more than the elevator limit".
    - `InvalidFloorException`: message "This floor does not exist".
12. Write `toString()`: shows ID, type, current floor and current load.

**How to test your part:** Make a PassengerElevator. Move it from floor 0 to floor 4 and check the printed floors. Load more than the limit and check for the exception.

## PERSON 2: Floor and Building

**What these classes are for:** A Floor is one floor. The Building holds all the floors.

**Floor class steps:**

1. Private variables: `floorNumber`, `floorName` (example: "Ground", "Floor 1", "Floor 2"), `waitingCount` (how many requests are waiting on this floor, starts at 0).
2. Make a constructor that takes floorNumber and floorName.
3. Make getters.
4. Write `addWaiting()` (adds 1 to waitingCount) and `removeWaiting()` (subtracts 1, but never goes below 0).
5. Write `toString()`: shows floor number, name and how many are waiting.

**Building class steps:**

1. Private variables: `Floor[] floors` and `totalFloors`.
2. Make a constructor that takes the number of floors. Use a loop to create all Floor objects. Floor 0 is named "Ground". Others are named "Floor 1", "Floor 2", and so on.
3. Write `getTopFloor()`: returns the highest floor number.
4. Write `isValidFloor(int n)`: returns true if the floor exists.
5. Write `getFloor(int n)`: returns the Floor object.
6. Write `showFloors()`: prints all the floors.

**How to test your part:** Make a Building with 6 floors (floor 0 to floor 5). Print all floors. Check that `isValidFloor(5)` is true and `isValidFloor(6)` is false. Also check `isValidFloor(-1)` is false.

## PERSON 3: Request

**What this class is for:** One Request remembers one trip: who wants to go where.

**Steps:**

1. Private variables: `requestId`, `fromFloor`, `toFloor`, `weight`, `requestType` ("PERSON" or "GOODS"), `served` (starts as false).
2. Request IDs must be automatic and unique: R1, R2, R3... Use a static counter.
3. Make a constructor that takes fromFloor, toFloor, weight and requestType.
4. Make getters.
5. Write `getDirection()`: returns "UP" if toFloor is bigger than fromFloor, "DOWN" if smaller, and "NONE" if both are the same.
6. Write `isValid()`: returns true only if fromFloor and toFloor are different, weight is more than 0, and requestType is "PERSON" or "GOODS".
7. Write `markServed()`: sets served to true.
8. Write `toString()`: one neat line. Example: `R2  PERSON  from 0 to 5 (UP)  80 kg  Served`.

**How to test your part:** Make a few requests. Print the direction and check `isValid()` for a good and a wrong request.

## PERSON 4: Controller and Main

**What this class is for:** This is the brain. It takes a request, chooses the elevator, and moves it.

**Steps:**

1. Private variables: `Building building`, `Elevator[] elevators`, `Request[] requests` (size 50, with a counter).
2. In the constructor, create the Building and the elevators (at least 3: 2 passenger and 1 goods) and put them in the array.
3. Write `findBestElevator(Request r)`:
   - If the request type is "PERSON", look only at "PASSENGER" elevators. If it is "GOODS", look only at "GOODS" elevators.
   - Among them, choose the one with the smallest distance between its current floor and the request's fromFloor.
   - Return that elevator. If none is found, return null.
4. Write `processRequest(Request r)`:
   - If `r.isValid()` is false, print a message and stop.
   - Check that fromFloor and toFloor exist using the Building. If not, throw `InvalidFloorException`.
   - Save the request in the requests array.
   - Find the best elevator. If null, print a message and stop.
   - Move the elevator to the fromFloor.
   - Print "Loading..." and call `elevator.load(weight)` (this can throw `OverloadException`).
   - Move the elevator to the toFloor.
   - Print "Unloading..." and call `elevator.unload()`.
   - Call `r.markServed()`.
5. Write `showElevatorStatus()`: prints every elevator (ID, type, floor, load).
6. Write `showAllRequests()`: prints every saved request with its status.
7. Main class: show the menu in a loop. Use try/catch to show the messages from `OverloadException` and `InvalidFloorException`.

**Menu:**

```
1. Show elevator status
2. New request
3. Show all requests
4. Stop program
```

## DEMO: your group must show all 6

1. Show the status of all elevators.
2. Make a PERSON request from floor 0 to floor 5. Show every floor the elevator passes.
3. Make a GOODS request. Show that the goods elevator is used.
4. Make two PERSON requests so that the program must choose between two passenger elevators. Show that the nearest one is chosen, and explain why.
5. Make a request with too much weight. The overload message must show.
6. Make a request to a floor that does not exist. The invalid floor message must show.

------

# PROJECT 5: RESTAURANT ORDERING

## What you will make

A restaurant program. Guests come in and are given a table. The waiter adds food and drinks to the table's order. At the end, the program makes the bill and the table becomes free again.

## Decisions you must make as a group (write them in your 5 lines)

1. Your menu: at least 9 items (3 starters, 3 main course, 3 drinks). Give each item a unique code. Example: S1, S2, S3 for starters, M1, M2, M3 for main course, D1, D2, D3 for drinks. Decide names and prices.
2. How many tables? Make at least 5, with different sizes (example: 2 tables for 2 people, 2 tables for 4 people, 1 table for 6 people).
3. Each table has a unique number. Example: T1, T2, T3...
4. Tax percent for each type of item. Example: starters 5%, main course 5%, drinks 12%.
5. A discount rule. Example: 10% discount if the total before tax is more than Rs. 1000.

## Rules of the restaurant

1. Guests are given the first free table that has enough seats for them.
2. If no table is free for them, they must wait (the program shows a message).
3. An order can have up to 20 different items. If the same item is added again, increase its quantity. Do not make a new line.
4. Quantity must be 1 or more.
5. Bill = subtotal (price x quantity of all items) + tax - discount.
6. After the bill is made, the table becomes free.

## PERSON 1: MenuItem, Starter, MainCourse, Drink and ItemNotFoundException

**What this class is for:** To store the details of one item in the menu, and to give the tax of its type.

**Steps:**

1. Make an abstract class `MenuItem`.
2. Private variables: `itemCode` (example "S1"), `name`, `price`.
3. Make a constructor that takes all three values.
4. Make getters.
5. Add two abstract methods: `getTaxPercent()` and `getCategory()`.
6. Make three child classes: `Starter`, `MainCourse`, `Drink`. Each one overrides `getTaxPercent()` with the percent your group decided, and `getCategory()` with "STARTER", "MAIN COURSE" or "DRINK".
7. Write `toString()`: shows code, name, category and price. Example: `S1  Paneer Tikka  (STARTER)  Rs. 180`.
8. Make the custom exception `ItemNotFoundException`: message "This item code is not in the menu".

**How to test your part:** Make one item of each type. Print them and print their tax percents.

## PERSON 2: Table and TableNotAvailableException

**What this class is for:** One object of this class is ONE table of the restaurant.

**Steps:**

1. Private variables: `tableNumber` (example "T1"), `capacity` (number of seats), `occupied` (true or false, starts as false), `currentOrder` (the Order of this table, starts as null).
2. Make a constructor that takes tableNumber and capacity.
3. Make getters.
4. Write these methods:
   - `isFree()`: returns true if the table is not occupied.
   - `canSeat(int guests)`: returns true if the table is free AND capacity is more than or equal to guests.
   - `occupy()`: sets occupied to true.
   - `setOrder(Order o)`: stores the order.
   - `freeTable()`: sets occupied to false and currentOrder to null.
5. Write `toString()`: shows table number, capacity and "Free" or "Occupied".
6. Make the custom exception `TableNotAvailableException`: message "No table is free for this group".

**How to test your part:** Make a table for 4. Check `canSeat(2)` and `canSeat(6)`. Occupy it and check `canSeat(2)` again.

## PERSON 3: Order and InvalidQuantityException

**What this class is for:** The Order remembers what a table has ordered.

**Steps:**

1. Private variables: `orderId`, `MenuItem[] items` (size 20), `int[] quantities` (size 20), `lineCount` (how many different items are in the order).
2. Order IDs are automatic and unique: O1, O2, O3... Use a static counter.
3. `addItem(MenuItem item, int quantity)`:
   - If quantity is 0 or less, throw `InvalidQuantityException`.
   - If the same item (same item code) is already in the order, add the quantity to the old line.
   - Otherwise, put the item and quantity in the next free place in the arrays. If the arrays are full, print a message.
4. Write `getSubtotal()`: adds price x quantity for every line.
5. Write these getters so that the Bill can read the order: `getLineCount()`, `getItemAt(int index)`, `getQuantityAt(int index)`.
6. Write `showOrder()`: prints every line like: `Paneer Tikka  x2  Rs. 360`, and the subtotal at the end.
7. Make the custom exception `InvalidQuantityException`: message "Quantity must be 1 or more".

**How to test your part:** Make an order. Add 2 items, then add the first item again. Check that it has only 2 lines, but the first quantity has increased.

## PERSON 4: Bill, Restaurant and Main

**What this class is for:** The Bill makes the final amount. The Restaurant is the main manager that holds the menu and the tables.

**Bill class steps:**

1. Private variables: `order`, `subtotal`, `tax`, `discount`, `total`.
2. Make a constructor that takes an Order and calculates everything:
   - `subtotal = order.getSubtotal()`
   - Tax: for each line, `tax += price x quantity x taxPercent / 100`. Use `getItemAt` and `getQuantityAt` of the Order.
   - Discount: use the rule your group decided.
   - `total = subtotal + tax - discount`
3. Write `printBill()`. Example output:

```
------ BILL ------
Paneer Tikka   x2   Rs. 360
Butter Naan    x4   Rs. 160
Subtotal:  Rs. 520
Tax:       Rs. 26
Discount:  Rs. 0
TOTAL:     Rs. 546
------------------
```

**Restaurant class steps:**

1. Private variables: `MenuItem[] menu`, `Table[] tables`.
2. In the constructor, create the menu items and the tables and put them in the arrays.
3. `showMenu()`: prints all items.
4. `findItem(String code)`: searches the menu. If not found, throw `ItemNotFoundException`.
5. `seatGuests(int guests)`: finds the first table where `canSeat(guests)` is true. Occupy it, create a new Order for it, and return the table. If no table is found, throw `TableNotAvailableException`.
6. `addToOrder(String tableNumber, String itemCode, int quantity)`: find the table and the item, and add the item to the table's order.
7. `generateBill(String tableNumber)`: create the Bill from the table's order, print it, and then free the table.
8. `showTables()`: prints all tables.

**Main class:** show the menu in a loop. Use try/catch to show messages from all 3 exceptions.

**Menu:**

```
1. Show menu
2. Seat guests
3. Add item to a table's order
4. Show a table's order
5. Generate bill and free the table
6. Show all tables
7. Stop program
```

## DEMO: your group must show all 7

1. Show the menu.
2. Seat a group of 2 and a group of 4. Show which tables they got.
3. Add 3 different items to a table. Then add one of the same items again. Show the order (the quantity must increase, no new line).
4. Try to add an item with a wrong code. The "item not found" message must show.
5. Try to add an item with quantity 0. The "invalid quantity" message must show.
6. Fill all the tables, then try to seat one more group. The "no table" message must show.
7. Generate a bill that gets the discount and a bill that does not. Show the table is free after the bill.

------

# PROJECT 6: VEHICLE RENTAL

## What you will make

A vehicle rental shop program. A customer rents a car or a bike for some days. When the vehicle is returned, the program makes an invoice with the rent, the late fee (if late), and the money to pay or refund.

## Decisions you must make as a group (write them in your 5 lines)

1. Your vehicles: at least 6 (at least 3 cars and 3 bikes). Each vehicle has a unique ID. Example: CAR1, CAR2, CAR3, BIKE1, BIKE2, BIKE3.
2. Rent per day for a car and for a bike.
3. Security deposit for a car and for a bike. (A deposit is money the customer pays at the start. It is returned at the end.)
4. Late fee per day. Example: half of the rent per day.
5. Your customers: at least 4, with unique IDs. Example: C1, C2, C3, C4.
6. The maximum number of vehicles one customer can rent at the same time (example: 2).

## Rules of the rental shop

1. Days are simple numbers. Example: start on day 10, agreed return on day 13 means 3 days.
2. Rent = agreed days x rent per day of that vehicle. The minimum is 1 day.
3. If the vehicle is returned after the agreed day, the late fee = late days x late fee per day.
4. At return, the staff can enter a damage charge (0 if no damage).
5. Total charges = rent + late fee + damage charge.
6. The customer already paid the deposit at the start. So at the end: if total charges are more than the deposit, the customer pays the difference. If total charges are less, the customer gets a refund of the difference.
7. A vehicle that is already rented cannot be rented again until it is returned.

**Example:** Car rent is Rs. 1500 per day, deposit is Rs. 2000, late fee is Rs. 750 per day. Rented on day 10, agreed return day 13 (3 days), returned on day 15 (2 days late), no damage. Rent = 4500. Late fee = 1500. Total charges = 6000. The customer pays 6000 - 2000 = Rs. 4000.

## PERSON 1: Vehicle, Car, Bike and VehicleNotAvailableException

**What this class is for:** To store the details of one rental vehicle.

**Steps:**

1. Make an abstract class `Vehicle`.
2. Private variables: `vehicleId`, `model` (example "Maruti Swift"), `available` (true or false, starts as true).
3. Make a constructor that takes vehicleId and model.
4. Make getters.
5. Add three abstract methods: `getDailyRate()`, `getSecurityDeposit()` and `getVehicleType()`.
6. Make child classes `Car` and `Bike`. Each one overrides these 3 methods with the values your group decided and "CAR" or "BIKE".
7. Write `markRented()` (sets available to false) and `markReturned()` (sets available to true).
8. Write `toString()`: shows ID, model, type, rate per day, and "Available" or "Rented".
9. Make the custom exception `VehicleNotAvailableException`: message "This vehicle is already rented".

**How to test your part:** Make a car and a bike. Print them. Rent the car and print again.

## PERSON 2: Customer and RentalLimitException

**What this class is for:** To store the details of one customer and to count how many vehicles they have now.

**Steps:**

1. Private variables: `customerId` (example "C1"), `name`, `phone`, `licenseNumber`, `activeRentals` (how many vehicles the customer has now, starts at 0).
2. Make a constructor that takes customerId, name, phone and licenseNumber.
3. Make getters.
4. Write `canRent(int maxRentals)`: returns true if `activeRentals` is less than maxRentals.
5. Write `increaseActive()` and `decreaseActive()`. (`decreaseActive` must never go below 0.)
6. Write `toString()`: shows ID, name, phone and active rentals.
7. Make the custom exception `RentalLimitException`: message "Customer has reached the rental limit".

**How to test your part:** Make a customer. Check `canRent(2)`. Increase active twice. Check `canRent(2)` again.

## PERSON 3: Rental and Invoice

**What these classes are for:** A Rental remembers one rental. The Invoice calculates the money at the end.

**Rental class steps:**

1. Private variables: `rentalId`, `vehicle`, `customer`, `startDay`, `agreedEndDay`, `actualReturnDay` (starts as 0), `closed` (starts as false).
2. Rental IDs are automatic and unique: R1, R2, R3... Use a static counter.
3. Make a constructor that takes vehicle, customer, startDay and agreedEndDay.
4. Make getters.
5. Write `getAgreedDays()`: returns agreedEndDay - startDay. If this is less than 1, return 1.
6. Write `closeRental(int actualReturnDay)`: saves the return day and sets closed to true.
7. Write `getLateDays()`: if actualReturnDay is more than agreedEndDay, return the difference. Otherwise return 0.

**Invoice class steps:**

1. Private variables: `rental`, `damageCharge`, `lateFeePerDay`.
2. Make a constructor that takes the rental, the damage charge and the late fee per day.
3. Write these methods:
   - `calculateRent()`: agreed days x daily rate of the vehicle.
   - `calculateLateFee()`: late days x late fee per day.
   - `calculateTotalCharges()`: rent + late fee + damage charge.
   - `calculateFinalAmount()`: total charges - deposit of the vehicle. (A positive number means the customer pays. A negative number means a refund.)
4. Write `printInvoice()`. Example output:

```
------ INVOICE ------
Rental: R3     Customer: Ravi
Vehicle: Maruti Swift
Days: 3        Rent: Rs. 4500
Late days: 2   Late fee: Rs. 1500
Damage charge: Rs. 0
Total charges: Rs. 6000
Deposit paid:  Rs. 2000
Customer pays: Rs. 4000
---------------------
```

If the final amount is negative, print "Refund to customer: Rs. ..." instead.

**How to test your part:** Use the example from the rules. Check that your answer is Rs. 4000.

## PERSON 4: RentalShop and Main

**What this class is for:** This is the main manager. It holds all vehicles, customers and rentals.

**Steps:**

1. Private variables: `Vehicle[] vehicles`, `Customer[] customers`, `Rental[] rentals` (size 50, with a counter), `maxRentals`, `lateFeePerDay`.
2. In the constructor, create your vehicles and customers and put them in the arrays.
3. Write `showAvailableVehicles()`: prints only the vehicles that are available.
4. Write `rentVehicle(String customerId, String vehicleId, int startDay, int endDay)`:
   - Find the customer and the vehicle. If not found, print a message and stop.
   - If the vehicle is not available, throw `VehicleNotAvailableException`.
   - If `customer.canRent(maxRentals)` is false, throw `RentalLimitException`.
   - Otherwise: call `vehicle.markRented()`, `customer.increaseActive()`, make a Rental, save it, and print the deposit the customer must pay now.
5. Write `returnVehicle(String rentalId, int actualDay, double damageCharge)`:
   - Find the rental. If not found or already closed, print a message and stop.
   - Close the rental, call `vehicle.markReturned()` and `customer.decreaseActive()`.
   - Make an Invoice and print it.
6. Write `showActiveRentals()`: prints all rentals that are not closed.
7. Main class: show the menu in a loop. Use try/catch to show the messages from your 2 exceptions.

**Menu:**

```
1. Show available vehicles
2. Rent a vehicle
3. Return a vehicle
4. Show active rentals
5. Stop program
```

## DEMO: your group must show all 6

1. Show the available vehicles.
2. Rent a car. Show that it is not in the available list anymore.
3. Try to rent the same car for another customer. The "already rented" message must show.
4. Let one customer rent vehicles until the limit. Try one more. The "limit" message must show.
5. Return a vehicle late with a damage charge. Show the invoice and check the numbers on paper.
6. Return a vehicle on time with no damage. Show that the customer gets a refund of the deposit (minus the rent, if the rent is less than the deposit).

------

# PROJECT 7: PAYROLL SYSTEM

## What you will make

A company salary program. The company has different types of employees (manager, developer, intern). Each type has different rules for allowance and PF. The program makes a salary slip for any employee.

## Decisions you must make as a group (write them in your 5 lines)

1. How many employees? Make at least 6 (at least 2 managers, 2 developers, 2 interns).
2. Each employee has a unique ID. Example: E101, E102, E103...
3. The basic salary of each employee.
4. The allowance rule for each type (the example below is only a guide).
5. PF percent (example: 12%).
6. The tax rule (example: if gross salary is more than Rs. 50000, tax is 10% of gross. Otherwise, no tax).

## Rules of the payroll

1. Allowance for each type (example):
   - Manager: 40% of basic + Rs. 5000 team allowance
   - Developer: 25% of basic
   - Intern: no allowance
2. **Gross salary = basic + allowance.**
3. PF = basic x PF percent. Managers and developers pay PF. Interns do not pay PF.
4. A month has 30 days. Absent deduction = (basic / 30) x absent days.
5. Tax follows the rule your group decided.
6. **Net salary = gross - PF - absent deduction - tax.**
7. Absent days must be between 0 and 30.

**Example:** Developer, basic Rs. 40000. Allowance = 25% = 10000. Gross = 50000. PF = 12% of 40000 = 4800. Absent 3 days: (40000 / 30) x 3 = 4000. Tax = 0 (because gross is not more than 50000). Net = 50000 - 4800 - 4000 - 0 = Rs. 41200.

## PERSON 1: Employee and InvalidSalaryException

**What this class is for:** The parent class that stores what is common for all employees.

**Steps:**

1. Make an abstract class `Employee`.
2. Private variables: `employeeId`, `name`, `basicSalary`.
3. Make a constructor that takes all three values. If the basic salary is 0 or less, throw `InvalidSalaryException`. (A constructor can throw an exception. Write `throws InvalidSalaryException` after the constructor name.)
4. Make getters.
5. Add two abstract methods: `getAllowance()` and `getRoleName()`.
6. Write a normal method `getPfPercent()` that returns the PF percent your group decided (example 12). Children can override it.
7. Write `toString()`: shows ID, name, role and basic salary.
8. Make the custom exception `InvalidSalaryException`: message "Basic salary must be more than 0".

**How to test your part:** Try to make an object with salary 0 (use a temporary child class). The exception must come.

## PERSON 2: Manager, Developer and Intern

**What these classes are for:** Each child class gives its own allowance rule and its own extra information.

**Steps:**

1. Make child class `Manager` that extends Employee.
   - Extra private variable: `teamSize`.
   - Override `getAllowance()`: 40% of basic + 5000 (or your group's rule).
   - Override `getRoleName()`: return "Manager".
2. Make child class `Developer` that extends Employee.
   - Extra private variable: `primaryLanguage` (example "Java").
   - Override `getAllowance()`: 25% of basic (or your group's rule).
   - Override `getRoleName()`: return "Developer".
3. Make child class `Intern` that extends Employee.
   - Extra private variable: `durationMonths`.
   - Override `getAllowance()`: return 0.
   - Override `getRoleName()`: return "Intern".
   - Override `getPfPercent()`: return 0 (interns do not pay PF).
4. Each child needs a constructor. Use `super(...)` to send the common values to the parent constructor.
5. Each child overrides `toString()` to also show its own extra variable.
6. Make getters for the extra variables.

**How to test your part:** Make one of each type with the same basic salary. Print their allowances. They must be different.

## PERSON 3: Payslip and InvalidAbsentDaysException

**What this class is for:** The Payslip does all the salary calculations for one employee for one month.

**Steps:**

1. Private variables: `employee`, `absentDays`.
2. Make a constructor that takes an Employee and absentDays. If absentDays is less than 0 or more than 30, throw `InvalidAbsentDaysException`.
3. Write these methods:
   - `calculateGross()`: basic + allowance.
   - `calculatePf()`: basic x `employee.getPfPercent()` / 100.
   - `calculateAbsentDeduction()`: (basic / 30) x absentDays.
   - `calculateTax()`: use your group's tax rule on the gross salary.
   - `calculateNetSalary()`: gross - PF - absent deduction - tax.
4. Write `printPayslip()`. Example output:

```
------ PAYSLIP ------
Employee: E103   Name: Neha   Role: Developer
Basic:             Rs. 40000
Allowance:         Rs. 10000
Gross salary:      Rs. 50000
PF:                Rs. 4800
Absent (3 days):   Rs. 4000
Tax:               Rs. 0
NET SALARY:        Rs. 41200
---------------------
```

1. Make the custom exception `InvalidAbsentDaysException`: message "Absent days must be between 0 and 30".

**How to test your part:** Use the example from the rules. Check that your net salary is Rs. 41200.

## PERSON 4: Company, EmployeeNotFoundException and Main

**What this class is for:** This is the main manager. It holds all employees and connects everything.

**Steps:**

1. Private variables: `Employee[] employees` (size is your choice, bigger than your employee count) and a counter `employeeCount`.
2. `addEmployee(Employee e)`:
   - If another employee already has the same ID, print "ID already exists" and stop.
   - If the array is full, print a message and stop.
   - Otherwise, store the employee and increase the counter.
3. `findEmployee(String id)`: search the array. If not found, throw `EmployeeNotFoundException`.
4. `generatePayslip(String id, int absentDays)`: find the employee, make a Payslip, and print it.
5. `showAllEmployees()`: prints all employees with their role.
6. `showSalaryReport()`: for each employee, make a Payslip with 0 absent days. Print name, role and net salary in one line each. At the end, print the total money the company must pay.
7. Create your starting employees (at least 6) in the constructor or in Main and add them using `addEmployee`.
8. Make the custom exception `EmployeeNotFoundException`: message "Employee ID not found".
9. Main class: show the menu in a loop. Use try/catch to show messages from your exceptions (and from `InvalidSalaryException` and `InvalidAbsentDaysException`).

**Menu:**

```
1. Add new employee
2. Show all employees
3. Generate payslip
4. Show salary report (all employees)
5. Stop program
```

## DEMO: your group must show all 7

1. Show all employees (all 3 types must be present).
2. Generate the payslip of a manager, a developer and an intern. Explain why the numbers are different.
3. Generate a payslip with absent days. Check the deduction on paper.
4. Show an employee whose gross salary is above your tax limit, and show the tax is charged.
5. Try to find an ID that does not exist. The "not found" message must show.
6. Try to add an employee with salary 0. The "invalid salary" message must show.
7. Show the salary report with the total payout.

## PROJECT 8: COLLEGE SYSTEM

### What you will make

A university registration system. Professors are assigned to teach courses, and students enroll in them. The system must enforce capacity limits for courses and course-load limits for students.

### Starting data (hardcode this in your Main class)

1. 3 Professors: `P1`, `P2`, `P3`.
2. 5 Courses: `C1` (Java), `C2` (Math), `C3` (Physics), `C4` (Art), `C5` (History).
3. 5 Students: `S1` to `S5`.

### Rules of the college

1. Each course has a maximum capacity (e.g., 2 students max).
2. A student can take at most 5 courses.
3. A professor can teach at most 3 courses.
4. A student cannot enroll in a course if it is full.

### PERSON 1: Course class and Exceptions

**What this class is for:** Represents a single subject being taught.
**Steps:**

1. Make private variables: `courseId` (String), `courseName` (String), `maxCapacity` (int), `currentEnrollment` (int).
2. Make a constructor taking ID, name, and capacity. Set `currentEnrollment = 0`.
3. Make getters for all variables.
4. Write method `enrollStudent()`:

* If `currentEnrollment` is equal to `maxCapacity`, do not add the student. Instead, **throw** a `CourseFullException`.
* Otherwise, increase `currentEnrollment` by 1.


5. Write method `dropStudent()`:

* If `currentEnrollment` is 0, **throw** a `StudentNotEnrolledException`.
* Otherwise, decrease `currentEnrollment` by 1.


6. Write `toString()`: return a string like `"C1 - Java (1/2 Enrolled)"`.
7. **Make 2 custom exceptions:** Create `CourseFullException` and `StudentNotEnrolledException` classes that extend `Exception`.

* **How to test your part:** Write a temporary `main` method, create a Course with capacity 2. Call `enrollStudent()` 3 times. The 3rd time, your exception should trigger.

### PERSON 2: Person, Student, and Professor

**What this class is for:** Stores details for humans in the college and enforces limits using Polymorphism.
**Steps:**

1. Make an `abstract class Person`.
2. Private variables: `personId` (String), `name` (String), an array `Course[] myCourses` (size 5), and an `int courseCount` (starts at 0).
3. Make a constructor that takes ID and name.
4. Add abstract methods: `public abstract int getMaxCourses();` and `public abstract String getRole();`
5. Make child class `Student` (extends Person):

* `getMaxCourses()` returns `5`.
* `getRole()` returns `"Student"`.


6. Make child class `Professor` (extends Person):

* `getMaxCourses()` returns `3`.
* `getRole()` returns `"Professor"`.


7. Write methods in the parent `Person` class:

* `canAddCourse()`: returns true if `courseCount < getMaxCourses()`.
* `assignCourse(Course c)`: puts the course into the `myCourses` array at index `courseCount`, then increases `courseCount` by 1.
* `printMyCourses()`: loops from 0 to `courseCount` and prints the course details.



* **How to test your part:** Make a Student and a Professor object. Print `getMaxCourses()` for both. It should print 5 and 3. This proves polymorphism works.

### PERSON 3: Department class

**What this class is for:** Groups courses and professors together logically.
**Steps:**

1. Private variables: `deptName` (String), `Course[] offeredCourses` (size 10), `Person[] faculty` (size 5). You also need counters for how many courses and faculty are currently in the arrays.
2. Make a constructor that takes `deptName`.
3. Write `addCourseToDept(Course c)`: adds the course to the array and increases the counter.
4. Write `addProfessorToDept(Person p)`: adds the professor to the array and increases the counter.
5. Write `listAllCourses()`: loops through the `offeredCourses` array and prints them.

### PERSON 4: Main System Manager

**What this class is for:** Holds the global arrays, connects the logic, and shows the text menu.
**Steps:**

1. Make private arrays: `Person[] allStudents`, `Person[] allProfessors`, `Course[] allCourses`.
2. In your constructor (or a startup method), create the Starting Data objects and put them into these arrays.
3. Write `registerStudentForCourse(String sId, String cId)`:

* Loop through arrays to find the Student object and the Course object.
* If the student's `canAddCourse()` is false, print "Student is at max load."
* Otherwise, try to call `course.enrollStudent()`. (You must put this inside a `try/catch` block!).
* If it succeeds (no exception caught), call `student.assignCourse(course)`.
* If `CourseFullException` is caught, print the error message nicely.


4. Build the `while(true)` loop with a text menu. Use `Scanner` to ask the user what they want to do.

### DEMO: Your group must show these test cases

1. Enroll student S1 into course C1 successfully.
2. Enroll student S2 into course C1. If C1's capacity is 2, it is now full.
3. Try to enroll student S3 into C1. The program must *not* crash, but should display your `CourseFullException` message.
4. Call `printMyCourses()` on a student to show their schedule.

---

## PROJECT 9: TIC-TAC-TOE

### What you will make

A digital 3x3 game board where a human can play against another human or against a computer bot that makes random valid moves.

### Starting data

1. A 3x3 character array filled with empty spaces (`' '`).

### Rules

1. X always goes first.
2. A player cannot place a mark on a cell that already has an X or O.
3. If the board fills up and no one has 3-in-a-row, it is a Draw.

### PERSON 1: Board class and Exceptions

**What this class is for:** Managing the 2D grid.
**Steps:**

1. Make a private variable: `char[][] grid = new char[3][3];`
2. Write `initializeBoard()`: use nested `for` loops to set every cell to `' '` (a space).
3. Write `printBoard()`: use loops to print the board. Example output format: `X | O |   `
4. Write `isBoardFull()`: loops through all 9 cells. If it finds even one `' '`, return `false`. If it finishes the loop without finding any spaces, return `true`.
5. Write `placeMark(int row, int col, char mark)`:

* If row or col is less than 0 or greater than 2, **throw** an `InvalidMoveException`.
* If `grid[row][col]` is not `' '`, **throw** a `CellOccupiedException`.
* Else, set `grid[row][col] = mark`.


6. **Make 2 custom exceptions:** `InvalidMoveException` and `CellOccupiedException`.

### PERSON 2: Player classes

**What this class is for:** Separates how humans pick their move versus how a computer picks a move.
**Steps:**

1. Make an `abstract class Player`.
2. Private variables: `name` (String), `mark` (char, either 'X' or 'O').
3. Constructor taking name and mark.
4. Abstract method: `public abstract int[] getMove();` (returns an array of 2 integers: row and col).
5. Make child `HumanPlayer`:

* `getMove()` asks the user: "Enter row (0-2):" and "Enter col (0-2):" using `Scanner`. Returns `new int[]{row, col}`.


6. Make child `ComputerPlayer`:

* `getMove()` uses `Math.random()`. Multiply by 3 and cast to `int` to get a random number between 0 and 2 for both row and col. Print "Computer chooses row X, col Y". Return `new int[]{row, col}`.



### PERSON 3: Game Logic class

**What this class is for:** Checking the win conditions.
**Steps:**

1. You need a method `boolean checkWin(Board board, char mark)`.
2. Check rows: loop `i` from 0 to 2. If `grid[i][0] == mark && grid[i][1] == mark && grid[i][2] == mark`, return true.
3. Check columns: loop `j` from 0 to 2. If `grid[0][j] == mark && grid[1][j] == mark && grid[2][j] == mark`, return true.
4. Check diagonals: Check `grid[0][0]`, `grid[1][1]`, `grid[2][2]` AND check `grid[0][2]`, `grid[1][1]`, `grid[2][0]`.
5. If none of these match, return `false`.

### PERSON 4: Main Engine

**What this class is for:** Alternating turns and handling the game loop.
**Steps:**

1. Ask the user for game mode: Human vs Human, or Human vs Computer. Create the `Player` objects based on the choice.
2. Make a `Player currentPlayer = player1;`
3. Make a `while(true)` loop:

* Call `board.printBoard()`.
* Ask `currentPlayer` for their move using `int[] move = currentPlayer.getMove();`.
* Try to place the mark: `board.placeMark(move[0], move[1], currentPlayer.getMark())`.
* **Important:** Put `placeMark` in a `try/catch` block! If an exception is caught, print the error and `continue;` the loop (do NOT switch players, make them try again). If the computer throws an exception (because it randomly guessed a full spot), the loop will just automatically make it guess again.
* If successful, call `checkWin()`. If true, print winner and break.
* Call `isBoardFull()`. If true, print Draw and break.
* Switch player: `if (currentPlayer == player1) currentPlayer = player2; else currentPlayer = player1;`



### DEMO: Your group must show these test cases

1. Play a full game between two humans where someone gets 3-in-a-row.
2. During the game, try to place an X on top of an O. Show the `CellOccupiedException` message.
3. During the game, type row `5` col `5`. Show the `InvalidMoveException` message.
4. Play a game against the Computer and show it picking moves automatically.

---

## PROJECT 10: QUIZ APPLICATION

### What you will make

A digital quiz test engine. It supports multiple-choice questions (A/B/C/D) and True/False questions. It keeps track of player scores.

### Starting data

1. Hardcode 3 Multiple Choice Questions and 2 True/False Questions in an array.

### Rules

1. Players get 10 points for each correct answer.
2. If a player types "E" for a question that only has A, B, C, D, the system should catch the error and ask again.

### PERSON 1: Question classes

**What this class is for:** Storing different question formats using Polymorphism.
**Steps:**

1. Make an `abstract class Question`.
2. Private variables: `questionText` (String), `points` (int, default to 10).
3. Abstract method: `public abstract boolean checkAnswer(String userAnswer);`
4. Abstract method: `public abstract void displayQuestion();`
5. Make child `MCQQuestion`:

* Variables: `String[] options` (size 4, for A, B, C, D text) and `String correctAnswer` (e.g., "B").
* `displayQuestion()` prints the question text, then loops through the 4 options and prints them.
* `checkAnswer(String ans)` compares `ans.toUpperCase()` to `correctAnswer`. Returns true if match, false if not.


6. Make child `TrueFalseQuestion`:

* Variables: `boolean correctAnswer`.
* `displayQuestion()` prints the question text and says "(Type True or False)".
* `checkAnswer(String ans)` parses the user's string to a boolean and checks if it matches.



### PERSON 2: Quiz Manager and Exceptions

**What this class is for:** Holding the test paper.
**Steps:**

1. Private variables: `Question[] quizQuestions` (size 5), `int count`.
2. Write `addQuestion(Question q)`: adds to the array. If the array is full, **throw** a `QuizFullException`.
3. Write `getQuestion(int index)`: returns the question at that spot.
4. **Make 2 custom exceptions:** `QuizFullException` and `InvalidOptionException` (You will throw `InvalidOptionException` if a user types something weird like "Z").

### PERSON 3: Player and ScoreBoard

**What this class is for:** Tracking who took the test and what they scored.
**Steps:**

1. Player variables: `playerName` (String), `totalScore` (int).
2. Write `addPoints(int p)` inside Player.
3. ScoreBoard variables: `Player[] history` (size 10), `int playerCount`.
4. Write `saveScore(Player p)`: adds player to the history array.
5. Write `showAllScores()`: loops through the history array and prints "Name: Score".

### PERSON 4: Main Engine

**What this class is for:** Running the actual test.
**Steps:**

1. In `main`, create the 5 questions and add them to the Quiz Manager.
2. Create the menu: 1. Take Quiz, 2. View History.
3. If they choose "Take Quiz":

* Ask for their name, create a `Player` object.
* Use a `for` loop from 0 to 4 to get each question.
* Call `question.displayQuestion()`.
* Use `Scanner` to get their answer.
* **Validation:** If the question is an MCQ, and they typed "X" or "123", **throw** your `InvalidOptionException`, catch it, print "Please type A, B, C, or D", and make them answer the *same* question again (decrease your loop counter `i--` so it repeats).
* Call `question.checkAnswer()`. If true, print "Correct!" and add points to the player. If false, print "Wrong!".
* After the loop, call `scoreboard.saveScore(player)`.



### DEMO: Your group must show these test cases

1. Take the quiz and get a mix of right and wrong answers. Show the final score.
2. When asked an MCQ question, type "Hello". Show the `InvalidOptionException` catching it and asking the user to try again.
3. Take the quiz with two different names, then view the Scoreboard history to prove both were saved in the array.

---

## PROJECT 11: MUSIC PLAYER

### What you will make

A music streaming simulator. Users can create playlists. Free users have limits and hear text-based "audio ads". Premium users have higher limits and no ads.

### Starting data

1. 10 songs hardcoded in a master array (e.g., S1, S2... S10).
2. 2 Users (One Free, One Premium).

### Rules

1. A Free user can have a maximum of 2 playlists.
2. A Premium user can have a maximum of 10 playlists.
3. When a Free user plays a playlist, an Ad must print after every 2 songs.

### PERSON 1: Song class and Exceptions

**What this class is for:** Holds the raw data for one music track.
**Steps:**

1. Private variables: `songId` (String), `title` (String), `artist` (String), `durationInSeconds` (int).
2. Constructor and getters.
3. Write `toString()`: return formatted string like `"S1 - Shape of You by Ed Sheeran"`.
4. **Make 2 custom exceptions:**

* `SongNotFoundException`: "The song ID you entered does not exist."
* `PlaylistFullException`: "This playlist has reached its maximum size."



### PERSON 2: User, FreeUser, PremiumUser

**What this class is for:** Enforces limits based on subscription tier.
**Steps:**

1. Make an `abstract class User`.
2. Private variables: `username` (String), `Playlist[] myPlaylists` (size 10), `int listCount = 0`.
3. Abstract methods: `public abstract int getMaxPlaylists();` and `public abstract boolean seesAds();`
4. Make `FreeUser`: `getMaxPlaylists()` returns 2. `seesAds()` returns `true`.
5. Make `PremiumUser`: `getMaxPlaylists()` returns 10. `seesAds()` returns `false`.
6. Write `createPlaylist(Playlist p)` in the `User` class:

* If `listCount == getMaxPlaylists()`, print "Limit reached, cannot create more playlists."
* Else, add to array and increase counter.



### PERSON 3: Playlist class

**What this class is for:** A collection of songs a user put together.
**Steps:**

1. Private variables: `playlistName` (String), `Song[] songs` (size 10), `int songCount`.
2. Write `addSong(Song s)`: If `songCount == 10`, **throw** a `PlaylistFullException`. Else, add to array and increase counter.
3. Write `playAll(User owner)`:

* Use a `for` loop to go through the songs array (from 0 to `songCount`).
* Print `"Playing: " + songs[i].getTitle()`.
* **The Ad Logic:** Inside the loop, check `if (owner.seesAds() == true)`. If yes, check `if (i % 2 == 1)` (this means it's the 2nd, 4th, 6th song). If both are true, print `"--- BUY PREMIUM NOW! (Audio Ad) ---"`.



### PERSON 4: Main System Manager

**What this class is for:** Connects users to songs through the text menu.
**Steps:**

1. Arrays: `Song[] library`, `User[] registeredUsers`. Initialize them with the Starting Data.
2. Write `findSongById(String id)`: loops through the library array. If found, return the Song object. If loop finishes and not found, **throw** `SongNotFoundException`.
3. Build the menu:

* **Search Song:** ask for ID, use `findSongById`, catch exception if bad ID.
* **Add Song to Playlist:** Ask user name, find user. Ask playlist name, find playlist. Ask song ID, find song. Call `playlist.addSong()`. Catch `PlaylistFullException`.
* **Play Playlist:** Find user, find playlist, call `playlist.playAll(user)`.



### DEMO: Your group must show these test cases

1. Play a playlist owned by a Premium user (prove no ads print).
2. Play a playlist owned by a Free user (prove ads print every 2nd song).
3. Try to add an 11th song to a playlist. Show the `PlaylistFullException`.
4. Try to search for a song ID that doesn't exist. Show the `SongNotFoundException`.

---

## PROJECT 12: BARBER SHOP SIMULATION (MONTE CARLO)

### What you will make

A time-based simulation (running in "ticks" or minutes). Customers arrive, sit in a waiting array, and barbers pull them from the array to cut their hair. Since Queue classes are banned, you will build a manual array queue.

### Starting data

1. 1 Junior Barber, 1 Senior Barber.
2. A waiting area with 3 chairs (Array of size 3).

### Rules

1. A Junior Barber needs 3 "ticks" (minutes) to finish a haircut.
2. A Senior Barber needs 1 "tick" to finish a haircut.
3. If all 3 chairs are full when a customer arrives, they immediately leave angry.

### PERSON 1: Customer and Exceptions

**What this class is for:** Represents the person arriving.
**Steps:**

1. Private variables: `customerId` (int), `name` (String).
2. Constructor and getters.
3. **Make 2 custom exceptions:**

* `QueueFullException`: "No chairs available, customer leaves."
* `ShopClosedException`: "The time is up, the shop is locked."



### PERSON 2: Barber classes

**What this class is for:** The workers who process the customers.
**Steps:**

1. Make an `abstract class Barber`.
2. Private variables: `name` (String), `int remainingWorkTime` (default 0), `Customer currentCustomer` (default null).
3. Abstract method: `public abstract int getSpeed();`
4. Make `JuniorBarber`: `getSpeed()` returns 3.
5. Make `SeniorBarber`: `getSpeed()` returns 1.
6. Write `isFree()`: returns true if `remainingWorkTime == 0`.
7. Write `startCut(Customer c)`: set `currentCustomer = c`. Set `remainingWorkTime = getSpeed()`.
8. Write `workOneTick()`: if `remainingWorkTime > 0`, reduce it by 1. If it hits 0, print "Barber finished with " + customer.name.

### PERSON 3: Shop Waiting Area (The Manual Queue)

**What this class is for:** Managing the chairs using an array.
**Steps:**

1. Private variables: `Customer[] chairs = new Customer[3];`, `int count = 0;`
2. Write `walkIn(Customer c)`:

* If `count == 3`, **throw** a `QueueFullException`.
* Else, put customer at `chairs[count]` and increase `count`. Print "Customer sat down."


3. Write `getNextCustomer()`:

* If `count == 0`, return null.
* Save `chairs[0]` to a temporary variable.
* **Shift Left:** Use a loop: `chairs[0] = chairs[1]; chairs[1] = chairs[2]; chairs[2] = null;`
* Decrease `count` by 1.
* Return the saved customer.



### PERSON 4: Main Simulation Engine

**What this class is for:** Running the clock loop.
**Steps:**

1. Setup the Barbers and the Shop.
2. Create a loop: `for(int tick = 1; tick <= 20; tick++)` (simulating 20 minutes).
3. Inside the loop:

* Ask the user via text menu: "Tick " + tick + ": Press 1 to advance time, Press 2 to spawn a new customer."
* If they choose 2: Create a customer. Call `shop.walkIn()`. Catch the `QueueFullException` if chairs are full and print "Customer left angry!".
* Check Barbers: If a barber `isFree()`, call `shop.getNextCustomer()`. If it returns a customer, call `barber.startCut()`.
* If a barber is not free, call `barber.workOneTick()`.



### DEMO: Your group must show these test cases

1. Spawn a customer. Advance time. Show the Senior barber finishing in 1 tick, while Junior takes 3.
2. Spawn 4 customers in a row without advancing time. Show the 4th customer triggering the `QueueFullException` and leaving.
3. Show the "Shift Left" array logic working (Customer 2 moves to Chair 1 when Customer 1 goes to the barber).

---

## PROJECT 13: TOLL PLAZA SIMULATION (MONTE CARLO)

### What you will make

Similar to Project 12, but for traffic. Cars and trucks queue up at a toll plaza. Booths process them based on vehicle type.

### Starting data

1. 2 Toll Booths.
2. A waiting lane that holds 5 vehicles.

### Rules

1. A Car pays Rs 50 and takes 1 tick to process.
2. A Truck pays Rs 150 and takes 3 ticks to process.

### PERSON 1: Booth and Exceptions

**What this class is for:** Where the money is collected.
**Steps:**

1. Private variables: `boothName`, `int totalMoneyCollected`, `int timeUntilFree`, `Vehicle currentVehicle`.
2. Write `isFree()`: returns true if `timeUntilFree == 0`.
3. Write `processVehicle(Vehicle v)`:

* Increase `totalMoneyCollected` by `v.getTollFee()`.
* Set `timeUntilFree = v.getProcessTime()`.


4. Write `workOneTick()`: reduce `timeUntilFree` by 1 if it is > 0.
5. **Make 2 custom exceptions:**

* `LaneFullException`: "The waiting lane is completely full."
* `InvalidVehicleException`: "System does not recognize this vehicle type."



### PERSON 2: Vehicle classes

**What this class is for:** Defines the traffic using Polymorphism.
**Steps:**

1. Make an `abstract class Vehicle`.
2. Private variables: `licensePlate` (String).
3. Abstract methods: `public abstract int getTollFee();` and `public abstract int getProcessTime();`
4. Make child `Car`: fee returns 50, process time returns 1.
5. Make child `Truck`: fee returns 150, process time returns 3.

### PERSON 3: Plaza (The Waiting Lane)

**What this class is for:** The manual array queue for traffic.
**Steps:**

1. Private variables: `Vehicle[] lane = new Vehicle[5];`, `int count = 0;`
2. Write `arrive(Vehicle v)`:

* If `count == 5`, **throw** `LaneFullException`.
* Else, put at `lane[count]`, increase count.


3. Write `letNextVehicleGo()`:

* Grab `lane[0]`.
* **Shift Left:** loop from 0 to 3, setting `lane[i] = lane[i+1]`. Set `lane[4] = null`.
* Decrease count. Return the vehicle.



### PERSON 4: Main Simulation Engine

**What this class is for:** Running the time ticks.
**Steps:**

1. Setup the array of 2 Booths and the Plaza lane.
2. Create a loop: `for(int tick = 1; tick <= 20; tick++)`.
3. Inside the loop:

* Menu: 1. Spawn Car, 2. Spawn Truck, 3. Advance Time.
* If spawning: create object, call `plaza.arrive()`. Catch `LaneFullException`.
* If advancing time: Loop through the 2 booths. If a booth `isFree()`, check if plaza has vehicles. If yes, `letNextVehicleGo()` and give it to the booth using `processVehicle()`. If booth is busy, call `workOneTick()`.


4. At the end, print the `totalMoneyCollected` from all booths.

### DEMO: Your group must show these test cases

1. Spawn a Car and a Truck. Show the Truck taking longer to clear the booth than the car.
2. Spawn 6 vehicles instantly. Show the 6th one throwing the `LaneFullException`.
3. Show Polymorphism working (the booth just asks `getTollFee()` and gets different prices).
4. Print the final collected revenue accurately.

---

## PROJECT 14: SNAKE AND LADDER GAME

### What you will make

A text-based board game. Players roll dice to move from 0 to exactly 100. Arrays will act as the board's "rules" for snakes and ladders.

### Starting data

1. 2 Players (1 Human, 1 Bot).
2. Hardcode 2 ladders and 2 snakes using arrays.

### Rules

1. A player must roll the exact number to land on 100. If they are at 98 and roll a 5, they cannot move.

### PERSON 1: Dice and Exceptions

**What this class is for:** Generating random rolls and handling game errors.
**Steps:**

1. Private variable: `int sides = 6;`
2. Write `int roll()`: use `Math.random()`. Multiply by sides, cast to int, add 1. Return a number from 1 to 6.
3. **Make 2 custom exceptions:**

* `InvalidMoveException`: "Roll is too high, you must land on exactly 100."
* `GameOverException`: "The game has already been won!"



### PERSON 2: Player classes

**What this class is for:** Different types of players.
**Steps:**

1. Make an `abstract class Player`.
2. Private variables: `name` (String), `position` (int, starts at 0).
3. Abstract method: `public abstract void takeTurnMsg();`
4. Make child `HumanPlayer`:

* `takeTurnMsg()` prints: "Press Enter to roll the dice..." (use Scanner to wait for input).


5. Make child `BotPlayer`:

* `takeTurnMsg()` prints: "Bot is rolling the dice automatically..."



### PERSON 3: Board class

**What this class is for:** Holding the hazard arrays.
**Steps:**

1. Private arrays:

* `int[] snakeHeads = {99, 45};` and `int[] snakeTails = {10, 5};`
* `int[] ladderBottoms = {15, 60};` and `int[] ladderTops = {35, 85};`


2. Write `checkSquare(int playerPos)`:

* Loop through `snakeHeads`. If `playerPos == snakeHeads[i]`, print "OH NO! Bitten by snake!", return `snakeTails[i]`.
* Loop through `ladderBottoms`. If `playerPos == ladderBottoms[i]`, print "YAY! Climbed a ladder!", return `ladderTops[i]`.
* Else, return `playerPos` (no change).



### PERSON 4: Main Game Engine

**What this class is for:** Alternating turns.
**Steps:**

1. Create an array of `Player[] players = new Player[2];` (Put human at index 0, bot at index 1).
2. Make a `while(true)` loop:

* For each player in the array:
* Call `player.takeTurnMsg()`.
* Call `dice.roll()`.
* Calculate `newPos = player.getPosition() + roll`.
* If `newPos > 100`, **throw** an `InvalidMoveException`. Catch it, print the warning, and their turn is over without moving.
* If `newPos <= 100`, pass `newPos` into `board.checkSquare(newPos)`.
* Update the player's position to whatever the board returns.
* Print "Player is now at space X".
* If position == 100, print "WINNER!", and break the loop.



### DEMO: Your group must show these test cases

1. Land on a ladder bottom and show the position update to the top.
2. Land on a snake head and show the position update to the bottom.
3. Show Polymorphism: the Human waits for an Enter keypress, but the Bot rolls instantly.
4. Have a player reach square 98, then roll a 5. Show the `InvalidMoveException` catching it.

---

## PROJECT 15: GEOMETRY SHAPES TOOLKIT

### What you will make

A mathematical tool where a user can generate geometric shapes, store them in a list, and ask the system to calculate total areas and perimeters using Polymorphism.

### Starting data

1. An empty array capable of holding 10 shapes.

### Rules

1. A dimension (radius, length, width, side) cannot be negative or zero.

### PERSON 1: Shape, Circle, Rectangle

**What this class is for:** The mathematical foundations.
**Steps:**

1. Make an `abstract class Shape`.
2. Private variables: `shapeId` (String), `color` (String).
3. Constructor and getters.
4. Abstract methods: `public abstract double calculateArea();` and `public abstract double calculatePerimeter();`
5. Make child `Circle`:

* Private variable `radius`.
* `calculateArea()` returns `3.14159 * radius * radius`.
* `calculatePerimeter()` returns `2 * 3.14159 * radius`.


6. Make child `Rectangle`:

* Private variables `length`, `width`.
* `calculateArea()` returns `length * width`.
* `calculatePerimeter()` returns `2 * (length + width)`.



### PERSON 2: Triangle and Exceptions

**What this class is for:** Triangle math and error handling.
**Steps:**

1. Make child `Triangle` (extends Shape):

* Private variables `sideA`, `sideB`, `sideC`.
* `calculatePerimeter()` returns `a + b + c`.
* `calculateArea()` use Heron's formula: `s = perimeter / 2`. Area = `Math.sqrt(s * (s-a) * (s-b) * (s-c))`.


2. **Make 2 custom exceptions:**

* `InvalidDimensionException`: "Dimensions must be greater than zero."
* `ShapeNotFoundException`: "No shape exists with that ID."



### PERSON 3: Shape Collection

**What this class is for:** Managing the array of shapes.
**Steps:**

1. Private variables: `Shape[] myShapes = new Shape[10];`, `int count = 0;`
2. Write `addShape(Shape s)`: adds to the array.
3. Write `getTotalArea()`: loops through from 0 to `count`. Adds up `myShapes[i].calculateArea()` into a total sum and returns it.
4. Write `getLargestShape()`: loops through and finds the shape with the highest area. Returns that Shape object.

### PERSON 4: Main Menu UI

**What this class is for:** Text inputs and calling the functions.
**Steps:**

1. Show a menu: 1. Add Circle, 2. Add Rectangle, 3. Add Triangle, 4. Show Total Area, 5. Show Largest Shape.
2. Use `Scanner` to ask for the dimensions based on the choice.
3. **Crucial Validation:** Before you use `new Circle(radius)`, check the user's input. If `radius <= 0`, **throw** an `InvalidDimensionException`.
4. Put the input logic inside a `try/catch`. If they enter a negative number, catch your exception, print the message, and return to the main menu.
5. If valid, call `collection.addShape()`.

### DEMO: Your group must show these test cases

1. Add one Circle, one Rectangle, and one Triangle successfully.
2. Call "Show Total Area". Explain how the system used Polymorphism to call the correct formula for each shape in the loop.
3. Try to create a Rectangle with a length of `-5`. Show your `InvalidDimensionException` catching it.
4. Call "Show Largest Shape" and print its details.