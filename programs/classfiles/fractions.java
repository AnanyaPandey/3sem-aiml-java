package mathhelper;
public class fractions {
    int p,q; // p = numerator and q = denominator

    fractions(int P, int Q) {
        if (Q == 0) {
            System.out.println("WARNING!: denominator 0 not allowed, using 1");
            Q = 1;
    }
        if (Q < 0) { P = -P; Q = -Q; }
        this.p = P;
        this.q = Q;
    }
    void show() {
        System.out.printf("%d/%d",p,q);
    }

    @Override 
    public String toString() {
        return String.format("%d/%d",p,q);
    }

    public fractions product(fractions other) {
        int P = this.p * other.p;
        int Q  = this.q * other.q;
        return new fractions(P,Q).SIMPLIFY();
    }

    public fractions divide(fractions other) {
        int P = this.p * other.q;
        int Q = this.q * other.p;
        return new fractions(P,Q).SIMPLIFY();
    }

    int hcf() {
        int P = Math.abs(this.p);
        int Q = Math.abs(this.q);
        int min,h=1;
        if(P<=Q || P ==0) 
            min=P;
        else
            min=Q;
        for(int i=1;i<=min;i++) {
            if(P%i==0 && Q%i==0)
                h=i;
        }
        return h;
    }

    public fractions SIMPLIFY() {
        int h = hcf();
        int P = this.p / h;
        int Q = this.q / h;
        return new fractions(P,Q);
    }

    public fractions AddF(fractions other) {
        int P = (this.p*other.q + this.q*other.p);
        int Q = this.q*other.q;
        return new fractions(P,Q).SIMPLIFY(); 
    }

        public fractions SubF(fractions other) {
        int P = (this.p*other.q - this.q*other.p);
        int Q = this.q*other.q;
        return new fractions(P,Q).SIMPLIFY(); 
    }

    public static void main (String[] args) {
        fractions f1 = new fractions(5,20);
        fractions f2 = new fractions(-3,-10);
        fractions f3 = f1.SIMPLIFY().AddF(f2.SIMPLIFY());
        System.out.println("Addition :"+f3);
        fractions f4 = f1.SIMPLIFY().SubF(f2.SIMPLIFY());
        System.out.println("Subtraction :"+f4);
    }
}