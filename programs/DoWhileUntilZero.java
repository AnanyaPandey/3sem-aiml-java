// Do-while loop until user enters 0

import java.util.Scanner;

public class DoWhileUntilZero {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number;

        // do-while guarantees the code runs at least once
        do {
            System.out.print("Enter a number (0 to stop): ");
            number = scanner.nextInt();
        } while (number != 0);

        System.out.println("You entered 0. Program stopped.");
        scanner.close();
    }
}