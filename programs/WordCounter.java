// Count Words in a Sentence

import java.util.Scanner;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence: ");
        String sentence = scanner.nextLine().trim(); // .trim() removes leading/trailing spaces

        // If the string is empty after trimming, there are 0 words
        if (sentence.isEmpty()) {
            System.out.println("Total words: 0");
        } else {
            // .split("\\s+") splits the sentence into an array of words at every space (or multiple spaces)
            String[] words = sentence.split("\\s+");
            System.out.println("Total words: " + words.length);
        }
        
        scanner.close();
    }
}