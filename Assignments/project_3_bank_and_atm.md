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

# PROJECT 3: BANK AND ATM

## What you will make

A bank program with an ATM. A customer puts a card in the ATM, types a PIN, and then can check balance, deposit money, withdraw money, and see the last 5 transactions.

## Decisions you must make as a group (write them in your 5 lines)

1. How many accounts does your bank have? Make at least 4 (at least 2 savings and 2 current).
2. Each account has a unique account number. Example: SB101, SB102 for savings and CA201, CA202 for current.
3. Opening balance of each account.
4. Minimum balance of a savings account (example: Rs. 500).
5. Maximum cash that the ATM gives in one withdrawal (example: Rs. 10000).
6. Each account has one card. Each card has its own unique card number and a 4-digit PIN.

## Rules of the bank

1. A savings account must always keep the minimum balance. A current account can go down to 0, but not below 0.
2. Deposit and withdraw amount must be more than 0.
3. ATM gives cash only in multiples of 100 (example: 500, 1200 are fine, 150 is not fine).
4. If the customer types a wrong PIN 3 times in a row, the card is blocked. A blocked card cannot be used.
5. A correct PIN makes the wrong-PIN count go back to 0.
6. Every deposit and every withdrawal must be saved as a transaction.

## PERSON 1: Account, SavingsAccount, CurrentAccount and the exceptions

**What this class is for:** To store the money of a customer and to follow the rules of deposit and withdraw.

**Steps:**

1. Make an abstract class `Account`.
2. Private variables: `accountNumber`, `holderName`, `balance`.
3. Make a constructor that takes all three values.
4. Make getters. Do NOT make a setter for balance. Balance must change only through deposit and withdraw. (This is what encapsulation means: nobody can change balance directly.)
5. Add two abstract methods: `getMinimumBalance()` and `getAccountType()`.
6. Make child class `SavingsAccount`: `getMinimumBalance()` returns your savings minimum (example 500). `getAccountType()` returns "SAVINGS".
7. Make child class `CurrentAccount`: `getMinimumBalance()` returns 0. `getAccountType()` returns "CURRENT".
8. Write `deposit(double amount)`:
   - If amount is 0 or less, throw `InvalidAmountException`.
   - Otherwise add the amount to balance.
9. Write `withdraw(double amount)`:
   - If amount is 0 or less, throw `InvalidAmountException`.
   - If (balance - amount) is less than `getMinimumBalance()`, throw `InsufficientBalanceException`.
   - Otherwise subtract the amount from balance.
10. Make 2 custom exception classes:
    - `InvalidAmountException`: message "Amount must be more than 0".
    - `InsufficientBalanceException`: message "Not enough balance".
11. Write `toString()` that shows account number, holder name, type and balance.

**How to test your part:** Make one savings and one current account. Withdraw so that the balance goes below the minimum. Check that savings gives the exception and current does not (when the balance is still above 0).

## PERSON 2: Card and CardBlockedException

**What this class is for:** One card is linked to one account. It checks the PIN and blocks itself after 3 wrong tries.

**Steps:**

1. Private variables: `cardNumber`, `pin` (a number like 4321), `account` (the Account this card belongs to), `wrongAttempts` (starts at 0), `blocked` (starts as false).
2. Make a constructor that takes cardNumber, pin and account.
3. Make getters for cardNumber, account and blocked. **Do NOT make a getter for the PIN.** The PIN must be secret. Only the card itself can check it.
4. Write `verifyPin(int enteredPin)`:
   - If the card is blocked, throw `CardBlockedException`.
   - If enteredPin is the same as pin: set wrongAttempts to 0 and return true.
   - If it is wrong: increase wrongAttempts by 1. If wrongAttempts is now 3, set blocked to true. Return false.
5. Make the custom exception `CardBlockedException`: message "Card is blocked. Please contact the bank".
6. Write `toString()` that shows card number and if it is blocked. Do not show the PIN.

**How to test your part:** Make a card. Try a wrong PIN 3 times. Then try the right PIN. The exception must come.

## PERSON 3: Transaction and TransactionLog

**What these classes are for:** A Transaction remembers one deposit or withdrawal. The TransactionLog keeps all transactions and can print the last 5 of an account.

**Transaction class steps:**

1. Private variables: `transactionId`, `accountNumber`, `type` ("DEPOSIT" or "WITHDRAW"), `amount`, `balanceAfter`.
2. Transaction IDs must be automatic and unique: TX1, TX2, TX3... Use a static counter.
3. Make a constructor that takes accountNumber, type, amount, balanceAfter.
4. Make getters.
5. Write `toString()`: one neat line. Example: `TX7  WITHDRAW  Rs. 500  Balance: Rs. 4500`.

**TransactionLog class steps:**

1. Private variables: `Transaction[] transactions` (size 200) and a counter `count`.
2. `addTransaction(Transaction t)`: store it in the array. If the array is full, print a message.
3. `showLast(String accountNumber, int n)`: go through the array from the END to the start. Print the transactions of this account number. Stop after n transactions. If there are none, print "No transactions".
4. `showAll(String accountNumber)`: print all transactions of this account.

**How to test your part:** Add 7 transactions for one account. Call `showLast(accountNumber, 5)`. Only the last 5 must show, newest first.

## PERSON 4: ATM and Main

**What this class is for:** This is the main manager. It holds all accounts and cards, checks the card and PIN, and does the work for the customer.

**Steps:**

1. Private variables: `Account[] accounts`, `Card[] cards`, `TransactionLog log`, `Card currentCard` (the card that is logged in, null when nobody is logged in), and `atmLimit` (maximum cash in one withdrawal).
2. In the constructor, create your accounts and cards (at least 4 of each) and put them in the arrays. Every card must be linked to its own account.
3. Write `findCard(String cardNumber)`: search the cards array. If not found, return null.
4. Write `login(String cardNumber, int pin)`:
   - Find the card. If not found, print "Card not found" and return false.
   - Call `card.verifyPin(pin)`. (This can throw `CardBlockedException`. Handle it in Main.)
   - If the PIN is right: set `currentCard` and return true.
   - If wrong: print how many tries are used and return false.
5. Write `checkBalance()`: prints the balance of the current card's account.
6. Write `deposit(double amount)`: call `account.deposit(amount)`. Then make a Transaction and add it to the log.
7. Write `withdraw(double amount)`:
   - If amount is not a multiple of 100, print a message and stop.
   - If amount is more than `atmLimit`, print a message and stop.
   - Call `account.withdraw(amount)`. Then make a Transaction and add it to the log.
8. Write `miniStatement()`: call `log.showLast(accountNumber, 5)`.
9. Write `logout()`: set `currentCard` to null.
10. Main class has two menus:
    - First menu: Insert card / Stop program. If the customer chooses "Insert card", ask for card number and PIN, and call `login`.
    - Second menu: shown only after a successful login.
11. Use try/catch to show messages from `CardBlockedException`, `InvalidAmountException` and `InsufficientBalanceException`.

**First menu:**

```
1. Insert card
2. Stop program
```

**Second menu (after login):**

```
1. Check balance
2. Deposit
3. Withdraw
4. Mini statement (last 5)
5. Logout
```

## DEMO: your group must show all 8

1. Login with the correct PIN.
2. Login with a wrong PIN once, then a correct PIN. Show that the wrong count goes back to 0.
3. Type a wrong PIN 3 times. Show that the card is blocked. Try to use the same card again. The blocked message must show.
4. Deposit money and check the balance.
5. Withdraw money and check the balance.
6. Try to withdraw an amount that is not a multiple of 100. The message must show.
7. On a savings account, withdraw so that the balance goes below the minimum. The exception message must show. Then show the current account in a similar case, and explain the difference.
8. Show the mini statement with the last 5 transactions.