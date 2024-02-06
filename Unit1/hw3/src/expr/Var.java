package expr;

import java.math.BigInteger;

public class Var implements Factor, Derivable {
    private String name;
    private BigInteger indexNumber;

    public Var(String name, String indexNumber) {
        this.name = name;
        this.indexNumber = new BigInteger(indexNumber);
    }

    public Var(String name, BigInteger indexNumber) {
        this.name = name;
        this.indexNumber = indexNumber;
    }

    public Poly diff(String op) {
        Poly ans = new Poly();
        if (this.name.equals(op)) {
            Unit unit = new Unit(this.indexNumber);
            unit.addVar(this.name, this.indexNumber.subtract(BigInteger.ONE));
            ans.addUnit(unit);
        }
        return ans;
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
