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