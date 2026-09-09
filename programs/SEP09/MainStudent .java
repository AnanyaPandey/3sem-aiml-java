class MainStudent {   
   public static void main(String[] args) {
        Student s1 = new Student("Ravi", 2132334, 0.0, 0.0, 78, 67, 88);
        s1.greet_Student();
        s1.calc_percentage();

        Student s2 = new Student("Anjali", 32134);
        s2.greet_Student();
        s2.calc_percentage();

        Student s3 = new Student("Ramesh");
        s3.greet_Student();
        s3.calc_percentage();
        s3.update_marks(90, 80, 70);
        s3.greet_Student();
        s3.calc_percentage();
    }   

}