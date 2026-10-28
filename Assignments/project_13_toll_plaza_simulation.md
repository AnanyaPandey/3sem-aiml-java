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

# PROJECT 13: TOLL PLAZA SIMULATION (MONTE CARLO)

## What you will make

A toll plaza with 2 booths. Cars and trucks wait in a lane of 5 places. A truck pays more and takes more time. Like Project 12, you can run it by hand, or run 100 random days and see the average money collected.

## Starting data

1. 2 toll booths: "Booth 1" and "Booth 2".
2. A waiting lane with 5 places (an array of size 5).

## Rules

1. A Car pays Rs 50 and needs 1 tick. A Truck pays Rs 150 and needs 3 ticks.
2. The vehicle type is typed by the user as text: "CAR" or "TRUCK" (any letter case). Anything else is not allowed.
3. If the lane is full when a vehicle arrives, the vehicle is lost (it turns away).
4. A booth collects the fee when it starts to process a vehicle.
5. Every time the clock moves by one tick, do these steps in this exact order:
   - Add 1 to the tick.
   - Every booth that is busy works one tick.
   - Every booth that is free takes the next vehicle from the lane (if there is one).
6. A day has 20 ticks.
7. In automatic mode, at every tick a vehicle arrives with a 50% chance. If it arrives, it is a car with 70% chance and a truck with 30% chance.
8. Classes do not print messages inside `arrive` and `workOneTick`. The Simulation prints.

## PERSON 1: Booth and the exceptions

**What this class is for:** The place where money is collected.

**Steps:**

1. Private variables: `boothName`, `totalMoneyCollected`, `timeUntilFree`, `vehiclesProcessed`.
2. Constructor takes the booth name. Make getters.
3. `isFree()`: true if `timeUntilFree` is 0.
4. `processVehicle(Vehicle v)`: add `v.getTollFee()` to `totalMoneyCollected`, set `timeUntilFree` to `v.getProcessTime()`, and add 1 to `vehiclesProcessed`.
5. `workOneTick()`: if `timeUntilFree` is more than 0, subtract 1.
6. `toString()`: shows name, "Free" or "Busy (N ticks left)", and money collected.
7. Make 2 custom exceptions:
   - `LaneFullException`: message "The waiting lane is full, vehicle turns away".
   - `InvalidVehicleException`: message "System does not recognize this vehicle type".

**How to test your part:** Process one truck. Check `isFree()`, call `workOneTick()` 3 times, and check again. Check the money.

## PERSON 2: Vehicle, Car and Truck

**What this class is for:** Different vehicles have different fees and times.

**Steps:**

1. Make an abstract class `Vehicle`.
2. Private variable: `licensePlate`. Constructor and `getLicensePlate()`.
3. Abstract methods: `getTollFee()`, `getProcessTime()` and `getVehicleType()`.
4. `Car`: fee 50, time 1, type "CAR".
5. `Truck`: fee 150, time 3, type "TRUCK".
6. `toString()` in Vehicle: example `TRUCK CG17AB1234 (Rs. 150)`.

**How to test your part:** Make a car and a truck. Put both in a `Vehicle[]` array and print the fee of each with a loop. This is polymorphism.

## PERSON 3: Plaza and Statistics

**What these classes are for:** The Plaza is the waiting lane. The Statistics keeps the numbers.

**Plaza steps:**

1. Private variables: `Vehicle[] lane = new Vehicle[5]`, `count`.
2. `arrive(Vehicle v)`: if the lane is full, throw `LaneFullException`. Otherwise store it.
3. `letNextVehicleGo()`: if `count` is 0, return null. Otherwise save `lane[0]`, move every vehicle one place to the left with a loop (it must work for any lane size), set the last used place to null, subtract 1 from `count`, and return the saved vehicle.
4. `hasVehicles()`, `getCount()` and `showLane()`.

**Statistics steps:**

1. Private variables: `lostVehicles`, `daysRun`, `totalMoney`.
2. `recordLost()`, `addMoney(int m)` and `endDay()`.
3. `printAverages()`: prints average money per day and average lost vehicles per day. (If `daysRun` is 0, print a message.)

**How to test your part:** Add 6 vehicles. The 6th must give the exception. Call `letNextVehicleGo()` 6 times. The 6th call must return null.

## PERSON 4: Simulation and Main

**What this class is for:** To run the clock and connect everything.

**Steps:**

1. Private variables: `Booth[] booths` (size 2), `Plaza plaza`, `Statistics stats`, `tick`.
2. `createVehicle(String type, String plate)`: if the type is "CAR" make a Car, if "TRUCK" make a Truck, otherwise throw `InvalidVehicleException`.
3. `addVehicle(Vehicle v)`: call `plaza.arrive(v)`. If `LaneFullException` comes, call `stats.recordLost()` and print the message.
4. `advanceOneTick()`: do the 3 steps from Rule 5.
5. `showStatus()`: print the tick, the lane, each booth, and the money collected by all booths.
6. `runRandomDays(int days)`: for each day, make new booths and a new plaza, loop 20 ticks (with the arrival chances from Rule 7), add the day's money to `stats`, and call `endDay()`. At the end, print the averages.
7. Main class: menu in a loop, with try/catch for both exceptions. Use ONE Scanner.

**Menu:**

```
1. Add a vehicle (type CAR or TRUCK, then the number plate)
2. Advance time by 1 tick
3. Show status
4. Run 100 random days (Monte Carlo)
5. Stop program
```

## DEMO: your group must show all 6

1. Add one car and one truck. Advance time. Show that the truck keeps its booth busy for 3 ticks and the car only for 1.
2. Add 6 vehicles without advancing time. The 6th must show the "lane is full" message.
3. Type the vehicle type "BUS". The "does not recognize" message must show.
4. Explain polymorphism: the booth only asks `getTollFee()`, and the car and the truck give different answers.
5. Show the total money of both booths, and check it on paper.
6. Run 100 random days. Show the average money per day. Run again to show that the result changes a little.