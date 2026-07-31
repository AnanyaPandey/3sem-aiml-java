# Data Types in Java and Memory Storage

## Topic: Primitive vs Non-Primitive types, memory size, and how they are stored (Stack vs Heap)

------

## 1. The Big Split — Primitive vs Non-Primitive

Java has two categories of data types:

1. **Primitive types** — 8 built-in types that store the actual value directly.
2. **Non-primitive (reference) types** — everything else (String, arrays, objects, classes). These store a *reference (address)* pointing to where the real data lives, not the value itself.

This split explains why memory behaves differently for `int x = 5;` compared to `String s = "Ravi";`.

------

## 2. The 8 Primitive Types

| Type      | Size                                  | Range                           | Default value |
| --------- | ------------------------------------- | ------------------------------- | ------------- |
| `byte`    | 1 byte (8 bits)                       | -128 to 127                     | 0             |
| `short`   | 2 bytes (16 bits)                     | -32,768 to 32,767               | 0             |
| `int`     | 4 bytes (32 bits)                     | ≈ -2.1 billion to 2.1 billion   | 0             |
| `long`    | 8 bytes (64 bits)                     | ≈ -9.2×10¹⁸ to 9.2×10¹⁸         | 0L            |
| `float`   | 4 bytes (32 bits)                     | ~6-7 decimal digits precision   | 0.0f          |
| `double`  | 8 bytes (64 bits)                     | ~15-16 decimal digits precision | 0.0d          |
| `char`    | 2 bytes (16 bits)                     | 0 to 65,535 (Unicode)           | '\u0000'      |
| `boolean` | not precisely defined (JVM-dependent) | true / false                    | false         |

**Pattern to notice:** the whole-number types double in size as you go: `byte`(1) → `short`(2) → `int`(4) → `long`(8). This makes the sizes easier to remember instead of arbitrary.

### A few important details

- **Why is `char` 2 bytes, not 1 like in C?** Java uses Unicode (to support characters from many languages worldwide), not just ASCII. This needs 16 bits instead of 8.
- **`float` vs `double`:** Both store decimal numbers, but `float` is less precise and less commonly used in practice. `double` is Java's default for decimal numbers — this is why writing `3.14` alone is treated as `double` unless you write `3.14f`.
- **`boolean`:** Doesn't have a fixed standardized size like the others — the JVM decides internally how to store it, since it only needs to represent two states (`true`/`false`). Its exact size isn't something you need to worry about.

------

## 3. How Primitives Are Stored — The Stack

**Primitive variables are stored directly on the Stack.**

```java
int age = 20;
double marks = 88.5;
```

Stack:

```
age   → 20
marks → 88.5
```

The actual value sits right there in the variable's memory slot. No separate lookup is needed — when you use `age`, Java goes straight to that stack slot and reads `20` directly.

**Why this matters:** stack memory is fast, and each variable's memory is automatically freed when it goes out of scope (e.g., when a method finishes running). This is why primitives are lightweight and quick to use.

------

## 4. How Non-Primitives Are Stored — Stack + Heap

Now contrast this with a reference type:

```java
String name = "Ravi";
```

Here, two things happen:

- The **actual string data** `"Ravi"` is stored in the **Heap** (a separate memory area for objects).
- The variable `name` sits on the **Stack**, but instead of holding the data itself, it holds the **address (reference)** pointing to where `"Ravi"` lives in the Heap.

```
Stack:                     Heap:
name  → [address] ------→  "Ravi"
```

**Analogy:** A primitive variable is like a locker that directly contains your item. A reference variable is like a piece of paper in the locker with an address written on it — the actual item is stored elsewhere, in a warehouse (the Heap), and the paper just tells you where to find it.

This is also why arrays and objects (anything created with `new`) go on the Heap, while the reference variable pointing to them sits on the Stack.

------

## 5. Stack vs Heap in Action — Copying Values vs Copying References

### 5.1 Copying a primitive copies the value

```java
int a = 10;
int b = a;
b = 20;
System.out.println(a); // still 10 — a and b are independent
```

`a` and `b` are two separate slots on the Stack, each with its own value. Changing `b` has no effect on `a`.

### 5.2 Copying a reference copies the address, not the object

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = arr1;
arr2[0] = 99;
System.out.println(arr1[0]); // prints 99!
```

`arr1` and `arr2` are two separate variables on the Stack, but both hold the **same address**, pointing to the **same array object** in the Heap. Changing the array through `arr2` also changes what `arr1` sees, because there is really only one array — just two references pointing to it.

------

## 6. Summary Table

| Feature         | Primitive types                  | Non-primitive types                     |
| --------------- | -------------------------------- | --------------------------------------- |
| Examples        | int, double, char, boolean, etc. | String, arrays, objects, classes        |
| Stores          | actual value                     | reference (address) to the data         |
| Memory location | Stack                            | Reference on Stack, actual data on Heap |
| Speed           | faster                           | slightly slower (extra lookup step)     |
| Default value   | 0 / false / etc.                 | `null`                                  |

------

## 7. Key Points to Remember

- Java has 8 primitive types: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`.
- Whole-number type sizes double: 1, 2, 4, 8 bytes for `byte`, `short`, `int`, `long`.
- `char` is 2 bytes in Java because it supports Unicode, not just ASCII.
- `double` is Java's default type for decimal numbers; use `f` suffix (e.g., `3.14f`) for `float`.
- Primitives are stored directly on the Stack with the actual value.
- Non-primitives (String, arrays, objects) store a reference on the Stack, while the real data lives on the Heap.
- Copying a primitive copies the value (independent copies); copying a reference copies the address (both variables point to the same object).

------

## 8. Exercises

1. Write a program that declares one variable of each primitive type, prints its value, and prints its size using `Type.SIZE` (in bits) or `Type.BYTES` (e.g., `Integer.SIZE`, `Integer.BYTES`).
2. Write a program that creates two `int` variables, copies one into the other, changes the copy, and prints both to show they are independent.
3. Write a program that creates two array references pointing to the same array, changes an element through one reference, and prints the array through the other reference to show the change reflects in both.
4. Write a program that declares a `float` and a `double` with the same decimal value and prints both, to observe the difference in precision.