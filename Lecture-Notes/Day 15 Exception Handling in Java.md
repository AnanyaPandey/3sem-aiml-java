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

### Most useful checked exceptions (for Syllabus)

**1. File and I/O (java.io)**

- `IOException`: parent of most input/output problems. Reading or writing files, streams, console.
- `FileNotFoundException`: file does not exist or cannot be opened. Used with `FileReader`, `FileInputStream`, `Scanner(File)`.
- `EOFException`: end of file reached unexpectedly while reading.
- `UnsupportedEncodingException`: wrong character encoding name.

**2. Threads**

- `InterruptedException`: thrown by `Thread.sleep()`, `join()`, `wait()`. Students meet this very early.

**3. Class and reflection**

- `ClassNotFoundException`: `Class.forName("...")` cannot find the class.
- `CloneNotSupportedException`: calling `clone()` on a class that does not implement `Cloneable`.
- `InstantiationException` and `IllegalAccessException`: creating an object using reflection fails.
- `NoSuchMethodException` and `NoSuchFieldException`: method or field not found using reflection.

**4. Database**

- `SQLException`: any JDBC problem such as connection, query, or table errors.

**5. Network**

- `MalformedURLException`: wrong URL format.
- `UnknownHostException`: host name cannot be resolved.
- `SocketException`: problem in socket connection.
- `URISyntaxException`: wrong URI format.

**6. Parsing and security**

- `ParseException` (java.text): cannot parse a date or number from a String.
- `NoSuchAlgorithmException`: wrong algorithm name, for example in `MessageDigest.getInstance("MD5")`.

**7. Concurrency (java.util.concurrent)**

- `ExecutionException`: a task running in an executor threw an exception.
- `TimeoutException`: waiting for a result took too long.

### Top 5 to focus 

1. `IOException`
2. `FileNotFoundException`
3. `InterruptedException`
4. `ClassNotFoundException`
5. `SQLException`

### More checked exceptions

**1. File handling (java.io and java.nio.file)**

- `NotSerializableException`: saving an object whose class does not implement `Serializable`.
- `InvalidClassException`, `StreamCorruptedException`: problems when reading saved objects.
- `NoSuchFileException`: file or path missing (with `Files.readAllLines()`, `Files.copy()`).
- `FileAlreadyExistsException`: creating a file that already exists.
- `AccessDeniedException`: no permission to read or write.
- `DirectoryNotEmptyException`: deleting a folder that still has files.
- `UTFDataFormatException`, `CharConversionException`: bad text encoding data.
- `ZipException` (java.util.zip): corrupt zip file.

**2. Network (java.net)**

- `ConnectException`: server refused or is not reachable.
- `SocketTimeoutException`: connection or read took too long.
- `BindException`: port already in use.
- `ProtocolException`: wrong HTTP method or protocol.

**3. Database (java.sql), all are children of `SQLException`**

- `SQLSyntaxErrorException`: wrong SQL query.
- `SQLIntegrityConstraintViolationException`: duplicate primary key, foreign key failure.
- `SQLTimeoutException`: query took too long.
- `BatchUpdateException`: failure in batch insert or update.

**4. Reflection**

- `ReflectiveOperationException`: parent of `ClassNotFoundException`, `NoSuchMethodException`, `InstantiationException` and others.
- `InvocationTargetException`: the method called through reflection itself threw an exception.

**5. Concurrency**

- `BrokenBarrierException`: used with `CyclicBarrier`.
- `ExecutionException`, `TimeoutException`: used with `Future` and `ExecutorService`.

**6. Security and encryption**

- `GeneralSecurityException`: parent of most security exceptions.
- `InvalidKeyException`, `NoSuchPaddingException`, `BadPaddingException`, `IllegalBlockSizeException`: used in AES/RSA encryption.
- `NoSuchProviderException`, `KeyStoreException`, `CertificateException`, `SignatureException`.

**7. XML and other libraries**

- `ParserConfigurationException`, `SAXException`, `TransformerException`: XML parsing.
- `NamingException` (javax.naming): JNDI lookups, used in enterprise apps.
- `MessagingException` (JavaMail, external library): sending email.
- `LineUnavailableException`, `UnsupportedAudioFileException` (javax.sound): audio programs.
- `AWTException`, `PrinterException`: GUI and printing.

### Part B: Unchecked exceptions (very useful later)

Compiler does not force you to handle these, but they are the most common in real programs.

**Basic**

- `NullPointerException`: using an object that is null.
- `ArithmeticException`: divide by zero (integers).
- `ArrayIndexOutOfBoundsException`, `StringIndexOutOfBoundsException`, `IndexOutOfBoundsException`: wrong index.
- `NegativeArraySizeException`: creating an array with negative size.
- `ArrayStoreException`: storing wrong type in an array.
- `ClassCastException`: wrong type casting.
- `NumberFormatException`: `Integer.parseInt("abc")`.

**Argument and state checks (good for your own classes)**

- `IllegalArgumentException`: method received a bad value, e.g. negative amount in `deposit()`.
- `IllegalStateException`: method called at wrong time, e.g. withdrawing from a closed account.
- `UnsupportedOperationException`: operation not allowed, e.g. `List.of(...).add()`.

**Collections and input**

- `ConcurrentModificationException`: changing a list while looping over it.
- `NoSuchElementException`: `iterator.next()` or `Scanner.next()` with nothing left.
- `InputMismatchException`: `Scanner.nextInt()` but user typed text.
- `EmptyStackException`: `pop()` on an empty `Stack`.

**Date, text and others**

- `DateTimeException`, `DateTimeParseException`: wrong date or time.
- `PatternSyntaxException`: wrong regex pattern.
- `UncheckedIOException`: wraps `IOException` inside streams and lambdas.
- `SecurityException`: action not permitted.
- `IllegalMonitorStateException`: wrong use of `wait()` or `notify()`.
- `CompletionException`, `CancellationException`, `RejectedExecutionException`: concurrency tasks.

### Part C: Errors (not exceptions, but students must know)

These extend `Error`. You normally do not catch them.

- `StackOverflowError`: infinite recursion.
- `OutOfMemoryError`: heap is full.
- `AssertionError`: failed `assert` statement.
- `NoClassDefFoundError`: class was there at compile time but missing at run time.
- `ExceptionInInitializerError`: exception inside a static block.
- `UnsupportedClassVersionError`: class compiled with newer Java than the one running it.

### Part D: Hierarchy in one view

```
Throwable
 ├── Error (do not handle)
 └── Exception
      ├── Checked: IOException, SQLException, InterruptedException, ...
      └── RuntimeException (unchecked)
           ├── NullPointerException
           ├── IllegalArgumentException
           └── ...
```

Important point: catching a parent also catches all its children. `catch (IOException e)` will also catch `FileNotFoundException`. Always put the child catch block first.

### Difference in THROW and THROWS

### Main difference

1. **`throw`** is a statement. It actually creates and throws one exception object at that moment.
2. **`throws`** is a warning in the method declaration. It tells the caller: “this method may throw this exception, so you must handle it.”

Simple way to remember: `throw` = do it, `throws` = may happen.

### Comparison table

| Point                           | `throw`                                   | `throws`                                        |
| ------------------------------- | ----------------------------------------- | ----------------------------------------------- |
| Where used                      | Inside method body                        | In method signature (after parentheses)         |
| Purpose                         | Throws an exception object                | Declares possible exceptions                    |
| Followed by                     | An object: `throw new Exception("msg")`   | Class names: `throws IOException, SQLException` |
| How many                        | One at a time                             | Many, separated by commas                       |
| Needed for checked exceptions   | Yes, either handle it or declare `throws` | Yes, this is how you declare                    |
| Needed for unchecked exceptions | Optional                                  | Optional                                        |

### Using `throw`

java

```java
public class Test {
    static void checkAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Age must be 18 or more");
        }
        System.out.println("Allowed");
    }

    public static void main(String[] args) {
        checkAge(15);
    }
}
```

`IllegalArgumentException` is unchecked, so `throws` is not required here.

### Using `throws`

java

```java
import java.io.*;

public class Test {
    static void readFile() throws FileNotFoundException {
        FileReader fr = new FileReader("data.txt");
    }

    public static void main(String[] args) {
        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
```

`readFile()` does not handle the exception. It passes the responsibility to the caller, and `main` handles it.

### Using both together (most common in real code)

This works well with the Bank Account class test idea. First make a custom checked exception:

java

```java
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}
```

Then the account class:

java

```java
class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    // throws: warning in signature
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            // throw: actually creates and throws the exception
            throw new InsufficientBalanceException("Not enough balance");
        }
        balance = balance - amount;
    }
}
```

And the caller:

java

```java
public class Main {
    public static void main(String[] args) {
        Account a = new Account(1000);
        try {
            a.withdraw(5000);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Output:**

```
Error: Not enough balance
```

### Step by step flow

1. `main` calls `withdraw(5000)`.
2. Inside `withdraw`, the `if` condition is true.
3. `throw new InsufficientBalanceException(...)` creates the object and throws it.
4. The method stops immediately. The next lines do not run.
5. Because the signature has `throws`, the compiler allows this and forces the caller to handle it.
6. `catch` in `main` receives the object and prints the message.

### Common mistakes students make

1. **Writing `throws` and `throw` mixed up.** `throw new ...` has `new` and an object. `throws` has only class names.
2. **Forgetting `throws` for a checked exception.** The code will not compile:

java

```java
static void test() {
    throw new Exception("error");   // compile error
}
```

Fix: add `throws Exception` or use `try-catch` inside.

3. **Writing code after `throw`.** Lines directly after `throw` are unreachable and give a compile error.
4. **Using `throws` with a new keyword:** `throws new Exception()` is wrong.

### Extra points

- `main` can also have `throws`: `public static void main(String[] args) throws IOException`. Then the JVM handles it, and the program crashes with an error message if the exception occurs. Fine for quick demos, but not good practice.
- You can re-throw a caught exception: `catch (IOException e) { throw e; }`.
- Overriding rule: an overriding method cannot declare a broader checked exception than the parent method.

