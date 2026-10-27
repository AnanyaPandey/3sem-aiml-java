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