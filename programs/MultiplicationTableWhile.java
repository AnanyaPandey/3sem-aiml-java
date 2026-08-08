// Multiplication table using a while loop

public class MultiplicationTableWhile {
    public static void main(String[] args) {
        int number = 5; // Assumed number
        int i = 1;

        System.out.println("Multiplication table for " + number + ":");
        
        while (i <= 10) {
            System.out.println(number + " x " + i + " = " + (number * i));
            i++; // Increase i by 1 each time
        }
    }
}