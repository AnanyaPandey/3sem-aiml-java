public class testingprogram {
    public static void main(String[] args) {
    int input=1;
    if(printhello(input)) {
        System.out.println("Calling the True function");
    } else {
        System.out.println("Calling the False function");
    }
    

    }

    public static boolean printhello(int input){
        //System.out.print("Printing Function");
        if(input ==1){
            return false;
        } else {
            return true;
        } 
    }
}