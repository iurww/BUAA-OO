import expr.Expr;
import parser.FuncDefiner;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cntFunc = sc.nextInt();
        sc.nextLine();
        for (int i = 1; i <= cntFunc; i++) {
            String func = sc.nextLine();
            func = func.replaceAll("\\s*", "");
            FuncDefiner.addFunc(func);
        }

        String input = sc.nextLine();
        input = input.replaceAll("\\s*", "");
        parser.Lexer lexer = new parser.Lexer(input);
        parser.Parser parser = new parser.Parser(lexer);

        Expr expr = parser.parseExpr();
        test(expr);

    }

    public static void test(Expr expr) {
        //System.out.println(expr.toString());
        System.out.println(expr.toPoly());
    }
}
