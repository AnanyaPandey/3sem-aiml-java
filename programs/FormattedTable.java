// Multiplication table using printf

public class FormattedTable {
    public static void main(String[] args) {
        int number = 8; // Assumed number

        System.out.println("Formatted table for " + number + ":");
        for (int i = 1; i <= 10; i++) {
            // %d prints an integer. %2d ensures it takes up at least 2 spaces for alignment.
            System.out.printf("%d x %2d = %3d\n", number, i, (number * i));
        }
    }
}