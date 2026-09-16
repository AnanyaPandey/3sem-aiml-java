# Objects and Methods: Passing, Returning, and Reusing Objects

First try to understand -  what does an object variable actually hold?

When you write:

```java
Bike myBike = new Bike();
```

two separate things happen, in two separate places:

1. Java builds the actual `Bike` object somewhere in memory — real space for its fields like `speed`. Think of this as **a house being built** in a colony.
2. That house gets an address. That address is written on a small slip of paper, and that slip is what actually gets stored in your variable `myBike`.

So: **`myBike` does not contain the Bike. `myBike` contains the address of the Bike.** The object itself lives elsewhere; the variable is just an address slip pointing to it.

This one fact is the key to everything below.

------

## Assigning one object variable to another

```java
Bike myBike = new Bike();
myBike.speed = 20;

Bike yourBike = myBike;
```

It's tempting to think `yourBike` is now a second, independent Bike. It is not. `yourBike = myBike` copies the **address slip**, not the house. After this line:

```
myBike   ----> address "Plot 4529" ----> [ Bike object: speed = 20 ]
yourBike ----> address "Plot 4529" ----------------^ (same object)
```

Two slips, one house. So:

```java
yourBike.speed = 99;
System.out.println(myBike.speed);   // prints 99, not 20
```

`myBike` and `yourBike` were never two different bikes — they're two names for the same bike.

------

## Passing an object into a method

```java
class Bike {
    int speed;
}

class Test {
    static void increaseSpeed(Bike b) {
        b.speed = b.speed + 10;
    }

    public static void main(String[] args) {
        Bike myBike = new Bike();
        myBike.speed = 20;

        increaseSpeed(myBike);

        System.out.println(myBike.speed);   // 30
    }
}
```

Trace it step by step:

- `myBike` is built and holds an address (say "Plot 4529"). `speed` is set to 20.
- `increaseSpeed(myBike)` is called. What's actually passed in is a **copy of the address slip** — a new slip `b`, local to the method, but with the same address written on it.
- Inside the method, `b.speed = b.speed + 10` follows that address to the same house and changes `speed` to 30.
- The method ends and slip `b` disappears — but the house itself still has `speed = 30`, because the house was changed, not the slip.
- Back in `main`, `myBike` still points to the same house and now sees `speed = 30`.

**A method can permanently change an object it receives, without returning anything** — because it was never working on a copy of the object, only a copy of the address to the same object.

### Contrast: passing a primitive (`int`) instead

```java
static void increaseSpeed(int speed) {
    speed = speed + 10;
}

public static void main(String[] args) {
    int mySpeed = 20;
    increaseSpeed(mySpeed);
    System.out.println(mySpeed);   // still 20
}
```

A primitive variable holds the value directly — there's no house involved. Calling `increaseSpeed(mySpeed)` copies the actual number `20` into the method's local variable. Two independent 20s now exist; changing one does not touch the other.

**The rule in one line:** for a primitive, passing it copies the *value*. For an object, passing it copies the *address*, and the address points back to one shared object.

------

## Returning an object from a method

Same idea, opposite direction — the method builds (or finds) an object and hands its address slip back to the caller.

```java
class Bike {
    String color;
}

class BikeFactory {
    static Bike createRedBike() {
        Bike b = new Bike();
        b.color = "Red";
        return b;             // hand the address slip back out
    }
}

class Test {
    public static void main(String[] args) {
        Bike myBike = BikeFactory.createRedBike();
        System.out.println(myBike.color);   // Red
    }
}
```

`createRedBike()` builds the house and holds the only slip to it (`b`). `return b;` copies that address out to the caller, where it's stored in `myBike`.

------

## Reusing an existing object instead of creating a new one

This is where the previous two ideas come together. Multiple methods can all work on **one** object that already exists, simply by passing its address around — never saying `new` themselves.

```java
class Account {
    double balance;
}

class Bank {
    static void deposit(Account a, double amount) {
        a.balance += amount;
    }
    static void withdraw(Account a, double amount) {
        a.balance -= amount;
    }
}

class Test {
    public static void main(String[] args) {
        Account acc = new Account();   // ONE object, built once
        acc.balance = 1000;

        Bank.deposit(acc, 500);
        Bank.withdraw(acc, 200);

        System.out.println(acc.balance);   // 1300
    }
}
```

Only one `Account` object is ever created — `acc`. `deposit()` and `withdraw()` never say `new Account()`. They receive the existing object's address and work directly on it. That's what "reusing an object instead of creating a new one" means.

### The common mistake

```java
static void badDeposit(Account a, double amount) {
    Account temp = new Account();   // a brand-new, unrelated house
    temp.balance = amount;
}
```

This compiles without complaint, but it does nothing useful: `temp` is a completely new, disconnected object. The caller's `acc` is untouched. This is a common trap — feeling like a method "must create something," when the correct move is to operate on the object already handed to it.

------

## Summary

| Action                                     | What actually moves                                          |
| ------------------------------------------ | ------------------------------------------------------------ |
| Passing an object into a method            | A copy of the **address**, pointing to the same object       |
| Returning an object from a method          | The **address** handed back out to the caller                |
| Reusing an existing object                 | Passing the address around; never calling `new` again for the same conceptual object |
| Passing a primitive (`int`, `double`, ...) | A copy of the **value itself** — no shared object involved   |