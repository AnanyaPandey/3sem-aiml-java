# Object-Oriented Programming (OOP) Concepts in Java

## Topic: Class, Object, Encapsulation, Inheritance, Polymorphism, Abstraction

------

## 1. Why Does OOP Exist?

So far, programs have been a list of instructions with some methods. As programs grow bigger — like a banking system or a college management system — treating everything as just data and separate functions gets messy and hard to manage.

OOP's core idea: **model your program around real-world "things" (objects)** — a Student, a BankAccount, a Car — each bundling its own data and its own behavior together, instead of keeping them separate.

------

## 2. Class and Object — the Foundation

**A class is a blueprint; an object is the actual thing built from that blueprint.**

A blueprint for a house isn't a house you can live in — it's just the plan. You can build many actual houses from one blueprint. Same with a class: `Student` is the blueprint, and `ravi`, `priya` are actual student objects built from it.

```java
class Student {
    String name;
    int age;

    void display() {
        System.out.println(name + " is " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student ravi = new Student();  // creating an object
        ravi.name = "Ravi";
        ravi.age = 20;
        ravi.display();
    }
}
```

`new Student()` is what actually builds a real object from the blueprint. `ravi` is a **reference variable** — objects live in the Heap, and `ravi` holds the reference to it (same idea covered in the data types and memory lesson).

------

## 3. Encapsulation — "Protect Your Data"

**Simple definition:** bundling data (variables) and the methods that work on that data into one unit (a class), and **restricting direct access** to that data from outside.

**Analogy:** think of a capsule (medicine) — the ingredients are sealed inside; you don't touch them directly, you just take the capsule as a whole. Or: an ATM — you don't reach into the bank's database directly; you interact through controlled operations (withdraw, deposit).

### How to do it in Java:

1. Make variables `private` (so no one outside the class can touch them directly).
2. Provide `public` methods (called **getters and setters**) to read or modify them — with your own rules/checks in between.

```java
class BankAccount {
    private double balance; // hidden from outside

    public double getBalance() {   // getter — read access
        return balance;
    }

    public void deposit(double amount) {  // controlled write access
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid amount!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        acc.deposit(1000);
        // acc.balance = 1000;  // ERROR! can't access private variable directly
        System.out.println(acc.getBalance());
    }
}
```

**Why this matters:** without encapsulation, anyone could write `acc.balance = -5000;` directly and break the logic. With encapsulation, the only door in is `deposit()`, and that door has a check (`amount > 0`) guarding it. This is the whole point — **control how your data can be changed.**

------

## 4. Inheritance — "Reuse and Extend"

**Simple definition:** one class (child) can acquire the properties and methods of another class (parent), and add its own on top.

**Analogy:** a child inherits traits from a parent, but also has their own unique traits.

```java
class Animal {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog extends Animal {   // Dog inherits from Animal
    void bark() {
        System.out.println("The dog barks.");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.eat();   // inherited from Animal
        d.bark();  // Dog's own method
    }
}
```

`extends` is the keyword that sets up inheritance. `Dog` gets `eat()` for free, without rewriting it, and adds `bark()` of its own.

------

## 5. Polymorphism — "Same Action, Different Behavior"

**Simple definition:** the word literally means "many forms." The same method name behaves differently depending on the object or the inputs.

### 5.1 Compile-time Polymorphism (Method Overloading)

Same method name, different parameters, decided at compile time.

```java
int add(int a, int b) { return a + b; }
double add(double a, double b) { return a + b; }
```

### 5.2 Runtime Polymorphism (Method Overriding)

A child class provides its **own version** of a method that already exists in the parent class. Java decides which version to run based on the actual object type, at run time.

```java
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a1 = new Dog();
        Animal a2 = new Cat();
        a1.sound(); // Dog barks
        a2.sound(); // Cat meows
    }
}
```

**Key point:** both `a1` and `a2` are declared as type `Animal`, but they behave differently when `sound()` is called — because Java looks at the **actual object** (`Dog` or `Cat`) at runtime, not just the declared type. This is why it's called *runtime* polymorphism.

**Analogy:** think of a "draw shape" command. If you say "draw" to a circle, it draws a circle. If you say "draw" to a square, it draws a square. Same command, different result — because the object itself decides how to respond.

------

## 6. Abstraction — "Show Essentials, Hide Complexity"

**Simple definition:** showing only the essential features, and hiding the internal complex details.

**Analogy:** when you drive a car, you use the steering wheel and pedals — you don't need to know how the engine's internal combustion works. The complexity is hidden; only what you need is exposed.

In Java, this is done using **abstract classes** or **interfaces** — the idea is: expose what's necessary, hide the rest. (Exact syntax for these is covered in more detail once inheritance is solid.)

------

## 7. The Four Pillars Together

| Pillar        | Simple meaning                   | Real-world analogy                            |
| ------------- | -------------------------------- | --------------------------------------------- |
| Encapsulation | protect data, controlled access  | capsule / ATM                                 |
| Inheritance   | reuse and extend                 | child inherits parent's traits                |
| Polymorphism  | same action, different behavior  | "draw" works differently for circle vs square |
| Abstraction   | show essentials, hide complexity | driving a car without knowing the engine      |

A class groups data and behavior together (the foundation). Encapsulation protects that data. Inheritance lets classes share and extend behavior. Polymorphism lets the same method behave differently. Abstraction hides what you don't need to see.

------

## 8. Key Points to Remember

- A class is a blueprint; an object is an actual instance created from it using `new`.
- Encapsulation: keep variables `private`, expose controlled access through `public` getter/setter methods.
- Inheritance: use `extends` so a child class reuses and builds on a parent class's features.
- Method overloading = same name, different parameters, decided at compile time.
- Method overriding = child class redefines a parent method; Java picks the version based on the actual object at runtime.
- `@Override` is used above an overridden method as good practice (helps catch mistakes).
- Abstraction hides internal complexity and shows only what is necessary, typically through abstract classes or interfaces.

------

## 9. Practice Questions

1. Create a `Person` class with `private` fields `name` and `age`, and public getter/setter methods. Try to access the fields directly from `main` and observe the error.
2. Create a `Vehicle` class with a method `move()`, and a `Car` class that extends it with an additional method `honk()`. Create a `Car` object and call both methods.
3. Write two overloaded methods `area()` — one for a circle (radius) and one for a rectangle (length, width).
4. Create a parent class `Shape` with a method `draw()`, and two child classes `Circle` and `Square` that override `draw()` differently. Create objects of type `Shape` pointing to `Circle` and `Square`, and call `draw()` on each to observe runtime polymorphism.
5. Create a `Employee` class with `private` salary field. Add a method `giveRaise(double amount)` that only allows the raise if `amount > 0`. Try giving a negative raise and observe the check in action.