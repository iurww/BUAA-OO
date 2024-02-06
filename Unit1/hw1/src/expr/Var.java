package expr;

import java.math.BigInteger;

public class Var implements Factor {
    private char name;
    private int indexNumber;

    public Var(char name, int indexNumber) {
        this.name = name;
        this.indexNumber = indexNumber;
    }

    @Override
    public Poly toPoly() {
        Mono mono = new Mono(new BigInteger("1"));
        mono.addVar(String.valueOf(name), indexNumber);
        Poly poly = new Poly();
        poly.addMono(mono);
        return poly;
    }

    @Override
    public String toString() {
        return "Var=" + name + "**" + indexNumber;
    }
}
