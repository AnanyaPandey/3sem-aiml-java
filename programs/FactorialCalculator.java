// Calculate factorial of a given number

public class FactorialCalculator {
    public static void main(String[] args) {
        int number = 5; // Assumed number
        
        // Using 'long' because factorials grow very large very quickly
        long factorial = 1; 

        // Loop from 1 up to the number, multiplying each time
        for (int i = 1; i <= number; i++) {
            factorial = factorial * i;
        }

        System.out.println("The factorial of " + number + " is: " + factorial);
    }
}

