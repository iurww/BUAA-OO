package expr;

import parser.FuncDefiner;
import parser.Lexer;
import parser.Parser;

import java.util.ArrayList;

public class Func implements Factor {
    private String funcExpr;
    private Expr expr;

    public Func(String name, ArrayList<Factor> paramList) {
        this.funcExpr = FuncDefiner.callFunc(name, paramList);
        this.expr = parse(funcExpr);
    }

    public Expr parse(String funcExpr) {
        Lexer lexer = new Lexer(funcExpr);
        Parser parser = new Parser(lexer);
        return parser.parseExpr();
    }

    @Override
    public Poly toPoly() {
        return expr.toPoly();
    }

    @Override
    public String toString() {
        return expr.toString();
    }
}
