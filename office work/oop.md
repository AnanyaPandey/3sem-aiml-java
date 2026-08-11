# Object-Oriented Programming — The Four Pillars

## Why OOP?

In procedural programming, data sits in variables that any function can touch, which makes large programs messy and error-prone. OOP organizes a program as a collection of **objects**, similar to real-world objects — a car, a person, a bank account. Each object has:

- **Data** (what it knows) — called fields/attributes
- **Behavior** (what it can do) — called methods

The four pillars are four principles for organizing objects well: **Encapsulation, Inheritance, Polymorphism, Abstraction.**

------

## 1. Encapsulation — protecting the data

### Concept

Encapsulation means hiding the internal data of an object and only allowing it to be accessed or changed through controlled methods.

Think of an ATM machine. You don't reach into the bank's vault and take money yourself — you use the ATM's buttons (methods): withdraw, deposit, check balance. The ATM checks your PIN and your balance before allowing any action. You never touch the actual cash storage directly.

### Why it matters

If any part of a program could directly do `account.balance = -5000`, there would be no way to stop invalid data. By forcing changes through a method like `withdraw()`, that method can check the rules first before making the change.

### How Java implements it

- Fields are marked `private` so nothing outside the class can touch them directly
- Public getter/setter methods are the only way in and out
- Setters can include validation logic

### Access modifiers in Java

| Modifier              | Same class | Same package | Subclass (other package) | Everywhere |
| --------------------- | ---------- | ------------ | ------------------------ | ---------- |
| `private`             | ✅          | ❌            | ❌                        | ❌          |
| default (no modifier) | ✅          | ✅            | ❌                        | ❌          |
| `protected`           | ✅          | ✅            | ✅                        | ❌          |
| `public`              | ✅          | ✅            | ✅                        | ✅          |

### Program

```java
class BankAccount {
    private double balance;   // sealed inside — nobody outside can touch this directly

    public BankAccount(double initialBalance) {
        if (initialBalance >= 0) {
            balance = initialBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Cannot deposit negative amount");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
        } else if (amount > balance) {
            System.out.println("Insufficient balance");
        } else {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(1000);
        acc.deposit(500);
        acc.withdraw(2000);   // rejected — not enough balance
        acc.withdraw(300);    // accepted

        // acc.balance = 99999;   // ERROR — balance is private, won't compile

        System.out.println("Final balance: " + acc.getBalance());
    }
}
```

------

## 2. Inheritance — reusing and extending

### Concept

Inheritance lets a **child class** acquire the fields and methods of a **parent class**, and add its own new fields/methods, or change the parent's behavior. It works like family traits — a child inherits features from a parent without recreating them, and can also have new traits of its own.

### Why it matters

Without inheritance, classes like `Car`, `Bike`, and `Truck` that all need `start()`, `stop()`, `honk()` would each need their own copy of that code. Inheritance lets shared code be written once, in a common parent (`Vehicle`), and every child gets it automatically.

### Basic syntax

```java
class Vehicle {
    void start() { System.out.println("Vehicle started"); }
}

class Car extends Vehicle {
    void openTrunk() { System.out.println("Trunk opened"); }
}
```

`Car` now has both `start()` (inherited) and `openTrunk()` (its own).

### Types of inheritance

**Single** — one child, one parent (the `Car extends Vehicle` example above).

**Multilevel** — a chain: grandparent → parent → child.

```java
class Animal {
    void eat() { System.out.println("eats"); }
}
class Dog extends Animal {
    void bark() { System.out.println("barks"); }
}
class Puppy extends Dog {
    void weep() { System.out.println("weeps"); }
}
// Puppy has eat(), bark(), AND weep()
```

**Hierarchical** — one parent, many children.

```java
class Animal {
    void eat() { System.out.println("eats"); }
}
class Dog extends Animal { void bark() { System.out.println("barks"); } }
class Cat extends Animal { void meow() { System.out.println("meows"); } }
```

**Multiple inheritance of classes — not supported in Java**

```java
class C extends A, B { }   // NOT allowed in Java
```

Java does not allow a class to extend two classes at once. If both `A` and `B` had a method `show()`, and `C extends A, B`, Java would have no way to know which parent's `show()` a call to `c.show()` should run. This is called the **Diamond Problem**, and Java avoids it by only allowing a class to extend one other class.

Java still allows multiple inheritance of *behavior*, through interfaces:

```java
interface Flyable { void fly(); }
interface Swimmable { void swim(); }

class Duck implements Flyable, Swimmable {
    public void fly() { System.out.println("Duck flies"); }
    public void swim() { System.out.println("Duck swims"); }
}
```

A class can implement as many interfaces as needed, since interfaces don't carry field data or constructors, so there is no ambiguity.

### Constructors in inheritance

When a child object is created, the parent's constructor runs first, automatically — even without writing anything for it.

```java
class Animal {
    Animal() {
        System.out.println("Animal constructor runs");
    }
}
class Dog extends Animal {
    Dog() {
        System.out.println("Dog constructor runs");
    }
}
public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        // Output:
        // Animal constructor runs
        // Dog constructor runs
    }
}
```

If the parent constructor needs an argument, it must be called explicitly using `super(...)` as the first line of the child constructor:

```java
class Animal {
    String type;
    Animal(String type) {
        this.type = type;
    }
}
class Dog extends Animal {
    String name;
    Dog(String name) {
        super("Mammal");   // must call parent's constructor first
        this.name = name;
    }
}
```

### Full program

```java
class Animal {
    protected String name;

    Animal(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + " is eating");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    void bark() {
        System.out.println(name + " is barking");
    }
}

class Puppy extends Dog {
    Puppy(String name) {
        super(name);
    }

    void weep() {
        System.out.println(name + " is weeping");
    }
}

public class Main {
    public static void main(String[] args) {
        Puppy p = new Puppy("Tommy");
        p.eat();    // from Animal (multilevel up)
        p.bark();   // from Dog
        p.weep();   // its own
    }
}
```

------

## 3. Polymorphism — one name, many behaviors

### Concept

"Poly" (many) + "morph" (forms) — the same method name behaving differently depending on context. Java has two separate mechanisms, and they should not be confused with each other.

### a) Compile-time polymorphism — Method Overloading

Same method **name**, different **parameter list**, in the **same class**. The compiler decides which version to run just by looking at the arguments passed, before the program even runs.

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(2, 3));         // uses int version
        System.out.println(calc.add(2.5, 3.5));      // uses double version
        System.out.println(calc.add(1, 2, 3));       // uses 3-param version
    }
}
```

Overloading requires a change in the *number* or *type* of parameters. Changing only the return type is not enough to overload a method.

### b) Runtime polymorphism — Method Overriding

A child class redefines a method that already exists in its parent, with the exact same signature. Which version actually runs is decided while the program is running, based on the real object — not the type of the variable holding it.

```java
class Shape {
    void draw() {
        System.out.println("Drawing a generic shape");
    }
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a circle");
    }
}

class Square extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a square");
    }
}

public class Main {
    public static void main(String[] args) {
        Shape s;                  // s is declared as type Shape

        s = new Circle();
        s.draw();                 // prints "Drawing a circle"

        s = new Square();
        s.draw();                 // prints "Drawing a square"
    }
}
```

`s` is declared as `Shape`, but Java does not look at the declared type to decide which `draw()` to run — it looks at what the object actually is, at that moment, while running. This is called **dynamic method dispatch**.

This becomes useful in real programs because code can be written once and work for objects it wasn't specifically written for:

```java
public class Main {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(), new Square(), new Circle() };

        for (Shape s : shapes) {
            s.draw();   // correct version runs automatically, for each different object
        }
    }
}
```

A `Triangle` class could be added later, and this exact loop would still work correctly without any change — Java figures out the right `draw()` at runtime.

### Rules of overriding

- Method name, parameter list, and return type must match the parent's (or return a subtype of it)
- The child's access modifier cannot be more restrictive than the parent's
- `@Override` is optional but recommended — it catches typos at compile time instead of silently creating a new unrelated method

### Overloading vs Overriding

|                  | Overloading                                | Overriding                                 |
| ---------------- | ------------------------------------------ | ------------------------------------------ |
| Happens in       | Same class                                 | Parent and child class                     |
| Method signature | Must be different                          | Must be exactly the same                   |
| Decided when     | Compile time                               | Runtime                                    |
| Purpose          | Same action name, different kinds of input | Child customizes/changes parent's behavior |

------

## 4. Abstraction — show what it does, hide how it does it

### Concept

Think about driving a car. Pressing the accelerator pedal makes the car go faster, without needing to know how fuel injection, pistons, and combustion work internally. The pedal hides all that complexity behind a simple interface: press = go faster.

Abstraction means exposing only what's necessary to use something, and hiding the internal complexity of how it's done.

Abstraction and encapsulation are related but different: encapsulation protects data, while abstraction hides implementation complexity and exposes only essential behavior.

### a) Abstract classes

An abstract class cannot be turned into an object on its own — it exists only to be extended. It can mix:

- abstract methods (no body — subclasses must fill them in)
- normal methods (with a body — shared code all children use as-is)

```java
abstract class Shape {
    abstract double area();     // no body — every subclass must provide this

    void display() {            // has a body — inherited as-is
        System.out.println("The area is: " + area());
    }
}

class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double area() {
        return length * width;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape s1 = new Circle(5);
        Shape s2 = new Rectangle(4, 6);

        s1.display();   // "The area is: 78.53..."
        s2.display();   // "The area is: 24.0"

        // Shape s3 = new Shape();  // ERROR — cannot instantiate an abstract class
    }
}
```

`new Shape()` is not allowed because `area()` has no implementation in `Shape` — if a plain `Shape` object could be created and `.area()` called on it, there would be nothing to run. Java blocks this at compile time, forcing every concrete (non-abstract) subclass to fill in the gap.

### b) Interfaces

An interface is a contract: any class that implements it must provide the listed methods. It normally has no implementation at all (Java 8 introduced optional exceptions to this).

```java
interface Payable {
    void pay(double amount);
}

class Employee implements Payable {
    public void pay(double amount) {
        System.out.println("Paid employee: " + amount);
    }
}

class Vendor implements Payable {
    public void pay(double amount) {
        System.out.println("Paid vendor: " + amount);
    }
}

public class Main {
    public static void main(String[] args) {
        Payable p1 = new Employee();
        Payable p2 = new Vendor();
        p1.pay(50000);
        p2.pay(20000);
    }
}
```

`Employee` and `Vendor` are unrelated classes with nothing in common except that both promise to have a `pay()` method. This allows writing generic code such as `processPayment(Payable p)` that works on any class fulfilling the contract, regardless of how different those classes are otherwise.

### Abstract class vs Interface

|                  | Abstract class                                          | Interface                                                    |
| ---------------- | ------------------------------------------------------- | ------------------------------------------------------------ |
| Use when         | Classes are closely related and share common code/state | Unrelated classes need to guarantee the same capability      |
| Fields           | Can have normal instance variables                      | Only constants (`public static final`)                       |
| Methods          | Mix of abstract + implemented                           | Traditionally all abstract (`default` methods allowed since Java 8) |
| A class can have | Only 1 abstract parent (`extends`)                      | Many interfaces (`implements`)                               |
| Constructor      | Yes                                                     | No                                                           |

------

## Putting all four pillars together

```java
abstract class Employee {                    // Abstraction
    private String name;                     // Encapsulation
    private double baseSalary;

    Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }

    abstract double calculateSalary();        // every subclass must define this
}

class Manager extends Employee {              // Inheritance
    private double bonus;

    Manager(String name, double baseSalary, double bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
    }

    @Override
    double calculateSalary() {                 // Polymorphism (overriding)
        return getBaseSalary() + bonus;
    }
}

class Developer extends Employee {
    Developer(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    double calculateSalary() {                 // Polymorphism (overriding)
        return getBaseSalary();
    }
}

public class Main {
    public static void main(String[] args) {
        Employee[] staff = {
            new Manager("Anita", 50000, 10000),
            new Developer("Raj", 40000)
        };

        for (Employee e : staff) {
            System.out.println(e.getName() + " earns: " + e.calculateSalary());
        }
    }
}
```

In this program: `private` fields with getters demonstrate encapsulation, `extends`/`super` demonstrate inheritance, the `abstract` class and method demonstrate abstraction, and `calculateSalary()` producing different results per object demonstrates runtime polymorphism.