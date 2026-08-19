// Program to demonstrate Class and Objects

public class Student {
    // Fields (attributes)
    private String name;
    private int rollNumber;
    private int[] marks; // marks in different subjects (out of 100 each)

    // Constructor
    public Student(String name, int rollNumber, int[] marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    // Method to calculate total marks
    public int calculateTotalMarks() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }

    // Method to calculate percentage
    public double calculatePercentage() {
        int total = calculateTotalMarks();
        return (total / (double) (marks.length * 100)) * 100;
    }

    // Method to determine grade based on percentage
    public char calculateGrade() {
        double percentage = calculatePercentage();

        if (percentage >= 90) {
            return 'A';
        } else if (percentage >= 75) {
            return 'B';
        } else if (percentage >= 60) {
            return 'C';
        } else if (percentage >= 40) {
            return 'D';
        } else {
            return 'F';
        }
    }

    // Method to check if student has passed
    public boolean hasPassed() {
        for (int mark : marks) {
            if (mark < 40) { // fail if any subject is below 40
                return false;
            }
        }
        return true;
    }

    // Method to display student's full report
    public void displayReport() {
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Total Marks: " + calculateTotalMarks());
        System.out.println("Percentage: " + calculatePercentage() + "%");
        System.out.println("Grade: " + calculateGrade());
        System.out.println("Result: " + (hasPassed() ? "PASS" : "FAIL"));
    }

    // Main method to test the class
    public static void main(String[] args) {
        // Creating a Student object
        int[] subjectMarks = {85, 78, 92, 60, 55};
        Student student1 = new Student("Ananya", 21, subjectMarks);

        student1.displayReport();

        System.out.println("----------------------");

        // Creating another Student object to show reusability
        int[] subjectMarks2 = {35, 42, 50, 30, 45};
        Student student2 = new Student("Rahul", 22, subjectMarks2);

        student2.displayReport();
    }
}