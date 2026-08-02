# Java Operators

#### 03-Aug-2026 

## What is an operator?

An operator is a symbol that performs an action on one or more values (called operands).

Example: In `a + b`, `+` is the operator, `a` and `b` are operands.

------

## 1. Arithmetic Operators

Used for basic math.

| Operator | Meaning             | Example (a=10, b=3) | Result                             |
| -------- | ------------------- | ------------------- | ---------------------------------- |
| `+`      | Addition            | a + b               | 13                                 |
| `-`      | Subtraction         | a - b               | 7                                  |
| `*`      | Multiplication      | a * b               | 30                                 |
| `/`      | Division            | a / b               | 3 (integer division drops decimal) |
| `%`      | Modulus (remainder) | a % b               | 1                                  |

**Teaching point:** `10 / 3` gives `3`, not `3.33`, because both are `int`. Ask students: "What if I want the decimal answer?" → Answer: make one operand a `double` (e.g. `10.0 / 3`).

```java
int a = 10, b = 3;
System.out.println(a / b);   // 3
System.out.println(a % b);   // 1
System.out.println(10.0 / 3); // 3.333...
```

------

## 2. Relational (Comparison) Operators

Used to compare two values. Always return `true` or `false` (boolean).

| Operator | Meaning               | Example | Result |
| -------- | --------------------- | ------- | ------ |
| `==`     | Equal to              | 5 == 5  | true   |
| `!=`     | Not equal to          | 5 != 3  | true   |
| `>`      | Greater than          | 5 > 3   | true   |
| `<`      | Less than             | 5 < 3   | false  |
| `>=`     | Greater than or equal | 5 >= 5  | true   |
| `<=`     | Less than or equal    | 5 <= 3  | false  |

**Common student mistake:** confusing `=` (assignment) with `==` (comparison). Worth pointing out explicitly.

------

## 3. Logical Operators

Combine multiple boolean conditions.

| Operator | Meaning                        | Example          | Result |
| -------- | ------------------------------ | ---------------- | ------ |
| `&&`     | Logical AND (both true)        | (5>3) && (2>1)   | true   |
| `||`     | Logical OR (at least one true) | (5>3) \|\| (1>2) | true   |
| `!`      | Logical NOT (reverses)         | !(5>3)           | false  |

**Teaching point — short-circuiting:** In `a && b`, if `a` is false, Java never checks `b`. In `a || b`, if `a` is true, Java never checks `b`. This matters for performance and avoiding errors (e.g. checking `x != null && x.value > 0`).

------

## 4. Assignment Operators

Used to assign values, often combined with arithmetic as a shortcut.

| Operator | Meaning             | Example | Same as   |
| -------- | ------------------- | ------- | --------- |
| `=`      | Assign              | a = 5   | —         |
| `+=`     | Add and assign      | a += 3  | a = a + 3 |
| `-=`     | Subtract and assign | a -= 3  | a = a - 3 |
| `*=`     | Multiply and assign | a *= 3  | a = a * 3 |
| `/=`     | Divide and assign   | a /= 3  | a = a / 3 |
| `%=`     | Modulus and assign  | a %= 3  | a = a % 3 |

------

## 5. Unary Operators

Act on a single operand.

| Operator | Meaning                     | Example    |
| -------- | --------------------------- | ---------- |
| `+`      | Unary plus (rarely used)    | +a         |
| `-`      | Unary minus (negates value) | -a         |
| `++`     | Increment by 1              | a++ or ++a |
| `--`     | Decrement by 1              | a-- or --a |
| `!`      | Logical complement          | !flag      |

**Pre vs Post increment — important to demo live:**

```java
int a = 5;
System.out.println(a++); // prints 5, THEN a becomes 6
System.out.println(a);   // 6

int b = 5;
System.out.println(++b); // b becomes 6 FIRST, then prints 6
```

Draw this on the board step-by-step — it's the #1 confusion point for beginners.

------

## 6. Ternary Operator

A shorthand for simple if-else. Syntax: `condition ? valueIfTrue : valueIfFalse`

```java
int a = 10, b = 20;
int max = (a > b) ? a : b;
System.out.println(max); // 20
```

Good line: "It's an if-else squeezed into one line, and it returns a value directly."

------

## 7. Bitwise Operators

Work on individual bits of integers. Usually a quick mention at this stage — deeper coverage comes with data representation topics.

| Operator | Meaning            |
| -------- | ------------------ |
| `&`      | Bitwise AND        |
| `|`      | Bitwise OR         |
| `^`      | Bitwise XOR        |
| `~`      | Bitwise complement |
| `<<`     | Left shift         |
| `>>`     | Right shift        |

Example: `5 & 3` → in binary `101 & 011` = `001` = `1`

You can keep this brief for a first lecture — just define each and give one example. Students rarely need depth here early on.

------

## 8. Operator Precedence (order of evaluation)

When multiple operators appear in one expression, Java follows a fixed order:

1. `()` parentheses — highest priority
2. `++` `--` `!` (unary)
3. `*` `/` `%`
4. `+` `-`
5. `<` `<=` `>` `>=`
6. `==` `!=`
7. `&&`
8. `||`
9. `=` `+=` etc. — lowest priority

**Classic example to put on the board:**

```java
int result = 10 + 2 * 5;  // 20, not 60 — multiplication happens first
int result2 = (10 + 2) * 5; // 60 — parentheses override precedence
```

------

### 1. Simple Calculator

```java
public class Calculator {
    public static void main(String[] args) {
        int a = 15, b = 4;
        System.out.println("Sum: " + (a + b));
        System.out.println("Difference: " + (a - b));
        System.out.println("Product: " + (a * b));
        System.out.println("Quotient: " + (a / b));
        System.out.println("Remainder: " + (a % b));
    }
}
```

### 2. Check Even or Odd (modulus)

```java
public class EvenOdd {
    public static void main(String[] args) {
        int num = 17;
        if (num % 2 == 0) {
            System.out.println(num + " is even");
        } else {
            System.out.println(num + " is odd");
        }
    }
}
```

### 3. Find the Largest of Three Numbers (relational + logical)

```java
public class LargestOfThree {
    public static void main(String[] args) {
        int a = 12, b = 45, c = 33;
        if (a >= b && a >= c) {
            System.out.println("Largest: " + a);
        } else if (b >= a && b >= c) {
            System.out.println("Largest: " + b);
        } else {
            System.out.println("Largest: " + c);
        }
    }
}
```

### 4. Swap Two Numbers Without a Third Variable (arithmetic)

```java
public class SwapNumbers {
    public static void main(String[] args) {
        int a = 5, b = 10;
        System.out.println("Before: a=" + a + " b=" + b);
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.println("After: a=" + a + " b=" + b);
    }
}
```

### 5. Simple Interest Calculator (arithmetic with decimals)

```java
public class SimpleInterest {
    public static void main(String[] args) {
        double principal = 10000, rate = 5.5, time = 3;
        double interest = (principal * rate * time) / 100;
        System.out.println("Simple Interest: " + interest);
    }
}
```

### 6. Grade Checker Using Ternary Operator

```java
public class GradeChecker {
    public static void main(String[] args) {
        int marks = 78;
        String grade = (marks >= 90) ? "A" : (marks >= 75) ? "B" : (marks >= 60) ? "C" : "F";
        System.out.println("Grade: " + grade);
    }
}
```

### 7. Counter Using Increment/Decrement

```java
public class Counter {
    public static void main(String[] args) {
        int count = 0;
        count++;
        count++;
        count++;
        System.out.println("Count after 3 increments: " + count);

        count--;
        System.out.println("Count after 1 decrement: " + count);
    }
}
```

### 8. Leap Year Checker (logical + modulus)

```java
public class LeapYear {
    public static void main(String[] args) {
        int year = 2024;
        boolean isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        System.out.println(year + " is leap year: " + isLeap);
    }
}
```

### 9. Toggle a Boolean Flag (logical NOT)

```java
public class ToggleFlag {
    public static void main(String[] args) {
        boolean isLightOn = true;
        System.out.println("Light on: " + isLightOn);
        isLightOn = !isLightOn;
        System.out.println("Light on: " + isLightOn);
    }
}
```

### 10. Temperature Converter (arithmetic + assignment operators)

```java
public class TempConverter {
    public static void main(String[] args) {
        double celsius = 37;
        double fahrenheit = (celsius * 9 / 5) + 32;
        System.out.println(celsius + "°C = " + fahrenheit + "°F");
    }
}
```