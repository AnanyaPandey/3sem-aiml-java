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

# PROJECT 12: BARBER SHOP SIMULATION (MONTE CARLO)

## What you will make

A barber shop with 2 barbers (one junior, one senior) and 3 waiting chairs. Time moves in "ticks" (1 tick = 1 minute). One day has 20 ticks. You can run the shop by hand (you decide who arrives and when). You can also let the computer run 100 random days and show the average result. This second part is the Monte Carlo part.

## Starting data

1. 1 Junior barber and 1 Senior barber (keep the Senior barber first in the array).
2. A waiting area with 3 chairs (an array of size 3).

## Rules of the shop

1. A Junior barber needs 3 ticks for one haircut. A Senior barber needs 1 tick.
2. If a customer arrives and all 3 chairs are full, the customer leaves angry. Count this as a lost customer.
3. The shop closes when the clock shows 20. After that, no new customer can enter.
4. Barbers take a customer from the chairs only when the clock moves.
5. Every time the clock moves by one tick, do these steps in this exact order:
   - Add 1 to the tick.
   - Every barber who is busy works one tick.
   - Every barber who is free takes the next customer from the chairs (if there is one).
6. In automatic mode, at every tick a customer arrives with a 40% chance (`Math.random() < 0.4`).
7. Classes do not print messages inside `walkIn` and `workOneTick`. They return a result, and the Simulation prints. (This lets automatic mode run silently.)

## PERSON 1: Customer and the exceptions

**What this class is for:** One object of this class is ONE customer.

**Steps:**

1. Private variables: `customerId`, `name`, `arrivalTick`.
2. The `customerId` is automatic: use a static counter (1, 2, 3...). The name is "Customer " plus the ID.
3. Constructor takes the arrival tick. Make getters.
4. `toString()`: example `Customer 3 (arrived at tick 5)`.
5. Make 2 custom exceptions:
   - `QueueFullException`: message "No chair is free, customer leaves angry".
   - `ShopClosedException`: message "The shop is closed for today".

**How to test your part:** Make 3 customers. Check that the IDs are 1, 2, 3.

## PERSON 2: Barber, JuniorBarber and SeniorBarber

**What this class is for:** The workers who cut hair. A senior barber is faster.

**Steps:**

1. Make an abstract class `Barber`.
2. Private variables: `name`, `remainingWorkTime` (starts at 0), `currentCustomer` (starts as null), `customersServed` (starts at 0).
3. Constructor takes the name. Make getters.
4. Abstract method: `getHaircutTime()`.
5. `JuniorBarber`: returns 3. `SeniorBarber`: returns 1.
6. `isFree()`: true if `remainingWorkTime` is 0.
7. `startCut(Customer c)`: save the customer, and set `remainingWorkTime` to `getHaircutTime()`.
8. `workOneTick()`: if `remainingWorkTime` is more than 0, subtract 1. If it is now 0, set `currentCustomer` to null, add 1 to `customersServed`, and return true (haircut finished). In all other cases return false.
9. `toString()`: shows name, type, and "Free" or "Busy with Customer X (N ticks left)".

**How to test your part:** Start a cut on a junior barber. Call `workOneTick()` 3 times. Only the 3rd call must return true.

## PERSON 3: Shop and Statistics

**What these classes are for:** The Shop is the waiting area. The Statistics keeps the numbers.

**Shop steps:**

1. Private variables: `Customer[] chairs = new Customer[3]`, `count`.
2. `walkIn(Customer c, int tick)`:
   - If `tick` is 20 or more, throw `ShopClosedException`.
   - If `count` equals the number of chairs, throw `QueueFullException`.
   - Otherwise put the customer in `chairs[count]` and add 1 to `count`.
3. `getNextCustomer()`:
   - If `count` is 0, return null.
   - Save `chairs[0]`. Then use a loop to move every customer one place to the left (this must work for any number of chairs, not only 3). Set the last used chair to null. Subtract 1 from `count`. Return the saved customer.
4. `getCount()` and `showChairs()` (print each chair as "Chair 1: Customer 4" or "Chair 2: Empty").

**Statistics steps:**

1. Private variables: `served`, `lost`, `daysRun`.
2. `recordServed()`, `recordLost()`, `endDay()` (adds 1 to `daysRun`).
3. `printTotals()`: prints served and lost.
4. `printAverages()`: prints average served and average lost per day. (If `daysRun` is 0, print a message. Do not divide by zero.)

**How to test your part:** Put 3 customers in the chairs. Try a 4th (exception). Take one out and check the others moved left.

## PERSON 4: Simulation and Main

**What this class is for:** To run the clock and connect everything.

**Steps:**

1. Private variables: `Barber[] barbers` (size 2), `Shop shop`, `Statistics stats`, `tick`.
2. `newCustomer()`: make a Customer and call `shop.walkIn(...)`. If `QueueFullException` comes, call `stats.recordLost()`. Print messages in manual mode.
3. `advanceOneTick()`: if `tick` is 20, throw `ShopClosedException`. Otherwise do the 3 steps from Rule 5. When `workOneTick()` returns true, call `stats.recordServed()`.
4. `showStatus()`: print the tick, the chairs, each barber, and the totals.
5. `runRandomDays(int days)`: for each day, make new barbers and a new shop, set tick to 0, and loop 20 times: with 40% chance call `newCustomer()` (silent), then `advanceOneTick()`. Call `stats.endDay()` at the end of each day. After all days, print the averages.
6. Main class: menu in a loop, with try/catch for both exceptions. Use ONE Scanner.

**Menu:**

```
1. Advance time by 1 tick
2. New customer arrives
3. Show shop status
4. Run 100 random days (Monte Carlo)
5. Stop program
```

## DEMO: your group must show all 6

1. Add 2 customers. Advance time. Show that both barbers took a customer. Advance again: the Senior barber finishes after 1 tick. Advance 2 more times: the Junior barber finishes after 3 ticks.
2. Add 4 customers without advancing time. The 4th must show "customer leaves angry". Show that the lost count is 1.
3. Add 3 customers, then advance one tick. Show that the 3rd customer moved to the first chair (shift left).
4. Advance the clock to 20. Add a customer. The "shop closed" message must show. Advance again. The same message must show.
5. Run 100 random days. Show the average served and lost customers.
6. Run it again. The numbers must be a little different. Explain why (random arrivals).