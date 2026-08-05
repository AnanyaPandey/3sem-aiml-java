import java.util.Scanner;
public class oddevenCounter {
    public static void  main(String[] arge){
        int oddcounter=0;
        int evencounter=0;

        Scanner sc = new Scanner(System.in);
	System.out.println("Welcome to my program");
        System.out.println("Enter a number ");

        int num = sc.nextInt();
        sc.nextLine();

        System.out.printf("Enter %d numbers ",num);
        for(int i=1; i <=num; i++) {
            int n = sc.nextInt();
            sc.nextLine();

            if (n%2==0){
                evencounter++; 
            } else {
                oddcounter++;
            }
        }
        System.out.printf("EvenNumbers %d OddNumbers %d",evencounter,oddcounter);
        sc.close();
    }
}
