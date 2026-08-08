// Calculate sum of 1 to N Without the formula 

public class SumToN {
    public static void main(String[] args) {
        int n = 10; // Assumed value for N
        int sum = 0;

        // Loop from 1 up to N, adding each number to the sum
        for (int i = 1; i <= n; i++) {
            sum = sum + i; 
        }

        System.out.println("The sum of 1 to " + n + " is: " + sum);
    }
}