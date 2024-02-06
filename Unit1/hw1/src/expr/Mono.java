package expr;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Mono {
    private BigInteger coe;
    private HashMap<String, Integer> vars;
    private final BigInteger zero = new BigInteger("0");

    public Mono(BigInteger coe) {
        this.coe = coe;
        this.vars = new HashMap<>();
    }

    public BigInteger getCoe() {
        return this.coe;
    }

    public HashMap<String, Integer> getVars() {
        return this.vars;
    }

    public void setCoe(BigInteger coe) {
        this.coe = coe;
    }

    public void addVar(String name, Integer indexNumber) {
        if (vars.containsKey(name)) {
            int oldIndexNumber = vars.get(name);
            vars.replace(name, oldIndexNumber + indexNumber);
        } else {
            if (indexNumber > 0) {
                vars.put(name, indexNumber);
            }
        }
    }

    public Mono mulMono(Mono mono) {
        Mono newMono = new Mono(this.coe.multiply(mono.getCoe()));
        for (String s : this.vars.keySet()) {
            newMono.addVar(s, this.vars.get(s));
        }
        for (String s : mono.vars.keySet()) {
            newMono.addVar(s, mono.vars.get(s));
        }
        return newMono;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vars);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mono)) {
            return false;
        } else {
            Mono mono = (Mono) obj;
            if (this.vars.size() != ((Mono) obj).getVars().size()) {
                return false;
            } else {
                boolean flag = true;
                for (String s : this.vars.keySet()) {
                    if (!mono.getVars().containsKey(s) ||
                            this.vars.get(s) != mono.getVars().get(s)) {
                        flag = false;
                    }
                }
                return flag;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (coe.compareTo(zero) > 0) {
            sb.append("+" + coe);
        } else if (coe.compareTo(zero) == 0) {
            return "";
        } else {
            sb.append(coe);
        }

        for (String s : this.vars.keySet()) {
            if (this.vars.get(s).compareTo(1) == 0) {
                sb.append("*" + s);
            } else if (this.vars.get(s).compareTo(2) == 0) {
                sb.append("*" + s + "*" + s);
            } else {
                sb.append("*" + s + "**" + this.vars.get(s));
            }
        }
        if (this.vars.keySet().size() > 0) {
            if (sb.substring(1, 3).equals("1*")) {
                sb.delete(1, 3);
            }
        }
        return sb.toString();
    }
}
