package expr;

import java.math.BigInteger;

public class Sin implements Factor, Derivable {
    private Factor factor;
    private BigInteger indexNumber;
    private Poly poly;

    public Sin(Factor factor, String indexNumber) {
        this.factor = factor;
        this.indexNumber = new BigInteger(indexNumber);
        this.poly = null;
    }

    public Sin(Poly poly, BigInteger indexNumber) {
        this.poly = poly;
        this.indexNumber = indexNumber;
        this.factor = null;
    }

    @Override
    public Poly diff(String op) {
        Poly cosPoly = new Poly();
        Unit unit = new Unit(this.indexNumber);
        unit.addSinCos(0, this.poly, this.indexNumber.subtract(BigInteger.ONE));
        unit.addSinCos(1, this.poly, BigInteger.ONE);
        cosPoly.addUnit(unit);

        Poly diffPoly = this.poly.diff(op);

        return diffPoly.mulPoly(cosPoly);
    }

    @Override
    public Poly toPoly() {
        if (factor == null) {
            Unit unit = new Unit(BigInteger.ONE);
            unit.addSinCos(0, this.poly, this.indexNumber);
            Poly poly = new Poly();
            poly.addUnit(unit);
            return poly;
        }
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
