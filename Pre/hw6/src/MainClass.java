import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<String> all = new ArrayList<>();
    private static int[] leapYear = {31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] commonYear = {31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        getMessages();
        //for(String s : all) System.out.println(s);
        //System.out.println("==========");

        while (sc.hasNextLine()) {
            String qtype = sc.next();
            String parameters = sc.nextLine();
            if (qtype.equals("qdate")) {
                query(1, parameters);
            } else if (qtype.equals("qsend")) {
                query(2, parameters);
            } else {
                query(3, parameters);
            }
        }
    }

    public static void getMessages() {
        while (true) {
            String line = sc.nextLine();
            if (line.equals("END_OF_MESSAGE")) {
                break;
            }
            String[] messages = line.split(";\\s*");
            for (String s : messages) {
                all.add(s + ";");
            }
        }
    }

    public static void query(int qtype, String parameters) {
        boolean needClean;
        String toClean;
        Pattern p = Pattern.compile("-c \"(.+?)\"");
        Matcher mc = p.matcher(parameters);
        if (mc.find()) {
            needClean = true;
            toClean = mc.group(1);
        } else {
            needClean = false;
            toClean = "";
        }
        if (qtype == 1) {
            p = Pattern.compile("(\\d{0,4})/(\\d{0,2})/(\\d{0,2})");
            Matcher mdate = p.matcher(parameters);
            if (mdate.find()) {
                if (isValidDate(mdate.group(1), mdate.group(2), mdate.group(3))) {
                    mdate.reset();
                    qDate(mdate, needClean, toClean);
                } else {
                    System.out.println("Command Error!: Wrong Date Format! "
                            + "\"qdate " + parameters.trim() + "\"");
                }
            }
        } else {
            if (isValidParameters(parameters)) {
                whichType(qtype, parameters, needClean, toClean);
            } else {
                if (qtype == 2) {
                    System.out.println("Command Error!: Not Vague Query! "
                            + "\"qsend " + parameters.trim() + "\"");
                } else {
                    System.out.println("Command Error!: Not Vague Query! "
                            + "\"qrecv " + parameters.trim() + "\"");
                }
            }
        }
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 == 0 && year % 400 != 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidDate(String y, String m, String d) {
        int iy = y.equals("") ? 0 : Integer.parseInt(y);
        int im = m.equals("") ? 0 : Integer.parseInt(m);
        int id = d.equals("") ? 0 : Integer.parseInt(d);
        int[] month = isLeapYear(iy) ? leapYear : commonYear;

        if (m.equals("") || (im >= 1 && im <= 12)) {
            return d.equals("") ? true : id >= 1 && id <= month[im];
        } else {
            return false;
        }
    }

    public static boolean isValidParameters(String parameters) {
        Pattern pv = Pattern.compile("-v");
        Pattern p = Pattern.compile("-ssq|-ssr|-pre|-pos");
        Matcher m = p.matcher(parameters);
        if (m.find()) {
            int pos = m.start();
            m = pv.matcher(parameters);
            if (m.find()) {
                if (m.start() > pos) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static void qDate(Matcher m0, boolean needClean, String toClean) {
        Pattern p = Pattern.compile("(\\d{0,4})/(\\d{0,2})/(\\d{0,2})");
        for (String message : all) {
            Matcher m = p.matcher(message);
            boolean flag = true;
            if (m.find() && m0.find()) {
                for (int i = 1; i <= 3; i++) {
                    if (!m0.group(i).equals("")) {
                        if (Integer.parseInt(m0.group(i)) != Integer.parseInt(m.group(i))) {
                            flag = false;
                        }
                    }
                }
            }
            if (flag == true) {
                System.out.println(clean(message, needClean, toClean));
            }
            m0.reset();
        }
    }

    public static void whichType(int qtype, String parameters, boolean needClean, String toClean) {
        Pattern pv = Pattern.compile("-v");
        Pattern p = Pattern.compile("-ssq|-ssr|-pre|-pos");
        Pattern pkey = Pattern.compile("\"(.+?)\"");
        Matcher m = pv.matcher(parameters);
        int type = 0;
        if (!m.find()) {
            type = 0;
        } else {
            m = p.matcher(parameters);
            if (m.find()) {
                if (m.group().equals("-ssq")) {
                    type = 1;
                } else if (m.group().equals("-ssr")) {
                    type = 2;
                } else if (m.group().equals("-pre")) {
                    type = 3;
                } else if (m.group().equals("-pos")) {
                    type = 4;
                }
            } else {
                type = 2;
            }
        }
        m = pkey.matcher(parameters);
        String key = "";
        if (m.find()) {
            key = m.group(1);
        }
        if (qtype == 2) {
            qSend(type, key, needClean, toClean);
        } else {
            qRecv(type, key, needClean, toClean);
        }

    }

    public static void qSend(int op, String key, boolean needClean, String toClean) {
        Pattern p;
        String start = "-";
        String end = "[^a-zA-Z0-9]";
        p = Pattern.compile(getRegex(op, key, start, end));
        for (String message : all) {
            Matcher m = p.matcher(message);
            if (m.find()) {
                System.out.println(clean(message, needClean, toClean));
            }
        }
    }

    public static void qRecv(int op, String key, boolean needClean, String toClean) {
        Pattern p;
        String start = "@";
        String end = "\\s";
        p = Pattern.compile(getRegex(op, key, start, end));
        for (String message : all) {
            Matcher m = p.matcher(message);
            if (m.find()) {
                System.out.println(clean(message, needClean, toClean));
            }
        }
    }

    public static String getRegex(int op, String key, String start, String end) {
        String valid = "[a-zA-Z0-9]*?";
        String ans = "";
        if (op == 0) {
            ans = start + key + end;
        } else if (op == 1) {
            ans += start;
            for (int i = 0; i < key.length(); i++) {
                ans += valid + key.charAt(i);
            }
            ans += valid + end;
        } else if (op == 2) {
            ans = start + valid + key + valid + end;
        } else if (op == 3) {
            ans = start + key + valid + end;
        } else {
            ans = start + valid + key + end;
        }
        return ans;
    }

    public static String clean(String message, boolean needClean, String toClean) {
        if (needClean) {
            char[] array = message.toCharArray();
            int begin = message.indexOf("\"");
            String changeToCleam = toClean.replace("?", "[?]").replace(".","\\.");
            Pattern p = Pattern.compile(changeToCleam);
            Matcher m = p.matcher(message);
            while (m.find()) {
                if (m.start() > begin) {
                    for (int i = m.start(); i < m.end(); i++) {
                        array[i] = '*';
                    }
                }
            }
            return String.valueOf(array);
        }
        return message;
    }

}