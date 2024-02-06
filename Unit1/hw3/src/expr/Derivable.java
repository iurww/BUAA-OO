package expr;

public interface Derivable {
    Poly diff(String op);

    Poly toPoly();
}
