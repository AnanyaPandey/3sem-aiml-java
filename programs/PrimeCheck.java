// Check if a number is prime using if/else

public class PrimeCheck {
    public static void main(String[] args) {
        int number = 7; // Assumed number
        boolean isPrime = true;

        if (number <= 1) {
            isPrime = false;
        } else {
            // Check for factors up to half the number
            for (int i = 2; i <= number / 2; i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        }

        if (isPrime) {
            System.out.println(number + " is a prime number.");
        } else {
            System.out.println(number + " is not a prime number.");
        }
    }
}