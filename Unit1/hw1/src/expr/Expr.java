package expr;

import java.math.BigInteger;
import java.util.ArrayList;

public class Expr implements Factor {
    private ArrayList<Term> terms;
    private int indexNumber;

    public Expr() {
        this.terms = new ArrayList<>();
        this.indexNumber = 1;
    }

    public void addTerm(Term term) {
        this.terms.add(term);
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public Poly toPoly() {
        Poly poly = new Poly();
        if (indexNumber == 0) {
            Mono mono = new Mono(new BigInteger("1"));
            poly.addMono(mono);
            return poly;
        }
        for (Term term : terms) {
            Poly temp = poly.addPoly(term.toPoly());
            poly = temp;
        }
        Poly temp = poly;
        for (int p = 2; p <= this.indexNumber; p++) {
            temp = temp.mulPoly(poly).mergeMono();
        }
        poly = temp;
        return poly;
    }

    @Override
    public String toString() {
        return "expr" + terms.toString() + "**" + String.valueOf(indexNumber);
    }

}
