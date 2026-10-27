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