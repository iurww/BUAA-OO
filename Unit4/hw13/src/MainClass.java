import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainClass {
    private static Library library = new Library();
    private static LocalDate collectDate = LocalDate.of(2023, 1, 1);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            String s = sc.next();
            int cnt = sc.nextInt();
            Book book = new Book(s, cnt);
            library.addBook(book);
        }

        int m = sc.nextInt();
        for (int i = 1; i <= m; i++) {
            String date = sc.next();
            date = date.substring(1, date.length() - 1);
            String stuId = sc.next();
            String op = sc.next();
            String book = sc.next();
            handleRequest(date, stuId, op, book);
        }

    }

    public static void handleRequest(String date, String stuId, String op, String book) {
        LocalDate curDate = LocalDate.parse(date, formatter);
        while (!collectDate.isAfter(curDate)) {
            library.collect(collectDate.format(formatter));
            collectDate = collectDate.plusDays(3);
        }
        if (op.equals("borrowed")) {
            library.borrowBook(date, stuId, book);
        } else if (op.equals("smeared")) {
            library.smearBook(date, stuId, book);
        } else if (op.equals("lost")) {
            library.lostBook(date, stuId, book);
        } else if (op.equals("returned")) {
            library.returnBook(date, stuId, book);
        }
    }

}
