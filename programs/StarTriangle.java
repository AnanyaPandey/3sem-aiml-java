// Right-angled triangle pattern of stars
// *
// **
// ***
// ****
// *****

public class StarTriangle {
    public static void main(String[] args) {
        int rows = 5; // Assumed number of rows

        // Outer loop controls the rows
        for (int i = 1; i <= rows; i++) {
            // Inner loop controls the stars in each row
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            // Move to the next line after printing stars for the current row
            System.out.println();
        }
    }
}