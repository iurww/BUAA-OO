import expr.Const;
import expr.Expr;
import expr.Var;
import expr.Factor;
import expr.Term;

public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();
        expr.addTerm(parseTerm());
        while (lexer.check() == "+-") {
            expr.addTerm(parseTerm());
        }
        return expr;
    }

    public Term parseTerm() {
        Term term;
        if (lexer.check() == "+-") {
            term = new Term(lexer.getOp());

        } else {
            term = new Term('+');
        }

        if (lexer.check() == "+-") {
            term.inverse(lexer.getOp());
        }
        term.addFactor(parseFactor());

        while (lexer.check() == "*") {
            lexer.getOp();
            term.addFactor(parseFactor());
        }
        term.mergeConst();
        return term;
    }

    public Factor parseFactor() {
        if (lexer.check() == "brackets") {
            lexer.getOp();
            Expr expr = parseExpr();
            lexer.getOp();
            String indexNumber = parseIndexNumber();
            expr.setIndexNumber(Integer.parseInt(indexNumber));
            return (Factor) expr;
        } else if (lexer.check() == "var") {
            char name = lexer.getVar();
            String indexNumber = parseIndexNumber();
            Var var = new Var(name, Integer.parseInt(indexNumber));
            return (Factor) var;
        } else {
            Const constant;
            if (lexer.check() == "+-") {
                constant = new Const(lexer.getOp() + lexer.getConst());
            } else {
                constant = new Const(lexer.getConst());
            }
            return (Factor) constant;
        }
    }

    public String parseIndexNumber() {
        if (lexer.check() == "**") {
            lexer.getPower();
            if (lexer.check() == "+-") {
                lexer.getOp();
            }
            return lexer.getConst();
        } else {
            return "1";
        }
    }

}
