package parser;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public int check() {
        if (pos >= input.length()) {
            return -1;
        }

        char c = input.charAt(pos);
        if (Character.isDigit(c)) {
            return 0;
        } else if (c == 'x' || c == 'y' || c == 'z') {
            return 1;
        } else if (c == 'f' || c == 'g' || c == 'h') {
            return 2;
        } else if (c == 's' && input.charAt(pos + 1) == 'i') {
            return 3;
        } else if (c == 'c' && input.charAt(pos + 1) == 'o') {
            return 4;
        } else if (c == '*' && input.charAt(pos + 1) == '*') {
            return 5;
        } else if (c == '*') {
            return 6;
        } else if (c == '+' || c == '-') {
            return 7;
        } else if (c == '(' || c == ')') {
            return 8;
        } else if (c == ',') {
            return 9;
        }
        return -1;
    }

    public String getConst() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return sb.toString();
    }

    public String getString(int cnt) {
        pos += cnt;
        return input.substring(pos - cnt, pos);
    }

    public char getChar() {
        return input.charAt(pos++);
    }

}
