package expr;

import java.math.BigInteger;

public class Sin implements Factor {
    private Factor factor;
    private BigInteger indexNumber;

    public Sin(Factor factor, String indexNumber) {
        this.factor = factor;
        this.indexNumber = new BigInteger(indexNumber);
    }

    @Override
    public Poly toPoly() {
        Poly exprPoly = factor.toPoly();
        exprPoly.setMark();
        if (exprPoly.equalsZero()) {
            Unit unit = new Unit(BigInteger.ZERO);
            if (indexNumber.equals(BigInteger.ZERO)) {
                unit.setCoe(BigInteger.ONE);
            }
            Poly poly = new Poly();
            poly.addUnit(unit);
            return poly;
        }
        Unit unit = new Unit(BigInteger.ONE);
        unit.addSinCos(0, exprPoly, indexNumber);
        Poly poly = new Poly();
        poly.addUnit(unit);
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(sin(");
        sb.append(factor.toString()).append(')');
        if (indexNumber.compareTo(BigInteger.ONE) > 0) {
            sb.append("**").append(indexNumber);
        }
        sb.append(')');
        return sb.toString();
    }
}
