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