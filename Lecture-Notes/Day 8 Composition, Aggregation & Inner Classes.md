# Java: Composition, Aggregation & Inner Classes — Complete Notes

------

## 1. Composition

### What is it? (Real-life first)

Think about a **Human Body** and a **Heart**. That specific heart was made for that specific body, lives inside it, and if the body dies, the heart dies with it. It cannot float around independently or be transplanted into someone else while still being "the same living relationship."

**Composition = a "HAS-A" relationship where the "part" is built BY the "whole," and cannot exist without it.** Both are born together, tied together, and die together.

### The Rule to Spot It in Code

**If you see the "part" object being created using `new` INSIDE the "whole" class's own constructor — that's composition.**

### Example 1: House and Room

```java
class Room {
    void showRoom() {
        System.out.println("This is a room");
    }
}

class House {
    Room R1;  // House will have a Room, calls it R1

    House() {
        R1 = new Room();  // <-- KEY LINE: House itself builds R1
        System.out.println("A new House was built, with its own R1");
    }
}
House h1 = new House();
House h2 = new House();
```

**Output:**

```
A new House was built, with its own R1
A new House was built, with its own R1
```

**What this proves:** each House builds its OWN separate Room. `h1`'s R1 and `h2`'s R1 are two completely different Room objects — never shared.

### Example 2: Car and Engine

```java
class Engine {
    void start() {
        System.out.println("Engine is starting...");
    }
}

class Car {
    Engine E1;

    Car() {
        E1 = new Engine();  // Car builds its own Engine, tied only to this car
    }

    void drive() {
        E1.start();
        System.out.println("Car is now driving");
    }
}
Car c1 = new Car();
c1.drive();
```

**Output:**

```
Engine is starting...
Car is now driving
```

### Important: A Class Can Be Composed of Multiple Different Classes

Composition doesn't limit a class to just ONE relationship — a Car can have an Engine AND a Battery at the same time.

```java
class Battery {
    void charge() {
        System.out.println("Battery charging...");
    }
}

class Car {
    Engine E1;
    Battery B1;

    Car() {
        E1 = new Engine();   // Car builds its own Engine
        B1 = new Battery();  // Car ALSO builds its own Battery
    }
}
```

### Important: A Class Can Have MULTIPLE Objects of the SAME Type

A Fruit isn't limited to one Seed — it can hold many, using a list. It's still composition, because the Fruit still creates every single one of them itself.

```java
import java.util.ArrayList;

class Seed {
    void grow() {
        System.out.println("A seed can grow into a new plant");
    }
}

class Fruit {
    ArrayList<Seed> seeds;

    Fruit(int numberOfSeeds) {
        seeds = new ArrayList<>();
        for (int i = 0; i < numberOfSeeds; i++) {
            seeds.add(new Seed());  // Fruit builds each seed itself, one by one
        }
        System.out.println("A Fruit was created with " + numberOfSeeds + " seeds");
    }
}
Fruit apple = new Fruit(5);
```

**Output:**

```
A Fruit was created with 5 seeds
```

### Key Point: Composition Is About Lifetime/Ownership, NOT About Access

Even though `House` creates `R1` inside its own constructor, `Room` is still a fully **separate class**, written outside House's body. That means `Room` has **zero automatic access** to House's private fields — creating an object inside a constructor decides "who builds it and how long it lives," not "what it's allowed to see."

```java
public class House {
    private String houseSecret = "Safe behind the painting";
    private Room R1;

    public House() {
        this.R1 = new Room("Living Room");
    }
}

class Room {
    private String name;

    public Room(String name) {
        this.name = name;
    }

    public void clean() {
        System.out.println("Cleaning room: " + name);
        // System.out.println(houseSecret); ❌ ERROR - Room cannot see House's private data
    }
}
```

------

## 2. Aggregation

### What is it? (Real-life first)

Think about a **Doctor** and a **Hospital**. A Hospital has Doctors — but a visiting doctor isn't "owned" by just one hospital. The same doctor can work at City Care Hospital on Monday and Sunrise Multispeciality on Tuesday. The Doctor exists **completely independently** of any one Hospital, and can be shared or reused elsewhere.

**Aggregation = a "HAS-A" relationship where the "part" is built OUTSIDE, independently, and just gets handed to / used by the "whole." It can exist without the whole, and can be shared.**

### The Rule to Spot It in Code

**If you see the "part" object being passed in as a PARAMETER (through the constructor or elsewhere), instead of being created with `new` inside — that's aggregation.**

### Example: Doctor and Hospital

```java
class Doctor {
    String name;
    String specialization;

    Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    void treatPatient() {
        System.out.println("Dr. " + name + " (" + specialization + ") is treating a patient");
    }
}

class Hospital {
    String hospitalName;
    Doctor D1;  // Hospital HAS a Doctor, but does NOT create the Doctor itself

    Hospital(String hospitalName, Doctor D1) {
        this.hospitalName = hospitalName;
        this.D1 = D1;  // just storing a reference to a Doctor built elsewhere
    }

    void runOPD() {
        System.out.println("OPD open at " + hospitalName);
        D1.treatPatient();
    }
}
public class Main {
    public static void main(String[] args) {
        // Doctor exists independently, created FIRST, on its own
        Doctor D1 = new Doctor("Mehta", "Cardiologist");

        // Same doctor visits two different hospitals
        Hospital h1 = new Hospital("City Care Hospital", D1);
        h1.runOPD();

        Hospital h2 = new Hospital("Sunrise Multispeciality", D1);
        h2.runOPD();
    }
}
```

**Output:**

```
OPD open at City Care Hospital
Dr. Mehta (Cardiologist) is treating a patient
OPD open at Sunrise Multispeciality
Dr. Mehta (Cardiologist) is treating a patient
```

**What this proves:** the exact SAME Doctor object (`D1`) is shared and reused across two different Hospital objects — something that could never happen in strict composition, where each House builds its own private Room that no other House can touch.

### Side-by-Side Comparison: Composition vs Aggregation

|                                                     | Composition                                               | Aggregation                                    |
| --------------------------------------------------- | --------------------------------------------------------- | ---------------------------------------------- |
| Relationship type                                   | HAS-A (strong)                                            | HAS-A (weak)                                   |
| Who creates the "part"?                             | The "whole" itself, via `new`, inside its own constructor | Created outside, then passed in as a parameter |
| Can the part exist independently?                   | No — tied to the whole's lifetime                         | Yes — fully independent                        |
| Can the same part be shared across multiple wholes? | No                                                        | Yes                                            |
| Real-life example                                   | House & Room, Car & Engine, Human Body & Heart            | Doctor & Hospital, Teacher & School            |
| Code giveaway                                       | `partObject = new Part();` inside the constructor         | `Part partObject` as a constructor parameter   |

------

## 3. Inner Classes

### What is it? (Real-life first)

Think about a **Human** and their **Intuition**. Intuition is born entirely inside a person's mind — you cannot remove someone's intuition and hand it to another person. And because it lives inside the same mind, it has **automatic, private access** to that person's private thoughts and feelings, without needing to "ask permission" through any public method.

**An Inner Class = a class defined physically INSIDE another class's `{ }` body**, because it only makes sense in the context of that outer class, and it gets special, automatic access to the outer class's private data.

### The Rule to Spot It in Code

**If a `class` keyword is written literally between the outer class's opening and closing curly braces `{ }` — that's an inner class.** This is purely about *where the class is written*, not about how or when its object gets created.

### Example: Human and Intuition

```java
public class Human {
    private String privateThought = "I'm nervous about tomorrow's exam";
    private Intuition gut;

    class Intuition {                      // <-- defined INSIDE Human's braces
        void sense() {
            System.out.println("Intuition senses something...");
            System.out.println("It quietly knows: " + privateThought);  // works - automatic access
        }
    }

    public Human() {
        this.gut = new Intuition();
    }

    public void makeDecision() {
        System.out.println("Thinking it over...");
        gut.sense();
        System.out.println("Decision made based on gut feeling.");
    }

    public static void main(String[] args) {
        Human person = new Human();
        person.makeDecision();
    }
}
```

**Output:**

```
Thinking it over...
Intuition senses something...
It quietly knows: I'm nervous about tomorrow's exam
Decision made based on gut feeling.
```

**What's special here:** `Intuition`'s `sense()` method directly reads `privateThought` — a `private` field of the outer `Human` class — with no getter method, no special permission asked. This ONLY works because `Intuition` is written inside `Human`. If `Intuition` were a separate class outside `Human`'s body (like `Room` was for `House`), this line would immediately fail to compile.

### Important Clarification: "Inner Class" and "Composition-style creation" Are Two Separate Decisions

It's easy to get confused here, so let's be precise. There are actually **two independent questions** you can ask about any "part" class:

**Question 1 — WHERE is the class physically written?**

- As its own separate class → normal class
- Nested inside another class's `{ }` → inner class

**Question 2 — WHEN/HOW is the object created?**

- Built by the whole itself, inside its own constructor → composition-style (tied lifetime)
- Passed in from outside → aggregation-style (independent lifetime)

These two questions can mix in any combination — they are not the same category:

|                                | Written as separate class       | Written as inner class                                       |
| ------------------------------ | ------------------------------- | ------------------------------------------------------------ |
| **Created inside constructor** | Composition (House & Room)      | Inner class, composition-style (Human & Intuition)           |
| **Passed in from outside**     | Aggregation (Doctor & Hospital) | Inner class, but object passed in (rare, unusual, but technically valid) |

**The one fact that actually makes something an "inner class":** purely Question 1 — where the `class` keyword is written in the code. Creating the object inside the constructor (like we did for `Intuition`) is simply the most common, natural way inner classes are used in practice — but it isn't what defines them as inner classes.

### The Syntax Detail: Creating an Inner Class Object From Outside

If you ever need to create an inner class object from **outside** the outer class (less common, but possible), the syntax looks unusual:

```java
Car c = new Car();
Car.SteeringWheel sw = c.new SteeringWheel();  // note: c.new, not just new
```

This is because an inner class object always needs to be tied to a specific instance of the outer class — you can't create the "part" floating on its own without first having a "whole" to attach it to.

------

## 4. Quick Summary — All Three Together

| Concept         | Where is the part written?      | Who creates the part?                | Can it exist independently? | Gets automatic private access? |
| --------------- | ------------------------------- | ------------------------------------ | --------------------------- | ------------------------------ |
| **Composition** | Separate class                  | The whole, in its own constructor    | No                          | No                             |
| **Aggregation** | Separate class                  | Outside, passed in                   | Yes                         | No                             |
| **Inner Class** | Nested inside the whole's `{ }` | Usually the whole (but not required) | No                          | Yes                            |

**One-line summary of each:**

- **Composition** — the whole builds the part itself; tied lifetimes; no special access.
- **Aggregation** — the part is built independently and just borrowed; can be shared across multiple wholes.
- **Inner Class** — the part is literally written inside the whole's code; gets private, VIP access to the whole's internal data, purely because of where it's defined.

**Composition = Strong HAS-A**
 **Aggregation = Weak HAS-A**

### Why "strong"?

House and Room — the bond is **strong** because Room's entire existence is *dependent* on House. If House is destroyed, Room is destroyed too. There's no way to break this bond and have Room survive on its own. Strong = tightly bound, can't be separated, one can't outlive the other.

### Why "weak"?

Doctor and Hospital — the bond is **weak** because it's *loosely* connected. You can break the bond anytime (Doctor stops visiting that Hospital) and both sides carry on existing perfectly fine independently. Weak = loosely bound, easily separated, either side survives on its own.

### Simple way to remember

Think of "strong" and "weak" as describing **how tightly the two objects are tied together**, not how "important" either one is.

- **Strong HAS-A (Composition):** cut the connection → the part dies. (Cut House → Room dies with it)
- **Weak HAS-A (Aggregation):** cut the connection → the part survives just fine. (Doctor stops visiting Hospital → Doctor still exists, still works elsewhere)

That's the whole distinction — "strong" and "weak" are just other names for exactly what we already covered: whether the part's lifetime is *tied to* or *independent of* the whole.