# Exception Handling in Java

## 1. Why does exception handling exist? (Start here, before any code)

Imagine you're driving to work on a route you know by heart. One day you reach a signal and find the road closed — nobody warned you.

- **Without a backup plan:** you just sit there, confused. Traffic behind you piles up. Everything stops.
- **With a backup plan:** you already knew "roads sometimes close" and had a diversion route ready. You take it, reach office a bit late, but you **get there**.

This is exactly what happens inside a running Java program. While it's executing, something unexpected can go wrong — dividing by zero, reading a file that doesn't exist, accessing an array position that isn't there. Without handling, the program crashes right there and everything after that line never runs. With exception handling, the program notices the problem, deals with it gracefully, and keeps running (or shuts down cleanly with a proper message instead of an ugly crash).

**The entire purpose of exception handling: convert "the program suddenly dies" into "the program handles it and moves on."**

## 2. What exactly is an Exception?

An **Exception** is an event that disrupts the normal flow of a program, occurring **during execution** — not caught by the compiler beforehand.

Think of your program as a train on a track, running smoothly station to station. An exception is a sudden obstacle on the track — a signal failure, a stuck level-crossing gate. The train can't just keep going as if nothing happened.

Real examples of "obstacles on the track" in Java:

- Dividing a number by zero → `ArithmeticException`
- Accessing `arr[10]` in an array with only 5 elements → `ArrayIndexOutOfBoundsException`
- Calling a method on an object that is `null` → `NullPointerException`
- Converting `"abc"` into an integer → `NumberFormatException`
- Opening a file that doesn't exist → `FileNotFoundException`

None of these are typos or syntax mistakes — the compiler is perfectly happy with the code. The problem only shows up when the program actually **runs** and hits that specific bad situation. That's the key distinction: exceptions are a **runtime** phenomenon.

## 3. Error vs. Exception

Java has two separate categories for "things that go wrong":

|                          | **Error**                                                    | **Exception**                                                |
| ------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Analogy                  | An earthquake hitting the office building — outside your control | A pothole on your route — unexpected, but you can plan around it |
| Example                  | `OutOfMemoryError`, `StackOverflowError`                     | `ArithmeticException`, `NullPointerException`                |
| Can the program recover? | Generally no — something is wrong at the JVM/system level    | Yes — this is what handling code is for                      |
| Do we try to catch it?   | No                                                           | Yes                                                          |

Going forward, "handling the problem" means **Exceptions**, not Errors. Errors are the JVM's problem, not something `try-catch` is meant to fix.

## 4. Checked vs. Unchecked Exceptions

Imagine going on a trek:

- **Checked Exceptions** are like the known, expected risks — "it might rain." The organizer (the Java **compiler**) forces you to carry a raincoat before you're even allowed to start. If you don't have one, the trek doesn't begin — code **won't compile** until the exception is handled.
- **Unchecked Exceptions** are like a sudden ankle twist. Nobody forces you to prepare a bandage in advance since it's not guaranteed to happen — but if it does happen mid-trek and you weren't ready, you're in trouble.
- **Checked Exceptions** — checked by the compiler at **compile time**. A method that can throw one must either catch it or declare it (`throws`), or the code won't compile. Example: `IOException`, `FileNotFoundException`.
- **Unchecked Exceptions** (a.k.a. **Runtime Exceptions**) — the compiler does not force handling. They occur while the program runs, often due to a logic mistake. Example: `ArithmeticException`, `NullPointerException`, `ArrayIndexOutOfBoundsException`, `NumberFormatException`.

### The exception family tree

```
Throwable
├── Error                     (JVM-level problems, don't handle these)
└── Exception
    ├── IOException, ...      (Checked — compiler forces handling)
    └── RuntimeException
        ├── ArithmeticException
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        └── NumberFormatException   (Unchecked — compiler doesn't force handling)
```

## 5. The five keywords

Picture a restaurant kitchen:

- **`try`** — "Let's attempt to cook this dish. Something might go wrong, but we're trying anyway."
- **`catch`** — "If something does go wrong, here's exactly what we do about it." You can have multiple `catch` blocks for different kinds of trouble (a fire needs a different response than a cut).
- **`finally`** — "No matter what happened, we always switch off the gas stove before leaving the kitchen." This block runs no matter what.
- **`throw`** — You (the chef) actively raising the alarm: "I am declaring right now — this is a problem!" Used to manually signal that something has gone wrong.
- **`throws`** — A warning label on the kitchen door: "Cooking here might cause a fire — enter prepared." Used in a method's signature to tell callers it might throw a given exception.

### 5.1 Basic try-catch

```java
public class Test {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;

        System.out.println("Before division");

        try {
            int result = a / b;              // risky line — dividing by zero
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero! Handled gracefully.");
        }

        System.out.println("After the try-catch block");
    }
}
```

**Output:**

```
Before division
Cannot divide by zero! Handled gracefully.
After the try-catch block
```

What happens, step by step:

1. `a / b` is the risky line — Java tries to run it.
2. The moment it fails, Java immediately jumps out of the `try` block (the `System.out.println("Result: " + result);` line never runs).
3. Java looks for a matching `catch` — finds `ArithmeticException` — and runs that block.
4. Once `catch` finishes, the program continues normally — `"After the try-catch block"` still prints. Without the try-catch, the program would have crashed right after `"Before division"` and never reached that final line.

### 5.2 Multiple catch blocks

A `try` block can contain more than one kind of risk, so multiple `catch` blocks are allowed — like a fire extinguisher for fires and a first-aid kit for cuts, kept separately.

```java
public class Test {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30};

        try {
            System.out.println(numbers[5]);          // out of bounds
            int result = 10 / 0;                       // would also fail, but never reached
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index problem: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Math problem: " + e.getMessage());
        }
    }
}
```

**Output:**

```
Array index problem: Index 5 out of bounds for length 3
```

The moment the array line fails, Java jumps straight to the matching `catch` block and skips everything else in `try` — the division line never gets a chance to run.

**Ordering rule:** if catching both a specific exception and a general parent (e.g. `ArithmeticException` and `Exception`), the specific one must come first. Java checks `catch` blocks top to bottom and stops at the first match — placing the general one first makes the specific block unreachable, which Java actually flags as a compile error (a helpful safety net).

### 5.3 The finally block

```java
public class Test {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught the exception");
        } finally {
            System.out.println("This always runs — cleanup code goes here");
        }
    }
}
```

**Output:**

```
Caught the exception
This always runs — cleanup code goes here
```

`finally` runs whether or not an exception occurred, and whether or not it was caught. Typically used for cleanup that must always happen — closing a file, closing a database connection, or (kitchen analogy) switching off the gas stove regardless of how the dish turned out.

### 5.4 throw — manually raising an exception

Sometimes you want to flag something as a problem even if Java itself wouldn't normally complain. A negative age doesn't crash Java by default, but logically it's invalid for your program.

```java
public class Test {
    static void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        System.out.println("Age set to: " + age);
    }

    public static void main(String[] args) {
        setAge(25);
        setAge(-5);   // this line triggers our manual throw
    }
}
```

**Output:**

```
Age set to: 25
Exception in thread "main" java.lang.IllegalArgumentException: Age cannot be negative
    at Test.setAge(Test.java:4)
    at Test.main(Test.java:11)
```

Since this call wasn't wrapped in a `try-catch`, the program crashes at that point. `throw` on its own doesn't protect you — it raises the alarm. A `try-catch` around `setAge(-5)` would be needed to handle it gracefully.

### 5.5 throws — warning callers in advance

`throws` goes in a method's signature. It doesn't handle anything itself — it's a declaration: "calling this method might result in this exception, be ready for it."

```java
import java.io.*;

public class Test {
    static void readFile() throws IOException {
        FileReader file = new FileReader("data.txt");   // might not exist
    }

    public static void main(String[] args) {
        try {
            readFile();
        } catch (IOException e) {
            System.out.println("File could not be read: " + e.getMessage());
        }
    }
}
```

`readFile()` uses `throws IOException` to warn callers it might fail. Since `IOException` is a **checked** exception, the compiler enforces this — if `main()` didn't wrap the call in `try-catch` (or itself declare `throws IOException`), this code would not compile at all.

## 6. Custom Exceptions

Sometimes the built-in exceptions don't describe a specific business problem well enough. Java lets you design your own — like wanting a very specific alarm just for "we've run out of a particular ingredient," rather than a generic "something went wrong" alarm.

```java
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);     // passes the message up to the parent Exception class
    }
}

class BankAccount {
    double balance;

    BankAccount(double balance) { this.balance = balance; }

    void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal");
        }
        balance -= amount;
        System.out.println("Withdrawal successful. Remaining balance: " + balance);
    }
}

public class Test {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        try {
            account.withdraw(500);
            account.withdraw(800);   // this will fail — only 500 left
        } catch (InsufficientBalanceException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}
```

**Output:**

```
Withdrawal successful. Remaining balance: 500.0
Transaction failed: Insufficient balance for this withdrawal
```

This combines everything above: `throws` on the method signature, `throw` to manually raise the custom exception, and `try-catch` at the calling site to handle it gracefully instead of crashing.

## 7. Quick summary table

| Keyword   | Job                                     | Analogy                                           |
| --------- | --------------------------------------- | ------------------------------------------------- |
| `try`     | Wraps risky code                        | Attempting the risky cooking step                 |
| `catch`   | Handles a specific problem if it occurs | Fire extinguisher, ready for a specific emergency |
| `finally` | Always runs, success or failure         | Switching off the gas stove no matter what        |
| `throw`   | You manually raise an exception         | Chef sounding the alarm themselves                |
| `throws`  | Warns callers a method might fail       | Warning label on the kitchen door                 |

## 8. Checked vs. Unchecked — quick reference

|                         | Checked                                | Unchecked (Runtime)                                          |
| ----------------------- | -------------------------------------- | ------------------------------------------------------------ |
| Checked by              | Compiler, at compile time              | Not enforced by compiler                                     |
| Must handle or declare? | Yes — code won't compile otherwise     | No — up to the programmer                                    |
| Examples                | `IOException`, `FileNotFoundException` | `ArithmeticException`, `NullPointerException`, `ArrayIndexOutOfBoundsException`, `NumberFormatException` |