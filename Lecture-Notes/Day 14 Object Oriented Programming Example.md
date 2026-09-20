## Example For OOP

```java
class Fraction {
    // ENCAPSULATION: fields are private - no outside code can touch them directly
    private int numerator;
    private int denominator;

    // Constructor - with validation logic
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    // Getters - controlled, read-only access to the private fields
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    // Private helper - GCD using Euclid's algorithm.
    // Marked private since it's just an internal implementation detail.
    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Addition: (a/b) + (c/d) = (a*d + c*b) / (b*d)
    public Fraction add(Fraction other) {
        int newNumerator = (this.numerator * other.denominator) + (other.numerator * this.denominator);
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    // Subtraction: (a/b) - (c/d) = (a*d - c*b) / (b*d)
    public Fraction subtract(Fraction other) {
        int newNumerator = (this.numerator * other.denominator) - (other.numerator * this.denominator);
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    // Multiplication: (a/b) * (c/d) = (a*c) / (b*d)
    public Fraction multiply(Fraction other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    // Division: (a/b) / (c/d) = (a*d) / (b*c)
    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by a zero fraction");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction(newNumerator, newDenominator);
    }

    // Simplify - reduces to lowest terms using GCD
    public Fraction simplify() {
        int commonDivisor = gcd(numerator, denominator);
        int newNumerator = numerator / commonDivisor;
        int newDenominator = denominator / commonDivisor;

        // keep the sign on the numerator only, not the denominator
        if (newDenominator < 0) {
            newNumerator = -newNumerator;
            newDenominator = -newDenominator;
        }

        return new Fraction(newNumerator, newDenominator);
    }

    // Simple display method - just prints directly, no toString/@Override needed
    public void display() {
        System.out.println(numerator + "/" + denominator);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(3, 4);

        System.out.print("f1 = ");
        f1.display();

        System.out.print("f2 = ");
        f2.display();

        System.out.print("Sum (simplified): ");
        f1.add(f2).simplify().display();

        System.out.print("Difference (simplified): ");
        f1.subtract(f2).simplify().display();

        System.out.print("Product (simplified): ");
        f1.multiply(f2).simplify().display();

        System.out.print("Division (simplified): ");
        f1.divide(f2).simplify().display();
    }
}
```

```java
// OUTPUT 

f1 = 1/2
f2 = 3/4
Sum (simplified): 5/4
Difference (simplified): -1/4
Product (simplified): 3/8
Division (simplified): 2/3
```

