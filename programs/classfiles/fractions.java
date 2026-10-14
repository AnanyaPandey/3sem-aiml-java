package mathhelper;
public class fractions {
    int p,q; // p = numerator and q = denominator

    fractions(int P, int Q) {
        this.p = P;
        this.q = Q;
    }
    void show() {
        System.out.printf("%d/%d",p,q);
    }

    public fractions product(fractions other) {
        int P = this.p * other.p;
        int Q  = this.q * other.q;
        return new fractions(P,Q);
    }

    public fractions divide(fractions other) {
        int P = this.p * other.q;
        int Q = this.q * other.p;
        return new fractions(P,Q);
    }

    int hcf() {
        int P = this.p;
        int Q = this.q;
        int min,h=1;
        if(P<=Q) 
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

    public static void main (String[] args) {
        fractions f1 = new fractions(5,20);
        fractions f2 = new fractions(3,10);
        fractions f3 = f1.SIMPLIFY().AddF(f2.SIMPLIFY());
        //System.out.println();
        f3.show();
    }


}