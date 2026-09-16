# Polymorphism  Method Overloading, Method Overriding & Super Reference

------

## 1. The `super` Keyword

`super` is how a child class talks to its parent class. It has **two different jobs**.

### 1a. Job One: Calling the Parent's Constructor

When you create a child object, Java needs to first set up the "parent part" of the object before it sets up the "child part." `super(...)` is how you trigger the parent's constructor manually.

```java
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor called — name set to " + name);
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name); // This line calls Animal's constructor and passes "name" to it
        System.out.println("Dog constructor called");
    }
}
Dog d = new Dog("Rex");
```

**Output:**

```
Animal constructor called — name set to Rex
Dog constructor called
```

**Why this order?** Because `super(name)` is the very first line inside Dog's constructor. It pauses Dog's constructor, runs Animal's constructor completely first, and only THEN continues with the rest of Dog's constructor.

**Important rule:** If you don't write `super(...)` yourself, Java automatically tries to call the parent's constructor that takes **no arguments**. If the parent doesn't have one, you'll get a compile error, forcing you to add `super(...)` yourself.

### 1b. Job Two: Calling the Parent's Method (When Overridden)

Sometimes the child rewrites a method but still wants to also run the parent's original version too, not replace it completely.

```java
class Animal {
    void eat() {
        System.out.println("Animal eats food");
    }
}

class Dog extends Animal {
    @Override
    void eat() {
        super.eat(); // first run Animal's original eat() logic
        System.out.println("Dog also eats dog food specifically");
    }
}
new Dog().eat();
```

**Output:**

```
Animal eats food
Dog also eats dog food specifically
```

Without `super.eat()`, only Dog's own message would print. With it, both run — parent's version first, then child's extra part.

### Example: Passing Extra Hardcoded Values Through `super`

A subtle but important pattern — a child constructor can take FEWER parameters than the parent, and fill in the rest itself:

```java
class Animal {
    String name;
    String species;

    Animal(String name, String species) {   // expects TWO parameters
        this.name = name;
        this.species = species;
    }
}

class Dog extends Animal {
    Dog(String name) {                       // Dog itself only takes ONE parameter
        super(name, "Dog");                  // but sends TWO to Animal - "Dog" is hardcoded here
    }
}
Dog d = new Dog("Rex");
```

**What's happening:** the caller of `new Dog("Rex")` only provides `name`. But Dog's own constructor is *designed* to always add `"Dog"` as the species, without the outside caller ever needing to supply it. `name` (received from outside) and `"Dog"` (hardcoded inside) are two separate things, combined together in the `super(...)` call.

------

## 2. Method Overloading

### Real-life analogy first

Think about your phone's calculator. The "+" button can add two numbers, two decimals, or more — same button, same symbol, but it works correctly no matter what you give it.

**Method Overloading = having multiple methods with the SAME name, but each accepting DIFFERENT parameters** (different number of parameters, or different types). Java automatically figures out which version to run, based on what you pass in.

### Why We Need It

Without overloading:

```java
int addTwoInts(int a, int b) { return a + b; }
double addTwoDoubles(double a, double b) { return a + b; }
int addThreeInts(int a, int b, int c) { return a + b + c; }
```

This is annoying — three different names to remember for the same basic idea. Overloading lets you use ONE name for all of them.

### Full Example

```java
class Calculator {
    int add(int a, int b) {
        System.out.println("Two int version called");
        return a + b;
    }

    double add(double a, double b) {
        System.out.println("Two double version called");
        return a + b;
    }

    int add(int a, int b, int c) {
        System.out.println("Three int version called");
        return a + b + c;
    }
}
Calculator calc = new Calculator();

System.out.println(calc.add(2, 3));         // matches Version 1
System.out.println(calc.add(2.5, 3.5));     // matches Version 2
System.out.println(calc.add(1, 2, 3));      // matches Version 3
```

**Output:**

```
Two int version called
5
Two double version called
6.0
Three int version called
6
```

**What's happening:** at the moment you write `calc.add(2, 3)`, Java looks at what you passed — two whole numbers — and matches it to the version whose parameters are `(int a, int b)`. This decision happens **at compile time**, before the program even runs — which is why overloading is also called **compile-time polymorphism**.

### The Rule: What Actually Makes Something "Overloaded"

Two methods with the same name are only "overloaded" if they differ in at least ONE of these:

1. **Number of parameters**

```java
void show(int a) { }
void show(int a, int b) { }   // different count — valid overload
```

1. **Type of parameters**

```java
void show(int a) { }
void show(double a) { }   // different type — valid overload
```

1. **Order of parameter types**

```java
void show(int a, String b) { }
void show(String a, int b) { }   // different order — valid overload
```

### What Does NOT Count — A Common Mistake

```java
int show(int a) { return a; }
double show(int a) { return a; }
// ❌ COMPILE ERROR!
```

Changing only the **return type** is NOT enough. Java looks at the method's name + parameter list (called the "signature") to tell methods apart — it completely ignores return type. Since both versions here have identical parameters `(int a)`, Java sees them as the same method declared twice.

------

## 3. Method Overriding

### Definition

When a child class writes its own version of a method that already exists in the parent — using the exact same method name and exact same parameters — this is called **overriding**. The child's version replaces the parent's version when called on a child object.

```java
class Animal {
    void sound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Woof Woof");
    }
}
Animal a = new Dog();
a.sound();
```

**Output:**

```
Woof Woof
```

**This is worth pausing on.** The variable `a` is declared as type `Animal`. You might expect it to run Animal's `sound()`. But it doesn't — it runs **Dog's** version, because that's the *actual* object stored inside `a` (we did `new Dog()`, not `new Animal()`). Java always looks at the real object type at runtime, not the variable's declared type. This is called **runtime polymorphism**, and it's only possible *because of* inheritance.

### Rules for Overriding

- Method name must match exactly
- Parameters (number, types, order) must match exactly
- Return type must match (or be a valid subtype)
- `@Override` isn't strictly required, but should always be used — catches typos immediately as compile errors instead of silent bugs
- You cannot override a method the parent marked `final`

------

## 4. Overloading vs Overriding — Don't Mix These Up

|                 | Overloading                                   | Overriding                                                  |
| --------------- | --------------------------------------------- | ----------------------------------------------------------- |
| Happens between | Multiple methods in the **same class**        | Parent class and Child class (**requires inheritance**)     |
| Method name     | Same                                          | Same                                                        |
| Parameters      | **Must be different**                         | **Must be exactly the same**                                |
| Purpose         | Same action name, different ways to call it   | Child class replaces/customizes parent's behavior           |
| Decided when    | Compile time (before running)                 | Runtime (while actually running, based on real object type) |
| Return type     | Can differ, as long as parameters also differ | Must match (or be a valid subtype)                          |

**One-line way to remember:** Overloading = **same class, different parameters**. Overriding = **different classes (parent/child), same everything, different body**.

------

## 5. Polymorphism

### Real-life analogy first

Think about the word "cut." A tailor cuts fabric. A surgeon cuts skin. A gardener cuts branches. Same word, completely different actions, depending on who's doing it.

**Polymorphism means "many forms"** — the same method name behaves differently depending on which object is actually calling it.

### The Two Types of Polymorphism in Java

Polymorphism is the umbrella term — it's not just overriding. It has two forms, both of which you've already learned individually:

**Type 1: Compile-time Polymorphism = Method Overloading** (Section 2 above) — same method name, different parameters, decided before the program runs.

**Type 2: Runtime Polymorphism = Method Overriding** (Section 3 above) — same method name, same parameters, but the child gives it different behavior, and Java decides which version to run WHILE the program is executing, based on the real object.

**Why people often say "polymorphism = overriding" only:** runtime polymorphism is the more powerful, more commonly discussed form — it's the one that enables flexible, reusable code (shown below). But strictly speaking, overloading counts as polymorphism too.

### Runtime Polymorphism — Full Example

```java
class Animal {
    void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Woof Woof");
    }
}

class Cat extends Animal {
    @Override
    void makeSound() {
        System.out.println("Meow");
    }
}
Animal a1 = new Dog();
Animal a2 = new Cat();

a1.makeSound();   // Output: Woof Woof
a2.makeSound();   // Output: Meow
```

**Step by step:** both `a1` and `a2` are declared as type `Animal`, but the actual objects stored inside are `Dog` and `Cat`. When you call `.makeSound()`, Java looks at the real object, not the declared type, and runs the correct version.

### Where This Becomes Really Useful — Arrays/Lists of Mixed Objects

```java
Animal[] animals = { new Dog(), new Cat(), new Animal() };

for (Animal a : animals) {
    a.makeSound();
}
```

**Output:**

```
Woof Woof
Meow
Some generic animal sound
```

One loop, one line (`a.makeSound()`), correctly handling three completely different behaviors automatically — no `if (animal is Dog) { ... } else if ...` needed anywhere.

------

## 6. Passing Objects to Methods

### Real-life analogy first

Imagine handing your car keys to a valet. You're not handing over a copy of your car — you're handing over the keys to the exact same car. Whatever the valet does happens to your actual car, not a separate copy.

In Java, passing an **object** into a method works the same way — you're passing a **reference** to the exact same object, not a brand new copy.

### Basic Example

```java
class Dog {
    String name;
    Dog(String name) { this.name = name; }
}

class Trainer {
    void train(Dog d) {                        // Dog object RECEIVED here
        System.out.println("Training " + d.name);
        d.name = d.name + " (trained)";         // modifying the SAME object
    }
}
Dog myDog = new Dog("Rex");
Trainer t = new Trainer();

t.train(myDog);                    // passing the Dog object HERE

System.out.println(myDog.name);    // myDog itself changed!
```

**Output:**

```
Training Rex
Rex (trained)
```

**The important insight:** `myDog` changed even though we never wrote `myDog = ...` after calling `train()`. That's because `d` inside `train()` and `myDog` outside both point to the exact same object in memory — like two people holding keys to the same car.

### Accessing an Object's Fields Inside a Method — Confirming the Pattern

When an object is passed into a method as a parameter, that method can access the object's fields and call its methods using `parameterName.fieldName` or `parameterName.methodName()` — exactly like `d.name` above.

**This only works if the field is visible** based on access modifier rules:

```java
class Dog {
    public String name;      // public - Trainer CAN access this
    private int secretCode;  // private - Trainer CANNOT access this
}

class Trainer {
    void train(Dog d) {
        System.out.println(d.name);          // ✅ works - public
        // System.out.println(d.secretCode); // ❌ ERROR - private, Trainer has no access
    }
}
```

**Rule:** to call an instance method like `train()`, you must first create a Trainer object — `train()` isn't static, so it belongs to a specific object, not the class itself.

```java
Trainer t = new Trainer();     // must create the object first
Dog myDog = new Dog();
t.train(myDog);                // then call the method ON it, passing the Dog in
```

------

## 7. The Best Combo: Polymorphism + Passing Objects Together

This is where everything comes together. Instead of writing separate methods for every possible subclass, you write ONE method that accepts the **parent type**, and it automatically works correctly no matter which child object is actually passed in.

```java
class Animal {
    String name;
    Animal(String name) { this.name = name; }
    void makeSound() {
        System.out.println(name + " makes a generic sound");
    }
}

class Dog extends Animal {
    Dog(String name) { super(name); }
    @Override
    void makeSound() {
        System.out.println(name + " says Woof!");
    }
}

class Cat extends Animal {
    Cat(String name) { super(name); }
    @Override
    void makeSound() {
        System.out.println(name + " says Meow!");
    }
}

class Zookeeper {
    void interact(Animal a) {              // accepts ANY Animal - parent type
        System.out.println("Zookeeper approaches " + a.name);
        a.makeSound();                     // polymorphism decides what actually happens
    }
}
Zookeeper zk = new Zookeeper();

zk.interact(new Dog("Rex"));
zk.interact(new Cat("Whiskers"));
```

**Output:**

```
Zookeeper approaches Rex
Rex says Woof!
Zookeeper approaches Whiskers
Whiskers says Meow!
```

**Why this is the "best" real use case, worth pausing on:** `Zookeeper`'s `interact()` method was written **once**, accepting the general type `Animal`. It never needed to know about `Dog` or `Cat` specifically. It correctly handles both, and would correctly handle any *future* animal type too (a `Bird`, a `Snake`) — **without changing a single line of `Zookeeper`'s code.**

**The one-line takeaway:** Passing objects lets you hand real, live data into methods; polymorphism lets that method behave correctly no matter which specific subclass it receives. Together, they let you write flexible, reusable code that doesn't need to be rewritten every time a new subclass is added — one of the biggest practical reasons OOP exists in the first place.

------

## 8. Quick Summary — All Four Concepts Together

| Concept                            | Core Idea                                                    |
| ---------------------------------- | ------------------------------------------------------------ |
| **`super`**                        | Lets a child call its parent's constructor or overridden method explicitly |
| **Method Overloading**             | Same method name, different parameters, resolved at compile time |
| **Method Overriding**              | Child rewrites a parent's method with identical signature, resolved at runtime |
| **Polymorphism**                   | The umbrella concept — "same name, many behaviors" — includes both Overloading (compile-time) and Overriding (runtime) |
| **Passing Objects**                | Methods receive a reference to the real object, not a copy — changes made inside are visible outside too |
| **Polymorphism + Passing Objects** | Write one method accepting the parent type; it automatically handles any current or future subclass correctly |