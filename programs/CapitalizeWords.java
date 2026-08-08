// Capitalize the First Letter of Each Word

import java.util.Scanner;

public class CapitalizeWords {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        String result = "";
        boolean capitalizeNext = true; // True initially to capitalize the very first letter

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (ch == ' ') {
                // If it's a space, just add the space and flag the next character to be capitalized
                result = result + ch;
                capitalizeNext = true;
            } else if (capitalizeNext) {
                // Capitalize the letter and turn off the flag
                result = result + Character.toUpperCase(ch);
                capitalizeNext = false;
            } else {
                // Add the character exactly as it is
                result = result + ch;
            }
        }

        System.out.println("Capitalized String: " + result);
        
        scanner.close();
    }
}

