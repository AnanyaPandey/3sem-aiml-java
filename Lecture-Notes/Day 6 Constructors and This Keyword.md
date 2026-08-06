

# Constructors in Java

## Topic: What a constructor is, its rules, `this` keyword, default constructor, and constructor overloading

------

## 1. The Problem Constructors Solve

Consider this `Student` class:

```java
class Student {
    String name;
    int age;
}
```

To use it, you had to do this every time:

```java
Student s1 = new Student();
s1.name = "Ravi";
s1.age = 20;
```

That's three separate lines just to set up one object. What if `name` and `age` could be set automatically, right at the moment the object is created, in one line? That's exactly what a **constructor** does.

------

## 2. Simple Definition

**A constructor is a special method that runs automatically when an object is created using `new`.** Its job is to **initialize** the object — set up its starting values.

**Analogy:** think of it like filling out an admission form the moment a new student joins a college — their name, roll number, etc. get recorded immediately at entry, instead of being filled in piece by piece later.

------

## 3. Rules That Make a Constructor a Constructor

1. It has the **same name as the class**.
2. It has **no return type** — not even `void`.
3. It runs **automatically**, exactly once, when `new ClassName()` is used.

```java
class Student {
    String name;
    int age;

    // constructor
    Student(String studentName, int studentAge) {
        name = studentName;
        age = studentAge;
    }

    void display() {
        System.out.println(name + " is " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Ravi", 20);  // constructor runs here automatically
        s1.display();  // Ravi is 20 years old.
    }
}
```

Notice we no longer write `s1.name = ...` and `s1.age = ...` separately — everything gets set up in a single line, `new Student("Ravi", 20)`, because the constructor handles it.

------

## 4. The `this` Keyword

A common situation: the parameter names are the same as the field names, which causes ambiguity:

```java
Student(String name, int age) {
    name = name;   // WRONG — does nothing useful, refers to the parameter itself
    age = age;
}
```

`this` refers to **"the current object being created."** It's used to distinguish the class's own field from the parameter that happens to have the same name.

```java
Student(String name, int age) {
    this.name = name;   // this.name = the object's field, name = the parameter
    this.age = age;
}
```

Read `this.name` as "**this object's** name" vs plain `name` as "the value that was just passed in."

------

## 5. Default Constructor

If you don't write any constructor at all, Java secretly provides one for free — called the **default constructor** — which takes no parameters and does nothing special (fields just get default values like `0`, `null`, `false`).

```java
class Student {
    String name;   // no constructor written
}

Student s1 = new Student();  // still works — Java's invisible default constructor runs
System.out.println(s1.name); // prints "null" — default value
```

**Important rule:** the moment you write **any** constructor yourself, Java **stops** providing the default one automatically. So if you write a parameterized constructor like `Student(String name, int age)`, you can no longer do `new Student()` unless you also explicitly write a no-argument constructor yourself.

------

## 6. Constructor Overloading

Just like methods, constructors can be overloaded — multiple constructors with different parameter lists, so an object can be created in more than one way:

```java
class Student {
    String name;
    int age;

    Student() {                      // no-argument constructor
        name = "Unknown";
        age = 0;
    }

    Student(String name, int age) {  // parameterized constructor
        this.name = name;
        this.age = age;
    }

    void display() {
        System.out.println(name + " is " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();               // uses no-arg constructor
        Student s2 = new Student("Priya", 21);     // uses parameterized constructor

        s1.display(); // Unknown is 0 years old.
        s2.display(); // Priya is 21 years old.
    }
}
```

Java picks the right constructor to run based on what arguments you pass — the same logic as method overloading.

------

## 7. Calling One Constructor from Another — `this(...)`

One constructor can call another constructor of the same class using `this(...)`, to avoid repeating the same initialization code:

```java
class Student {
    String name;
    int age;

    Student() {
        this("Unknown", 0);  // calls the other constructor below
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### The key thing to understand: the no-arg constructor still *has* to supply values — it just supplies fixed/default ones itself

java

```java
class Student {
    String name;
    int age;

    Student() {
        this("Unknown", 0);  // <-- these are NOT user input, they're hardcoded defaults
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

When the programmer writes:

```java
Student s1 = new Student();  // no arguments passed
```

Here's exactly what happens:

1. Java sees `new Student()` with no arguments → it matches the **no-argument constructor**.
2. Inside that no-argument constructor, the line `this("Unknown", 0);` runs.
3. This is **you (the class writer)** deciding what values to use when no one gives any — you're the one hardcoding `"Unknown"` and `0` right there in the code.
4. That call jumps to the **two-argument constructor**, passing `"Unknown"` and `0` as if the programmer had typed `new Student("Unknown", 0)`.
5. `this.name = name;` and `this.age = age;` then run, setting `name = "Unknown"` and `age = 0`.

So the user calling `new Student()` never types any values — but the class itself always ensures **some** values reach the fields, by supplying its own defaults through `this(...)`.

### What's happening — precisely

It's not that Java automatically "fills in whatever you didn't provide." It's simpler than that: **you call a specific constructor based on how many arguments you pass, and that constructor decides what values everything gets.**

java

```java
class Student {
    String name;
    int age;
    String college;

    Student() {
        this("Unknown", 0);   // hardcoded defaults for name and age
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
        college = "ABC Engineering College";  // always set the same way here
    }
}
```

- `new Student("Ravi", 20)` → directly runs the 2-argument constructor → `name = "Ravi"`, `age = 20`, `college = "ABC Engineering College"`.
- `new Student()` → runs the no-arg constructor → which itself calls `this("Unknown", 0)` → so it ends up running the 2-argument constructor anyway, just with **fixed placeholder values you wrote in advance** → `name = "Unknown"`, `age = 0`, `college = "ABC Engineering College"`.

### The key correction

"**for the fields which I did not give, this one provides default value"** — close, but it's not field-by-field. **You can't mix and match** — like, give `name` but skip `age`. You either call the no-arg constructor (get ALL the defaults from that path) or the 2-arg constructor (supply BOTH values yourself). There's no partial version where Java fills in just the missing pieces individually — that's not how Java constructors work (some other languages support true "default parameter values" per-field, but Java doesn't; overloading is Java's way of approximating it).

### One-line summary

**Which constructor you call decides which values you're responsible for providing — everything else in that path is whatever the constructor code says, including any defaults hardcoded via `this(...)`.**

------

## 8. Summary Table

| Feature     | Constructor              | Regular Method                 |
| ----------- | ------------------------ | ------------------------------ |
| Name        | same as class name       | any name you choose            |
| Return type | none, not even `void`    | must have one (or `void`)      |
| Called      | automatically, via `new` | manually, whenever you call it |
| Purpose     | initialize a new object  | perform an action/calculation  |

------

## 9. Key Points to Remember

- A constructor has the same name as the class and no return type.
- It runs automatically once, when an object is created with `new`.
- Use `this.field` to distinguish an object's field from a parameter with the same name.
- If you write no constructor, Java provides a default (no-argument) one automatically — but this disappears the moment you write any constructor yourself.
- Constructors can be overloaded, just like methods, to allow creating objects in different ways.
- `this(...)` lets one constructor call another constructor in the same class.

------

## 10. Practice Questions

1. Create a `Book` class with fields `title` and `price`, a constructor to set both, and a `display()` method. Create two `Book` objects with different values and display both.
2. Create a `Rectangle` class with a no-argument constructor that sets `length` and `width` to 1, and a parameterized constructor that accepts both values. Create one object using each constructor and print the area for both.
3. Create a `Car` class with fields `brand` and `speed`, using `this` in the constructor to resolve a naming conflict between parameters and fields.
4. Create a `Movie` class with three constructors: one with no arguments (default title "Unknown"), one with just a title, and one with title and year — demonstrating constructor overloading.
5. Modify Exercise 4 so the no-argument and title-only constructors both call the full constructor using `this(...)`, instead of repeating the initialization code.