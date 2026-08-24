public class StudentCC {
    // Instance variables
    String name;
    int age;
    String course;

    // Constructor 1: only name
    public StudentCC(String name) {
        // "this()" - calls Constructor 2 (name, age), not Constructor 3 directly
        this(name, 18);
        System.out.println("Constructor 1 called");
    }

    // Constructor 2: name and age
    public StudentCC(String name, int age) {
        // "this()" - calls Constructor 3, passing a default course
        this(name, age, "Not Assigned");
        System.out.println("Constructor 2 called");
    }

    // Constructor 3: name, age, and course (full constructor)
    public StudentCC(String name, int age, String course) {
        // "this." - refers to the current object's fields
        // Needed here because parameter names are same as field names
        this.name = name;
        this.age = age;
        this.course = course;
        System.out.println("Constructor 3 called");
    }

    // Method using "this" to return the current object
    public StudentCC updateCourse(String course) {
        this.course = course;   // "this." resolves naming conflict
        return this;            // "this" returns the current object (method chaining)
    }

    public void display() {
        System.out.println("Name: " + this.name + ", Age: " + this.age + ", Course: " + this.course);
    }

    public static void main(String[] args) {
        System.out.println("--- Creating student1 with only name ---");
        StudentCC student1 = new StudentCC("Ananya");
        student1.display();

        System.out.println("\n--- Creating student2 with name and age ---");
        StudentCC student2 = new StudentCC("Rahul", 20);
        student2.display();

        System.out.println("\n--- Creating student3 with name, age, course ---");
        StudentCC student3 = new StudentCC("Priya", 19, "AI & ML");
        student3.display();

        System.out.println("\n--- Using method chaining with 'this' ---");
        student1.updateCourse("Data Science").display();
    }
}