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

# PROJECT 15: GEOMETRY SHAPES TOOLKIT

## What you will make

A program where the user creates shapes (circle, rectangle, triangle), keeps them in an array, and asks for areas, perimeters, a search by ID, and a full report.

## Starting data

1. An empty array that can hold 10 shapes.

## Rules

1. Every dimension (radius, length, width, side) must be more than 0.
2. A triangle must be possible: the sum of any two sides must be MORE than the third side. (Sides 1, 1, 10 are not allowed.)
3. The check is done inside the constructor of each shape. So a wrong shape can never be made, anywhere in the program.
4. Shape IDs are automatic: SH1, SH2, SH3... (If a wrong shape fails, one number may be skipped. This is fine.)
5. Use 3.14159 for pi.

## Order of work

Person 2 writes the two exceptions FIRST (15 minutes) and gives them to the group, because Person 1 and Person 3 need them.

## PERSON 1: Shape, Circle and Rectangle

**What this class is for:** The base of all shapes.

**Steps:**

1. Make an abstract class `Shape`.
2. Private variables: `shapeId` and `color`. Use a static counter to make the ID automatically.
3. Constructor takes the color. Make getters.
4. Abstract methods: `getShapeName()`, `calculateArea()`, `calculatePerimeter()`.
5. `toString()` in Shape: example `SH2  Circle  (red)  Area: 78.54  Perimeter: 31.42`. Use `String.format("%.2f", ...)`.
6. `Circle`: private variable `radius`. The constructor takes color and radius, and throws `InvalidDimensionException` if radius is 0 or less (write `throws InvalidDimensionException` after the constructor). Area is `3.14159 * radius * radius`. Perimeter is `2 * 3.14159 * radius`.
7. `Rectangle`: private variables `length`, `width`. Same check in the constructor. Area is `length * width`. Perimeter is `2 * (length + width)`.
8. Make getters for radius, length and width.

**How to test your part:** Make a circle and a rectangle and print them. Try radius 0 and length -5. The exception must come.

## PERSON 2: Triangle, Report and the exceptions

**What these classes are for:** The triangle with its extra check, the final report, and the two exceptions.

**Steps:**

1. Make the 2 custom exceptions. Each one has a constructor that takes a message and sends it to `super(message)`:
   - `InvalidDimensionException`.
   - `ShapeNotFoundException`.
2. `Triangle` extends Shape. Private variables `sideA`, `sideB`, `sideC`.
3. In the constructor: if any side is 0 or less, throw `InvalidDimensionException` with the message "Dimensions must be more than 0". If the sum of any two sides is not more than the third side, throw it with the message "These sides cannot make a triangle".
4. `calculatePerimeter()`: `a + b + c`.
5. `calculateArea()`: Heron's formula. `s = perimeter / 2`. Area = `Math.sqrt(s * (s-a) * (s-b) * (s-c))`.
6. Make `getShapeName()` and getters for the sides.
7. `Report` class: the constructor takes a `ShapeCollection`. `printReport()` prints: number of shapes, every shape, total area, total perimeter, and the largest shape. If there are no shapes, print "No shapes to report".

**How to test your part:** Make a triangle 3, 4, 5. The area must be 6. Try 1, 1, 10 and check the exception.

## PERSON 3: ShapeCollection

**What this class is for:** To keep the array of shapes and to calculate with all of them.

**Steps:**

1. Private variables: `Shape[] shapes = new Shape[10]`, `count`.
2. `addShape(Shape s)`: if the array is full, print "Collection is full (10 shapes)" and stop. Otherwise store it.
3. `getCount()` and `getShapeAt(int index)`.
4. `findShape(String id)`: search by ID. If not found, throw `ShapeNotFoundException` with the message "No shape exists with that ID".
5. `getTotalArea()` and `getTotalPerimeter()`: loop and add up the values. Each shape's own formula runs (polymorphism).
6. `getLargestShape()`: return the shape with the biggest area. If there are no shapes, return null.
7. `showAll()`: print every shape. If empty, print "No shapes yet".

**How to test your part:** Add 3 shapes and check the totals on paper. Call `getLargestShape()` on an empty collection. It must not crash.

## PERSON 4: Main

**What this class is for:** To take input and call the other classes.

**Steps:**

1. Create ONE `Scanner` and one `ShapeCollection`.
2. For options 1 to 3: ask the color and the dimensions. Read numbers as text and use `Double.parseDouble` inside `try/catch` (letters must not crash the program).
3. Do NOT check the dimensions in Main. Just call `new Circle(...)` and so on, and catch `InvalidDimensionException` and print its message.
4. For option 5: ask for an ID, call `findShape`, and catch `ShapeNotFoundException`.
5. For option 6: make a `Report` and call `printReport()`.

**Menu:**

```
1. Add circle
2. Add rectangle
3. Add triangle
4. Show all shapes
5. Find a shape by ID
6. Show report
7. Stop program
```

## DEMO: your group must show all 7

1. Add one circle, one rectangle and one triangle.
2. Show all shapes and the report. Explain how polymorphism calls the right formula for each shape.
3. Add a rectangle with length -5. The message must show, and the program must go back to the menu.
4. Add a triangle with sides 1, 1, 10. The "cannot make a triangle" message must show.
5. Find a shape by a good ID, and then by "SH99". The "no shape" message must show.
6. Show the report when there are no shapes. The program must not crash.
7. Add 10 shapes, then try an 11th. The "collection is full" message must show.