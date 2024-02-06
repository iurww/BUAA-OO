import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<String> all = new ArrayList<>();

    public static void main(String[] args) {
        getMessages();
        //for(String s : all) System.out.println(s);

        while (sc.hasNext()) {
            String op = sc.next();
            if (op.equals("qdate")) {
                String key = sc.next();
                qdate(key);
            } else if (op.equals("qsend")) {
                String key = sc.next();
                if (key.equals("-v")) {
                    key = sc.next();
                    key = key.substring(1, key.length() - 1);
                    qsend(0, key);
                } else {
                    key = key.substring(1, key.length() - 1);
                    qsend(1, key);
                }
            } else {
                String key = sc.next();
                if (key.equals("-v")) {
                    key = sc.next();
                    key = key.substring(1, key.length() - 1);
                    qrecv(0, key);
                } else {
                    key = key.substring(1, key.length() - 1);
                    qrecv(1, key);
                }
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

    public static void qdate(String key) {
        Pattern p = Pattern.compile("(\\d{0,4})/(\\d{0,2})/(\\d{0,2})");
        Matcher m0 = p.matcher(key);
        for (String message : all) {
            Matcher m = p.matcher(message);
            boolean flag = true;
            if (m.find() && m0.find()) {
                if (!m0.group(1).equals("")) {
                    if (Integer.parseInt(m0.group(1)) != Integer.parseInt(m.group(1))) {
                        flag = false;
                    }
                }
                if (!m0.group(2).equals("")) {
                    if (Integer.parseInt(m0.group(2)) != Integer.parseInt(m.group(2))) {
                        flag = false;
                    }
                }
                if (!m0.group(3).equals("")) {
                    if (Integer.parseInt(m0.group(3)) != Integer.parseInt(m.group(3))) {
                        flag = false;
                    }
                }
            }
            if (flag == true) {
                System.out.println(message);
            }
            m0.reset();
        }
    }

    public static void qsend(int op, String key) {
        Pattern p;
        if (op == 0) {
            p = Pattern.compile("-[a-zA-Z0-9]*?" + key + "[a-zA-Z0-9]*?[^a-zA-Z0-9]");
        } else {
            p = Pattern.compile("-" + key + "[^a-zA-Z0-9]");
        }
        for (String message : all) {
            Matcher m = p.matcher(message);
            if (m.find()) {
                System.out.println(message);
            }
        }
    }

    public static void qrecv(int op, String key) {
        Pattern p;
        if (op == 0) {
            p = Pattern.compile("@[a-zA-Z0-9]*?" + key + "[a-zA-Z0-9]*?\\s");
        } else {
            p = Pattern.compile("@" + key + "\\s");
        }
        for (String message : all) {
            Matcher m = p.matcher(message);
            if (m.find()) {
                System.out.println(message);
            }
        }
    }

}