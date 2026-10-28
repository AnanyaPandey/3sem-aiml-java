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

# PROJECT 10: QUIZ APPLICATION

## What you will make

A quiz program with multiple choice questions (A, B, C, D) and True/False questions. Every correct answer gives marks. The program keeps the scores of players.

## Starting data

1. 3 multiple choice questions and 2 True/False questions about Java.
2. Each question gives 10 points.

## Rules

1. A multiple choice answer must be A, B, C or D (small or capital letter).
2. A True/False answer must be True or False (any letter case).
3. If the answer is not valid, the program shows a message and asks the SAME question again. No points are lost.
4. The scoreboard keeps the last 10 players.

## PERSON 1: Question, MCQQuestion and TrueFalseQuestion

**What this class is for:** Two types of questions, with their own way to check answers.

**Steps:**

1. Make an abstract class `Question`.
2. Private variables: `questionText`, `points`.
3. Constructor takes questionText and points. Make getters.
4. Abstract methods: `displayQuestion()`, `isValidAnswer(String ans)` and `checkAnswer(String ans)`.
5. Child `MCQQuestion`: extra variables `String[] options` (size 4) and `correctAnswer` ("A" to "D").
   - `displayQuestion()`: print the question and the 4 options with A, B, C, D.
   - `isValidAnswer()`: true only for A, B, C, D (any letter case).
   - `checkAnswer()`: true if the answer equals `correctAnswer` (ignore letter case).
6. Child `TrueFalseQuestion`: extra variable `boolean correctAnswer`.
   - `displayQuestion()`: print the question and "(Type True or False)".
   - `isValidAnswer()`: true only for "true" or "false" (ignore letter case).
   - `checkAnswer()`: compare the answer with `correctAnswer`.

**How to test your part:** Make one of each. Test `isValidAnswer("Hello")` (false) and `checkAnswer(...)` with a right and a wrong answer.

## PERSON 2: Quiz and the exceptions

**What this class is for:** To hold the questions and to check an answer for the program.

**Steps:**

1. Private variables: `Question[] questions` (size 5), `count`.
2. `addQuestion(Question q)`: if the array is full, throw `QuizFullException`. Otherwise store it.
3. `getQuestion(int index)`, `getQuestionCount()`, `getTotalPoints()` (sum of all points).
4. `submitAnswer(int index, String ans, Player p)`:
   - If `!question.isValidAnswer(ans)`, throw `InvalidOptionException`.
   - If `question.checkAnswer(ans)`: call `p.addPoints(question.getPoints())` and return true.
   - Otherwise return false.
5. Make 2 custom exceptions:
   - `QuizFullException`: message "The quiz already has 5 questions".
   - `InvalidOptionException`: message "This is not a valid answer for this question".

**How to test your part:** Add 6 questions. The 6th must give the exception. Call `submitAnswer` with "Z". The other exception must come.

## PERSON 3: Player and ScoreBoard

**What these classes are for:** To remember who took the quiz and what they scored.

**Steps:**

1. `Player`: private variables `playerName`, `totalScore`. Constructor takes the name. Make getters. Write `addPoints(int p)`.
2. `Player.toString()`: example `Ravi : 30`.
3. `ScoreBoard`: private variables `Player[] history` (size 10), `playerCount`.
4. `saveScore(Player p)`: store it. If the array is full, print "Scoreboard is full".
5. `showAllScores()`: print every player and score. If empty, print "No scores yet".
6. `showTopScorer()`: find and print the player with the highest score.

**How to test your part:** Save 3 players with different scores. Check `showTopScorer()`.

## PERSON 4: Main

**What this class is for:** To run the quiz.

**Steps:**

1. Create ONE `Scanner`, one `Quiz` and one `ScoreBoard`.
2. Create the 5 questions and add them with `addQuestion` inside `try/catch`.
3. "Take quiz": ask the name, make a `Player`, and loop through the questions using `quiz.getQuestionCount()` (do not write the number 5 in your loop).
4. For each question: display it, read the answer, and call `quiz.submitAnswer(...)`. If `InvalidOptionException` comes, print the message and ask the same question again (use a `while` loop for each question). Print "Correct!" or "Wrong!".
5. At the end print the score (for example "30 out of 50") and save the player in the ScoreBoard.

**Menu:**

```
1. Take quiz
2. View all scores
3. Show top scorer
4. Stop program
```

## DEMO: your group must show all 4

1. Take the quiz with some right and some wrong answers. Show the final score.
2. For an MCQ type "Hello", and for a True/False type "Maybe". The message must show and the same question must come again.
3. Take the quiz with two names. Show both in the scoreboard and show the top scorer.
4. Explain how `isValidAnswer` and `checkAnswer` work differently in the two question types (polymorphism).