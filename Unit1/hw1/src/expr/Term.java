package expr;

import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private ArrayList<Factor> factors;
    private BigInteger coe;

    public Term(char sign) {
        this.factors = new ArrayList<>();
        if (sign == '+') {
            this.coe = new BigInteger("1");
        } else {
            this.coe = new BigInteger("-1");
        }
    }

    public ArrayList<Factor> getFactors() {
        return factors;
    }

    public BigInteger getCoe() {
        return coe;
    }

    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }

    public void inverse(char sign) {
        if (sign == '-') {
            this.coe = this.coe.negate();
        }
    }

    public void mergeConst() {
        for (int i = 0; i < factors.size(); i++) {
            if (factors.get(i) instanceof Const) {
                Const t = (Const) factors.get(i);
                this.coe = this.coe.multiply(t.getNum());
                factors.remove(i);
                i--;
            }
        }
    }

    public Poly toPoly() {
        Poly poly = new Poly();
        Mono mono = new Mono(coe);
        poly.addMono(mono);
        for (Factor factor : factors) {
            Poly temp = poly.mulPoly(factor.toPoly());
            poly = temp;
        }
        return poly;
    }

    @Override
    public String toString() {
        return "term" + coe + "*" + factors.toString();
    }

}
