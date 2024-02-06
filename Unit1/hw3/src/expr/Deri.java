package expr;

public class Deri implements Factor {
    private String op;
    private Expr expr;

    public Deri(String name, Expr expr) {
        this.op = name.substring(1);
        this.expr = expr;
    }

    @Override
    public Poly toPoly() {
        return expr.toPoly().diff(op);
    }

    @Override
    public String toString() {
        return "(d" + op + "(" + expr.toString() + "))";
    }
}
