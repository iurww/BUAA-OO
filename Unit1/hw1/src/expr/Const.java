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
        return poly;
    }

    @Override
    public String toString() {
        return "Const=" + num.toString();
    }
}
