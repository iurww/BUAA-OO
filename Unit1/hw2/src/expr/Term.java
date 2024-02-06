package expr;

import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private BigInteger coe;
    private ArrayList<Factor> factors;

    public Term(char sign) {
        this.factors = new ArrayList<>();
        if (sign == '+') {
            this.coe = new BigInteger("1");
        } else {
            this.coe = new BigInteger("-1");
        }
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
        Unit unit = new Unit(coe);
        poly.addUnit(unit);
        for (Factor factor : factors) {
            Poly temp = poly.mulPoly(factor.toPoly());
            poly = temp;
        }
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (coe.compareTo(BigInteger.ZERO) >= 0) {
            sb.append('+').append(coe);
        } else {
            sb.append(coe);
        }
        for (Factor factor : factors) {
            sb.append('*');
            sb.append(factor.toString());
        }
        if (this.factors.size() > 0) {
            if (sb.substring(1, 3).equals("1*")) {
                sb.delete(1, 3);
            }
        }
        return sb.toString();
    }
}
