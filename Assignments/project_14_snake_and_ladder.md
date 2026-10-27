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

## PROJECT 14: SNAKE AND LADDER GAME

### What you will make

A text-based board game. Players roll dice to move from 0 to exactly 100. Arrays will act as the board's "rules" for snakes and ladders.

### Starting data

1. 2 Players (1 Human, 1 Bot).
2. Hardcode 2 ladders and 2 snakes using arrays.

### Rules

1. A player must roll the exact number to land on 100. If they are at 98 and roll a 5, they cannot move.

### PERSON 1: Dice and Exceptions

**What this class is for:** Generating random rolls and handling game errors.
**Steps:**

1. Private variable: `int sides = 6;`
2. Write `int roll()`: use `Math.random()`. Multiply by sides, cast to int, add 1. Return a number from 1 to 6.
3. **Make 2 custom exceptions:**
   * `InvalidMoveException`: "Roll is too high, you must land on exactly 100."
   * `GameOverException`: "The game has already been won!"

### PERSON 2: Player classes

**What this class is for:** Different types of players.
**Steps:**

1. Make an `abstract class Player`.
2. Private variables: `name` (String), `position` (int, starts at 0).
3. Abstract method: `public abstract void takeTurnMsg();`
4. Make child `HumanPlayer`:
   * `takeTurnMsg()` prints: "Press Enter to roll the dice..." (use Scanner to wait for input).
5. Make child `BotPlayer`:
   * `takeTurnMsg()` prints: "Bot is rolling the dice automatically..."

### PERSON 3: Board class

**What this class is for:** Holding the hazard arrays.
**Steps:**

1. Private arrays:
   * `int[] snakeHeads = {99, 45};` and `int[] snakeTails = {10, 5};`
   * `int[] ladderBottoms = {15, 60};` and `int[] ladderTops = {35, 85};`
2. Write `checkSquare(int playerPos)`:
   * Loop through `snakeHeads`. If `playerPos == snakeHeads[i]`, print "OH NO! Bitten by snake!", return `snakeTails[i]`.
   * Loop through `ladderBottoms`. If `playerPos == ladderBottoms[i]`, print "YAY! Climbed a ladder!", return `ladderTops[i]`.
   * Else, return `playerPos` (no change).

### PERSON 4: Main Game Engine

**What this class is for:** Alternating turns.
**Steps:**

1. Create an array of `Player[] players = new Player[2];` (Put human at index 0, bot at index 1).
2. Make a `while(true)` loop:
   * For each player in the array:
   * Call `player.takeTurnMsg()`.
   * Call `dice.roll()`.
   * Calculate `newPos = player.getPosition() + roll`.
   * If `newPos > 100`, **throw** an `InvalidMoveException`. Catch it, print the warning, and their turn is over without moving.
   * If `newPos <= 100`, pass `newPos` into `board.checkSquare(newPos)`.
   * Update the player's position to whatever the board returns.
   * Print "Player is now at space X".
   * If position == 100, print "WINNER!", and break the loop.

### DEMO: Your group must show these test cases

1. Land on a ladder bottom and show the position update to the top.
2. Land on a snake head and show the position update to the bottom.
3. Show Polymorphism: the Human waits for an Enter keypress, but the Bot rolls instantly.
4. Have a player reach square 98, then roll a 5. Show the `InvalidMoveException` catching it.