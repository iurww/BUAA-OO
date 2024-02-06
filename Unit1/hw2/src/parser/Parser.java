package parser;

import expr.Const;
import expr.Expr;
import expr.Var;
import expr.Factor;
import expr.Term;
import expr.Sin;
import expr.Cos;
import expr.Func;
import parser.Lexer;

import java.math.BigInteger;
import java.util.ArrayList;

public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();
        expr.addTerm(parseTerm());
        while (lexer.check() == 7) {
            expr.addTerm(parseTerm());
        }
        return expr;
    }

    public Term parseTerm() {
        Term term = (lexer.check() == 7 ? new Term(lexer.getChar()) : new Term('+'));

        if (lexer.check() == 7) {
            term.inverse(lexer.getChar());
        }
        term.addFactor(parseFactor());
        while (lexer.check() == 6) {
            lexer.getChar();
            term.addFactor(parseFactor());
        }
        term.mergeConst();
        return term;
    }

    public Factor parseFactor() {
        if (lexer.check() == 8) {
            lexer.getChar();
            Expr expr = parseExpr();
            lexer.getChar();
            String indexNumber = parseIndexNumber();
            expr.setIndexNumber(new BigInteger(indexNumber));
            return expr;
        } else if (lexer.check() == 1) {
            return parseVar();
        } else if (lexer.check() == 2) {
            return parseFunc();
        } else if (lexer.check() == 3 || lexer.check() == 4) {
            return parseSinCos();
        } else {
            return parseConst();
        }
    }

    public Factor parseVar() {
        char name = lexer.getChar();
        String indexNumber = parseIndexNumber();
        return new Var(name, indexNumber);
    }

    public Factor parseConst() {
        Const constant = (lexer.check() == 7 ?
                new Const(lexer.getChar() + lexer.getConst()) :
                new Const(lexer.getConst()));
        return constant;
    }

    public Factor parseSinCos() {
        final String name = lexer.getString(3);
        lexer.getChar();
        Factor factor = parseFactor();
        lexer.getChar();
        String indexNumber = parseIndexNumber();
        return name.equals("sin") ? new Sin(factor, indexNumber) : new Cos(factor, indexNumber);
    }

    public Factor parseFunc() {
        final String name = lexer.getString(1);
        ArrayList<Factor> actualParam = new ArrayList<>();
        lexer.getChar();
        actualParam.add(parseFactor());
        while (lexer.check() == 9) {
            lexer.getChar();
            actualParam.add(parseFactor());
        }
        lexer.getChar();
        return new Func(name, actualParam);
    }

    public String parseIndexNumber() {
        if (lexer.check() == 5) {
            lexer.getString(2);
            if (lexer.check() == 7) {
                lexer.getChar();
            }
            return lexer.getConst();
        } else {
            return "1";
        }
    }

}
