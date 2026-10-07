package mathhelper;
public class fractions {
    int p,q; // numerator and denominator

    fractions(int N,int D) {
        this.p = N;
        this.q = D;
    }

    public void show(){
        System.out.printf("%d/%d",p,q);
    }

    @Override
    public String toString() {
        return p + "/" + q;
    }

    public fractions multiply(fractions f) {
        int P = this.p * f.p;
        int Q = this.q * f.q;
        return new fractions(P,Q);
    }

    public fractions divide(fractions f) {
        int P = this.p * f.q;
        int Q = this.q * f.p;
        return new fractions(P,Q);
    }

    public int lcm() {
        int a = this.p; // numebrator
        int b = this.q; // denominator
        int h = this.hcf();
        return (a*b)/h;
    }

    public void simplify() {
        int h = this.hcf();
        this.p = this.p / h;
        this.q = this.q / h ;

        if(this.q <0) {
            this.p = -this.p;
            this.q = -this.q;
        }
    }

    public int hcf() {
        int a = this.p; // numerator
        int b = this.q; // denominator
        int minn,hcf=1;
        if(a<=b)
            minn = a;
        else 
            minn = b;
        for(int i =1;i<=minn;i++) {
            if(a%i==0 && b%i==0)
                hcf = i;
        }
        return hcf;
    }

    public static void main(String[] args) {
        fractions f1 = new fractions(2,5);
        fractions f2 = new fractions(3,5);
        fractions f3 = f1.multiply(f2);
        fractions f4 = f1.divide(f2);
        System.out.println("The product of f1 f2 is "+f3);
        System.out.println("The division of f1 f2 is  "+f4);
    }
}

