package expr;

import java.math.BigInteger;

public class Const implements Factor {
    private BigInteger num;

    public Const(String num) {
        this.num = new BigInteger(num);
    }

    public BigInteger getNum() {
        return this.num;
    }

    @Override
    public Poly toPoly() {
        Poly poly = new Poly();
        Unit unit = new Unit(num);
        poly.addUnit(unit);
        return poly;
    }

    @Override
    public String toString() {
        return '(' + num.toString() + ')';
    }
}
