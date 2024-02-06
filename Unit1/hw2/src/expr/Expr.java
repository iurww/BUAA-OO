package expr;

import java.math.BigInteger;
import java.util.ArrayList;

public class Expr implements Factor {
    private ArrayList<Term> terms;
    private BigInteger indexNumber;
    private final BigInteger zero = new BigInteger("0");

    public Expr() {
        this.terms = new ArrayList<>();
        this.indexNumber = new BigInteger("1");
    }

    public void addTerm(Term term) {
        this.terms.add(term);
    }

    public void setIndexNumber(BigInteger indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public Poly toPoly() {
        Poly poly = new Poly();
        if (indexNumber.equals(zero)) {
            Unit unit = new Unit(new BigInteger("1"));
            poly.addUnit(unit);
            return poly;
        }
        for (Term term : terms) {
            Poly temp = poly.addPoly(term.toPoly());
            poly = temp;
        }
        poly = poly.powPoly(indexNumber);
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("((");
        for (Term term : terms) {
            sb.append(term.toString());
        }
        sb.append(')');
        sb.append("**").append(indexNumber);
        sb.append(')');
        return sb.toString();
    }

}
