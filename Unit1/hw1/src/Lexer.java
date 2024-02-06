public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public String check() {
        if (pos >= input.length()) {
            return "end";
        }

        char c = input.charAt(pos);
        if (Character.isDigit(c)) {    // 0-9
            return "digit";
        } else if (Character.isWhitespace(c)) {    // 0-9
            return "empty";
        } else if (Character.isLetter(c)) {    // x,y,z
            return "var";
        } else if (input.charAt(pos) == '*' && input.charAt(pos + 1) == '*') {    // **
            return "**";
        } else if (input.charAt(pos) == '*') {    // *,+,-,(,)
            return "*";
        } else if (input.charAt(pos) == '+' || input.charAt(pos) == '-') {
            return "+-";
        } else {
            return "brackets";
        }
    }

    public String getConst() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return sb.toString();
    }

    public String getEmpty() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && input.charAt(pos) == ' ' || input.charAt(pos) == '\t') {
            sb.append(input.charAt(pos));
            pos++;
        }
        return sb.toString();
    }

    public char getVar() {
        return input.charAt(pos++);
    }

    public String getPower() {
        pos += 2;
        return "**";
    }

    public char getOp() {
        return input.charAt(pos++);
    }

}
