# Arrays in Java

## What is an Array?

An array is a container that holds a **fixed number of values of the same type**, stored together in memory, and accessed using an **index**.

Instead of creating separate variables for related data:

```java
int marks1 = 90;
int marks2 = 85;
int marks3 = 70;
// ... and so on for 100 students
```

You can store all values under a single array name:

```java
int[] marks = new int[100];
```

## Declaring and Creating Arrays

Declaration and creation are two separate steps:

```java
int[] marks;          // declaration
marks = new int[5];   // creation - allocates 5 slots
```

They can also be combined:

```java
int[] marks = new int[5];
```

**Note:** `int[] marks` and `int marks[]` are both valid syntax in Java, but `int[] marks` is the preferred style.

## Array Indexing

Array indices start at **0**, not 1.

```java
int[] marks = new int[5];   // valid indices: 0, 1, 2, 3, 4

marks[0] = 90;
marks[4] = 75;
```

Accessing an invalid index throws a runtime exception:

```java
marks[5] = 100;   // ArrayIndexOutOfBoundsException
```

## Default Values

When an array is created with `new`, Java automatically fills it with default values:

| Type                               | Default value |
| ---------------------------------- | ------------- |
| `int`, `double`, etc. (numeric)    | `0`           |
| `boolean`                          | `false`       |
| Object references (`String`, etc.) | `null`        |

## Array Literals

If you know the values in advance, you can initialize an array directly:

```java
int[] marks = {90, 85, 70, 60, 75};
```

## The `length` Property

Every array has a `length` property (not a method — no parentheses):

```java
System.out.println(marks.length);   // 5
```

**Note:** This is different from `String`, where `length()` is a method. Be careful not to write `marks.length()` — it will not compile.

## Looping Through Arrays

**Standard `for` loop** (use when you need the index):

```java
for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}
```

**Enhanced `for-each` loop** (use when you only need the values):

```java
for (int m : marks) {
    System.out.println(m);
}
```

## Arrays Are Objects

Even arrays of primitive types (like `int[]`) are objects in Java:

- Arrays are stored on the heap.
- Passing an array to a method passes a **reference** to it — changes made inside the method affect the original array.
- Comparing arrays with `==` compares references, not their contents.

## Printing Arrays

Printing an array directly does not show its contents:

```java
int[] marks = {90, 85, 70};
System.out.println(marks);   // prints something like [I@1b6d3586
```

Use `Arrays.toString()` instead:

```java
import java.util.Arrays;

System.out.println(Arrays.toString(marks));   // [90, 85, 70]
```

## Useful Array Methods (`java.util.Arrays`)

```java
import java.util.Arrays;

Arrays.sort(marks);                     // sorts the array in place
System.out.println(Arrays.toString(marks));
```

## Multi-Dimensional Arrays

A 2D array is essentially an "array of arrays."

```java
int[][] grid = new int[3][4];   // 3 rows, 4 columns
grid[0][0] = 1;
grid[2][3] = 99;
```

Using literals:

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};
```

Example  Addition

```java

int[][] a = {{1, 2}, {3, 4}};
int[][] b = {{5, 6}, {7, 8}};
int[][] sum = new int[2][2];

for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
        sum[i][j] = a[i][j] + b[i][j];
    }
}
```



## Summary

- Arrays store multiple values of the same type under one name.
- Indices start at 0.
- Size is fixed once created.
- `length` is a property, not a method.
- Use `Arrays.toString()` to print array contents.
- Arrays are objects and are passed by reference.

## Exercises

**Basic (index/loop practice)**

1. Create an array of 10 integers, take input from the user, and print them in reverse order.
2. Find the sum and average of all elements in an array.
3. Find the largest and smallest element in an array.
4. Count how many even and odd numbers are in an array.
5. Copy all elements of one array into another array.

**Searching and sorting**

6. Search for a given number in an array using linear search (print index if found, else "not found").
7. Sort an array in ascending order without using `Arrays.sort()` (implement bubble sort or selection sort manually).
8. Given a sorted array, implement binary search to find a target value.

**Manipulation**

9. Reverse an array in place (without creating a new array).
10. Remove duplicate elements from an array and print the result.
11. Merge two arrays into a third array.
12. Left-rotate an array by `k` positions.
13. Find the second largest element in an array without sorting.

**Logic-building**

14. Check if an array is a palindrome (reads the same forwards and backwards).
15. Find all pairs of elements in an array whose sum equals a given target number.
16. Count the frequency of each element in an array (how many times each number appears).
17. Find the missing number in an array containing 1 to N with one number missing.

**2D arrays**

18. Create a 3x3 matrix, take input from the user, and print it in matrix format.
19. Add two matrices of the same size.
20. Find the transpose of a matrix.
21. Print the sum of the diagonal elements of a square matrix.

**Mini project-style (good for take-home)**

22. Store marks of 5 students in an array, and print each student's grade based on marks (using if-else with array traversal).
23. Build a simple "array-based to-do list" — store tasks as strings in an array and let the user add/view them (introduces array of `String`, ties into loops).

Want me to turn a subset of these into a markdown practice sheet (with just the questions, no solutions) for students, or one with solutions for your own reference?