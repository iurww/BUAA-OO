import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MainClass {
    private static LinkedHashMap<String, Library> libraries = new LinkedHashMap<>();
    private static LocalDate collectDate = LocalDate.of(2023, 1, 1);
    private static LocalDate curDate = LocalDate.of(2023, 1, 1);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            String school = sc.next();
            int m = sc.nextInt();
            Library library = new Library(school, libraries);
            libraries.put(school, library);
            for (int j = 1; j <= m; j++) {
                String bookName = sc.next();
                int cnt = sc.nextInt();
                String allow = sc.next();
                Book book = new Book(bookName, cnt, school, allow);
                library.addBook(book);
            }
        }


        int q = sc.nextInt();
        for (int i = 1; i <= q; i++) {
            String date = sc.next();
            date = date.substring(1, date.length() - 1);
            String stu = sc.next();
            String school = stu.split("-")[0];
            String stuId = stu.split("-")[1];
            String op = sc.next();
            String book = sc.next();
            LocalDate reqDate = LocalDate.parse(date, formatter);
            if (!curDate.isEqual(reqDate)) {
                handleRequest(date, school, stuId, op, book, true);
                curDate = reqDate;
            } else {
                handleRequest(date, school, stuId, op, book, false);
            }
        }
        libraries.values().forEach(library -> {
            library.order();
        });
        libraries.values().forEach(library -> {
            library.send(curDate.format(formatter));
        });

    }

    public static void handleRequest(String date, String school, String stuId,
                                     String op, String book, boolean flag) {
        if (flag) {
            libraries.values().forEach(library -> {
                library.order();
            });
            libraries.values().forEach(library -> {
                library.send(curDate.format(formatter));
            });
            libraries.values().forEach(library -> {
                library.receive(curDate.plusDays(1).format(formatter));
            });
            libraries.values().forEach(library -> {
                library.distribute(curDate.plusDays(1).format(formatter));
            });
        }
        LocalDate curDate = LocalDate.parse(date, formatter);
        while (!collectDate.isAfter(curDate)) {
            libraries.values().forEach(library -> {
                library.purchase(collectDate.format(formatter));
            });
            System.out.printf("[%s] arranging librarian arranged all the books\n",
                    collectDate.format(formatter));
            libraries.values().forEach(library -> {
                library.collect(collectDate.format(formatter));
            });
            collectDate = collectDate.plusDays(3);
        }
        if (op.equals("borrowed")) {
            libraries.get(school).borrowBook(date, stuId, book);
        } else if (op.equals("smeared")) {
            libraries.get(school).smearBook(date, stuId, book);
        } else if (op.equals("lost")) {
            libraries.get(school).lostBook(date, stuId, book);
        } else if (op.equals("returned")) {
            libraries.get(school).returnBook(date, stuId, book);
        }
    }

}
