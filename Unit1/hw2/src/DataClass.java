import java.util.Random;

public class DataClass {
    public static final Random random = new Random();
    public static boolean hasBracket = false;

    public static void main(String[] args) {
//        try{
//            PrintStream ps = new PrintStream("E:\\Program\\OO\\1\\test-1\\data.txt");
//            System.setOut(ps);
//        } catch (FileNotFoundException e){
//            System.out.println(e);
//        }

        for(int i=1;i<=100;i++) {
            System.out.println(getExpr());
        }

    }

    public static String getExpr() {
        StringBuilder sb = new StringBuilder();
        int cnt = random.nextInt(4) + 1;
        sb.append(getAddSub(3) + getEmpty() + getTerm());
        for (int i = 2; i <= cnt; i++) {
            sb.append(getEmpty() + getAddSub(2) + getEmpty());
            sb.append(getTerm());
        }
        return sb.toString();
    }

    public static String getTerm() {
        StringBuilder sb = new StringBuilder();
        int cnt = random.nextInt(4) + 1;
        sb.append(getAddSub(3) + getEmpty() + getFactor());
        for (int i = 2; i <= cnt; i++) {
            sb.append(getEmpty() + "*" + getEmpty());
            sb.append(getFactor());
        }
        return sb.toString();
    }

    public static String getFactor() {
        int type = random.nextInt(3);
        if (hasBracket) type %= 2;
        if (type == 0) {
            return getVar();
        } else if (type == 1) {
            return getAddSub(3) + getConst();
        } else {
            int hasExp = random.nextInt(2);
            hasBracket = true;
            String expr = getExpr();
            hasBracket = false;
            if (hasExp == 0) {
                return "(" + expr+ ")";
            } else {
                return "(" + expr + ")" + getExp();
            }
        }
    }

    public static String getEmpty() {
        int len = random.nextInt(3);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= len; i++) {
            int type = random.nextInt(2);
            if (type == 0) {
                sb.append("");
            } else {
                sb.append("");
            }
        }
        return sb.toString();
    }

    public static String getAddSub(int flag) {
        int type = random.nextInt(flag);
        if (type == 0) {
            return "+";
        } else if (type == 1) {
            return "-";
        } else {
            return "";
        }
    }

    public static String getConst() {
        int len = random.nextInt(5);
        StringBuilder sb = new StringBuilder();
        int t = random.nextInt(2);
        sb.append(t);
        for (int i = 2; i <= len; i++) {
            t = random.nextInt(10);
            sb.append(t);
        }
        return sb.toString();
    }

    public static String getVar() {
        char var = (char) (random.nextInt(3) + 120);
        int hasExp = random.nextInt(2);
        if (hasExp == 0) {
            return var + "";
        } else {
            return var + getExp();
        }

    }

    public static String getExp() {
        int hasPlus = random.nextInt(2);
        if (hasPlus == 1) {
            return getEmpty() + "**" + getEmpty() + "+" + "0" + random.nextInt(9);
        } else {
            return getEmpty() + "**" + getEmpty() + random.nextInt(4);
        }
    }
}
