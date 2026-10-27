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