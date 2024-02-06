import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Office {
    private Library library;
    private LinkedHashMap<String, Library> libraries;
    private ArrayList<String> date;
    private ArrayList<String> stu;
    private ArrayList<Book> books;
    private ArrayList<Book> receive;
    private ArrayList<Book> returned;
    private ArrayList<Book> otherReturned;
    private BookList temp;
    private HashSet<String> borrowB;
    private HashMap<String, Book> borrowC;

    public Office(Library library, LinkedHashMap<String, Library> libraries) {
        this.library = library;
        this.date = new ArrayList<>();
        this.stu = new ArrayList<>();
        this.books = new ArrayList<>();
        this.receive = new ArrayList<>();
        this.returned = new ArrayList<>();
        this.otherReturned = new ArrayList<>();
        this.libraries = libraries;
        this.temp = new BookList();
    }

    public BookList getTemp(String date) {
        temp.clear();
        for (Book book : library.getOrderAdmin().getNeedBuy()) {
            temp.addBook(book);
        }
        for (HashMap<String, Book> m : temp.getBooks().values()) {
            for (Book book : m.values()) {
                if (book.getType().equals("B") &&
                        library.getStudents().get(book.getNeedBuy()).hasB()) {
                    continue;
                }
                System.out.printf("[%s] %s-%s got purchased by purchasing department in %s\n",
                        date, book.getSchool(), book, library.getSchool());
                library.getBookList().addBook(new Book(book.toString(), 0, book.getSchool(), "Y"));
                library.getBookList().getBook(book.toString()).addMaxCnt(Math.max(3,book.getCnt()));
            }
        }
        temp.align();
        for (Book book : otherReturned) {
            temp.addBook(book);
        }
        library.getOrderAdmin().getNeedBuy().clear();
        otherReturned.clear();
        return temp;
    }

    public void addOrder(String date, String stuId, Book book) {
        this.date.add(date);
        this.stu.add(stuId);
        this.books.add(book);
    }

    public void addReturned(Book book) {
        returned.add(book);
    }

    public void order() {
        borrowB = new HashSet<>();
        borrowC = new HashMap<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getType().equals("B")) {
                if (borrowB.contains(stu.get(i)) || library.getStudents().get(stu.get(i)).hasB()) {
                    continue;
                }
            } else if (book.getType().equals("C")) {
                if (book.equals(borrowC.get(stu.get(i))) ||
                        library.getStudents().get(stu.get(i)).hasC(book)) {
                    continue;
                }
            }
            if (find(book) == null) {
                library.getOrderAdmin().order(date.get(i), stu.get(i), book, book.getType());
                if (book.getType().equals("B")) {
                    borrowB.add(stu.get(i));
                } else if (book.getType().equals("C")) {
                    borrowC.put(stu.get(i), book);
                }
            }
        }
    }

    public void send(String date) {

        for (int i = 0; i < books.size(); i++) {
            String school;
            Book book = books.get(i);
            if (book.getType().equals("B")) {
                if (borrowB.contains(stu.get(i)) || library.getStudents().get(stu.get(i)).hasB()) {
                    continue;
                }
            } else if (book.getType().equals("C")) {
                if (book.equals(borrowC.get(stu.get(i))) ||
                        library.getStudents().get(stu.get(i)).hasC(book)) {
                    continue;
                }
            }
            if ((school = find(book)) != null) {
                book = new Book(book.toString(), 1, school, "");
                libraries.get(school).getBookList().borrowBook(book);
                System.out.printf("[%s] %s-%s got transported by purchasing department in %s\n",
                        date, book.getSchool(), book, school);
                State.print(date, book.toString(), 5);
                if (book.getType().equals("B")) {
                    borrowB.add(stu.get(i));
                } else if (book.getType().equals("C")) {
                    borrowC.put(stu.get(i), book);
                }
                book.setBookedBy(stu.get(i));
                receive.add(book);
            }
        }
        for (int i = 0; i < returned.size(); i++) {
            Book book = returned.get(i);
            System.out.printf("[%s] %s-%s got transported by purchasing department in %s\n",
                    date, book.getSchool(), book, library.getSchool());
            State.print(date, book.toString(), 5);
            libraries.get(book.getSchool()).getOffice().otherReturned.add(book);
        }
        this.date.clear();
        this.stu.clear();
        this.books.clear();
        this.returned.clear();
    }

    public void receive(String date) {
        for (Book book : receive) {
            System.out.printf("[%s] %s-%s got received by purchasing department in %s\n",
                    date, book.getSchool(), book, library.getSchool());
            State.print(date, book.toString(), 6);
        }
        for (Book book : otherReturned) {
            System.out.printf("[%s] %s-%s got received by purchasing department in %s\n",
                    date, book.getSchool(), book, library.getSchool());
            State.print(date, book.toString(), 6);
        }
    }

    public void distribute(String date) {
        for (Book book : receive) {
            Student student = library.getStudents().get(book.getBookedBy());
            student.addBook(book);
            System.out.printf("[%s] purchasing department lent %s-%s to %s-%s\n",
                    date, book.getSchool(), book, student.getSchool(), student.getId());
            System.out.printf("[%s] %s-%s borrowed %s-%s from purchasing department\n",
                    date, student.getSchool(), student.getId(), book.getSchool(), book);
            State.print(date, book.toString(), 2);
        }
        receive.clear();
    }

    public String find(Book book) {
        for (Library otherLibrary : libraries.values()) {
            Book otherBook = otherLibrary.getBookList().getBook(book.toString());
            if (otherBook != null && otherBook.getCnt() > 0) {
                if (otherBook.getAllow().equals("Y")) {
                    return otherLibrary.getSchool();
                }
            }
        }
        return null;
    }
}
