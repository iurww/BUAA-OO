import expr.Expr;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        input = input.replaceAll("\\s*", "");

        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);

        Expr expr = parser.parseExpr();
        //test(expr);
        System.out.println(expr.toPoly().mergeMono());
    }

    public static void test(Expr expr) {
        System.out.println(expr.toString());
        System.out.println(expr.toPoly().toString());
    }
}
