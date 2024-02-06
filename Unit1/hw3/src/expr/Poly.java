package expr;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Poly {
    private HashMap<Unit, BigInteger> unitList;

    public Poly() {
        this.unitList = new HashMap<>();
    }

    public void setMark() {
        for (Unit unit : unitList.keySet()) {
            unit.setMark();
        }
        HashMap<Unit, BigInteger> newUnitList = new HashMap<>();
        for (Unit unit : unitList.keySet()) {
            newUnitList.put(unit, unitList.get(unit));
        }
        unitList = newUnitList;
    }

    public boolean isFactor() {
        if (unitList.size() == 1) {
            for (Unit unit : unitList.keySet()) {
                return unit.isFactor();
            }
        }
        return false;
    }

    public boolean equalsZero() {
        return toString().equals("0");
    }

    public void addUnit(Unit unit) {
        if (unitList.keySet().contains(unit)) {
            BigInteger oldCoe = unitList.get(unit);
            unit.setCoe(oldCoe.add(unit.getCoe()));
            unitList.remove(unit);
            unitList.put(unit, unit.getCoe());
        } else {
            unitList.put(unit, unit.getCoe());
        }
    }

    public Poly addPoly(Poly poly) {
        Poly newPoly = new Poly();
        for (Unit unit : this.unitList.keySet()) {
            newPoly.addUnit(unit);
        }
        for (Unit unit : poly.unitList.keySet()) {
            newPoly.addUnit(unit);
        }
        return newPoly;
    }

    public Poly mulPoly(Poly poly) {
        Poly newPoly = new Poly();
        for (Unit unit1 : this.unitList.keySet()) {
            for (Unit unit2 : poly.unitList.keySet()) {
                newPoly.addUnit(unit1.mulUnit(unit2));
            }
        }
        return newPoly;
    }

    public Poly powPoly(BigInteger indexNumber) {
        Poly temp = this;
        Poly ans = new Poly();
        Unit one = new Unit(BigInteger.ONE);
        ans.addUnit(one);
        BigInteger exp = indexNumber;
        while (exp.compareTo(BigInteger.ZERO) > 0) {
            if (exp.mod(new BigInteger("2")).equals(BigInteger.ONE)) {
                ans = ans.mulPoly(temp);
            }
            temp = temp.mulPoly(temp);
            exp = exp.divide(new BigInteger("2"));
        }
        return ans;
    }

    public Poly diff(String op) {
        Poly poly = new Poly();
        for (Unit unit : unitList.keySet()) {
            poly = poly.addPoly(unit.diff(op));
        }
        return poly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Poly poly = (Poly) o;
        return unitList.keySet().equals(poly.unitList.keySet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitList.keySet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Unit unit : unitList.keySet()) {
            sb.append(unit.toString());
        }
        if (sb.toString().equals("")) {
            return "0";
        }
        if (sb.charAt(0) == '+') {
            return sb.substring(1);
        }
        return sb.toString();
    }
}
