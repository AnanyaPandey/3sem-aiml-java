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

## PROJECT 9: TIC-TAC-TOE

### What you will make

A digital 3x3 game board where a human can play against another human or against a computer bot that makes random valid moves.

### Starting data

1. A 3x3 character array filled with empty spaces (`' '`).

### Rules

1. X always goes first.
2. A player cannot place a mark on a cell that already has an X or O.
3. If the board fills up and no one has 3-in-a-row, it is a Draw.

### PERSON 1: Board class and Exceptions

**What this class is for:** Managing the 2D grid.
**Steps:**

1. Make a private variable: `char[][] grid = new char[3][3];`
2. Write `initializeBoard()`: use nested `for` loops to set every cell to `' '` (a space).
3. Write `printBoard()`: use loops to print the board. Example output format: `X | O |   `
4. Write `isBoardFull()`: loops through all 9 cells. If it finds even one `' '`, return `false`. If it finishes the loop without finding any spaces, return `true`.
5. Write `placeMark(int row, int col, char mark)`:
   * If row or col is less than 0 or greater than 2, **throw** an `InvalidMoveException`.
   * If `grid[row][col]` is not `' '`, **throw** a `CellOccupiedException`.
   * Else, set `grid[row][col] = mark`.
6. **Make 2 custom exceptions:** `InvalidMoveException` and `CellOccupiedException`.

### PERSON 2: Player classes

**What this class is for:** Separates how humans pick their move versus how a computer picks a move.
**Steps:**

1. Make an `abstract class Player`.
2. Private variables: `name` (String), `mark` (char, either 'X' or 'O').
3. Constructor taking name and mark.
4. Abstract method: `public abstract int[] getMove();` (returns an array of 2 integers: row and col).
5. Make child `HumanPlayer`:
   * `getMove()` asks the user: "Enter row (0-2):" and "Enter col (0-2):" using `Scanner`. Returns `new int[]{row, col}`.
6. Make child `ComputerPlayer`:
   * `getMove()` uses `Math.random()`. Multiply by 3 and cast to `int` to get a random number between 0 and 2 for both row and col. Print "Computer chooses row X, col Y". Return `new int[]{row, col}`.

### PERSON 3: Game Logic class

**What this class is for:** Checking the win conditions.
**Steps:**

1. You need a method `boolean checkWin(Board board, char mark)`.
2. Check rows: loop `i` from 0 to 2. If `grid[i][0] == mark && grid[i][1] == mark && grid[i][2] == mark`, return true.
3. Check columns: loop `j` from 0 to 2. If `grid[0][j] == mark && grid[1][j] == mark && grid[2][j] == mark`, return true.
4. Check diagonals: Check `grid[0][0]`, `grid[1][1]`, `grid[2][2]` AND check `grid[0][2]`, `grid[1][1]`, `grid[2][0]`.
5. If none of these match, return `false`.

### PERSON 4: Main Engine

**What this class is for:** Alternating turns and handling the game loop.
**Steps:**

1. Ask the user for game mode: Human vs Human, or Human vs Computer. Create the `Player` objects based on the choice.
2. Make a `Player currentPlayer = player1;`
3. Make a `while(true)` loop:
   * Call `board.printBoard()`.
   * Ask `currentPlayer` for their move using `int[] move = currentPlayer.getMove();`.
   * Try to place the mark: `board.placeMark(move[0], move[1], currentPlayer.getMark())`.
   * **Important:** Put `placeMark` in a `try/catch` block! If an exception is caught, print the error and `continue;` the loop (do NOT switch players, make them try again). If the computer throws an exception (because it randomly guessed a full spot), the loop will just automatically make it guess again.
   * If successful, call `checkWin()`. If true, print winner and break.
   * Call `isBoardFull()`. If true, print Draw and break.
   * Switch player: `if (currentPlayer == player1) currentPlayer = player2; else currentPlayer = player1;`

### DEMO: Your group must show these test cases

1. Play a full game between two humans where someone gets 3-in-a-row.
2. During the game, try to place an X on top of an O. Show the `CellOccupiedException` message.
3. During the game, type row `5` col `5`. Show the `InvalidMoveException` message.
4. Play a game against the Computer and show it picking moves automatically.