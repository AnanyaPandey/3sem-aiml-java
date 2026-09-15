# Java: Abstraction, Abstract Classes, Interfaces, Concrete Class & Singleton Class and Static

# 1. What is Abstraction? (Real-life explanation first)

Think about driving a car. When you want to go faster, you press the accelerator pedal. You don't need to know:

- How fuel gets injected into the engine
- How the pistons move
- How the spark plugs ignite

You just press a pedal, and the car speeds up. The **complicated stuff is hidden from you**. You only see and use the **simple part** (the pedal).

**Abstraction = hiding the complicated details, and showing only the simple part that matters to the user.**

### Why does programming need this?

When you write big programs, other parts of your code (or other programmers) need to *use* your class, but they shouldn't need to know *how* it works internally — they just need to know *what* it can do.

So abstraction in Java is a way of saying:

> "This class/method exists, and here's its name — but I won't show you (or force you to worry about) the internal working details."

### What exactly gets "hidden"? (Slowing down on this, since it's the part that confuses people most)

Let's look at this from **two different people's perspectives**: the person who WRITES a class, and the person who USES that class somewhere else.

```java
abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    double area() {
        return 3.14 * radius * radius;   // <-- the actual FORMULA, the "complexity"
    }
}
Shape c = new Circle(5);
System.out.println(c.area());
```

**Here's where the hiding actually happens.** The person calling `c.area()` gets the correct answer, `78.5`. But they never had to see, write, or even know that the formula involves `3.14 * radius * radius`. That specific formula — the "complexity" — lives completely inside `Circle`, tucked away. The caller only knows: "this is a Shape, and Shapes have an `area()` method that gives me a number."

**The hiding isn't about you, the programmer building the classes — it's about whoever USES your classes later.** If someone else on your team, or future-you six months from now, just wants to use `Circle` and `Rectangle` objects, they interact with `.area()` and get correct results, without ever having to read or understand the math/logic inside each one. Exactly like the car pedal — you press it, the car moves, you don't need to know about fuel injection.

**A second layer of hiding:** different shapes calculate area **completely differently internally** — a Circle uses `radius`, a Rectangle uses `length * width`, totally different formulas — but from the outside, calling `.area()` looks and feels *identical* no matter which one you're using. That inconsistency is hidden behind one uniform, simple method name.

------

## 2. Building Up to Abstract Classes: The Problem They Solve

Let's build this up slowly with a non-metaphor, practical example, to see WHY abstract classes exist, not just what they are.

Say you're designing a program for an office where different employees calculate their salary differently — a manager, a developer, an intern — but your main program just wants to say "get me this employee's salary," without caring who they specifically are.

### Step 1: Without any structure — the problem

```java
class Manager {
    double calculateManagerSalary() { return 50000; }
}

class Developer {
    double calculateDeveloperSalary() { return 30000; }
}
```

Problem: every class has a **different method name**. Your main program has to know exactly who it's dealing with every single time. Messy, and doesn't scale as you add more employee types.

### Step 2: The fix — make a RULE that everyone must follow

We say: "Every type of Employee MUST have a method called `getSalary()`." We don't say HOW to calculate it — we just make it **mandatory** that the method exists. This rule can be written as an abstract class:

```java
abstract class Employee {
    abstract double getSalary();   // no code, no body — just a rule: "must have this method"
}
```

This is NOT a fully working class on its own. It's a **rulebook with some structure** — it says: "anyone who extends `Employee` MUST write their own `getSalary()` method."

### Step 3: Real classes follow that rule

```java
class Manager extends Employee {
    @Override
    double getSalary() {
        return 50000;     // Manager's own way of calculating
    }
}

class Developer extends Employee {
    @Override
    double getSalary() {
        return 30000;     // Developer's own way of calculating
    }
}
```

**Java literally will not let you compile `Manager` unless it writes a `getSalary()` method.** This is the core power of abstraction — forcing structure while allowing customization.

### Step 4: The payoff — using them uniformly

```java
Employee e1 = new Manager();
Employee e2 = new Developer();

System.out.println(e1.getSalary()); // 50000
System.out.println(e2.getSalary()); // 30000
```

Your main program just calls `.getSalary()` on anyone. It doesn't care if it's a Manager or Developer, or how they calculate it internally. It only knows: "this is an Employee, and Employees have a `getSalary()` method." **That's abstraction in action — hiding "how", exposing only "what".**

------

## 3. Abstract Classes — Full Definition and Rules

### Definition

An **abstract class** is a class that is deliberately left incomplete. It can contain:

- **Real, fully-implemented methods** — ready to use as-is, shared automatically by every child
- **Abstract methods** — method signatures with no body at all, which FORCE any child class to implement them, or that child itself must also be declared abstract

**Key restriction:** you cannot create an object directly from an abstract class using `new`. It exists purely to be extended — it's an intentionally unfinished blueprint.

### Full Working Example — Shape

```java
abstract class Shape {
    // REAL method - fully implemented, shared by every child automatically, no need to rewrite it
    void displayInfo() {
        System.out.println("This is a shape.");
    }

    // ABSTRACT method - no body, every child MUST implement this
    abstract double area();
}

class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    double area() {
        return 3.14 * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
Shape c = new Circle(5);
Shape r = new Rectangle(4, 6);

// Shape s = new Shape();  ❌ ERROR - cannot instantiate an abstract class directly

c.displayInfo();
System.out.println("Circle area: " + c.area());

r.displayInfo();
System.out.println("Rectangle area: " + r.area());
```

**Output:**

```
This is a shape.
Circle area: 78.5
This is a shape.
Rectangle area: 24.0
```

### Exactly HOW Java Forces the Child to Implement the Method

This is worth slowing down on specifically, since "forced" can sound vague. Try this:

```java
class Triangle extends Shape {
    double base, height;
    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    // forgot to write area() here!
}
// ❌ COMPILE ERROR:
// "Triangle is not abstract and does not override abstract method area() in Shape"
```

**What's actually happening:** Java's rule is — if a class extends an abstract class, it must either (a) implement EVERY abstract method inherited from the parent, or (b) also be declared `abstract` itself (passing the obligation further down the chain). Since `Triangle` did neither, Java refuses to compile it at all. This is not a warning, not a runtime crash later — it's a hard stop, right at compile time, before the program even runs. That's the literal mechanism behind "forced."

### Why Use an Abstract Class? (Three Concrete Reasons)

1. **Forces a contract** — every subclass of `Shape` is guaranteed to have an `area()` method; Java won't compile a subclass that forgets it. This prevents accidental bugs where a developer forgets to implement something important.
2. **Shares common code** — `displayInfo()` is written once, in the parent, and every child gets it for free without rewriting it. Saves duplication.
3. **Hides implementation, exposes behavior** — code using a `Shape` reference doesn't need to know if it's a Circle or Rectangle underneath, just that it has an `area()`. This is abstraction working hand-in-hand with polymorphism.

------

## 4. Interfaces — Full Definition and Rules

### Real-life analogy first

Think of a job application form. It says "You must provide: Name, Phone Number, Email." It doesn't care HOW you got that phone number — it just demands the fields exist, filled in, before accepting the application.

**An interface is exactly this — a strict checklist/contract listing method names that MUST exist, with ZERO actual code inside it at all.** This is even stricter than an abstract class, which is still allowed to have some real, working methods.

### Syntax

```java
interface Shape {
    double area();   // no { }, no body - just a name, parameters, return type, then a semicolon
}
```

That trailing semicolon (`;`) instead of curly braces `{ }` is the giveaway — there is genuinely no code here at all, not even a placeholder.

### A Class Uses `implements`, Not `extends`

```java
class Circle implements Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    public double area() {          // MUST write this - Java forces it
        return 3.14 * radius * radius;
    }
}
Shape c = new Circle(5);
System.out.println(c.area());   // Output: 78.5
```

If `Circle` forgets to write `area()`, Java refuses to compile — exactly the same forcing mechanism as abstract classes: *"Circle is not abstract and does not override abstract method area() in Shape."*

### The Big Advantage: Implementing MULTIPLE Interfaces at Once

This is something abstract classes genuinely CANNOT do — a class can only `extends` ONE other class, ever, but can `implements` as MANY interfaces as it wants.

```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {   // TWO interfaces at once - totally fine
    public void fly() {
        System.out.println("Duck flies");
    }
    public void swim() {
        System.out.println("Duck swims");
    }
}
Duck d = new Duck();
d.fly();
d.swim();
```

**Output:**

```
Duck flies
Duck swims
```

A `Duck` gains two completely unrelated "abilities" at once. `class Duck extends Flyable, Swimmable` would be an immediate compile error if these were regular/abstract classes — Java simply does not allow extending two classes (this is the Diamond Problem avoidance we covered under Inheritance). Interfaces sidestep this entirely because there's no actual code inside them to create a conflict — the implementing class always writes its own single, final version of every method.

------

## 5. The `@Override` Annotation — Used With Both Abstract Classes and Interfaces

### What it actually is

An **annotation** — a special marker placed directly above a method, starting with `@`. It does nothing by itself when the program runs. Its entire job is to tell the **compiler**: "I intend for this method to override/implement something from a parent class or interface. Please double-check that for me."

### What it checks for you

The compiler looks at the method below `@Override` and verifies: does this method's name + parameters actually match something in the parent class or interface? If yes, compiles fine. If no, the compiler stops you immediately, before you ever run the program.

### Why this matters — a typo, traced through both scenarios

```java
abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    double Area() {            // TYPO: capital "A" instead of lowercase "a"
        return 3.14 * radius * radius;
    }
}
```

**With `@Override` present:** Java immediately says *"Error: method does not override or implement a method from a supertype"* — pointing straight at the mistake, right away, with a clear message.

**Without `@Override`:** Java would think you're just writing a brand new, extra method called `Area()` (capital A), completely separate from the real, still-unimplemented `area()` from `Shape`. Since `Circle` never actually implemented the *real* `area()`, this would still fail to compile — but with a much more confusing, generic error message. And in some interface situations with default methods, a typo like this could even **silently compile and run incorrectly**, creating a bug that's very hard to track down.

**Best practice: always write `@Override` whenever overriding or implementing a method.** There's no real downside — only added safety and clarity for anyone reading your code.

------

## 6. Abstract Class vs Interface — Full Comparison

|                                            | Abstract Class                                               | Interface                                                    |
| ------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Keyword to use it                          | `extends`                                                    | `implements`                                                 |
| Can it have real, working methods?         | Yes — some methods can have full code                        | Traditionally, no — all methods are empty (modern Java allows some exceptions, but treat as "no" for beginner level) |
| Can it have normal fields/variables?       | Yes, normal instance fields                                  | Only `public static final` constants (rare, advanced case)   |
| Can a class inherit from more than one?    | No — only ONE, via `extends`                                 | **Yes** — a class can `implements` several interfaces at once |
| Can you create an object from it directly? | No                                                           | No                                                           |
| Constructors allowed?                      | Yes                                                          | No                                                           |
| Best used when...                          | Related classes share SOME common ready-made code, plus some things each must individually define | You just want to force a "must have this ability" rule, especially across unrelated classes, or need multiple different abilities at once |

### Similarities Between Them (Worth Noting Explicitly)

- Neither can be instantiated directly with `new` — both are meant to be extended/implemented, not used standalone
- Both can contain method signatures with no body, that force child/implementing classes to provide the actual code — this is the "forcing" mechanism discussed above
- Both are core tools for achieving **abstraction** — hiding implementation, exposing only behavior
- Both use `@Override` on the methods that fulfill the contract, and Java checks compliance the same way — at compile time
- Both allow you to write code against the general type (`Shape s = ...`) and have it work correctly no matter which specific subclass/implementation is actually used — this is **polymorphism** working through abstraction

### When to Choose Which

- Choose **abstract class** when your related classes share meaningful, reusable code, and there's a natural "IS-A" hierarchy (a Circle IS-A Shape) — you want to avoid duplicating shared logic across every child.
- Choose **interface** when you just need to guarantee certain behavior exists, especially across classes that aren't naturally related, or when a class needs multiple different "abilities" at once (like `Duck` needing both `Flyable` and `Swimmable`).

------

## 7. Concrete Class

### The Short Answer

There is no special syntax or separate rule for "making" a concrete class — **every class you write is a concrete class by default**, unless you deliberately mark it `abstract` or write it as an `interface`. The term "concrete class" exists purely to **contrast** against abstract classes and interfaces, both of which are deliberately incomplete on purpose.

### The One Real Rule That Defines "Concrete"

A class is concrete if:

1. It is **not** marked `abstract`
2. **Every** method in it has actual, working code — nothing left unimplemented
3. Because of both of the above, you are allowed to do `new ClassName()` directly, with no restrictions

### Side-by-Side Example

```java
class Circle {                    // CONCRETE class - fully complete
    double radius;
    Circle(double radius) { this.radius = radius; }

    double area() {
        return 3.14 * radius * radius;   // fully implemented, nothing left unfinished
    }
}
Circle c = new Circle(5);   // ✅ works fine - Circle is concrete, complete, instantiable
```

Compare directly with the abstract version from Section 3:

```java
// Shape s = new Shape();  ❌ ERROR - Shape is abstract, cannot instantiate directly
```

**Every single class we've written throughout this entire course — `Dog`, `Animal`, `Car`, `House`, `Trainer`, the `implements`-based `Circle`, `Student`, `Employee`'s children — has been a concrete class the whole time.** We just never had a reason to use that specific word for them until we contrasted them against abstract classes and interfaces.

### Quick Comparison Table

| Term               | Meaning                                                      |
| ------------------ | ------------------------------------------------------------ |
| **Concrete class** | A normal class — 100% complete, no missing pieces, objects can be created directly |
| **Abstract class** | A partly-built class — some pieces deliberately left unfinished, cannot create objects directly |
| **Interface**      | A pure checklist — zero implementation at all, cannot create objects directly |

**One-line definition: every class is "concrete" unless you deliberately make it incomplete by marking it `abstract` or writing it as an `interface`. There's no special keyword needed to "make" something concrete — it's simply the default state of a normal class.**

------

## 8. Static — Instance Members vs Static Members

### The Core Idea: Two Different Worlds

Everything you normally do — `p1.eating()`, `p1.name` — belongs to **instance members**: things that belong to **one specific object**. `static` introduces a second, different world: **static members** — things that belong to the **class itself**, not to any individual object.

### Full Example — Both Side by Side

```java
class Person {
    String name;          // INSTANCE variable - each object gets its own copy
    int age;               // INSTANCE variable - each object gets its own copy

    static int population; // STATIC variable - only ONE copy, shared by ALL objects, belongs to the CLASS itself

    Person(String name, int age) {
        this.name = name;
        this.age = age;
        population++;      // every time a Person is created, this ONE shared number goes up
    }

    void eating() {         // INSTANCE method - needs an object to run on
        System.out.println(name + " is eating");
    }

    static void showPopulation() {   // STATIC method - belongs to the class, doesn't need an object
        System.out.println("Total people created: " + population);
    }
}
```

### Tracing Through Usage

```java
Person p1 = new Person("Rex", 25);
Person p2 = new Person("Max", 30);

p1.eating();     // Output: Rex is eating       <- instance method, called ON an object
p2.eating();     // Output: Max is eating       <- instance method, called ON a different object

System.out.println(p1.name);    // Output: Rex   <- p1's OWN copy of name
System.out.println(p2.name);    // Output: Max   <- p2's OWN copy of name (completely separate!)
```

So far, each object has its own separate copies of `name` and `age` — `p1.name` and `p2.name` are two different pieces of memory, holding two different values.

**Now the static part:**

```java
Person.showPopulation();    // Output: Total people created: 2
```

Notice: `Person.showPopulation()`, not `p1.showPopulation()`. We call this directly on the **class name**, because `population` and `showPopulation()` don't belong to any single object — they belong to the class as a whole. There's only **ONE** `population` variable in existence, no matter how many Person objects you create — every object shares that same single number.

### Proving `population` Is Truly Shared (Not Separate Per Object)

```java
System.out.println(p1.population);   // Output: 2
System.out.println(p2.population);   // Output: 2  <- SAME value, even through it's a different object!
```

Even accessed through `p1` and `p2` separately, both show `2` — because there's only ONE `population` variable total, shared by everyone. (Java technically *allows* writing `p1.population`, but this is misleading and bad style — always write `Person.population` instead, to make clear it's shared, not personal to `p1`.)

### The Core Rule

A **plain, normal field** (no `static`) does **NOT** belong to the class — it only exists once an object is created, and every object gets its own separate copy. You **cannot** write `Person.name` for a non-static field — Java gives an error, because `name` doesn't exist on the class itself, only on individual objects.

```java
// System.out.println(Person.name);   ❌ ERROR - name is NOT static, doesn't belong to the class
System.out.println(Person.population);   // ✅ works - population IS static, belongs to the class
```

### Side-by-Side Summary

|                           | Instance member (normal)             | Static member                                                |
| ------------------------- | ------------------------------------ | ------------------------------------------------------------ |
| Belongs to                | Each individual object               | The class itself                                             |
| How many copies exist?    | One copy PER object                  | Only ONE copy total, shared by everyone                      |
| How to access             | `objectName.member` (e.g. `p1.name`) | `ClassName.member` (e.g. `Person.population`)                |
| Needs an object to exist? | Yes — must do `new` first            | No — exists as soon as the class is loaded, even with zero objects created |

### Why This Matters for Singleton (Coming Up Next)

This is exactly why `getInstance()` in Singleton is `static`, and why the variable holding the one instance is `static` too — the whole POINT of Singleton is "this one thing should belong to the class itself, not to any individual object," which is precisely what `static` means. `President.getInstance()` works because `getInstance()` doesn't need any object to already exist to be called — that's actually the whole trick that lets it create the first object in the first place.

**One-line summary: normal (non-static) variables/methods belong to each object separately and are accessed via `objectName.thing`; `static` variables/methods belong to the class itself, shared by everyone, and are accessed via `ClassName.thing`.**

------

## 9. Singleton Class

### Real-life analogy first

Think about the President of a country. At any given moment, there is exactly **ONE** President — never zero, never two. No matter how many government departments need to talk to "the President," they're all talking to the **same single person**. You can't accidentally create a second President just because a new department needs one.

**A Singleton class is designed so that only ONE object (instance) of it can ever exist in the entire program**, no matter how many times or from how many places you try to create one.

### Why Would You Ever Want This?

- **Database connection manager** — you don't want 10 different parts of your program opening 10 separate connections to the same database; you want everyone sharing ONE connection.
- **App configuration/settings** — settings should be identical everywhere; multiple copies could cause different parts of the program to disagree on settings.
- **Logging system** — every part of the app should write to the SAME log file/object, not create separate loggers that step on each other.

### Building It Up, Piece by Piece

**Step 1: Make the constructor `private`**, so nobody outside can just type `new President()`.

```java
class President {
    private President() {
        System.out.println("A President has been created");
    }
}
// President p = new President();  ❌ ERROR - constructor is private, cannot access it from outside
```

Nobody outside `President` can create an object directly anymore — the door is locked.

**Step 2: But then... how do we EVER create one? The class creates it FOR itself, and keeps it.**

```java
class President {
    private static President onlyPresident;   // will hold the ONE existing object

    private President() {
        System.out.println("A President has been created");
    }

    public static President getInstance() {
        if (onlyPresident == null) {           // if no President exists yet...
            onlyPresident = new President();   // ...create the ONE and ONLY one
        }
        return onlyPresident;                  // give back the same object every time
    }
}
```

### Tracing Through This Very Carefully

```java
President p1 = President.getInstance();
President p2 = President.getInstance();
```

**First call — `President.getInstance()`:**

1. Checks: `onlyPresident == null`? Yes — nobody has been created yet.
2. So it runs `onlyPresident = new President();` — THIS is the only time `new President()` ever runs.
3. Prints: `A President has been created`
4. Returns this newly-created object, stored into `p1`.

**Second call — `President.getInstance()`:**

1. Checks: `onlyPresident == null`? **No** — it already holds a real object from before.
2. So it **skips** the `new President()` line entirely.
3. Just returns the **same** existing object, stored into `p2`.

**Output:**

```
A President has been created
```

(Only printed ONCE — even though we called `getInstance()` twice!)

### Proving `p1` and `p2` Are the SAME Object

```java
System.out.println(p1 == p2);   // Output: true
```

`==` here checks if two variables point to the **exact same object in memory** — and it does! No matter how many times you call `getInstance()`, from anywhere in your program, you always get back the same single object.

### The Three Required Ingredients

```java
class President {
    private static President onlyPresident;   // 1. a private static variable to HOLD the one instance

    private President() { }                    // 2. a private constructor - blocks outside creation

    public static President getInstance() {    // 3. a public static method - the ONLY way in
        if (onlyPresident == null) {
            onlyPresident = new President();
        }
        return onlyPresident;
    }
}
```

| Ingredient                               | Why it's needed                                              |
| ---------------------------------------- | ------------------------------------------------------------ |
| `private static President onlyPresident` | Needs to be `static` so it belongs to the CLASS itself (one shared slot), not to any individual object — since we're not allowed to have multiple objects in the first place |
| `private` constructor                    | Physically blocks anyone outside from typing `new President()` directly |
| `public static getInstance()`            | The only "front door" left open — controls creation, checks if one already exists before making a new one |

### A More Realistic Example: App Settings

```java
class AppSettings {
    private static AppSettings instance;
    private String theme;

    private AppSettings() {
        theme = "Light Mode";     // some default setting
    }

    public static AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
        }
        return instance;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }
}
AppSettings settings1 = AppSettings.getInstance();
settings1.setTheme("Dark Mode");

AppSettings settings2 = AppSettings.getInstance();   // getting the SAME instance
System.out.println(settings2.getTheme());            // reads the change made through settings1!
```

**Output:**

```
Dark Mode
```

**Why this proves the point:** `settings1` changed the theme to "Dark Mode." Even though `settings2` was fetched **separately**, through a completely different call to `getInstance()`, it sees "Dark Mode" too — because `settings1` and `settings2` are literally the same underlying object. If these were two separate, normal objects, `settings2` would still show "Light Mode" (the default), completely unaware of what `settings1` did.

### Where `static` Fits Into Singleton

The entire Singleton pattern only works because of `static`:

- `onlyPresident` is `static` — one shared slot belonging to the class, not duplicated per object.
- `getInstance()` is `static` — can be called using just `President.getInstance()`, without ever needing an object to already exist. This is the whole trick — it's what lets `getInstance()` create the very first object in the first place, since normally you'd need an object to call a method, but here the method itself is what produces the object.

------

## 10. Quick Summary — All Concepts Together

| Concept             | Can create objects directly?                   | Purpose                                                      |
| ------------------- | ---------------------------------------------- | ------------------------------------------------------------ |
| **Concrete class**  | Yes                                            | A normal, fully complete class                               |
| **Abstract class**  | No                                             | Partial blueprint — some shared code, some forced-to-implement methods |
| **Interface**       | No                                             | Pure contract — zero code, just forces required methods; supports multiple at once |
| **Singleton class** | Only ONE, ever, controlled by the class itself | Guarantees exactly one shared object exists across the whole program |

### One-Line Definitions to Remember

- **Abstraction** — hiding the complicated "how," exposing only the simple "what."
- **Abstract class** — a partly-finished class blueprint; some ready-made code, some methods children must fill in themselves; a class can extend only ONE.
- **Interface** — a pure checklist with zero code, forcing implementing classes to provide specific methods; a class can implement MANY at once.
- **Concrete class** — the default state of any normal class; fully complete, objects can be created directly.
- **Singleton** — a class engineered so only one object of it can ever exist, using a private constructor plus a static access method.