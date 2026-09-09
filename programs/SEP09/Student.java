public class Student {
    String name;
    long rollno;
    double cgpa;
    private double percentage;
    private int java_marks,os_marks, wd_marks;

    public Student(String nam, long roll) {
        this(nam,roll,0.0,0.0,0,0,0);
    }

    public Student(String nam) {
        this(nam,0,0,0,0,0,0);
    }

    public Student(String nam, long roll, double cgpa, double per, int jav, int wd, int os) {
            this.name = nam;
            this.rollno = roll;
            this.cgpa = cgpa;
            this.percentage = per;
            this.java_marks = jav;
            this.wd_marks = wd;
            this.os_marks = os;

    }

    void update_marks(int jav, int wd, int os) {
        this.java_marks = jav;
        this.wd_marks = wd;
        this.os_marks = os;
    }

    void calc_percentage() {
        int total_marks;
        total_marks = java_marks + os_marks + wd_marks;
        percentage = total_marks /3;
        System.out.println("Percentage is "+percentage);
    }

    void greet_Student() {
        System.out.println("Hello : "+name);
        System.out.println("Roll no: "+rollno);
    }

}

