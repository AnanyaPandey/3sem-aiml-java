# Packages 

------

## 1. What is a Package? (Definition, Explained Slowly)

### Real-life analogy first

Think about a **library**. A library doesn't throw every book onto one giant shelf — it organizes books into sections: "Fiction," "Science," "History." Books are grouped by what they're related to, and two books can even share a title as long as they're in different sections, without any confusion.

### The actual definition

**A package in Java is a way of grouping related classes and interfaces together, under a common name, and organizing them into a folder structure on disk.**

Formally: a package is a **namespace** — a named container that holds classes, so that every class gets a unique, full identity made up of "which package it belongs to" + "its own name." Think of it like a surname: two people can both be named "Sam," but "Sam Johnson" and "Sam Patel" are clearly different people once you add the family name. A package name works exactly like that surname, attached to every class inside it.

```java
package Example1;

public class Student {
    // this class's TRUE, full identity is: Example1.Student
    // not just "Student"
}
```

Without a package, every class you write silently goes into what's called the **default package** — an unnamed, shared bucket. This works fine for tiny single-file practice programs, but breaks down the moment you have multiple files, multiple students, or multiple folders — which is exactly the "duplicate class" issue you ran into in your lab.

------

## 2. Why Do Packages Exist? (The Major Uses — Not Just Conflict Resolution)

Avoiding naming collisions is just ONE benefit. Here are all the real reasons packages exist:

### 2a. Avoiding Naming Collisions

As covered — two classes with the same simple name (`Student`) can coexist peacefully if they belong to different packages (`Ananya.Student` vs `Rahul.Student`).

### 2b. Logical Organization

Just like a library groups books by topic, packages let you group classes by what they're related to. A large real project might have:

```
com.company.payroll     -> classes related to paying employees
com.company.inventory   -> classes related to stock/inventory
com.company.hr          -> classes related to human resources
```

This makes a huge codebase (with hundreds or thousands of classes) navigable and understandable, instead of one giant unsorted pile of files.

### 2c. Access Control (Encapsulation at a Bigger Scale)

Packages let you control visibility **between groups of classes**, not just between individual classes. Using **default (no modifier) access**, you can create classes/methods that are only usable *within* their own package — completely hidden from every other package. This lets you build "internal helper" classes that outside code isn't even allowed to touch, protecting your internal implementation details from misuse.

### 2d. Reusability

Once a class is neatly placed inside a package, it becomes easy to reuse that exact package in a completely different project — just copy the package folder over, or share the compiled `.jar` file (a packaged bundle of compiled classes). This is exactly how Java's own built-in functionality is delivered to you — as packages you `import`.

### 2e. Easier Maintenance in Team Projects

When multiple people (like your lab students, or a real dev team) work on the same overall project, packages let everyone organize their own work in isolated, clearly-labeled sections, without accidentally overwriting or colliding with someone else's class names.

------

## 3. Creating a Package — Syntax

The `package` keyword must be the **very first line** of the file — before anything else, even before `import` statements.

```java
package Example1;

public class Student {
    String name;

    Student(String name) {
        this.name = name;
    }

    void show() {
        System.out.println("Student name: " + name);
    }
}
```

### The Golden Rule: Package Name Must Match Folder Structure

If you write `package Example1;`, this file MUST physically live inside a folder named exactly `Example1`.

```
Example1/
    Student.java        (contains: package Example1;)
```

For multi-level (nested) packages, each dot `.` in the name becomes one folder deeper:

```java
package com.school.students;
com/
    school/
        students/
            Student.java
```

This isn't just a convention — it's a strict requirement the Java compiler enforces.

------

## 4. Using a Class From Another Package — `import`

A class sitting inside a package isn't automatically visible elsewhere. You must `import` it first.

**Example1/Student.java:**

```java
package Example1;

public class Student {
    String name;

    public Student(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("Student name: " + name);
    }
}
```

**Example2/BiggerProgram.java:**

```java
package Example2;

import Example1.Student;   // bringing Student in from the Example1 package

public class BiggerProgram {
    public static void main(String[] args) {
        Student s = new Student("Rex");
        s.show();
    }
}
```

**Output:**

```
Student name: Rex
```

### Two Ways to Import

**Specific import (recommended):**

```java
import Example1.Student;   // imports ONLY Student
```

**Wildcard import (imports everything public in that package):**

```java
import Example1.*;         // imports EVERY public class inside Example1
```

Specific imports are usually preferred — it's immediately clear exactly which classes are being used, and there's no risk of accidentally pulling in something unintended.

------

## 5. Java's Own Built-In Packages (You've Already Been Using These)

Every time you've written this:

```java
import java.util.ArrayList;
```

...you were importing `ArrayList` from Java's own built-in `java.util` package. Java ships with a huge standard library of pre-built packages.

| Package     | What it contains                                             |
| ----------- | ------------------------------------------------------------ |
| `java.lang` | Fundamentals: `String`, `Math`, `Integer`, `System`, `Object` |
| `java.util` | Utility classes: `ArrayList`, `HashMap`, `Scanner`, `Random` |
| `java.io`   | Reading/writing files, input/output streams                  |
| `java.time` | Dates and times                                              |
| `java.net`  | Networking (URLs, sockets)                                   |

**Special case — `java.lang`:** this one package is **automatically imported into every single Java file**, without you ever writing an import line. That's exactly why you've never had to write `import java.lang.String;`, even though `String` is used constantly.

------

## 6. Compiling and Running Packaged Programs (Command Line)

This is the part that trips up most beginners, so let's be precise.

### Folder setup

```
MyProject/
    Example1/
        Student.java        (package Example1;)
    Example2/
        BiggerProgram.java  (package Example2; imports Example1.Student)
```

### Step 1: Compile — run this from the `MyProject` folder (the parent of both package folders)

```bash
javac -d . Example1/Student.java Example2/BiggerProgram.java
```

- `javac` — the Java compiler
- `-d .` — tells the compiler: "place the compiled `.class` files into a folder structure matching their package names, starting from the current directory (`.`)"

After this runs, you'll see the compiler automatically created:

```
MyProject/
    Example1/
        Student.java
        Student.class       <-- auto-generated
    Example2/
        BiggerProgram.java
        BiggerProgram.class <-- auto-generated
```

### Step 2: Run — also from the `MyProject` folder, using the fully qualified class name

```bash
java Example2.BiggerProgram
```

**Important detail:** when *running*, you use a **dot** between package and class name (`Example2.BiggerProgram`), not a slash — even though on disk it's stored as `Example2/BiggerProgram.class`. This is a common point of confusion; just remember: dots when writing Java code or referring to a class, slashes only for actual file/folder paths.

### Common Errors and Fixes

| Error                               | Cause                                                        | Fix                                                          |
| ----------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| "duplicate class"                   | Two classes with the same name, neither has a `package` declaration | Add proper `package` lines matching folder names             |
| "package does not exist"            | Importing a package that wasn't compiled yet, or wrong folder structure | Recompile everything together with `-d .`, check folder names match package names exactly |
| "could not find or load main class" | Running with the wrong name format (using `/` instead of `.`, or running from the wrong folder) | Run using dots, from the parent folder, e.g. `java Example2.BiggerProgram` |

------

## 7. Access Modifiers — Revisited With Packages

Packages are exactly where the "different package" distinction actually starts to matter. Here's the complete picture, now that packages are in play:

| Modifier                          | Same class | Same package | Different package (child class, via inheritance) | Different package (unrelated class) |
| --------------------------------- | ---------- | ------------ | ------------------------------------------------ | ----------------------------------- |
| `private`                         | Yes        | No           | No                                               | No                                  |
| *(default — no modifier written)* | Yes        | Yes          | No                                               | No                                  |
| `protected`                       | Yes        | Yes          | Yes                                              | No                                  |
| `public`                          | Yes        | Yes          | Yes                                              | Yes                                 |

### New concept: Default (Package-Private) Access

If you write a class, field, or method with **no modifier at all**, it gets what's called **default** or **"package-private"** access:

```java
class Helper {          // no modifier = default access
    void assist() {
        System.out.println("Helping...");
    }
}
```

This means: visible to any class **inside the same package**, but completely invisible to every class outside that package — even a child class in a different package cannot see it (this is more restrictive than `protected`, which at least allows access to child classes regardless of package).

### Full Demonstration Example

**Example1/Animal.java:**

```java
package Example1;

public class Animal {
    private String secretId = "hidden-001";     // visible only inside Animal itself
    String internalCode = "PKG-DEFAULT";         // default - visible only within Example1
    protected String name = "Generic Animal";    // visible to child classes, any package
    public String species = "Animal";            // visible everywhere

    public Animal(String name, String species) {
        this.name = name;
        this.species = species;
    }

    protected void breathe() {
        System.out.println(name + " is breathing");
    }
}
```

**Example1/Helper.java (same package as Animal):**

```java
package Example1;

public class Helper {
    void checkAccess() {
        Animal a = new Animal("Rex", "Dog");
        System.out.println(a.internalCode);   // ✅ works - same package, default access allowed
        // System.out.println(a.secretId);    // ❌ ERROR - private, not visible even in same package
    }
}
```

**Example2/Dog.java (different package, child class via inheritance):**

```java
package Example2;

import Example1.Animal;

public class Dog extends Animal {
    public Dog(String name) {
        super(name, "Dog");
    }

    public void bark() {
        breathe();                          // ✅ works - protected, visible to child class
        System.out.println(name + " says Woof!");   // ✅ works - protected
        // System.out.println(internalCode);  // ❌ ERROR - default access, not visible outside Example1
        // System.out.println(secretId);      // ❌ ERROR - private, never visible outside Animal
        System.out.println(species);        // ✅ works - public, visible everywhere
    }
}
```

This single example demonstrates all four access levels behaving exactly as the table describes, across two real packages.

------

## 8. Quick Summary Table

| Term                                 | Meaning                                                      |
| ------------------------------------ | ------------------------------------------------------------ |
| **Package**                          | A named folder-based grouping of related classes; gives every class a unique full identity |
| **Default package**                  | Where a class goes with no `package` line — shared bucket, prone to naming collisions |
| **`import`**                         | Brings a class from another package into scope so it can be used |
| **`import pkg.\*;`**                 | Wildcard import — brings in every public class from that package |
| **Fully qualified name**             | `packageName.ClassName` — a class's complete, unambiguous identity |
| **`java.lang`**                      | Auto-imported into every file; contains `String`, `Math`, `System`, etc. |
| **Default (package-private) access** | No modifier written — visible only within the same package   |
| **`-d .` flag**                      | Tells `javac` to build proper package folder structure for compiled `.class` files |

## 9. One-Line Definition to Remember

**A package is a named folder that groups related Java classes together, giving each class a unique full identity, controlling how visible classes are to each other, and keeping large projects organized.**