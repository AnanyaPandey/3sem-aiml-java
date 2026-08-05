

# Formatted Printing and String Methods in Java

#### 03-Aug-2026

## Topic: printf format specifiers, argument indexing, and common String methods

------

## 1. Formatted Printing with `printf`

`printf` prints text using **format specifiers** — placeholders that get replaced by actual values, in the order you list them.

```java
System.out.printf("Name: %s, Age: %d%n", "Ravi", 20);
```

### 1.1 Common Format Specifiers

| Specifier | Meaning                                                     | Example             |
| --------- | ----------------------------------------------------------- | ------------------- |
| `%d`      | integer                                                     | `%d` → 21           |
| `%f`      | floating point (6 decimals by default)                      | `%f` → 89.456000    |
| `%.2f`    | float, 2 decimals                                           | `%.2f` → 89.46      |
| `%s`      | string                                                      | `%s` → "Ravi"       |
| `%c`      | character                                                   | `%c` → 'A'          |
| `%b`      | boolean                                                     | `%b` → true         |
| `%x`      | integer in hexadecimal                                      | `%x` → 1f           |
| `%o`      | integer in octal                                            | `%o` → 17           |
| `%e`      | scientific notation                                         | `%e` → 8.945600e+01 |
| `%n`      | new line (platform-safe, preferred over `\n` inside printf) | goes to next line   |

### 1.2 Width and Alignment

You can control how much space a value takes up, and whether it's left or right aligned.

```java
System.out.printf("[%10d]%n", 45);     // right-aligned, width 10 → [        45]
System.out.printf("[%-10d]%n", 45);    // left-aligned, width 10  → [45        ]
System.out.printf("[%10s]%n", "hi");   // right-aligned string    → [        hi]
System.out.printf("[%-10s]%n", "hi");  // left-aligned string     → [hi        ]
```

This is very useful for printing neat tables — e.g., a multiplication table or a mark sheet where columns need to line up.

### 1.3 Argument Indexing — `%1$d`, `%2$s`, etc.

Normally, `printf` matches specifiers to arguments **in order**, left to right. But sometimes you want to reuse the same value more than once, or print them in a different order than they were passed. Argument indexing lets you pick exactly which argument a specifier refers to, using `%<position>$<specifier>`.

```java
System.out.printf("%1$d + %1$d = %2$d%n", 5, 10);
// Output: 5 + 5 = 10
```

Here, `%1$d` refers to the **1st** argument (`5`), used twice, and `%2$d` refers to the **2nd** argument (`10`).

Another example — printing values in reverse order:

```java
System.out.printf("%2$s is %1$d years old.%n", 20, "Ravi");
// Output: Ravi is 20 years old.
```

Even though `20` was passed first and `"Ravi"` second, `%2$s` picks the 2nd argument (`"Ravi"`) and `%1$d` picks the 1st (`20`).

**When this is useful:** repeating the same value multiple times in one sentence without passing it multiple times, or rearranging output order without rearranging your arguments.

### 1.4 Padding Numbers with Zeros

```java
System.out.printf("%05d%n", 42);  // 00042
```

`%05d` means: print as an integer, minimum width 5, pad with leading zeros. Useful for things like roll numbers, IDs, or timestamps.

------

## 2. String Methods

Strings in Java come with many built-in methods to inspect, transform, and search text. Here are the most useful ones, grouped by purpose.

### 2.1 Changing Case

```java
String name = "Hello World";

System.out.println(name.toUpperCase());  // HELLO WORLD
System.out.println(name.toLowerCase());  // hello world
```

### 2.2 Length and Character Access

```java
String s = "Hello";

System.out.println(s.length());     // 5
System.out.println(s.charAt(1));    // e (index starts at 0)
```

### 2.3 Trimming Whitespace

```java
String s = "   Hello   ";
System.out.println(s.trim());      // "Hello" (removes leading/trailing spaces)
```

### 2.4 Substring — extracting part of a string

```java
String s = "Hello World";

System.out.println(s.substring(6));      // World (from index 6 to end)
System.out.println(s.substring(0, 5));   // Hello (index 0 up to, not including, 5)
```

### 2.5 Searching Within a String

```java
String s = "Hello World";

System.out.println(s.contains("World"));      // true
System.out.println(s.indexOf("World"));       // 6 (starting index where it's found)
System.out.println(s.startsWith("Hello"));    // true
System.out.println(s.endsWith("World"));      // true
```

`indexOf()` searches from the **beginning** of the string and returns the position of the **first** match. If the text isn't found, it returns `-1`.

```java
String s = "banana";
System.out.println(s.indexOf("a"));       // 1 (first 'a')
System.out.println(s.lastIndexOf("a"));   // 5 (last 'a')
System.out.println(s.indexOf("z"));       // -1 (not found)
```

`lastIndexOf()` works the same way as `indexOf()`, but searches from the **end** of the string and returns the position of the **last** match instead of the first.

### 2.6 Comparing Strings

```java
String a = "hello";
String b = "HELLO";

System.out.println(a.equals(b));            // false (case-sensitive)
System.out.println(a.equalsIgnoreCase(b));  // true (ignores case)
System.out.println(a.compareTo(b)); // Returns 0 if both are same  
// Returns the difference between the ASCII code number if both are differnt. 
// e.g. 
a = "Pyramid"; // ASCII 80 121 114 97 109 105 100
b = "pyramid"; // ASCC 112 121 114 97 109 105 100
// returns 32
```

**Important note:** never compare strings using `==` — that compares references (memory addresses), not actual text content. Always use `.equals()` for comparing string values. This is a very common beginner mistake worth remembering.

### 2.7 Replacing Text

```java
String s = "Hello World";
System.out.println(s.replace("World", "Java"));  // Hello Java
```

### 2.8 Splitting a String

```java
String s = "apple,banana,mango";
String[] fruits = s.split(",");
// fruits = ["apple", "banana", "mango"]

for (String fruit : fruits) {
    System.out.println(fruit);
}
```

### 2.9 Joining/Concatenation

```java
String first = "Hello";
String second = "World";

System.out.println(first + " " + second);        // Hello World (using +)
System.out.println(first.concat(" ").concat(second)); // Hello World (using concat)
```

### 2.10 Checking if Empty

```java
String s = "";
System.out.println(s.isEmpty());       // true
System.out.println(s.isBlank());       // true (also true for strings with only spaces)
```

### 2.11 Converting Other Types to/from String

```java
int num = 25;
String s1 = String.valueOf(num);   // int to String → "25"

String s2 = "100";
int n = Integer.parseInt(s2);      // String to int → 100
```

------

## 3. Quick Reference Table — String Methods

| Method                                      | Purpose                                  |
| ------------------------------------------- | ---------------------------------------- |
| `toUpperCase()` / `toLowerCase()`           | change case                              |
| `length()`                                  | number of characters                     |
| `charAt(index)`                             | get character at a position              |
| `trim()`                                    | remove leading/trailing spaces           |
| `substring(start)` / `substring(start,end)` | extract part of string                   |
| `contains(text)`                            | check if text exists inside              |
| `indexOf(text)`                             | find position of the first match of text |
| `lastIndexOf(text)`                         | find position of the last match of text  |
| `startsWith(text)` / `endsWith(text)`       | check beginning/end                      |
| `equals(other)`                             | compare string content (case-sensitive)  |
| `equalsIgnoreCase(other)`                   | compare content ignoring case            |
| `replace(old, new)`                         | replace text                             |
| `split(delimiter)`                          | break into an array of parts             |
| `concat(other)`                             | join two strings                         |
| `isEmpty()` / `isBlank()`                   | check if string has no content           |
| `String.valueOf(x)`                         | convert other types to String            |
| `Integer.parseInt(s)`                       | convert String to int                    |
| string1.compareTo(string2)                  | Compares the ascii value of both         |

------

## 4. Key Points to Remember

- `printf` uses format specifiers like `%d`, `%f`, `%s`, `%c` to insert values into a formatted string.
- Width can be controlled (`%10d` right-aligned, `%-10d` left-aligned), and numbers can be zero-padded (`%05d`).
- Argument indexing (`%1$d`, `%2$s`) lets you pick a specific argument by position, and reuse or reorder values.
- Always use `.equals()` to compare string content — never `==`.
- `substring(start, end)` includes `start` but excludes `end`.
- `split()` returns an array of strings, useful for breaking up comma/space-separated data.

------

### StringBuilder

`StringBuilder` is a separate class from `String`, so it wouldn't show up if the lesson only covered `String` class methods. That's exactly why it wasn't mentioned there.

**Why it exists:**

In Java, `String` objects are **immutable** — once created, their content can never change. Every time you do something like `s = s + "x"` or `s.toUpperCase()`, Java isn't modifying `s` — it's creating a *brand new* String object in memory and reassigning the reference. Do that in a loop a thousand times, and you've created a thousand throwaway String objects, which wastes memory and slows things down.

`StringBuilder` solves this. It's a **mutable** sequence of characters — you can change its content in place without creating a new object every time.

**Key methods:**

| Method            | What it does                           |
| ----------------- | -------------------------------------- |
| `append(x)`       | Adds `x` to the end                    |
| `insert(i, x)`    | Inserts `x` at index `i`               |
| `reverse()`       | Reverses the character sequence        |
| `deleteCharAt(i)` | Removes character at index `i`         |
| `toString()`      | Converts it back to a regular `String` |
| `length()`        | Number of characters currently stored  |

**Why we used it in the palindrome program:**

java

```java
String reversed = new StringBuilder(cleaned).reverse().toString();
```

Breaking that down:

1. `new StringBuilder(cleaned)` — wraps our cleaned string in a `StringBuilder` object.
2. `.reverse()` — reverses it in place (this method exists on `StringBuilder`, but **not** on `String` — that's why we can't just do `cleaned.reverse()`).
3. `.toString()` — converts it back into a normal `String` so we can compare it with `.equals()`.

## 5. Exercises

1. Take a sentence as input and print it in uppercase and lowercase.
2. Take a full name as input and print only the first 3 characters using `substring`.
3. Take two words as input and check if they are equal, ignoring case.
4. Take a sentence as input, split it into words using `split(" ")`, and print each word on a new line.
5. Print a formatted table of 5 students' names and marks, aligned neatly using width specifiers in `printf`.
6. Using argument indexing, write a single `printf` statement that prints: `"Ravi scored 85. Well done, Ravi!"` by passing `"Ravi"` and `85` only once each.
7. Take a string as input and count the number of vowels and consonants in it.
8. Take a string as input and check if it is a palindrome (reads the same forwards and backwards).
9. Take a sentence as input and count the number of words in it.
10. Take a string as input and print it with the first letter of each word capitalized (e.g., "hello world" → "Hello World").
11. Take a string as input and remove all the spaces from it

### Practice Problems 

1. Take a string as input and count how many times each character appears in it (e.g., "hello" → h:1, e:1, l:2, o:1).
2. Take a string as input and check whether it contains only digits, only letters, or a mix of both.
3. Take two strings as input and check whether they are anagrams of each other (e.g., "listen" and "silent").
4. Take a sentence as input and reverse the order of the words (not the letters) — e.g., "I love Java" → "Java love I".
5. Take a string as input and remove all duplicate characters from it, keeping only the first occurrence of each.
6. Take a string as input and find the first non-repeating character in it (e.g., "swiss" → 'w').

### Slightly Harder Problems

1. Take a string as input and check if it is a valid palindrome, ignoring spaces, punctuation, and case (e.g., "A man a plan a canal Panama").
2. Take a paragraph as input and find the most frequently occurring word in it.
3. Take a string as input and compress it using counts — e.g., "aaabbbcc" → "a3b3c2".
4. Take a string containing multiple names separated by commas, sort them alphabetically, and print them back joined by commas.
5. Take a string as input and check whether the parentheses/brackets in it are balanced — e.g., "(a+b)*(c-d)" is balanced, "(a+b*(c-d)" is not.
6. Write a simple Caesar cipher program: take a string and a shift number, and shift each letter forward by that many positions in the alphabet (e.g., shift 1: "abc" → "bcd").

```java
// Input a sentence and calculate numnber of words in it 

import java.util.Scanner;

public class WordCount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a sentence: ");
        String sentence = scanner.nextLine();

        String[] words = sentence.trim().split(" ");
        int count = 0;

        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                count++;
            }
        }

        System.out.println("Number of words: " + count);

        scanner.close();
    }
}
```



```java
// Check number of Voewls and Consonants
import java.util.Scanner;

public class VowelConsonantCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        // Convert everything to lowercase to make checking easier
        String lowerCaseInput = input.toLowerCase();
        
        int vowelCount = 0;
        int consonantCount = 0;
        
        // Loop through each character in the string
        for (int i = 0; i < lowerCaseInput.length(); i++) {
            char ch = lowerCaseInput.charAt(i);
            
            // First, ensure the character is actually a letter (ignores spaces, numbers, etc.)
            if (ch >= 'a' && ch <= 'z') {
                // Check if the letter is a vowel
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowelCount++;
                } 
                // If it is a letter but NOT a vowel, it must be a consonant
                else {
                    consonantCount++;
                }
            }
        }
        
        System.out.println("Total Vowels: " + vowelCount);
        System.out.println("Total Consonants: " + consonantCount);
        
        scanner.close();
    }
}
```



```java
// Program to Check Palindrome String

import java.util.Scanner;

public class PalindromeCheck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        if (isPalindrome(input)) {
            System.out.println("\"" + input + "\" is a palindrome.");
        } else {
            System.out.println("\"" + input + "\" is not a palindrome.");
        }

        scanner.close();
    }

    public static boolean isPalindrome(String s) {
        // Remove spaces and convert to lowercase
        String cleaned = s.replaceAll("\\s+", "").toLowerCase();

        // Reverse the cleaned string
        String reversed = new StringBuilder(cleaned).reverse().toString();

        return cleaned.equals(reversed);
    }
}
```

```java
// Take a string as input and check whether it contains only digits, only letters, or a mix of both.
import java.util.Scanner;

public class StringAnalyzerLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        if (input.isEmpty()) {
            System.out.println("The string is empty.");
            return;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // Loop through the string one character at a time
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            
            if (Character.isLetter(currentChar)) {
                hasLetter = true;
            } else if (Character.isDigit(currentChar)) {
                hasDigit = true;
            } else {
                hasSpecial = true; // This catches spaces, punctuation, etc.
            }
        }

        // Print the result based on what we found
        if (hasSpecial) {
            System.out.println("The string contains special characters or spaces.");
        } else if (hasLetter && !hasDigit) {
            System.out.println("The string contains ONLY letters.");
        } else if (!hasLetter && hasDigit) {
            System.out.println("The string contains ONLY digits.");
        } else if (hasLetter && hasDigit) {
            System.out.println("The string contains a MIX of both letters and digits.");
        }
        
        scanner.close();
    }
}
```

