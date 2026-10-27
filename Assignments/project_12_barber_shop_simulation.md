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