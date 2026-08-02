# Java Console Input and Output

#### 29-Jul-2026

## Topic: Reading input with `System.in`, writing output with `System.out`

------

## 1. Output in Java

Java gives us three main ways to print output. All three belong to `System.out`.

### 1.1 `System.out.print()`

- Prints text as it is.
- Does **not** move to a new line after printing.

```java
System.out.print("Hello");
System.out.print("World");
```

**Output:**

```
HelloWorld
```

### 1.2 `System.out.println()`

- Same as `print()`, but moves the cursor to a new line after printing.
- "ln" means "line".

```java
System.out.println("Hello");
System.out.println("World");
```

**Output:**

```
Hello
World
```

### 1.3 `System.out.printf()`

- Used for **formatted** output.
- Works like `printf` in C.
- Uses format specifiers (placeholders) that get replaced by values.

```java
int age = 21;
double marks = 89.456;
System.out.printf("Age: %d, Marks: %.2f%n", age, marks);
```

**Output:**

```
Age: 21, Marks: 89.46
```

#### Common format specifiers

| Specifier | Meaning                    | Example           |
| --------- | -------------------------- | ----------------- |
| `%d`      | integer                    | `%d` → 21         |
| `%f`      | floating point             | `%f` → 89.456000  |
| `%.2f`    | float, 2 decimals          | `%.2f` → 89.46    |
| `%s`      | string                     | `%s` → "Ravi"     |
| `%c`      | character                  | `%c` → 'A'        |
| `%n`      | new line (safer than `\n`) | goes to next line |

### 1.4 Quick comparison

| Method      | New line after? | Formatting support? |
| ----------- | --------------- | ------------------- |
| `print()`   | No              | No                  |
| `println()` | Yes             | No                  |
| `printf()`  | No (use `%n`)   | Yes                 |

------

## 2. Input in Java — `System.in`

- `System.in` is a raw **InputStream**. On its own it only reads raw bytes, which is inconvenient.
- So we wrap it inside helper classes that make reading easier.
- The most common and beginner-friendly way is the **`Scanner`** class.

### 2.1 Using `Scanner` (recommended for beginners)

Steps:

1. Import the Scanner class.
2. Create a Scanner object, wrapping `System.in`.
3. Use Scanner methods to read the type of data you need.
4. Close the Scanner when done (good practice).

```java
import java.util.Scanner;

public class InputDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        System.out.print("Enter your CGPA: ");
        double cgpa = sc.nextDouble();

        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("CGPA: " + cgpa);

        sc.close();
    }
}
```

### 2.2 Common Scanner methods

| Method          | Reads                                |
| --------------- | ------------------------------------ |
| `nextInt()`     | an integer                           |
| `nextDouble()`  | a decimal number                     |
| `nextFloat()`   | a float value                        |
| `nextLong()`    | a long integer                       |
| `next()`        | a single word (stops at space)       |
| `nextLine()`    | a full line of text (stops at Enter) |
| `nextBoolean()` | true/false                           |

### 2.3 Important trap: mixing `nextInt()` and `nextLine()`

`nextInt()` reads only the number and leaves the "Enter key" (newline) unread in the buffer. If you call `nextLine()` right after, it reads that leftover newline instead of waiting for new input — so it appears to get skipped.

**Problem example:**

```java
Scanner sc = new Scanner(System.in);
System.out.print("Enter age: ");
int age = sc.nextInt();

System.out.print("Enter name: ");
String name = sc.nextLine();   // this gets skipped!
```

**Fix:** add an extra `sc.nextLine()` to consume the leftover newline.

```java
int age = sc.nextInt();
sc.nextLine();   // clears leftover newline

System.out.print("Enter name: ");
String name = sc.nextLine();   // now works correctly
```

### 2.4 Other ways to read input (for awareness, not needed on day one)

- **`BufferedReader` + `InputStreamReader`**: faster, reads text but everything comes as a `String` (you must convert to int/double yourself using `Integer.parseInt()` etc.). Good for competitive programming.
- **`Console` class**: used for reading passwords securely (input is hidden). Does not work in some IDEs.

------

## 3. Practice Program (put both input and output together)

```java
import java.util.Scanner;

public class StudentRecord {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter roll number: ");
        int roll = sc.nextInt();
        sc.nextLine(); // clear buffer

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter marks: ");
        double marks = sc.nextDouble();

        System.out.println("\n--- Student Record ---");
        System.out.printf("Roll No : %d%n", roll);
        System.out.printf("Name    : %s%n", name);
        System.out.printf("Marks   : %.2f%n", marks);

        sc.close();
    }
}
```

------

## 4. Key Points to Remember

- `System.out.print` → no new line.
- `System.out.println` → new line after.
- `System.out.printf` → formatted output using `%d`, `%f`, `%s`, etc.
- `Scanner` is the simplest way to read console input; import it from `java.util.Scanner`.
- Always match the Scanner method to the data type you expect (`nextInt()` for int, `nextLine()` for a full line of text).
- Watch out for the `nextInt()` / `nextLine()` buffer issue — a very common bug for beginners.
- Close the Scanner (`sc.close()`) once input reading is done.

------

## 5. Possible Class Exercise Ideas

1. Take two numbers as input and print their sum, difference, product using `printf`.
2. Take a student's name, roll number, and 3 subject marks; print a formatted mark sheet.
3. Ask the user for their name and age; print a formatted sentence like: `"Ravi is 20 years old."`