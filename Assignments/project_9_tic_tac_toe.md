# RULES FOR ALL PROJECTS

1. Work in a group of 4. Each person makes ONE main part (one or more classes, as written in your project). Write your name in a comment at the top of your class.
2. Use only Java. Use arrays to store many things (many spots, many books, many accounts). Do not use ArrayList, HashMap, or any database.
3. Your project must use these 5 things. Here is what each one means:
   - **Private variables with getters (encapsulation):** Make your variables private. Give a public method (getter) to read each one. Example: `private String spotId;` and `public String getSpotId()`
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

# PROJECT 9: TIC-TAC-TOE

## What you will make

A 3x3 game. A human plays against another human, or against a computer that picks random moves. The program keeps a score of wins.

## Rules of the game

1. X always goes first.
2. A mark cannot be placed on a cell that is already used.
3. Row and column are numbers from 0 to 2.
4. 3 same marks in a row, in a column or in a diagonal is a win. If the board is full and nobody won, it is a draw.

## PERSON 1: Board and the exceptions

**What this class is for:** To keep the 3x3 grid and to protect it.

**Steps:**

1. Private variable: `char[][] grid = new char[3][3]`.
2. Constructor calls `reset()`.
3. `reset()`: use nested loops to set every cell to `' '`.
4. `getCell(int row, int col)`: returns the character in that cell. (Other classes need this, because `grid` is private.)
5. `placeMark(int row, int col, char mark)`:
   - If row or col is less than 0 or more than 2, throw `InvalidMoveException`.
   - If the cell is not `' '`, throw `CellOccupiedException`.
   - Otherwise, set the mark.
6. `isFull()`: false if any cell is `' '`, otherwise true.
7. `printBoard()`: print the grid in a neat way, for example `X | O |  `.
8. Make 2 custom exceptions:
   - `InvalidMoveException`: message "Row and column must be from 0 to 2".
   - `CellOccupiedException`: message "This cell is already used".

**How to test your part:** Place a mark. Place another one on the same cell. Place one on row 5. Both exceptions must come.

## PERSON 2: Player, HumanPlayer and ComputerPlayer

**What this class is for:** A human and a computer choose a move in different ways.

**Steps:**

1. Make an abstract class `Player`.
2. Private variables: `name`, `mark` ('X' or 'O').
3. Constructor takes name and mark. Make getters `getName()` and `getMark()`.
4. Abstract method: `public abstract int[] getMove();` It returns an array with row and col.
5. Child `HumanPlayer`:
   - The constructor also takes the shared `Scanner` from Main.
   - `getMove()` asks "Enter row (0-2):" and "Enter col (0-2):", reads text, and uses `Integer.parseInt`.
   - If the user types letters, catch `NumberFormatException` and return `{-1, -1}`. (The Board will then throw `InvalidMoveException`.)
6. Child `ComputerPlayer`:
   - `getMove()` uses `Math.random()` to get a row and a col from 0 to 2.
   - Print "Computer chooses row R, col C".

**How to test your part:** Call `getMove()` of both players and print the result. Type letters as input once.

## PERSON 3: Game

**What this class is for:** To run one full game: turns, win check and draw check.

**Steps:**

1. Private variables: `Board board`, `Player player1` (always X), `Player player2`.
2. Constructor takes the board and the two players.
3. `checkWin(char mark)`: use `board.getCell(r, c)`. Check 3 rows, 3 columns and 2 diagonals. Return true or false.
4. `play()`:
   - Set `currentPlayer` to player1.
   - Loop: print the board, ask `getMove()`, then try `board.placeMark(...)`.
   - If an exception comes, print its message and ask the same player again. Do NOT change the player.
   - After a good move: if `checkWin(mark)`, print the board and the winner and return that Player.
   - If `board.isFull()`, print "Draw" and return null.
   - Otherwise switch the player.

**How to test your part:** Use two ComputerPlayers and call `play()`. A full game must run and finish.

## PERSON 4: ScoreBoard and Main

**What this class is for:** To start games, keep the score, and show the menu.

**Steps:**

1. Class `ScoreBoard`: variables `xWins`, `oWins`, `draws`. Write `recordResult(Player winner)` (null means draw) and `showScore()`.
2. In `Main`, create ONE `Scanner`.
3. For a new game: create a new `Board`, the two players, and a `Game`. Call `play()`. Send the result to `recordResult`.
4. Read the menu choice as text. If it is not a valid number, show a message.

**Menu:**

```
1. Human vs Human
2. Human vs Computer
3. Show score
4. Stop program
```

## DEMO: your group must show all 5

1. Play a full game between two humans with a winner.
2. Place a mark on a used cell. The "cell used" message must show, and the same player must play again.
3. Type row 5 and col 5. The "invalid move" message must show.
4. Type letters as a move. The program must not crash.
5. Play against the computer, then show the score for 2 games.