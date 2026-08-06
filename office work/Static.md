# Static

### Step 1: The core problem `static` solves

Start with what students already know: normally, to use a class's variables or methods, you need to create an **object** first.

java

```java
class Student {
    int age;
    void display() {
        System.out.println(age);
    }
}

Student s = new Student();  // must create an object first
s.display();                 // then call the method through it
```

Now ask: "What if I want a variable or method that belongs to the **class itself**, not tied to any individual object — something shared, or something usable without ever creating an object?" That's exactly what `static` is for.

### Step 2: Simple definition

**`static` means "this belongs to the class, not to any individual object."**

Anything marked `static` is:

- Created **once**, when the class is loaded — not once per object.
- **Shared** by all objects of that class.
- Accessible **without creating an object**, directly using the class name.

### Step 3: Static variables — shared across all objects

**Analogy:** think of a college. Each student has their own individual roll number (unique per student — that's a regular/instance variable). But the college name is the same for every student — one shared value, not something each student carries separately. That shared value is like a `static` variable.

java

```java
class Student {
    String name;          // instance variable — separate copy for each object
    static String college = "ABC Engineering College";  // static — shared by all

    Student(String name) {
        this.name = name;
    }

    void display() {
        System.out.println(name + " studies at " + college);
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Ravi");
        Student s2 = new Student("Priya");

        s1.display(); // Ravi studies at ABC Engineering College
        s2.display(); // Priya studies at ABC Engineering College

        Student.college = "XYZ Engineering College"; // change once
        s1.display(); // Ravi studies at XYZ Engineering College
        s2.display(); // Priya studies at XYZ Engineering College — changed for BOTH!
    }
}
```

**Key demo moment:** changing `college` once affects **both** `s1` and `s2`, because there's only **one copy** of a static variable in memory, shared by every object — unlike `name`, where each object has its own separate copy.

### Step 4: Static methods — callable without an object

This is exactly why `main` is `static` — Java needs to run it **before any object exists** in your program. There's nothing to create an object from yet, so `main` must be callable directly at the class level.

java

```java
class MathHelper {
    static int square(int num) {
        return num * num;
    }
}

public class Main {
    public static void main(String[] args) {
        int result = MathHelper.square(5);  // called using class name, no object needed
        System.out.println(result); // 25
    }
}
```

Notice: no `new MathHelper()` anywhere — you call it directly as `ClassName.methodName()`.

**This is also exactly why all the methods you've written so far in class exercises are `static`** — because you're calling them directly from `main` (which is itself static), without creating any objects. Once you start creating objects of your own classes regularly (OOP unit), you'll start writing non-static methods too, which do require an object to call.

### Step 5: One important rule to flag

**A static method cannot directly use a non-static (instance) variable**, because static methods run without any object existing — and instance variables only exist *inside* an object. This is a common source of red squiggly-line errors for beginners, worth mentioning once:

java

```java
class Demo {
    int x = 10;          // instance variable

    static void show() {
        System.out.println(x); // ERROR — can't access instance variable from static method
    }
}
```

### Summary table

|                  | `static`                                        | non-static (instance)                            |
| ---------------- | ----------------------------------------------- | ------------------------------------------------ |
| Belongs to       | the class                                       | each individual object                           |
| Memory           | one copy, shared                                | separate copy per object                         |
| Called using     | `ClassName.member`                              | `objectName.member`                              |
| Needs an object? | No                                              | Yes                                              |
| Example          | `main`, utility/helper methods, shared counters | `name`, `age`, `display()` on a specific student |