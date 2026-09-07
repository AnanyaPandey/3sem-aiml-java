# Java Inheritance — Detailed Notes

------

## 1. What is Inheritance? (Real-life explanation first)

Imagine you're designing a program for a vehicle showroom. You need three types of vehicles: **Car**, **Bike**, and **Truck**.

Now think about what's common between all three:

- All of them have a `speed`
- All of them have a `start()` action
- All of them have a `stop()` action

If you didn't know about inheritance, you'd write this "speed, start, stop" code **three separate times** — once inside Car, once inside Bike, once inside Truck. That's repetitive, messy, and if you ever need to fix a bug in `start()`, you'd have to fix it in three different places.

**Inheritance solves this.** You write the common code **once**, in a general class called `Vehicle`. Then Car, Bike, and Truck each simply say "I am a type of Vehicle" and they **automatically** get all of Vehicle's code, without retyping it.

**In one line:** Inheritance lets one class (called the **child** or **subclass**) automatically receive the fields and methods of another class (called the **parent** or **superclass**) — so you don't repeat code, and you only fix bugs in one place.

### The "IS-A" Test

Before using inheritance, always ask: does this relationship make sense as **"X IS-A Y"**?

- A Car IS-A Vehicle ✅ — makes sense, use inheritance
- A Dog IS-A Animal ✅ — makes sense, use inheritance
- A Car IS-A Engine ❌ — doesn't make sense. A car *has* an engine, it *isn't* an engine. (This wrong kind of relationship is called "composition", a different concept — don't use inheritance for it.)

This test will save you from misusing inheritance later.

------

## 2. The Keywords You Need to Know

Before looking at code, let's understand every keyword involved, in plain words:

- **`extends`** — This is the word the child class uses to say "I inherit from this parent." Written like: `class Dog extends Animal`.
- **`super`** — This is how a child class reaches back and talks to its parent — either to run the parent's constructor, or to run the parent's method.
- **`protected`** — This is a visibility rule (called an "access modifier"). It means: "this field/method is visible to child classes, but hidden from random unrelated classes outside."

Keep these three words in mind — everything below uses them.

------

## 3. Basic Syntax — Your First Example

Let's start as simple as possible.

```java
class Parent {
    void greet() {
        System.out.println("Hello from Parent");
    }
}

class Child extends Parent {
    // Notice: Child is completely empty!
    // But because it "extends Parent", it still has greet()
}
Child c = new Child();
c.greet(); // Output: Hello from Parent
```

**What just happened, step by step:**

1. We created an object of `Child` using `new Child()`.
2. `Child` never wrote a `greet()` method itself.
3. But since `Child extends Parent`, Java automatically lets `Child` use `Parent`'s `greet()` method.
4. So `c.greet()` runs `Parent`'s version of the method, and prints the message.

This is the entire core idea of inheritance — the child automatically has access to everything the parent has (unless it's `private`, which we'll explain later).

------

## 4. The `super` Keyword — In Detail

`super` has **two different jobs**. Let's go through each slowly with its own example.

### 4a. Job One: Calling the parent's constructor

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

**Why does it print in this order?** Because `super(name)` is the very first line inside Dog's constructor. It pauses Dog's constructor, runs Animal's constructor completely first, and only THEN continues with the rest of Dog's constructor. This makes sense — you want the "general" Animal setup (like the name) to be ready before the more "specific" Dog setup happens.

**Important rule to remember:** If you don't write `super(...)` yourself, Java will automatically try to call the parent's constructor that takes **no arguments**. If the parent doesn't have one, you will get a compile error, and you'll be forced to add `super(...)` yourself.

### 4b. Job Two: Calling the parent's method (when you've overridden it)

Sometimes the child rewrites a method but still wants to also run the parent's original version of it too, not replace it completely. `super.methodName()` does this.

```java
class Animal {
    void eat() {
        System.out.println("Animal eats food");
    }
}

class Dog extends Animal {
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

------

## 5. Types of Inheritance in Java — One at a Time, With Full Examples

Java allows THREE types directly using classes: **Single, Multilevel, Hierarchical**. It does NOT allow two types directly using classes: **Multiple, Hybrid** (we'll explain exactly why in section 6, and how interfaces solve it).

### Type 1: Single Inheritance

**Definition:** One parent, one child. The simplest possible case — just what we saw above.

```java
class Animal {
    void eat() {
        System.out.println("This animal eats food");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}
Dog d = new Dog();
d.eat();   // inherited from Animal
d.bark();  // Dog's own method
```

**Output:**

```
This animal eats food
Dog barks
```

`Dog` has TWO methods available even though it only wrote one itself — `eat()` came free from `Animal`.

------

### Type 2: Multilevel Inheritance

**Definition:** A chain of inheritance — like a family tree with three generations. Grandparent → Parent → Child, where each class extends the one directly above it.

```java
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}

class Puppy extends Dog {
    void weep() {
        System.out.println("Puppy weeps");
    }
}
Puppy p = new Puppy();
p.eat();   // comes from Animal (grandparent, two levels up)
p.bark();  // comes from Dog (parent, one level up)
p.weep();  // Puppy's own method
```

**Output:**

```
Animal eats
Dog barks
Puppy weeps
```

**What's happening:** `Puppy extends Dog`, and `Dog extends Animal`. So `Puppy` doesn't just get Dog's stuff — it also gets everything Dog itself inherited from Animal. Inheritance "flows down" through the whole chain, however long it is.

------

### Type 3: Hierarchical Inheritance

**Definition:** The opposite shape — ONE parent, but MULTIPLE children, each independently extending that same parent.

```java
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    void meow() {
        System.out.println("Cat meows");
    }
}
Dog d = new Dog();
Cat c = new Cat();

d.eat();  // inherited
d.bark(); // Dog's own

c.eat();  // inherited
c.meow(); // Cat's own
```

**Output:**

```
Animal eats
Dog barks
Animal eats
Cat meows
```

**What's happening:** Both `Dog` and `Cat` separately inherit from `Animal`. They both get `eat()`, but `Dog` doesn't know about Cat's `meow()`, and Cat doesn't know about Dog's `bark()`. They're siblings — related to the parent, not to each other.

------

### Type 4: Multiple Inheritance (blocked for classes — shown here to understand what's NOT allowed)

**Definition:** One child trying to inherit from TWO parent classes at once.

```java
class A {
    void show() {
        System.out.println("A's show");
    }
}

class B {
    void display() {
        System.out.println("B's show");
    }
}

// class C extends A, B { }   
// ❌ This line does NOT compile in Java. Java simply does not allow this syntax.
```

We'll explain exactly *why* Java blocks this, and the workaround, in the very next section — it's important enough to deserve its own explanation.

------

### Type 5: Hybrid Inheritance

**Definition:** A combination/mix of the above types in one design (for example, hierarchical mixed with multilevel). Since a hybrid design usually ends up requiring multiple inheritance somewhere inside it, Java doesn't support it directly using classes either. Like Type 4, it's solved using interfaces (next section).

------

## 6. Why Doesn't Java Allow Multiple Inheritance With Classes? (The Diamond Problem, explained slowly)

This is a famous problem in programming languages, so let's go through it carefully with an example.

Imagine, hypothetically, Java DID allow this:

```java
class A {
    void show() {
        System.out.println("A's show method");
    }
}

class B {
    void show() {
        System.out.println("B's show method");
    }
}

// Hypothetically, if this worked:
// class C extends A, B { }
```

Now imagine you write:

```java
C obj = new C();
obj.show();
```

**Here's the confusion:** `C` inherited `show()` from BOTH `A` and `B`, and both versions have completely different code inside them. When you call `obj.show()`, which one should run? A's version? B's version? There's no clear answer — this confusion is literally called the **"Diamond Problem"** (because if you draw the class relationships, they form a diamond shape: C at the bottom connects up to both A and B, which both connect up to some common ancestor).

**Java's solution:** To avoid this confusion entirely, Java made a simple rule: **a class can only `extends` ONE other class.** Period. No ambiguity possible.

### But then how do we get the benefits of "multiple abilities"? — Interfaces

Java still lets a class gain multiple "abilities" from multiple sources, but through **interfaces** instead of classes. The difference is: a basic interface method has **no actual code inside it** (just the method name and return type) — so there's no conflicting code to be confused about. The class itself always writes the real, final code.

```java
interface A {
    void show(); // no code — just a name and a promise
}

interface B {
    void display(); // no code — just a name and a promise
}

class C implements A, B {
    public void show() {
        System.out.println("C's own show method");
    }
    public void display() {
        System.out.println("C's own display method");
    }
}
C obj = new C();
obj.show();
obj.display();
```

**Output:**

```
C's own show method
C's own display method
```

No confusion here — `C` writes its own single version of each method, so there's nothing to be ambiguous about, even though it's technically getting "multiple abilities" from A and B.

------

## 7. Method Overriding (this concept always travels together with inheritance)

**Definition:** When a child class writes its own version of a method that already exists in the parent — using the exact same method name and exact same parameters — this is called **overriding**. The child's version replaces the parent's version when called on a child object.

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

**This is worth stopping on, because it's a bit surprising at first.** Notice the variable `a` is declared as type `Animal`. You might expect it to run Animal's `sound()`. But it doesn't — it runs **Dog's** version, because that's the *actual* object stored inside `a` (we did `new Dog()`, not `new Animal()`). Java always looks at the real object type at runtime, not the variable's declared type, when deciding which overridden method to run. This specific behavior even has its own name — **runtime polymorphism** — and it's only possible *because of* inheritance.

**Rules for overriding, so Java accepts it:**

- Method name must match exactly
- Parameters (number, types, order) must match exactly
- Return type must match (or be a valid subtype)
- `@Override` above the method isn't strictly required, but you should always write it — if you make a small typo in the method name, Java will immediately tell you "this isn't actually overriding anything," catching your mistake early
- You cannot override a method that the parent marked as `final` (explained in section 9)

------

## 8. Constructors Are NOT Inherited

This surprises a lot of beginners: unlike normal methods, a child class does **not** inherit the parent's constructors directly. However, the parent's constructor **always runs automatically first**, before the child's constructor's own code runs.

```java
class Animal {
    Animal() {
        System.out.println("Animal object being created");
    }
}

class Dog extends Animal {
    Dog() {
        System.out.println("Dog object being created");
    }
}
new Dog();
```

**Output:**

```
Animal object being created
Dog object being created
```

**Why this order?** Think of it like building a house — you must lay the foundation (the parent part of the object) before you can build the upper floor (the child part). Java enforces this automatically, every single time, even if you never write `super()` yourself.

------

## 9. Preventing Inheritance Using `final`

Sometimes, as the designer of a class, you might want to say "no one is allowed to extend this — it must be used exactly as is." The `final` keyword does this.

### 9a. Blocking a whole class from being inherited

```java
final class Animal {
    void eat() {
        System.out.println("eating");
    }
}

// class Dog extends Animal { }
// ❌ COMPILE ERROR — you cannot extend a final class
```

### 9b. Blocking just one specific method from being overridden (but the class itself can still be extended)

```java
class Animal {
    final void breathe() {
        System.out.println("Breathing — this behavior cannot change");
    }

    void eat() {
        System.out.println("eating");
    }
}

class Dog extends Animal {
    // void breathe() { }  
    // ❌ COMPILE ERROR — cannot override a final method

    @Override
    void eat() {
        System.out.println("Dog eating specifically"); // ✅ this is fine, eat() wasn't final
    }
}
```

This gives you fine control — you can allow a class to be extended in general, but lock down one specific critical method so no child can ever change its behavior.

------

## 10. The `Object` Class — Java's Hidden Root Parent

Here's something most beginners don't realize: **every single class you ever write in Java automatically inherits from a built-in class called `Object`**, even if you never write `extends` anything at all.

```java
class Dog {
    // You wrote this, but Java secretly treats it as:
    // class Dog extends Object { }
}
```

**Why does this matter to you?** Because this is exactly why every object you ever create in Java — no matter what class it is — automatically has certain methods available, like `.toString()`, `.equals()`, and `.hashCode()`, without you ever writing them. They all come from `Object`, sitting silently at the very top of every inheritance chain in Java.

------

## 11. Access Modifiers and How They Affect Inheritance

Not everything the parent has is automatically visible to the child. It depends on how the parent marked its fields/methods.

| Modifier    | Visible inside the same class | Visible to child class | Visible to unrelated outside classes |
| ----------- | ----------------------------- | ---------------------- | ------------------------------------ |
| `private`   | Yes                           | **No**                 | No                                   |
| `protected` | Yes                           | **Yes**                | No                                   |
| `public`    | Yes                           | **Yes**                | Yes                                  |

```java
class Animal {
    private String secret = "hidden from everyone else";
    protected String name = "Generic Animal";
    public String type = "Animal";
}

class Dog extends Animal {
    void printInfo() {
        // System.out.println(secret);  
        // ❌ ERROR — 'secret' is private, Dog cannot see it at all

        System.out.println(name);       // ✅ works — protected is visible to child
        System.out.println(type);       // ✅ works — public is visible everywhere
    }
}
```

**Takeaway:** if you want a field to be usable by child classes but still hidden from the rest of the program, mark it `protected` (not `private`, and not `public`).

------

## 12. Full Summary Table

| Type         | Shape/Structure            | Directly supported by Java classes? |
| ------------ | -------------------------- | ----------------------------------- |
| Single       | One parent → one child     | ✅ Yes                               |
| Multilevel   | Chain: A → B → C           | ✅ Yes                               |
| Hierarchical | One parent → many children | ✅ Yes                               |
| Multiple     | One child → many parents   | ❌ No — use interfaces instead       |
| Hybrid       | A mix of the above         | ❌ No — use interfaces instead       |

------

## 13. Key Terms, One More Time, All Together

- **Inheritance** — a child class automatically reuses a parent class's fields and methods
- **`extends`** — the keyword a child class uses to inherit from a parent class
- **`super`** — used inside a child class to call the parent's constructor, or the parent's version of an overridden method
- **Method Overriding** — child class rewrites a method with the exact same signature as the parent's, to give it new behavior
- **`@Override`** — an annotation that tells Java (and other programmers) "I intend to override this method" — helps catch mistakes
- **`final` class** — a class that cannot be extended by anyone
- **`final` method** — a method that cannot be overridden by any child class
- **`Object` class** — the invisible top-level parent that every Java class inherits from, even without writing `extends`
- **IS-A relationship** — the mental test to check whether inheritance is the right tool for the relationship you're modeling
- **Diamond Problem** — the ambiguity that would happen if a class could inherit conflicting code from two parents at once; this is why Java blocks multiple inheritance with classes

### Some Other Important Points

### 1. Example of a `protected` Method in Single Inheritance

A protected field is data; a protected method is behavior. Same visibility rule applies — visible to child classes, hidden from unrelated outside classes.

java

```java
class Animal {
    protected void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    void useParentMethod() {
        makeSound(); // ✅ Dog can call this directly - it's protected, so visible to child
    }
}
```

java

```java
Dog d = new Dog();
d.useParentMethod(); // Output: Some generic animal sound

// Outside, in an unrelated class in a different package:
// d.makeSound(); ❌ ERROR - not visible outside the class/package hierarchy
```

### 2. Inheritance Across Two Different Files

Yes, this is actually the **normal, everyday way** Java is used — real projects almost never put every class in one file. Here's exactly how it works.

**File 1: `Animal.java`**

java

```java
public class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " is eating");
    }
}
```

**File 2: `Dog.java`**

java

```java
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    public void bark() {
        System.out.println(name + " says Woof!");
    }
}
```

**Do you need `public class`?** Only under one specific Java rule: **a `.java` file's filename must match its public class name.** So `Animal.java` must contain `public class Animal`, and `Dog.java` must contain `public class Dog`. That's a filename-matching rule, not an inheritance rule.

**Does the PARENT specifically need to be public?** Not because of inheritance itself — but because of **where the files live**:

- If `Animal` and `Dog` are in the **same package** (same folder), `Animal` doesn't need to be `public` at all — package-level (default, no modifier) access is enough for `Dog` to extend it.
- If `Animal` and `Dog` are in **different packages**, then yes, `Animal` MUST be `public` — otherwise `Dog` (in another package) can't even see the class to extend it in the first place.

### 3. Inheritance Example Where Child Gets PROPERTIES, Not Just Methods

You've actually already seen this without realizing — `name` in the Animal/Dog examples is a property (field), not a method. Let's make it very explicit and obvious with a dedicated example.

java

```java
class Animal {
    protected String name;   // PROPERTY (field)
    protected int age;       // PROPERTY (field)

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }

    void printDetails() {
        // Dog never declared "name" or "age" itself,
        // but it directly uses them - they came from Animal
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
```

java

```java
Dog d = new Dog("Rex", 3);
d.printDetails(); // Output: Name: Rex, Age: 3
```

`Dog` never wrote `String name;` anywhere — it inherited that **property slot** from `Animal`, along with the method that uses it.

### 4. Multiple Inheritance Workaround 

### `class C implements A, B` — Can You Call `A.show()` Directly?

Careful here — this is a common misunderstanding. Let's slow down.

java

```java
interface A {
    void show();
}

interface B {
    void display();
}

class C implements A, B {
    public void show() {
        System.out.println("C's version of show");
    }
    public void display() {
        System.out.println("C's version of display");
    }
}
```

**`A.show()` directly, like a static call?** ❌ No — this doesn't work at all, because `show()` is an **instance method** (needs a real object to run on), not a `static` method (which belongs to the class itself, no object needed). `A` doesn't have a real body for `show()` anyway — it's just a rule/promise, there's no actual code inside `A` to run.

**What if you do this?**

java

```java
C obj = new C();
A ref = obj;      // storing a C object in an A-type variable
ref.show();       // what runs here?
```

**Output:** `C's version of show`

Even though `ref` is *declared* as type `A`, it still runs **C's** actual code — because `A` never had any real implementation to begin with; `C`'s is the *only* real version that exists. You can only call `A.show()` directly (without an object) if `show()` were marked `static` inside the interface — that's a special, less common case.

### 5. Real-Life Example of a Class You Don't Want Inherited (`final`)

Think of a class like `PasswordEncryptor` in a banking app.

java

```java
final class PasswordEncryptor {
    public String encrypt(String password) {
        // very carefully tested, secure encryption logic
        return "encrypted_" + password.hashCode();
    }
}
```

**Why final?** If this class could be extended, someone could create a subclass, override `encrypt()`, and secretly weaken the security logic (e.g., make it not actually encrypt anything) — while the rest of the app still thinks it's using a safe `PasswordEncryptor`. Marking it `final` guarantees: this exact logic is the ONLY logic that will ever run, no one can quietly swap it out through inheritance. Java's own built-in `String` class is `final` for a similar reason — its behavior must stay 100% predictable everywhere.

### 6. Can a Child Class Itself Be `final`?

Yes, absolutely. `final` just means "nothing can extend THIS class" — it doesn't care whether the class itself is a parent or a child.

java

```java
class Animal {
    void eat() { System.out.println("eating"); }
}

class Dog extends Animal {     // Dog is a child of Animal...
    final void bark() {
        System.out.println("Woof");
    }
}

// If we wanted to stop anyone from extending Dog further:
final class Puppy extends Dog {   // Puppy IS allowed to extend Dog
    // but now:
    // class BabyPuppy extends Puppy { }  ❌ ERROR - Puppy is final, chain stops here
}
```

So `Puppy` is both a child (of Dog) AND final (blocks further children) at the same time — these are two separate, unrelated facts about the same class.

### 7. One File = One Public Class — So How Do Hierarchical/Interface Inheritance Work?

The "one public class per file" rule only limits how many **public** classes can be in one file — it doesn't limit how many classes total, and it doesn't limit how many files your program has. Real inheritance structures are simply spread across multiple files, one main public class per file, like this:

**File 1: `Animal.java`**

java

```java
public class Animal {
    private String secretId;         // private - only Animal itself can see this
    protected String name;           // protected - visible to child classes
    public String species;           // public - visible everywhere

    public Animal(String name, String species) {
        this.name = name;
        this.species = species;
        this.secretId = "internal-" + name.hashCode();
    }

    protected void breathe() {       // protected method - usable by children
        System.out.println(name + " is breathing");
    }

    public void showSpecies() {      // public method - usable by anyone
        System.out.println("Species: " + species);
    }
}
```

**File 2: `Dog.java`** (hierarchical child #1)

java

```java
public class Dog extends Animal {
    public Dog(String name) {
        super(name, "Dog");
    }

    public void bark() {
        breathe();                   // ✅ allowed - protected, inherited
        System.out.println(name + " says Woof!");  // ✅ 'name' is protected, visible
        // System.out.println(secretId); ❌ ERROR - private, not visible even to child
    }
}
```

**File 3: `Cat.java`** (hierarchical child #2, sibling of Dog)

java

```java
public class Cat extends Animal {
    public Cat(String name) {
        super(name, "Cat");
    }

    public void meow() {
        breathe();                   // ✅ allowed - protected, inherited
        System.out.println(name + " says Meow!");
    }
}
```

**File 4: `Main.java`** (using everything together)

java

```java
public class Main {
    public static void main(String[] args) {
        Dog d = new Dog("Rex");
        d.bark();
        d.showSpecies();

        Cat c = new Cat("Whiskers");
        c.meow();
        c.showSpecies();
    }
}
```

**Output:**

```
Rex is breathing
Rex says Woof!
Species: Dog
Whiskers is breathing
Whiskers says Meow!
Species: Cat
```

So each file has exactly one `public class` matching its filename (`Animal.java` → `public class Animal`, and so on), and inheritance works across files exactly the same as if they were in one file — Java just needs them either in the same package, or properly `import`ed if in different packages.