// Write a program that prints numbers from 1 to 50, but skips multiples of 3 using continue, and stops completely if the number exceeds 40 using break.
// Print 1 to 50 (Skip multiples of 3, stop after 40)

public class BreakContinueExample {
    public static void main(String[] args) {
        System.out.println("Numbers from 1 to 40 (skipping multiples of 3):");
        
        for (int i = 1; i <= 50; i++) {
            // Stop the loop completely if the number is greater than 40
            if (i > 40) {
                break;
            }
            
            // Skip the rest of the loop for multiples of 3
            if (i % 3 == 0) {
                continue;
            }
            
            System.out.println(i);
        }
    }
}