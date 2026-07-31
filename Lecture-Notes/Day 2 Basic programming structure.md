# Basic Structure of a Java Program

## Topic: Writing your first program, syntax rules, if-else, loops, and use of semicolon

------

## 1. The Basic Structure (Skeleton) of a Java Program

Every Java program you write in this course will follow the same shape:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Once you understand this skeleton, everything else is just filling it in.

### 1.1 The class

```java
public class HelloWorld { ... }
```

Java is an *object-oriented* language, so every piece of code must live inside a class — even a program that just prints one line.

**Important rule:** the file name must match the class name exactly. This class must be saved as `HelloWorld.java`.

### 1.2 The main method

```java
public static void main(String[] args) { ... }
```

This is the **entry point** of the program — the exact place where Java starts running your code. No matter how many classes or methods a program has, execution always begins at `main`.

### 1.3 The statements inside

The actual instructions, such as `System.out.println(...)`, go inside `main`'s curly braces `{ }`.

------

## 2. `public static void main(String[] args)` Explained Word by Word

| Part              | Meaning                                                      |
| ----------------- | ------------------------------------------------------------ |
| `public`          | Access modifier — this method can be called from **anywhere**, including by Java itself. It must be public so the Java runtime can start it. |
| `static`          | This method belongs to the **class itself**, not to any object. Java can run it without first creating an object — which is important because at the very start, no object exists yet. |
| `void`            | This method **doesn't return any value**. `main` just runs and finishes. |
| `main`            | The exact name Java looks for as the starting point. Must be spelled exactly `main` (lowercase). |
| `(String[] args)` | A list of command-line arguments the program can receive when started. You don't need to use this yet — just always write it this way. |

**Memory hook:** `public static void main` is Java's fixed doorway — every program's execution enters through this exact doorway.

------

## 3. Writing and Running Your First Program

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Java requires two separate steps to run a program:

```
javac HelloWorld.java     → compiles into HelloWorld.class
java HelloWorld           → runs the compiled program
```

Java is a *compiled + interpreted* language — your code first compiles into bytecode (`.class` file), and then the JVM (Java Virtual Machine) runs that bytecode.

------

## 4. Use of Semicolon `;`

- A semicolon marks the **end of one statement** — like a full stop at the end of a sentence.

- Every executable statement needs one:

  ```java
  System.out.println("Hi");int x = 5;
  ```

- Lines that open a block do **NOT** end with a semicolon, because they are not statements — they open a block that continues with `{ }`:

  ```java
  class HelloWorld {          // no semicolonpublic static void main(String[] args) {   // no semicolonif (x > 5) {                 // no semicolon
  ```

### Common mistake to avoid

```java
if (marks > 40); {
    System.out.println("Pass");
}
```

Here, the semicolon right after `if(...)` ends the if-statement immediately with an empty action. As a result, `"Pass"` will print **no matter what value `marks` has**, because the `{ }` block below now runs unconditionally, disconnected from the `if`. Always double check you have not placed a semicolon right after an `if` condition.

------

## 5. Control Structure — `if / else`

### 5.1 Plain `if`

```java
int marks = 75;
if (marks >= 40) {
    System.out.println("Pass");
}
```

If the condition in `( )` is true, the code inside `{ }` runs. If false, it is skipped.

### 5.2 `if / else`

```java
if (marks >= 40) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```

Exactly one of the two blocks runs — never both, never neither.

### 5.3 `if / else if / else` — multiple conditions

```java
if (marks >= 90) {
    System.out.println("Grade A");
} else if (marks >= 75) {
    System.out.println("Grade B");
} else if (marks >= 40) {
    System.out.println("Grade C");
} else {
    System.out.println("Fail");
}
```

Java checks conditions **top to bottom**, and stops at the first one that is true. Even if a later condition would also be true, it is never checked once an earlier one matches. For example, with `marks = 95`, the program prints `"Grade A"` and does not check the remaining conditions at all.

------

## 6. Loops

Loops let you repeat a block of code without writing it out multiple times — for example, printing numbers 1 to 10 without writing `println` ten times.

### 6.1 `for` loop — best when you know how many times to repeat

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

The three parts inside `( )`:

| Part        | Meaning                                  | Runs when?                  |
| ----------- | ---------------------------------------- | --------------------------- |
| `int i = 1` | initialization — starting value          | once, at the very beginning |
| `i <= 5`    | condition — checked before each loop run | before every iteration      |
| `i++`       | update — changes `i`                     | after every iteration       |

**Trace table for the example above:**

| i value | condition `i<=5`? | printed    | i becomes |
| ------- | ----------------- | ---------- | --------- |
| 1       | true              | 1          | 2         |
| 2       | true              | 2          | 3         |
| 3       | true              | 3          | 4         |
| 4       | true              | 4          | 5         |
| 5       | true              | 5          | 6         |
| 6       | false             | loop stops | —         |

### 6.2 `while` loop — best when repetitions depend on a condition

```java
int i = 1;
while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Same logic as `for`, written differently — initialization happens *before* the loop, condition is checked at the top, and the update is done manually *inside* the loop body.

**Caution:** forgetting `i++` inside a while loop causes an **infinite loop** — a very common bug. Always make sure the loop body changes something that eventually makes the condition false.

### 6.3 `do-while` loop — runs at least once

```java
int i = 1;
do {
    System.out.println(i);
    i++;
} while (i <= 5);
```

Key difference: the condition is checked **after** the body runs, so the body always executes at least once — even if the condition is false from the start.

Example where the condition is false immediately:

```java
int i = 10;
do {
    System.out.println(i);
    i++;
} while (i <= 5);
```

This still prints `10` once, even though `10 <= 5` is false, because `do-while` always runs the body at least one time before checking.

### 6.4 Loop Comparison Table

| Loop       | Condition checked | Minimum runs | Best used when                    |
| ---------- | ----------------- | ------------ | --------------------------------- |
| `for`      | before each run   | 0            | number of repetitions is known    |
| `while`    | before each run   | 0            | repetitions depend on a condition |
| `do-while` | after each run    | 1            | the body must run at least once   |

------

## 7. Key Points to Remember

- Every Java program is wrapped inside a class, and the file name must match the class name.
- `main` is the fixed entry point of every Java program: `public static void main(String[] args)`.
- Compiling and running are two separate steps: `javac` then `java`.
- Every statement ends with a semicolon `;`; block-opening lines (`class`, `main`, `if`, loops) do not.
- Never place a semicolon right after an `if` condition — it silently breaks the logic.
- `if / else if / else` checks top to bottom and stops at the first true condition.
- Use `for` when the number of repetitions is known, `while` when it depends on a condition, and `do-while` when the body must run at least once.
- Forgetting the update step (like `i++`) in a `while` loop causes an infinite loop.

------

## 8. Exercises

1. Write a program that prints all even numbers from 1 to 20 using a `for` loop.

2. Write a program that checks whether a number is prime using `if/else`.

3. Write a program using a `while` loop that prints the multiplication table of a number entered by the user.

4. Write a program using `do-while` that keeps asking the user to enter a number until they enter 0.

5. Take a number as input and print its multiplication table from 1 to 10 in a neatly aligned format using `printf`.

6. Take a number as input and print whether it is positive, negative, or zero. If it is positive, also print whether it is even or odd.

7. Take the number of rows as input and print a right-angled triangle pattern of stars.

8. Take the number of subjects as input, then take that many marks one by one, calculate the total and average, and print the average with 2

   decimal places along with a grade.

9. Fix a secret number in the code. Keep asking the user to guess it, telling them "Too high" or "Too low" each time, and stop when they guess correctly.