package expr;

import java.math.BigInteger;

public class Cos implements Factor, Derivable {
    private Factor factor;
    private BigInteger indexNumber;
    private Poly poly;

    public Cos(Factor factor, String indexNumber) {
        this.factor = factor;
        this.indexNumber = new BigInteger(indexNumber);
        poly = null;
    }

    public Cos(Poly poly, BigInteger indexNumber) {
        this.poly = poly;
        this.indexNumber = indexNumber;
        factor = null;
    }

    @Override
    public Poly diff(String op) {
        Poly sinPoly = new Poly();
        Unit unit = new Unit(this.indexNumber.negate());
        unit.addSinCos(1, this.poly, this.indexNumber.subtract(BigInteger.ONE));
        unit.addSinCos(0, this.poly, BigInteger.ONE);
        sinPoly.addUnit(unit);

        Poly diffPoly = this.poly.diff(op);

        return diffPoly.mulPoly(sinPoly);
    }

    @Override
    public Poly toPoly() {
        if (factor == null) {
            Unit unit = new Unit(BigInteger.ONE);
            unit.addSinCos(1, this.poly, this.indexNumber);
            Poly poly = new Poly();
            poly.addUnit(unit);
            return poly;
        }
        Poly exprPoly = factor.toPoly();
        exprPoly.setMark();
        if (exprPoly.equalsZero()) {
            Unit unit = new Unit(BigInteger.ONE);
            Poly poly = new Poly();
            poly.addUnit(unit);
            return poly;
        }
        Unit unit = new Unit(BigInteger.ONE);
        unit.addSinCos(1, exprPoly, indexNumber);
        Poly poly = new Poly();
        poly.addUnit(unit);
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(cos(");
        sb.append(factor.toString()).append(')');
        if (indexNumber.compareTo(BigInteger.ONE) > 0) {
            sb.append("**").append(indexNumber);
        }
        sb.append(')');
        return sb.toString();
    }
}
