package parser;

import expr.Factor;

import java.util.ArrayList;
import java.util.HashMap;

public class FuncDefiner {
    private static HashMap<String, String> func = new HashMap<>();
    private static HashMap<String, ArrayList<String>> params = new HashMap<>();
    private static HashMap<String, String> varMap = new HashMap<>();

    static {
        varMap.put("x", "u");
        varMap.put("y", "v");
        varMap.put("z", "w");
    }

    public static void addFunc(String input) {
        final String name = input.substring(0, 1);
        String expr = input.substring(input.indexOf('=') + 1);
        Lexer lexer = new Lexer(expr);
        Parser parser = new Parser(lexer);
        expr = parser.parseExpr().toPoly().toString();
        expr = expr.replaceAll("x", "u");
        expr = expr.replaceAll("y", "v");
        expr = expr.replaceAll("z", "w");
        func.put(name, expr);

        String paramString = input.substring(input.indexOf('(') + 1, input.indexOf(')'));
        String[] paramArray = paramString.split(",");
        ArrayList<String> paramList = new ArrayList<>();
        for (String s : paramArray) {
            paramList.add(varMap.get(s));
        }
        params.put(name, paramList);
    }

    public static String callFunc(String name, ArrayList<Factor> actualParam) {
        String expr = func.get(name);
        ArrayList<String> formalParam = params.get(name);
        for (int i = 0; i < formalParam.size(); i++) {
            expr = expr.replaceAll(formalParam.get(i), "("+actualParam.get(i).toPoly().toString()+")");
        }
        return expr;
    }
}
