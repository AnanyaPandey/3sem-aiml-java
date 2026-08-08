// Check if entered number is a palindrome

import java.util.Scanner;

public class PalindromeCheck2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number to check for palindrome: ");
        int originalNumber = scanner.nextInt();
        
        int tempNumber = originalNumber;
        int reversedNumber = 0;

        // Loop to reverse the number
        while (tempNumber != 0) {
            int lastDigit = tempNumber % 10; // Get the last digit
            reversedNumber = (reversedNumber * 10) + lastDigit; // Add it to the reversed number
            tempNumber = tempNumber / 10; // Remove the last digit from the temp number
        }

        // Compare the reversed number with the original
        if (originalNumber == reversedNumber) {
            System.out.println(originalNumber + " is a palindrome.");
        } else {
            System.out.println(originalNumber + " is not a palindrome.");
        }
        
        scanner.close();
    }
}

