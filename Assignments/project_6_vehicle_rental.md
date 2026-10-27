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