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

## PROJECT 10: QUIZ APPLICATION

### What you will make

A digital quiz test engine. It supports multiple-choice questions (A/B/C/D) and True/False questions. It keeps track of player scores.

### Starting data

1. Hardcode 3 Multiple Choice Questions and 2 True/False Questions in an array.

### Rules

1. Players get 10 points for each correct answer.
2. If a player types "E" for a question that only has A, B, C, D, the system should catch the error and ask again.

### PERSON 1: Question classes

**What this class is for:** Storing different question formats using Polymorphism.
**Steps:**

1. Make an `abstract class Question`.
2. Private variables: `questionText` (String), `points` (int, default to 10).
3. Abstract method: `public abstract boolean checkAnswer(String userAnswer);`
4. Abstract method: `public abstract void displayQuestion();`
5. Make child `MCQQuestion`:
   * Variables: `String[] options` (size 4, for A, B, C, D text) and `String correctAnswer` (e.g., "B").
   * `displayQuestion()` prints the question text, then loops through the 4 options and prints them.
   * `checkAnswer(String ans)` compares `ans.toUpperCase()` to `correctAnswer`. Returns true if match, false if not.
6. Make child `TrueFalseQuestion`:
   * Variables: `boolean correctAnswer`.
   * `displayQuestion()` prints the question text and says "(Type True or False)".
   * `checkAnswer(String ans)` parses the user's string to a boolean and checks if it matches.

### PERSON 2: Quiz Manager and Exceptions

**What this class is for:** Holding the test paper.
**Steps:**

1. Private variables: `Question[] quizQuestions` (size 5), `int count`.
2. Write `addQuestion(Question q)`: adds to the array. If the array is full, **throw** a `QuizFullException`.
3. Write `getQuestion(int index)`: returns the question at that spot.
4. **Make 2 custom exceptions:** `QuizFullException` and `InvalidOptionException` (You will throw `InvalidOptionException` if a user types something weird like "Z").

### PERSON 3: Player and ScoreBoard

**What this class is for:** Tracking who took the test and what they scored.
**Steps:**

1. Player variables: `playerName` (String), `totalScore` (int).
2. Write `addPoints(int p)` inside Player.
3. ScoreBoard variables: `Player[] history` (size 10), `int playerCount`.
4. Write `saveScore(Player p)`: adds player to the history array.
5. Write `showAllScores()`: loops through the history array and prints "Name: Score".

### PERSON 4: Main Engine

**What this class is for:** Running the actual test.
**Steps:**

1. In `main`, create the 5 questions and add them to the Quiz Manager.
2. Create the menu: 1. Take Quiz, 2. View History.
3. If they choose "Take Quiz":
   * Ask for their name, create a `Player` object.
   * Use a `for` loop from 0 to 4 to get each question.
   * Call `question.displayQuestion()`.
   * Use `Scanner` to get their answer.
   * **Validation:** If the question is an MCQ, and they typed "X" or "123", **throw** your `InvalidOptionException`, catch it, print "Please type A, B, C, or D", and make them answer the *same* question again (decrease your loop counter `i--` so it repeats).
   * Call `question.checkAnswer()`. If true, print "Correct!" and add points to the player. If false, print "Wrong!".
   * After the loop, call `scoreboard.saveScore(player)`.

### DEMO: Your group must show these test cases

1. Take the quiz and get a mix of right and wrong answers. Show the final score.
2. When asked an MCQ question, type "Hello". Show the `InvalidOptionException` catching it and asking the user to try again.
3. Take the quiz with two different names, then view the Scoreboard history to prove both were saved in the array.