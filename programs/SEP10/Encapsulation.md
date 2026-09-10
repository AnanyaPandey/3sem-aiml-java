### The Idea

**Encapsulation** = keep data private, only accessible through controlled methods (getters/setters).
 **Inheritance** = child class reuses/extends a parent class.

Combining them means: the parent class protects its own data using encapsulation, and the child class still manages to work with that data properly — through the parent's methods, not by breaking in directly.

### Example: BankAccount → SavingsAccount

java

```java
class BankAccount {
    private double balance;         // ENCAPSULATION: private, hidden from outside
    protected String accountHolder; // protected: visible to child classes

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    // controlled access to the private field - this IS encapsulation
    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        }
    }

    protected void deductBalance(double amount) {
        // protected, so child classes CAN use this, but outside classes cannot
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance");
        }
    }
}
```

java

```java
class SavingsAccount extends BankAccount {    // INHERITANCE: SavingsAccount IS-A BankAccount
    private double interestRate;

    public SavingsAccount(String accountHolder, double initialBalance, double interestRate) {
        super(accountHolder, initialBalance);   // reuse parent's constructor
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate / 100;  // uses parent's PUBLIC getter
        deposit(interest);                                     // uses parent's PUBLIC method
        System.out.println(accountHolder + " earned interest: " + interest);
    }

    public void withdraw(double amount) {
        deductBalance(amount);   // uses parent's PROTECTED method - only children can do this
        System.out.println(accountHolder + " withdrew: " + amount);
    }
}
```

java

```java
public class Main {
    public static void main(String[] args) {
        SavingsAccount acc = new SavingsAccount("Ananya", 1000, 5);

        acc.addInterest();
        acc.withdraw(200);

        System.out.println("Final Balance: " + acc.getBalance());

        // acc.balance = 999999;  ❌ ERROR - balance is private, cannot touch it directly,
        //                          even though SavingsAccount is a child class!
    }
}
```

**Output:**

```
Deposited: 50.0
Ananya earned interest: 50.0
Insufficient balance   (only if withdrawal amount exceeds balance - won't happen here)
Ananya withdrew: 200.0
Final Balance: 850.0
```

### Why This Shows BOTH Concepts Together

**Encapsulation in action:**

- `balance` is `private` — even `SavingsAccount`, despite being a child class, **cannot directly touch it** (`acc.balance = 999999` would be a compile error). It's forced to go through `getBalance()`, `deposit()`, and `deductBalance()` — the controlled doors Animal chose to expose.

**Inheritance in action:**

- `SavingsAccount` never wrote its own `balance` field or `deposit()` logic — it got all of that for free from `BankAccount` via `extends`.

**The important overlap — why they need each other here:**

- Notice `balance` is `private` (not accessible to child), but `deductBalance()` is `protected` (accessible to child). This is a deliberate design choice: the parent class is saying "children can perform withdrawals through my controlled method, but they can never directly edit the raw number themselves." That's encapsulation **enforcing rules even on your own child classes** — inheritance gives you reuse, but encapsulation still keeps the actual data safe and controlled, no matter how many layers of subclassing happen.

**The one-line takeaway:** Inheritance lets `SavingsAccount` reuse `BankAccount`'s code, but encapsulation makes sure that even as a child, it can only affect `balance` through methods the parent explicitly allowed — never directly.