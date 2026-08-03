# Control Structures in Java — If-Else and Loops

#### 30-Jul-2026

## Topic: Decision making with if-else, and repetition with for, while, do-while loops

------

## 1. Control Structure — `if / else`

### 1.1 Plain `if`

```java
int marks = 75;
if (marks >= 40) {
    System.out.println("Pass");
}
```

If the condition in `( )` is true, the code inside `{ }` runs. If false, it is skipped.

### 1.2 `if / else`

```java
if (marks >= 40) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```

Exactly one of the two blocks runs — never both, never neither.

### 1.3 `if / else if / else` — multiple conditions

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

### 1.4 Nested `if`

An `if` statement can be placed inside another `if` block, to check a further condition only when the outer condition is already true.

```java
int num = 12;
if (num > 0) {
    if (num % 2 == 0) {
        System.out.println("Positive and Even");
    } else {
        System.out.println("Positive and Odd");
    }
} else {
    System.out.println("Not Positive");
}
```

### 1.5 Common mistake — semicolon after `if`

```java
if (marks > 40); {
    System.out.println("Pass");
}
```

The semicolon right after `if(...)` ends the if-statement immediately with an empty action. As a result, `"Pass"` will print **no matter what value `marks` has**, because the `{ }` block below now runs unconditionally, disconnected from the `if`. Always double check there is no semicolon right after an `if` condition.

------

## 2. Loops

Loops let you repeat a block of code without writing it out multiple times — for example, printing numbers 1 to 10 without writing `println` ten times.

### 2.1 `for` loop — best when you know how many times to repeat

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

### 2.2 `while` loop — best when repetitions depend on a condition

```java
int i = 1;
while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Same logic as `for`, written differently — initialization happens *before* the loop, condition is checked at the top, and the update is done manually *inside* the loop body.

**Caution:** forgetting `i++` inside a while loop causes an **infinite loop** — a very common bug. Always make sure the loop body changes something that eventually makes the condition false.

### 2.3 `do-while` loop — runs at least once

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

### 2.4 Nested loops

A loop can be placed inside another loop — the inner loop completes all its iterations for each single iteration of the outer loop. Commonly used for patterns and grids.

```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        System.out.print(i + "," + j + "  ");
    }
    System.out.println();
}
```

**Output:**

```
1,1  1,2  1,3  
2,1  2,2  2,3  
3,1  3,2  3,3  
```

### 2.5 Loop Comparison Table

| Loop       | Condition checked | Minimum runs | Best used when                    |
| ---------- | ----------------- | ------------ | --------------------------------- |
| `for`      | before each run   | 0            | number of repetitions is known    |
| `while`    | before each run   | 0            | repetitions depend on a condition |
| `do-while` | after each run    | 1            | the body must run at least once   |

------

## 3. Loop Control Keywords

### 3.1 `break` — exits the loop immediately

```java
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break;
    }
    System.out.println(i);
}
```

Prints `1 2 3 4` and stops — the loop ends completely the moment `i == 5`.

### 3.2 `continue` — skips the current iteration only

```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue;
    }
    System.out.println(i);
}
```

Prints `1 2 4 5` — when `i == 3`, that single iteration is skipped, but the loop continues with the next value.

------

## 4. Key Points to Remember

- `if / else if / else` checks top to bottom and stops at the first true condition.
- A semicolon right after an `if` condition silently breaks the logic — always check for this.
- Use `for` when the number of repetitions is known, `while` when it depends on a condition, and `do-while` when the body must run at least once.
- Forgetting the update step (like `i++`) in a `while` loop causes an infinite loop.
- Nested loops run the inner loop fully for every single iteration of the outer loop.
- `break` exits a loop completely; `continue` skips only the current iteration and moves to the next one.

------

## 5. Exercises

1. Write a program that prints all even numbers from 1 to 20 using a `for` loop.
2. Write a program that checks whether a number is prime using `if/else`.
3. Write a program using a `while` loop that prints the multiplication table of a number entered by the user.
4. Write a program using `do-while` that keeps asking the user to enter a number until they enter 0.
5. Write a program using nested loops to print a right-angled triangle pattern of stars.
6. Write a program that prints numbers from 1 to 50, but skips multiples of 3 using `continue`, and stops completely if the number exceeds 40 using `break`.
7. Write a program to count the odd and even numbers entered by the user, Ask user how many numbers they want to enter first.
8. Write a program to calculate sum of 1 to N ? (1 + 2 + 3 + ... N) ?
9. Write a program to calculate factorial of a given number ?
10. Write a program to check if entered number is palindrome ?

### Printing Patterns

<img src="Images\pattern1.jpg" alt="image-20260802223535603" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5;j++) {
        System.out.printf("%d ",j);
    }
    System.out.printlm("");
}
```

<img src="Images\pattern2.jpg" alt="image-20260802224301996" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5;j++) {
        System.out.printf("%d ",i+j);
    }
    System.out.printlm("");
}
```

<img src="Images\pattern3.jpg" alt="image-20260802224447791" style="zoom:25%;" />

```java
int count=1;
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5;j++) {
        System.out.printf("%d ",count);
        count++;
    }
    System.out.printlm("");
}
```

<img src="Images\pattern4.jpg" alt="image-20260802224636527" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=i;j++) {
        System.out.printf("%d ",j);
    }
    System.out.printlm("");
}
```

<img src="Images\pattern5.jpg" alt="image-20260802224848541" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5-i+1;j++) {
        System.out.printf("%d ",j);
    }
    System.out.printlm("");
}
```

<img src="Images\pattern6.jpg" alt="image-20260802225625374" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5;j++) {
        if (j > i) {
            System.out.printf("*");
        } else {
            System.out.printf(" ");
        }
    }
    System.out.printlm("");
}
```

<img src="Images\pattern7.jpg" alt="image-20260802230144740" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5;j++) {
        if (j+i > 5) {
            System.out.printf("*");
        } else {
            System.out.printf(" ");
        }
    }
    System.out.printlm("");
}
```

<img src="Images\pattern8.jpg" alt="image-20260802230644971" style="zoom:25%;" />

```java
for (int i =1;i<=5;i++) {
    for (int j=1;j<=5;j++) {

    }
    for (int k=1;k<=5;k++) {

    }
    
    System.out.printlm("");
}
```

