import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<String> all = new ArrayList<>();

    public static void main(String[] args) {
        getMessages();

        while (sc.hasNext()) {
            String op = sc.next();
            String key = sc.next();
            if (op.equals("qdate")) {
                qdate(key);
            } else if (op.equals("qsend")) {
                key = key.substring(1, key.length() - 1);
                qsend(key);
            } else {
                key = key.substring(1, key.length() - 1);
                qrecv(key);
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
        Pattern p = Pattern.compile("(\\d{1,4})/(\\d{1,2})/(\\d{1,2})");
        Matcher m = p.matcher(key);
        int keyDate = getDate(m);
        for (String message : all) {
            m = p.matcher(message);
            int date = getDate(m);
            if (keyDate == date) {
                System.out.println(message);
            }
        }
    }

    public static void qsend(String key) {
        Pattern p = Pattern.compile("-" + key + "[^a-zA-Z0-9]");
        for (String message : all) {
            Matcher m = p.matcher(message);
            if (m.find()) {
                System.out.println(message);
            }
        }
    }

    public static void qrecv(String key) {
        Pattern p = Pattern.compile("@" + key + "\\s");
        for (String message : all) {
            Matcher m = p.matcher(message);
            if (m.find()) {
                System.out.println(message);
            }
        }
    }

    public static int getDate(Matcher m) {
        int ans = 0;
        if (m.find()) {
            ans += Integer.parseInt(m.group(1)) * 10000;
            ans += Integer.parseInt(m.group(2)) * 100;
            ans += Integer.parseInt(m.group(3));
        }
        return ans;
    }

}