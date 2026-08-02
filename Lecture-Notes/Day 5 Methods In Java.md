# Methods (Functions) in Java

#### 04-Aug-2026

## Topic: Creating and using methods, parameters, return types, and method overloading

------

## 1. Why Do We Need Methods?

Suppose you need to calculate the square of a number in 3 different places in your program. Writing the same calculation logic three times would be repetitive and hard to maintain. Methods solve this: **write the logic once, reuse it many times.**

You have already used a method without realizing it — `main` is a method too. It's just a special one that Java calls automatically when the program starts. Now you learn to write your own.

------

## 2. Method Syntax

```java
returnType methodName(parameterType parameterName) {
    // body
    return value; // only if returnType is not void
}
```

Example:

```java
public static int square(int num) {
    int result = num * num;
    return result;
}
```

| Part                | Meaning                                                      |
| ------------------- | ------------------------------------------------------------ |
| `public`            | access modifier — who can call this method                   |
| `static`            | belongs to the class, callable without creating an object — same idea as `main` |
| `int` (return type) | the type of value this method sends back after running       |
| `square`            | the method's name — choose something meaningful              |
| `(int num)`         | parameter — the input this method needs to do its job        |
| `return result;`    | sends the calculated value back to whoever called this method |

------

## 3. Calling a Method

```java
public class MethodDemo {
    public static void main(String[] args) {
        int answer = square(5);
        System.out.println(answer); // 25
    }

    public static int square(int num) {
        int result = num * num;
        return result;
    }
}
```

A method must be **called** to run — just defining it does nothing on its own. Since `main` is `static`, any method it calls directly must also be `static`.

------

## 4. `void` Methods — When There's Nothing to Return

```java
public static void greet(String name) {
    System.out.println("Hello, " + name + "!");
}
```

Called as:

```java
greet("Ravi"); // no need to store the result — there isn't one
```

**Rule:** if a method's job is just to "do something" (print, display), use `void`. If its job is to "calculate and hand back a value," give it a real return type and use `return`.

------

## 5. Multiple Parameters

```java
public static int add(int a, int b) {
    return a + b;
}
int sum = add(10, 20); // 30
```

The order and number of arguments you pass must match the parameter list exactly.

------

## 6. Method Overloading

Java allows multiple methods with the **same name**, as long as the parameters differ (different type or different count):

```java
public static int add(int a, int b) {
    return a + b;
}

public static double add(double a, double b) {
    return a + b;
}

public static int add(int a, int b, int c) {
    return a + b + c;
}
```

Java figures out which version to run based on what you pass in:

```java
add(2, 3);       // calls the int version → 5
add(2.5, 3.5);   // calls the double version → 6.0
add(1, 2, 3);    // calls the three-parameter version → 6
```

This is called **method overloading** — same name, different behavior depending on input. It's an early example of *polymorphism*, covered in more detail in the OOP unit.

------

## 7. A Combined Example

```java
public class Calculator {
    public static void main(String[] args) {
        System.out.println(add(5, 3));
        System.out.println(isEven(10));
        printLine();
        System.out.println(square(4));
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static boolean isEven(int num) {
        return num % 2 == 0;
    }

    public static void printLine() {
        System.out.println("------------------");
    }

    public static int square(int num) {
        return num * num;
    }
}
```

This shows a method returning `int`, one returning `boolean`, one returning `void`, and how they all live side by side in one class and get called from `main`.

------

## 8. Key Points to Remember

- A method is a reusable block of code that performs a specific task.
- Syntax: `returnType methodName(parameters) { ... }`
- Use `void` when the method doesn't need to send back a value; otherwise pick the correct return type and use `return`.
- A method must be called to run — just defining it does nothing on its own.
- Methods called directly from `main` must also be `static` at this stage.
- The order and number of arguments passed must match the method's parameter list.
- Overloading lets you reuse the same method name with different parameter lists (different type or count).

------

## 9. More Examples

**Example 1 — checking a leap year**

```java
public static boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}
```

**Example 2 — finding the maximum of two numbers**

```java
public static int max(int a, int b) {
    if (a > b) {
        return a;
    } else {
        return b;
    }
}
```

**Example 3 — printing a multiplication table (void method with a loop inside)**

```java
public static void printTable(int num) {
    for (int i = 1; i <= 10; i++) {
        System.out.printf("%d x %d = %d%n", num, i, num * i);
    }
}
```

**Example 4 — overloaded method to find area (different parameter counts)**

```java
public static double area(double side) {          // square
    return side * side;
}

public static double area(double length, double width) { // rectangle
    return length * width;
}
```

------

## 10. Practice Questions

1. Write a method `isPrime(int num)` that returns `true` if the number is prime, `false` otherwise. Call it from `main` for a few different numbers.
2. Write a method `factorial(int num)` that returns the factorial of a number using a loop.
3. Write a method `reverseNumber(int num)` that returns the reverse of a given number (e.g., 123 → 321).
4. Write a method `countVowels(String word)` that returns how many vowels are in the given word.
5. Write two overloaded methods named `volume` — one that calculates the volume of a cube (single side length) and one that calculates the volume of a cuboid (length, width, height).
6. Write a `void` method `printGrade(int marks)` that prints the grade (A/B/C/Fail) based on the marks, using the grading logic from the if-else notes.
7. Write a method `sumOfDigits(int num)` that returns the sum of all digits of a number (e.g., 123 → 6).
8. Write a method `isPalindrome(String word)` that returns `true` if the word reads the same forwards and backwards.

### Do all your methods have to be static?

**Only if you're calling them directly from `main` without creating an object first** — which is exactly what you've been doing so far in all your practice programs. That's the specific situation, not a universal Java rule. In Java everything is on object so if you want to call a function without creating an object of it you need to call it static.

```java
public class Demo {
    public static void main(String[] args) {
        square(5);  // calling directly, no object created → square must be static
    }

    public static int square(int num) {
        return num * num;
    }
}
```

### Can you "discard" static and still call it directly from main?

No — if you remove `static` from `square`, this breaks:

```java
public class Demo {
    public static void main(String[] args) {
        int result = square(5); // ERROR — can't call non-static method without an object
    }

    public int square(int num) {  // static removed
        return num * num;
    }
}
```

Java will refuse to compile this, because `square` is now an **instance method** — it belongs to individual objects, not the class itself. Since `main` is static and runs before any object exists, it has no object to call `square` through.

### The actual way around it — create an object first

```java
public class Demo {
    public static void main(String[] args) {
        Demo d = new Demo();       // create an object
        int result = d.square(5);  // now call through the object
        System.out.println(result);
    }

    public int square(int num) {   // non-static is fine now
        return num * num;
    }
}
```

This now works, because `square` is being called **through an object** (`d`), not directly by name. This is actually the more common, more "proper OOP" way of writing things — where most of your real class methods will be non-static, and only `main` stays static as the entry point.

### So what does `static` actually mean, one more time, simply

**`static` = "belongs to the class as a whole, usable without needing any object."**
 **non-static (instance) = "belongs to a specific object, only usable after creating one with `new`."**

You've been writing everything `static` so far purely because it was the simplest way to call methods directly from `main` while you were still learning syntax basics (loops, conditions, methods) — before objects were introduced. That was a reasonable teaching shortcut for that stage.

**Now that you're moving into proper OOP (classes, objects, encapsulation)**, the natural shift is: your class's real methods become **non-static**, and you create an object in `main` to use them — which is exactly the pattern we used in the encapsulation and constructor examples (`BankAccount acc = new BankAccount(); acc.deposit(1000);`).

### One-line takeaway

Don't think of it as "discarding" static — think of it as: **static was the training-wheels approach for early lessons; non-static + objects is the real OOP approach**, and you'll naturally use non-static methods more and more as you get deeper into classes and objects.