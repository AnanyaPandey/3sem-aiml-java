// Positive/Negative/Zero and Even/Odd

public class NumberProperties {
    public static void main(String[] args) {
        int number = 14; // Assumed number

        if (number > 0) {
            System.out.println(number + " is positive.");
            
            // Nested if/else for even/odd check
            if (number % 2 == 0) {
                System.out.println("It is also an even number.");
            } else {
                System.out.println("It is also an odd number.");
            }
        } else if (number < 0) {
            System.out.println(number + " is negative.");
        } else {
            System.out.println("The number is zero.");
        }
    }
}