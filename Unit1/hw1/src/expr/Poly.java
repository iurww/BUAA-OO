package expr;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Poly {
    private ArrayList<Mono> monoList;

    public Poly() {
        this.monoList = new ArrayList<>();
    }

    public ArrayList<Mono> getMonoList() {
        return monoList;
    }

    public void addMono(Mono mono) {
        this.monoList.add(mono);
    }

    public void addMonoList(ArrayList<Mono> monos) {
        this.monoList.addAll(monos);
    }

    public Poly addPoly(Poly poly) {
        Poly newPoly = new Poly();
        newPoly.addMonoList(this.getMonoList());
        newPoly.addMonoList(poly.getMonoList());
        return newPoly;
    }

    public Poly mulPoly(Poly poly) {
        Poly newPoly = new Poly();
        for (Mono mono1 : this.getMonoList()) {
            for (Mono mono2 : poly.getMonoList()) {
                newPoly.addMono(mono1.mulMono(mono2));
            }
        }
        return newPoly;
    }

    public Poly mergeMono() {
        HashMap<Mono, BigInteger> monos = new HashMap<>();
        for (Mono mono : monoList) {
            if (monos.containsKey(mono)) {
                BigInteger oldCoe = monos.get(mono);
                monos.replace(mono, oldCoe.add(mono.getCoe()));
            } else {
                monos.put(mono, mono.getCoe());
            }
        }
        Poly poly = new Poly();
        for (Mono mono : monos.keySet()) {
            mono.setCoe(monos.get(mono));
            poly.addMono(mono);
        }
        return poly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Mono mono : monoList) {
            sb.append(mono.toString());
        }
        if (sb.toString().equals("")) {
            return "0";
        }
        return sb.toString();
    }
}
