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

# PROJECT 14: SNAKE AND LADDER GAME

## What you will make

A text board game for 2 players: one human and one bot. Players start at 0 and must reach exactly 100. Arrays store the snakes and ladders. The game also has a "test mode", where you can choose the dice number, so that you can show every rule in the demo.

## Starting data

1. 2 players: a Human and a Bot.
2. 2 snakes: head at 99 goes to 10, head at 45 goes to 5.
3. 2 ladders: bottom at 15 goes to 35, bottom at 60 goes to 85.

## Rules

1. A player must land exactly on 100. If position + roll is more than 100, the player does not move.
2. If a player lands on a snake head, the player goes down to the snake tail.
3. If a player lands on a ladder bottom, the player goes up to the ladder top.
4. When a player reaches 100, the game is won, and no more turns are allowed.
5. In test mode, before each roll the program asks for the dice number (1 to 6, or 0 for a random roll). At the start of the game, it also asks for the start position of each player.

## PERSON 1: Dice and the exceptions

**What this class is for:** To give the dice number, random or chosen.

**Steps:**

1. Private variables: `sides` (6) and `nextRoll` (starts at 0).
2. `setNextRoll(int n)`: save `n` (for test mode).
3. `roll()`: if `nextRoll` is more than 0, return it and set `nextRoll` back to 0. Otherwise use `Math.random()` to return a number from 1 to `sides`.
4. Make 2 custom exceptions:
   - `InvalidMoveException`: message "Roll is too high, you must land on exactly 100".
   - `GameOverException`: message "The game has already been won".

**How to test your part:** Call `roll()` 20 times and check that all numbers are from 1 to 6. Use `setNextRoll(4)` and check that the next roll is 4 and the one after is random.

## PERSON 2: Player, HumanPlayer and BotPlayer

**What this class is for:** Both players move in the same way, but they wait for the roll in different ways.

**Steps:**

1. Make an abstract class `Player`.
2. Private variables: `name`, `position` (starts at 0).
3. Constructor takes the name. Make `getName()` and `getPosition()`.
4. `moveTo(int newPosition)`: sets the position directly (used for snakes, ladders and test mode).
5. `move(int roll)`: if `position + roll` is more than 100, throw `InvalidMoveException`. Otherwise add the roll to the position.
6. Abstract method: `waitForRoll()`.
7. `HumanPlayer`: the constructor also takes the shared `Scanner`. `waitForRoll()` prints "Press Enter to roll the dice..." and waits for the user to press Enter.
8. `BotPlayer`: `waitForRoll()` prints "Bot is rolling the dice automatically..." and does not wait.

**How to test your part:** Move a player to 98 and call `move(5)`. The exception must come, and the position must stay 98.

## PERSON 3: Board

**What this class is for:** To keep the snakes and ladders.

**Steps:**

1. Private arrays: `snakeHeads`, `snakeTails`, `ladderBottoms`, `ladderTops` (use the starting data).
2. `checkSquare(int position)`:
   - Loop through `snakeHeads`. If the position matches, print "OH NO! Bitten by a snake!" and return the matching tail.
   - Loop through `ladderBottoms`. If the position matches, print "YAY! Climbed a ladder!" and return the matching top.
   - Otherwise return the same position.
3. `showRules()`: print every snake (head to tail) and every ladder (bottom to top).
4. `isValid()`: returns true only if every snake head is higher than its tail, every ladder top is higher than its bottom, and both pairs of arrays have the same length.

**How to test your part:** Call `checkSquare` with 99, 15, and 50. Check the three answers (10, 35, 50).

## PERSON 4: Game and Main

**What this class is for:** To run the turns and connect everything.

**Steps:**

1. `Game` private variables: `Player[] players` (human at 0, bot at 1), `Dice dice`, `Board board`, `gameOver` (false), `testMode`, and the shared `Scanner`.
2. `playTurn(Player p)`:
   - If `gameOver` is true, throw `GameOverException`.
   - Call `p.waitForRoll()`.
   - If `testMode` is true, ask "Type dice number (1-6), or 0 for random" and call `dice.setNextRoll(...)`.
   - Call `dice.roll()` and print the number.
   - Try `p.move(roll)`. If `InvalidMoveException` comes, print the message, and the turn ends.
   - Otherwise call `board.checkSquare(...)` and `p.moveTo(...)` with the answer. Print "Name is now at square X".
   - If the position is 100, print "WINNER!" and set `gameOver` to true.
3. `play()`: loop forever through the players and call `playTurn`. When `GameOverException` comes, print the message and stop the loop.
4. In test mode, `play()` first asks the start position of each player and calls `moveTo`.
5. Main class: menu in a loop. Use ONE Scanner.

**Menu:**

```
1. Play a game
2. Play a game in test mode
3. Show snakes and ladders
4. Stop program
```

## DEMO: your group must show all 5

1. Test mode: start at 10, roll 5. The player must climb the ladder from 15 to 35.
2. Test mode: start at 40, roll 5. The player must go down the snake from 45 to 5.
3. Show polymorphism: the Human waits for Enter, the Bot rolls at once.
4. Test mode: start at 98 and roll 5. The "must land exactly on 100" message must show, and the position stays 98. Then roll 2. The player wins, and the "game already won" message must show after that.
5. Play one normal game with random dice until someone wins.