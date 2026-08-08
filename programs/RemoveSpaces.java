// Remove All Spaces from a String

import java.util.Scanner;

public class RemoveSpaces {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        // .replace() looks for the first target (" ") and replaces it with the second target ("")
        String noSpaces = input.replace(" ", "");

        System.out.println("String without spaces: " + noSpaces);
        
        scanner.close();
    }
}

    