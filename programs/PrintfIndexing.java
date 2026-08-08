// Printf Argument Indexing
// (In printf, %1$s means "use the 1st argument as a string" and %2$d means 
// "use the 2nd argument as a decimal integer". This lets you reuse arguments without passing them multiple times.)

public class PrintfIndexing {
    public static void main(String[] args) {
        String name = "Ravi";
        int score = 85;

        // %1$s refers to "Ravi" (1st argument), %2$d refers to 85 (2nd argument)
        System.out.printf("%1$s scored %2$d. Well done, %1$s!\n", name, score);
    }
}
