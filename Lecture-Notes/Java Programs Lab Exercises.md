## Java Programs

### If-Else / Conditional Statements

1. Check whether a number is positive, negative, or zero.
2. Check whether a given number is even or odd.
3. Find the largest of three numbers.
4. Create a program to calculate area of a circle 
5. Check whether a year is a leap year.
6. Check whether a given character is a vowel or consonant.
7. Calculate the grade of a student based on marks entered (A/B/C/D/F).
8. Check whether a triangle is valid given its three angles.
9. Find whether a given number is divisible by both 3 and 5.
10. Check whether a given character is an alphabet, digit, or special symbol.
11. Calculate electricity bill based on units consumed (different rate slabs).

### For Loop (and Loop-based Programs)

1. Print numbers from 1 to N.
2. Print the multiplication table of a given number.
3. Find the sum of first N natural numbers.
4. Find the factorial of a given number.
5. Print the Fibonacci series up to N terms.
6. Check whether a given number is prime.
7. Print all prime numbers between 1 and N.
8. Reverse the digits of a given number.
9. Check whether a given number is a palindrome.
10. Check whether a given number is an Armstrong number.
11. Print a right-angled triangle pattern of stars.
12. Print a pyramid pattern of numbers.
13. Find the sum of digits of a number.
14. Find the GCD (HCF) of two numbers.
15. Find the LCM of two numbers.

### Methods (Function Creation)

1. Write a method to check whether a number is prime and call it from main.
2. Write a method to calculate the factorial of a number using recursion.
3. Write a method to swap two numbers (with and without a third variable).
4. Write a method that returns the square and cube of a given number.
5. Write a method to find the largest among three numbers passed as parameters.
6. Write an overloaded method `add()` that works for two integers, three integers, and two doubles.
7. Write a method to check whether a given number is an Armstrong number.
8. Write a method to convert temperature from Celsius to Fahrenheit and vice versa.
9. Write a method to calculate simple interest given principal, rate, and time.
10. Write a recursive method to find the sum of first N natural numbers.

### Arrays

1. Find the largest and smallest element in an array.
2. Find the sum and average of elements in an array.
3. Count the number of even and odd elements in an array.
4. Reverse an array without using a second array.
5. Search for an element in an array using linear search.
6. Sort an array in ascending order using bubble sort.
7. Find the second largest element in an array.
8. Remove duplicate elements from an array.
9. Merge two arrays into a third array.
10. Find the frequency of each element in an array.
11. Add two matrices using 2D arrays.
12. Multiply two matrices using 2D arrays.
13. Find the transpose of a matrix.
14. Check whether a matrix is a magic square.
15. Find the sum of diagonal elements of a square matrix.

### String Methods

1. Check whether a given string is a palindrome.
2. Count the number of vowels and consonants in a string.
3. Reverse a string without using the built-in `reverse()` method.
4. Count the number of words in a sentence.
5. Check whether two strings are anagrams of each other.
6. Convert a string to uppercase and lowercase without using `toUpperCase()`/`toLowerCase()`.
7. Find the length of a string without using `length()`.
8. Remove all white spaces from a string.
9. Count the occurrences of a particular character in a string.
10. Check whether a string contains only digits.
11. Find the first non-repeated character in a string.
12. Concatenate two strings without using the `+` operator or `concat()`.
13. Extract a substring from a given string using `substring()`.
14. Replace all occurrences of a character in a string using `replace()`.
15. Split a sentence into words using `split()` and print each word.

## Object Oriented Programming 

### Classes and Objects (Basics)

1. Create a `Student` class with fields (name, roll number, marks) and a method to display the details. Create an object and call the method.
2. Create a `Rectangle` class with length and width, and a method to calculate area and perimeter.
3. Create a `Circle` class with radius, and methods to calculate area and circumference.
4. Create an `Employee` class with name, id, and salary, and a method to display a salary slip.
5. Create a `Book` class with title, author, and price, and a method to apply a discount.
6. Create a `BankAccount` class with balance, and methods to deposit and withdraw money.

### Constructors

1. Create a `Student` class with a default constructor that initializes name as "Unknown" and marks as 0.
2. Create a `Rectangle` class with a parameterized constructor that takes length and width.
3. Create a class `Point` with both a default constructor and a parameterized constructor (constructor overloading).
4. Create a `Car` class with a constructor that initializes brand, model, and year, and displays them.
5. Create a class `Product` where the constructor calculates and stores the final price after tax.
6. Demonstrate the difference between a class with and without an explicit constructor (default constructor behavior).

### `this` Keyword

1. Create a `Student` class where constructor parameter names are the same as field names, and use `this` to resolve the ambiguity.
2. Write a class `Box` where one constructor calls another constructor of the same class using `this()`.
3. Create a class `Employee` that uses `this` to return the current object from a method (method chaining).
4. Write a program demonstrating a setter method using `this.field = field` for multiple fields.

### Passing Objects to Methods

1. Write a method that takes a `Student` object as a parameter and displays its details.
2. Write a method `compareMarks(Student s1, Student s2)` that takes two `Student` objects and returns the one with higher marks.
3. Write a method that takes a `Rectangle` object and returns a new `Rectangle` object with doubled dimensions.
4. Write a method `updateSalary(Employee e, double bonus)` that modifies the salary field of an `Employee` object passed to it.
5. Write a method that takes a `Point` object and returns the distance from the origin (0,0).
6. Demonstrate that objects are passed by reference in Java by modifying an object inside a method and showing the change reflects outside.

### Object Arrays

1. Create an array of 5 `Student` objects, initialize them, and display all their details using a loop.
2. Create an array of `Employee` objects and find the employee with the highest salary.
3. Create an array of `Book` objects and sort them by price (using a simple sorting logic).
4. Create an array of `Rectangle` objects and calculate the total area of all rectangles.
5. Create an array of `Circle` objects and print the circle with the largest circumference.
6. Write a method that accepts an array of `Student` objects and returns the average marks.
7. Create an array of `Product` objects and count how many products have a price above a given value.
8. Create an array of `Employee` objects and print names of employees earning above the average salary.