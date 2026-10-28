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

# PROJECT 8: COLLEGE SYSTEM

## What you will make

A college registration program. Professors are given courses to teach. Students enroll in courses, and can also drop them. The program follows the limits of the college.

## Starting data (create in the constructor of `College`)

1. 3 professors: P1, P2, P3.
2. 6 courses: C1 Java, C2 Math, C3 Physics, C4 Art, C5 History, C6 Chemistry.
3. 5 students: S1 to S5.
4. One department called "Computer Science" that holds all 6 courses and all 3 professors.

## Rules of the college

1. Each course can have at most 2 students.
2. A student can take at most 3 courses.
3. A professor can teach at most 2 courses.
4. A student cannot enroll in the same course two times.
5. A course has only one professor.
6. A student cannot drop a course that he or she did not take.

## PERSON 1: Course and its exceptions

**What this class is for:** One object of this class is ONE course.

**Steps:**

1. Private variables: `courseId`, `courseName`, `maxCapacity`, `currentEnrollment` (starts at 0), `professorName` (starts as "Not assigned").
2. Constructor takes courseId, courseName and maxCapacity.
3. Make getters for all variables. Also make `setProfessorName(String name)`.
4. `enrollStudent()`: if `currentEnrollment` equals `maxCapacity`, throw `CourseFullException`. Otherwise add 1.
5. `dropStudent()`: if `currentEnrollment` is 0, throw `StudentNotEnrolledException`. Otherwise subtract 1.
6. `toString()`: example `C1 - Java (1/2 enrolled) Professor: Not assigned`.
7. Make 2 custom exceptions:
   - `CourseFullException`: message "This course is full".
   - `StudentNotEnrolledException`: message "This student is not enrolled in this course".

**How to test your part:** Make a course with capacity 2. Call `enrollStudent()` 3 times. The third time must give the exception. Call `dropStudent()` on an empty course. The other exception must come.

## PERSON 2: Person, Student and Professor

**What this class is for:** To store a person of the college and to keep the list of his or her courses.

**Steps:**

1. Make an abstract class `Person`.
2. Private variables: `personId`, `name`, `Course[] myCourses` (size 5), `courseCount` (starts at 0).
3. Constructor takes personId and name. Make getters.
4. Add two abstract methods: `getMaxCourses()` and `getRole()`.
5. Child class `Student`: `getMaxCourses()` returns 3, `getRole()` returns "Student".
6. Child class `Professor`: `getMaxCourses()` returns 2, `getRole()` returns "Professor".
7. Methods in `Person`:
   - `canAddCourse()`: true if `courseCount` is less than `getMaxCourses()`.
   - `hasCourse(Course c)`: true if this course is already in the array (compare course IDs).
   - `addCourse(Course c)`: put the course in the array and add 1 to the counter.
   - `removeCourse(Course c)`: find it, shift the other courses left so there is no gap, subtract 1 from the counter.
   - `printMyCourses()`: print all courses of this person. If none, print "No courses".
8. Make the custom exception `CourseLimitException`: message "Course limit reached".

**How to test your part:** Make a student and a professor. Print `getMaxCourses()` for both (3 and 2). Add 3 courses to the student, remove the first one, and print. There must be no gap.

## PERSON 3: Department

**What this class is for:** To group the courses and professors, and to find them by ID.

**Steps:**

1. Private variables: `deptName`, `Course[] courses` (size 10), `courseCount`, `Professor[] professors` (size 5), `professorCount`.
2. Constructor takes deptName. Make a getter.
3. `addCourse(Course c)` and `addProfessor(Professor p)`: store and add 1 to the counter. If the array is full, print a message.
4. `findCourse(String id)` and `findProfessor(String id)`: search the array. Return null if not found.
5. `listAllCourses()`: print every course.
6. `listProfessors()`: print every professor with the number of courses.
7. `showReport()`: for each course print the course, the professor name and the enrollment. At the end print how many courses have no professor.
8. Make the custom exception `AlreadyEnrolledException`: message "Student is already enrolled in this course".

**How to test your part:** Add 3 courses and 2 professors. Find one by ID. Find a wrong ID and check that you get null.

## PERSON 4: College and Main

**What this class is for:** This is the main manager. It connects everything and shows the menu.

**Steps:**

1. Private variables: `Department dept`, `Student[] students` (size 5).
2. In the constructor, create the starting data and put it in the department and the array.
3. `enrollStudent(String sId, String cId)`:
   - Find the student and the course. If not found, print a message and stop.
   - If `student.hasCourse(course)`: throw `AlreadyEnrolledException`.
   - If `student.canAddCourse()` is false: throw `CourseLimitException`.
   - Call `course.enrollStudent()` (can throw `CourseFullException`).
   - Only after it works: call `student.addCourse(course)`.
4. `dropStudent(String sId, String cId)`:
   - Find both. If `student.hasCourse(course)` is false, throw `StudentNotEnrolledException`.
   - Call `course.dropStudent()` and `student.removeCourse(course)`.
5. `assignProfessor(String pId, String cId)`:
   - Find both. If the course already has a professor, print a message and stop.
   - If `professor.canAddCourse()` is false: throw `CourseLimitException`.
   - Call `professor.addCourse(course)` and `course.setProfessorName(professor.getName())`.
6. `showCoursesOf(String id)`: find a student or professor and call `printMyCourses()`.
7. Main class: menu in a loop, with try/catch for all 5 exceptions.

**Menu:**

```
1. Show all courses
2. Enroll student in course
3. Drop student from course
4. Assign professor to course
5. Show courses of a student or professor
6. Department report
7. Stop program
```

## DEMO: your group must show all 8

1. Enroll S1 in C1.
2. Enroll S2 in C1. The course is now full (2/2).
3. Enroll S3 in C1. The "course is full" message must show.
4. Enroll S1 in C2 and C3, then in C4. The "course limit" message must show.
5. Enroll S1 in C1 again. The "already enrolled" message must show.
6. Drop S1 from C1. Then drop S1 from C1 again. The "not enrolled" message must show.
7. Assign P1 to C1 and C2, then to C3. The "course limit" message must show.
8. Show the courses of a student and the department report.