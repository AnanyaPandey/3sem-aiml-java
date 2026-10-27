# RULES FOR ALL PROJECTS

1. Work in a group of 4. Each person makes ONE main part (one or more classes, as written in your project). Write your name in a comment at the top of your class.
2. Use only Java. Use arrays to store many things (many spots, many books, many accounts). Do not use ArrayList, HashMap, or any database.
3. Your project must use these 5 things. Here is what each one means:
   - **Private variables with getters (encapsulation):** Make your variables private. Give a public method (getter) to read each one. Example: `private String spotId;` and `public String getSpotId()`.
   - **Inheritance:** One parent class, and child classes that extend it. Example: Vehicle is the parent. Car and Bike are children.
   - **Overriding:** The parent has a method. Each child writes its own version of the same method. Example: `getHourlyRate()` gives 20 in Car and 10 in Bike.
   - **Abstract class:** A parent class that you never create objects of directly. You write `abstract` before the class name. Example: `abstract class Vehicle`.
   - **Custom exceptions:** Your own error classes. You make them with `extends Exception`. You use `throw` when something is wrong and `try/catch` to show a nice message.
4. Make a Main class with a menu. The user types a number to choose. If the user types a wrong number, show a message. The program must not crash.
5. **Submit these 4 things:**
   - (a) your code
   - (b) one page diagram with boxes for your classes
   - (c) 5 lines on why you made these classes and what numbers you decided
   - (d) who did what
6. Present for 10 minutes. Every person explains their own class and answers questions on it.

## Tips for working in a group

1. First, sit together and agree on the names of the classes and methods. Write them on paper.
2. Each person writes and tests their own class first, with a small `main` method.
3. Only after that, join all the classes together.
4. If your part needs a class of another person, wait for them or write a simple temporary version to test.
5. Do not copy code from the internet or from AI tools. You will be asked to explain every line of your own class.

------

## PROJECT 8: COLLEGE SYSTEM

### What you will make

A university registration system. Professors are assigned to teach courses, and students enroll in them. The system must enforce capacity limits for courses and course-load limits for students.

### Starting data (hardcode this in your Main class)

1. 3 Professors: `P1`, `P2`, `P3`.
2. 5 Courses: `C1` (Java), `C2` (Math), `C3` (Physics), `C4` (Art), `C5` (History).
3. 5 Students: `S1` to `S5`.

### Rules of the college

1. Each course has a maximum capacity (e.g., 2 students max).
2. A student can take at most 5 courses.
3. A professor can teach at most 3 courses.
4. A student cannot enroll in a course if it is full.

### PERSON 1: Course class and Exceptions

**What this class is for:** Represents a single subject being taught.
**Steps:**

1. Make private variables: `courseId` (String), `courseName` (String), `maxCapacity` (int), `currentEnrollment` (int).
2. Make a constructor taking ID, name, and capacity. Set `currentEnrollment = 0`.
3. Make getters for all variables.
4. Write method `enrollStudent()`:
   * If `currentEnrollment` is equal to `maxCapacity`, do not add the student. Instead, **throw** a `CourseFullException`.
   * Otherwise, increase `currentEnrollment` by 1.
5. Write method `dropStudent()`:
   * If `currentEnrollment` is 0, **throw** a `StudentNotEnrolledException`.
   * Otherwise, decrease `currentEnrollment` by 1.
6. Write `toString()`: return a string like `"C1 - Java (1/2 Enrolled)"`.
7. **Make 2 custom exceptions:** Create `CourseFullException` and `StudentNotEnrolledException` classes that extend `Exception`.

* **How to test your part:** Write a temporary `main` method, create a Course with capacity 2. Call `enrollStudent()` 3 times. The 3rd time, your exception should trigger.

### PERSON 2: Person, Student, and Professor

**What this class is for:** Stores details for humans in the college and enforces limits using Polymorphism.
**Steps:**

1. Make an `abstract class Person`.
2. Private variables: `personId` (String), `name` (String), an array `Course[] myCourses` (size 5), and an `int courseCount` (starts at 0).
3. Make a constructor that takes ID and name.
4. Add abstract methods: `public abstract int getMaxCourses();` and `public abstract String getRole();`
5. Make child class `Student` (extends Person):
   * `getMaxCourses()` returns `5`.
   * `getRole()` returns `"Student"`.
6. Make child class `Professor` (extends Person):
   * `getMaxCourses()` returns `3`.
   * `getRole()` returns `"Professor"`.
7. Write methods in the parent `Person` class:
   * `canAddCourse()`: returns true if `courseCount < getMaxCourses()`.
   * `assignCourse(Course c)`: puts the course into the `myCourses` array at index `courseCount`, then increases `courseCount` by 1.
   * `printMyCourses()`: loops from 0 to `courseCount` and prints the course details.

* **How to test your part:** Make a Student and a Professor object. Print `getMaxCourses()` for both. It should print 5 and 3. This proves polymorphism works.

### PERSON 3: Department class

**What this class is for:** Groups courses and professors together logically.
**Steps:**

1. Private variables: `deptName` (String), `Course[] offeredCourses` (size 10), `Person[] faculty` (size 5). You also need counters for how many courses and faculty are currently in the arrays.
2. Make a constructor that takes `deptName`.
3. Write `addCourseToDept(Course c)`: adds the course to the array and increases the counter.
4. Write `addProfessorToDept(Person p)`: adds the professor to the array and increases the counter.
5. Write `listAllCourses()`: loops through the `offeredCourses` array and prints them.

### PERSON 4: Main System Manager

**What this class is for:** Holds the global arrays, connects the logic, and shows the text menu.
**Steps:**

1. Make private arrays: `Person[] allStudents`, `Person[] allProfessors`, `Course[] allCourses`.
2. In your constructor (or a startup method), create the Starting Data objects and put them into these arrays.
3. Write `registerStudentForCourse(String sId, String cId)`:
   * Loop through arrays to find the Student object and the Course object.
   * If the student's `canAddCourse()` is false, print "Student is at max load."
   * Otherwise, try to call `course.enrollStudent()`. (You must put this inside a `try/catch` block!).
   * If it succeeds (no exception caught), call `student.assignCourse(course)`.
   * If `CourseFullException` is caught, print the error message nicely.
4. Build the `while(true)` loop with a text menu. Use `Scanner` to ask the user what they want to do.

### DEMO: Your group must show these test cases

1. Enroll student S1 into course C1 successfully.
2. Enroll student S2 into course C1. If C1's capacity is 2, it is now full.
3. Try to enroll student S3 into C1. The program must *not* crash, but should display your `CourseFullException` message.
4. Call `printMyCourses()` on a student to show their schedule.