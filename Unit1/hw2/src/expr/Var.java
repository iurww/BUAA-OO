package expr;

import java.math.BigInteger;

public class Var implements Factor {
    private char name;
    private BigInteger indexNumber;

    public Var(char name, String indexNumber) {
        this.name = name;
        this.indexNumber = new BigInteger(indexNumber);
    }

    @Override
    public Poly toPoly() {
        Unit unit = new Unit(BigInteger.ONE);
        unit.addVar(String.valueOf(name), indexNumber);
        Poly poly = new Poly();
        poly.addUnit(unit);
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(name);
        sb.append("**").append(indexNumber);
        sb.append(')');
        return sb.toString();
    }
}
