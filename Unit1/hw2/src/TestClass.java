import expr.Expr;
import expr.Poly;
import expr.Sin;
import expr.Unit;
import parser.Lexer;
import parser.Parser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class TestClass {
    public static void main(String[] args) {

        Lexer lexer1 = new Lexer("z");
        Parser parser1 = new Parser(lexer1);
        Expr expr1 = parser1.parseExpr();
        Sin sin1 = new Sin(expr1, "1");
        Poly poly1 = sin1.toPoly();

        Lexer lexer2 = new Lexer("z");
        Parser parser2 = new Parser(lexer2);
        Expr expr2 = parser2.parseExpr();
        Sin sin2 = new Sin(expr2, "1");
        Poly poly2 = sin1.toPoly();

        Unit t1=null;
//        for(Unit unit : poly1.getUnitList().keySet()){
//            t1 = unit;
//        }
//        Unit t2=null;
//        for (Unit unit : poly2.getUnitList().keySet()) {
//            t2 = unit;
//        }


//        System.out.println(poly1.equals(poly2));
//        System.out.println(t1.equals(t2));


        System.out.println(poly1);
        System.out.println(poly2);
        System.out.println(poly1.hashCode());
        System.out.println(poly2.hashCode());
        System.out.println(poly1.equals(poly2));

    }
}
