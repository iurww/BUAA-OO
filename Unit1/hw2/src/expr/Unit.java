package expr;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Unit {
    private BigInteger coe;
    private HashMap<String, BigInteger> vars;
    private HashMap<Poly, BigInteger> sinMap;
    private HashMap<Poly, BigInteger> cosMap;
    private BigInteger mark;
    private final BigInteger zero = new BigInteger("0");

    public Unit(BigInteger coe) {
        this.coe = coe;
        this.vars = new HashMap<>();
        this.sinMap = new HashMap<>();
        this.cosMap = new HashMap<>();
        this.mark = null;
    }

    public BigInteger getCoe() {
        return this.coe;
    }

    public void setCoe(BigInteger coe) {
        this.coe = coe;
    }

    public int size() {
        return this.vars.size() + this.sinMap.size() + this.cosMap.size();
    }

    public void setMark() {
        this.mark = this.coe;
    }

    public boolean isFactor() {
        if (size() == 0) {
            return true;
        } else if (size() == 1) {
            if (coe.equals(BigInteger.ONE)) {
                return true;
            }
        }
        return false;
    }

    public void addVar(String name, BigInteger indexNumber) {
        if (vars.containsKey(name)) {
            BigInteger oldIndexNumber = vars.get(name);
            vars.replace(name, oldIndexNumber.add(indexNumber));
        } else if (indexNumber.compareTo(BigInteger.ZERO) > 0) {
            vars.put(name, indexNumber);
        }
    }

    public void addSinCos(int type, Poly poly, BigInteger indexNumber) {
        HashMap<Poly, BigInteger> map = (type == 0 ? this.sinMap : this.cosMap);
        if (map.containsKey(poly)) {
            BigInteger oldIndexNumber = map.get(poly);
            map.replace(poly, oldIndexNumber.add(indexNumber));
        } else if (indexNumber.compareTo(BigInteger.ZERO) > 0) {
            map.put(poly, indexNumber);
        }
    }

    public Unit mulUnit(Unit unit) {
        Unit newUnit = new Unit(this.coe.multiply(unit.getCoe()));
        for (String s : this.vars.keySet()) {
            newUnit.addVar(s, this.vars.get(s));
        }
        for (String s : unit.vars.keySet()) {
            newUnit.addVar(s, unit.vars.get(s));
        }
        for (Poly poly : this.sinMap.keySet()) {
            newUnit.addSinCos(0, poly, this.sinMap.get(poly));
        }
        for (Poly poly : unit.sinMap.keySet()) {
            newUnit.addSinCos(0, poly, unit.sinMap.get(poly));
        }
        for (Poly poly : this.cosMap.keySet()) {
            newUnit.addSinCos(1, poly, this.cosMap.get(poly));
        }
        for (Poly poly : unit.cosMap.keySet()) {
            newUnit.addSinCos(1, poly, unit.cosMap.get(poly));
        }
        return newUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        Unit unit = (Unit) o;
        return vars.equals(unit.vars) && sinMap.equals(unit.sinMap) && cosMap.equals(unit.cosMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, vars, sinMap, cosMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (coe.compareTo(zero) > 0) {
            sb.append('+').append(coe);
        } else if (coe.compareTo(zero) == 0) {
            return "";
        } else {
            sb.append(coe);
        }

        for (String s : this.vars.keySet()) {
            if (this.vars.get(s).compareTo(BigInteger.ONE) == 0) {
                sb.append('*').append(s);
            } else if (mark == null && this.vars.get(s).compareTo(new BigInteger("2")) == 0) {
                sb.append('*').append(s).append('*').append(s);
            } else {
                sb.append('*').append(s).append("**").append(this.vars.get(s));
            }
        }
        for (Poly poly : this.sinMap.keySet()) {
            if (this.sinMap.get(poly).compareTo(BigInteger.ONE) == 0) {
                if (poly.isFactor()) {
                    sb.append("*sin(").append(poly).append(")");
                } else {
                    sb.append("*sin((").append(poly).append("))");
                }
            } else {
                if (poly.isFactor()) {
                    sb.append("*sin(").append(poly).append(")");
                    sb.append("**").append(this.sinMap.get(poly));
                } else {
                    sb.append("*sin((").append(poly).append("))");
                    sb.append("**").append(this.sinMap.get(poly));
                }
            }
        }
        for (Poly poly : this.cosMap.keySet()) {
            if (this.cosMap.get(poly).compareTo(BigInteger.ONE) == 0) {
                if (poly.isFactor()) {
                    sb.append("*cos(").append(poly).append(")");
                } else {
                    sb.append("*cos((").append(poly).append("))");
                }
            } else {
                if (poly.isFactor()) {
                    sb.append("*cos(").append(poly).append(")");
                    sb.append("**").append(this.cosMap.get(poly));
                } else {
                    sb.append("*cos((").append(poly).append("))");
                    sb.append("**").append(this.cosMap.get(poly));
                }
            }
        }
        if (this.size() > 0) {
            if (sb.substring(1, 3).equals("1*")) {
                sb.delete(1, 3);
            }
        }
        return sb.toString();
    }
}
