import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {

    private static ArrayList<JsonObject> all = new ArrayList<>();
    private static HashMap<String, String> month = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        getmessages();
        init();
        while (sc.hasNext()) {
            String query = sc.next();
            if (query.equals("Qdate")) {
                String userid = sc.next();
                String dateRange = sc.next();
                qdate(userid, dateRange);
            } else if (query.equals("Qemoji")) {
                String id = sc.next();
                qemoji(id);
            } else if (query.equals("Qcount")) {
                String dateRange = sc.next();
                qcount(dateRange);
            } else if (query.equals("Qtext")) {
                String id = sc.next();
                qtext(id);
            } else if (query.equals("Qsensitive")) {
                String userid = sc.next();
                qsensitive(userid);
            } else {
                String id = sc.next();
                qlang(id);
            }
        }
    }

    public static void getmessages() {
        String jsonLine;
        while (true) {
            jsonLine = sc.nextLine();
            if (jsonLine.equals("END_OF_MESSAGE")) {
                break;
            }
            JsonParser parser = new JsonParser(jsonLine);
            all.add(parser.parseObject());
        }
    }

    public static void init() {
        month.put("Jan", "01");
        month.put("Feb", "02");
        month.put("Mar", "03");
        month.put("Apr", "04");
        month.put("May", "05");
        month.put("Jun", "06");
        month.put("Jul", "07");
        month.put("Aug", "08");
        month.put("Sep", "09");
        month.put("Oct", "10");
        month.put("Nov", "11");
        month.put("Dec", "12");
    }

    public static boolean inRange(String date, String dateRange) {
        String[] range = dateRange.split("~");
        if (date.compareTo(range[0]) > -1 && date.compareTo(range[1]) < 1) {
            return true;
        } else {
            return false;
        }
    }

    public static void qdate(String userid, String dateRange) {
        int cnt = 0;
        int retweet = 0;
        int favorite = 0;
        int reply = 0;
        for (JsonObject message : all) {
            JsonObject content = (JsonObject) message.get("\"raw_value\"");
            String muserid = (String) content.get("\"user_id\"");

            String date = (String) content.get("\"created_at\"");
            Pattern p = Pattern.compile("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)" +
                    " (\\d{2}) \\d{2}:\\d{2}:\\d{2} (\\d{4})");
            Matcher m = p.matcher(date);
            m.find();
            String fdate = m.group(3) + "-" + month.get(m.group(1)) + "-" + m.group(2);

            if (muserid.equals(userid) && inRange(fdate, dateRange)) {
                cnt++;
                retweet += Integer.parseInt((String) content.get("\"retweet_count\""));
                favorite += Integer.parseInt((String) content.get("\"favorite_count\""));
                reply += Integer.parseInt((String) content.get("\"reply_count\""));
            }
        }
        System.out.println(cnt + " " + retweet + " " + favorite + " " + reply);
    }

    public static void qemoji(String id) {
        for (JsonObject message : all) {
            JsonObject content = (JsonObject) message.get("\"raw_value\"");
            String mid = (String) content.get("\"id\"");

            if (mid.equals(id)) {
                ArrayList<Object> emojis = (ArrayList<Object>) content.get("\"emojis\"");
                ArrayList<JsonObject> remojies = new ArrayList<>();
                for (Object emoji : emojis) {
                    remojies.add((JsonObject) emoji);
                }
                if (remojies.size() == 0) {
                    System.out.println("None");
                    return;
                }
                Collections.sort(remojies);
                for (JsonObject emoji : remojies) {
                    String s = (String) emoji.get("\"name\"");
                    System.out.print(s.substring(1, s.length() - 1) + " ");
                }
            }
        }
        System.out.println();
    }

    public static void qcount(String dateRange) {
        int cnt = 0;
        for (JsonObject message : all) {
            String downloadDate = (String) message.get("\"download_datetime\"");
            downloadDate = downloadDate.substring(1, 11);
            if (inRange(downloadDate, dateRange)) {
                cnt++;
            }
        }
        System.out.println(cnt);
    }

    public static void qtext(String id) {
        for (JsonObject message : all) {
            JsonObject content = (JsonObject) message.get("\"raw_value\"");
            String text = (String) content.get("\"full_text\"");
            String mid = (String) content.get("\"id\"");
            if (mid.equals(id)) {
                if (text.equals("null")) {
                    System.out.println("None");
                } else if (!text.equals("\"\"")) {
                    System.out.println(text.substring(1, text.length() - 1));
                }
            }
        }
    }

    public static void qsensitive(String userid) {
        int cnt = 0;
        for (JsonObject message : all) {
            JsonObject content = (JsonObject) message.get("\"raw_value\"");
            String muserid = (String) content.get("\"user_id\"");
            String sensitive = (String) content.get("\"possibly_sensitive_editable\"");
            if (muserid.equals(userid) && sensitive.equals("true")) {
                cnt++;
            }
        }
        System.out.println(cnt);
    }

    public static void qlang(String id) {
        for (JsonObject message : all) {
            JsonObject content = (JsonObject) message.get("\"raw_value\"");
            String lang = (String) content.get("\"lang\"");
            String mid = (String) content.get("\"id\"");
            if (mid.equals(id)) {
                System.out.println(lang.substring(1, lang.length() - 1));
            }
        }
    }
}
