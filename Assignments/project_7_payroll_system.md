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

# PROJECT 7: PAYROLL SYSTEM

## What you will make

A company salary program. The company has different types of employees (manager, developer, intern). Each type has different rules for allowance and PF. The program makes a salary slip for any employee.

## Decisions you must make as a group (write them in your 5 lines)

1. How many employees? Make at least 6 (at least 2 managers, 2 developers, 2 interns).
2. Each employee has a unique ID. Example: E101, E102, E103...
3. The basic salary of each employee.
4. The allowance rule for each type (the example below is only a guide).
5. PF percent (example: 12%).
6. The tax rule (example: if gross salary is more than Rs. 50000, tax is 10% of gross. Otherwise, no tax).

## Rules of the payroll

1. Allowance for each type (example):
   - Manager: 40% of basic + Rs. 5000 team allowance
   - Developer: 25% of basic
   - Intern: no allowance
2. **Gross salary = basic + allowance.**
3. PF = basic x PF percent. Managers and developers pay PF. Interns do not pay PF.
4. A month has 30 days. Absent deduction = (basic / 30) x absent days.
5. Tax follows the rule your group decided.
6. **Net salary = gross - PF - absent deduction - tax.**
7. Absent days must be between 0 and 30.

**Example:** Developer, basic Rs. 40000. Allowance = 25% = 10000. Gross = 50000. PF = 12% of 40000 = 4800. Absent 3 days: (40000 / 30) x 3 = 4000. Tax = 0 (because gross is not more than 50000). Net = 50000 - 4800 - 4000 - 0 = Rs. 41200.

## PERSON 1: Employee and InvalidSalaryException

**What this class is for:** The parent class that stores what is common for all employees.

**Steps:**

1. Make an abstract class `Employee`.
2. Private variables: `employeeId`, `name`, `basicSalary`.
3. Make a constructor that takes all three values. If the basic salary is 0 or less, throw `InvalidSalaryException`. (A constructor can throw an exception. Write `throws InvalidSalaryException` after the constructor name.)
4. Make getters.
5. Add two abstract methods: `getAllowance()` and `getRoleName()`.
6. Write a normal method `getPfPercent()` that returns the PF percent your group decided (example 12). Children can override it.
7. Write `toString()`: shows ID, name, role and basic salary.
8. Make the custom exception `InvalidSalaryException`: message "Basic salary must be more than 0".

**How to test your part:** Try to make an object with salary 0 (use a temporary child class). The exception must come.

## PERSON 2: Manager, Developer and Intern

**What these classes are for:** Each child class gives its own allowance rule and its own extra information.

**Steps:**

1. Make child class `Manager` that extends Employee.
   - Extra private variable: `teamSize`.
   - Override `getAllowance()`: 40% of basic + 5000 (or your group's rule).
   - Override `getRoleName()`: return "Manager".
2. Make child class `Developer` that extends Employee.
   - Extra private variable: `primaryLanguage` (example "Java").
   - Override `getAllowance()`: 25% of basic (or your group's rule).
   - Override `getRoleName()`: return "Developer".
3. Make child class `Intern` that extends Employee.
   - Extra private variable: `durationMonths`.
   - Override `getAllowance()`: return 0.
   - Override `getRoleName()`: return "Intern".
   - Override `getPfPercent()`: return 0 (interns do not pay PF).
4. Each child needs a constructor. Use `super(...)` to send the common values to the parent constructor.
5. Each child overrides `toString()` to also show its own extra variable.
6. Make getters for the extra variables.

**How to test your part:** Make one of each type with the same basic salary. Print their allowances. They must be different.

## PERSON 3: Payslip and InvalidAbsentDaysException

**What this class is for:** The Payslip does all the salary calculations for one employee for one month.

**Steps:**

1. Private variables: `employee`, `absentDays`.
2. Make a constructor that takes an Employee and absentDays. If absentDays is less than 0 or more than 30, throw `InvalidAbsentDaysException`.
3. Write these methods:
   - `calculateGross()`: basic + allowance.
   - `calculatePf()`: basic x `employee.getPfPercent()` / 100.
   - `calculateAbsentDeduction()`: (basic / 30) x absentDays.
   - `calculateTax()`: use your group's tax rule on the gross salary.
   - `calculateNetSalary()`: gross - PF - absent deduction - tax.
4. Write `printPayslip()`. Example output:

```
------ PAYSLIP ------
Employee: E103   Name: Neha   Role: Developer
Basic:             Rs. 40000
Allowance:         Rs. 10000
Gross salary:      Rs. 50000
PF:                Rs. 4800
Absent (3 days):   Rs. 4000
Tax:               Rs. 0
NET SALARY:        Rs. 41200
---------------------
```

1. Make the custom exception `InvalidAbsentDaysException`: message "Absent days must be between 0 and 30".

**How to test your part:** Use the example from the rules. Check that your net salary is Rs. 41200.

## PERSON 4: Company, EmployeeNotFoundException and Main

**What this class is for:** This is the main manager. It holds all employees and connects everything.

**Steps:**

1. Private variables: `Employee[] employees` (size is your choice, bigger than your employee count) and a counter `employeeCount`.
2. `addEmployee(Employee e)`:
   - If another employee already has the same ID, print "ID already exists" and stop.
   - If the array is full, print a message and stop.
   - Otherwise, store the employee and increase the counter.
3. `findEmployee(String id)`: search the array. If not found, throw `EmployeeNotFoundException`.
4. `generatePayslip(String id, int absentDays)`: find the employee, make a Payslip, and print it.
5. `showAllEmployees()`: prints all employees with their role.
6. `showSalaryReport()`: for each employee, make a Payslip with 0 absent days. Print name, role and net salary in one line each. At the end, print the total money the company must pay.
7. Create your starting employees (at least 6) in the constructor or in Main and add them using `addEmployee`.
8. Make the custom exception `EmployeeNotFoundException`: message "Employee ID not found".
9. Main class: show the menu in a loop. Use try/catch to show messages from your exceptions (and from `InvalidSalaryException` and `InvalidAbsentDaysException`).

**Menu:**

```
1. Add new employee
2. Show all employees
3. Generate payslip
4. Show salary report (all employees)
5. Stop program
```

## DEMO: your group must show all 7

1. Show all employees (all 3 types must be present).
2. Generate the payslip of a manager, a developer and an intern. Explain why the numbers are different.
3. Generate a payslip with absent days. Check the deduction on paper.
4. Show an employee whose gross salary is above your tax limit, and show the tax is charged.
5. Try to find an ID that does not exist. The "not found" message must show.
6. Try to add an employee with salary 0. The "invalid salary" message must show.
7. Show the salary report with the total payout.