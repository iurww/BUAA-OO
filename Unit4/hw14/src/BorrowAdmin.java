import java.util.HashMap;

public class BorrowAdmin {
    private Library library;
    private BookList temp;

    public BorrowAdmin(Library library) {
        this.library = library;
        this.temp = new BookList();
    }

    public BookList getTemp() {
        return temp;
    }

    public void borrow(String date, String stuId, Book book) {
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId, library.getSchool()));
        students.put(stuId, student);
        if (student.hasB()) {
            System.out.printf("[%s] borrowing and returning librarian " +
                            "refused lending %s-%s to %s-%s\n"
                    , date, book.getSchool(), book, student.getSchool(), stuId);
            State.print(date, book.toString(), 1);
            temp.addBook(book);
        } else {
            System.out.printf("[%s] borrowing and returning librarian lent %s-%s to %s-%s\n",
                    date, book.getSchool(), book, student.getSchool(), stuId);
            System.out.printf("[%s] %s-%s borrowed %s-%s from borrowing and returning librarian\n",
                    date, student.getSchool(), stuId, book.getSchool(), book);
            State.print(date, book.toString(), 2);
            library.getOrderAdmin().clearB(stuId);
            student.addBook(book);
        }
    }

    public void punish(String date, String stuId) {
        System.out.printf("[%s] %s-%s got punished by borrowing and returning librarian\n",
                date, library.getSchool(), stuId);
        System.out.printf("[%s] borrowing and returning librarian received %s-%s's fine\n",
                date, library.getSchool(), stuId);
    }

    public void returnB(String date, String stuId, Book book, int type) {
        System.out.printf("[%s] %s-%s returned %s-%s to borrowing and returning librarian\n",
                date, library.getSchool(), stuId, book.getSchool(), book);
        System.out.printf("[%s] borrowing and returning librarian collected %s-%s from %s-%s\n",
                date, book.getSchool(), book, library.getSchool(), stuId);
        State.print(date, book.toString(), 3);
        Student student = library.getStudents().get(stuId);
        student.returnBook(book);
        if (book.getSchool().equals(library.getSchool())) {
            if (type > 0) {
                temp.addBook(book);
            } else {
                System.out.printf("[%s] %s-%s got repaired by logistics division in %s\n",
                        date, book.getSchool(), book, library.getSchool());
                library.getLogistics().addBook(book);
                State.print(date, book.toString(), 4);
            }
        } else {
            if (type == 0) {
                System.out.printf("[%s] %s-%s got repaired by logistics division in %s\n",
                        date, book.getSchool(), book, library.getSchool());
                State.print(date, book.toString(), 4);
            }
            library.getOffice().addReturned(book);
        }

    }
}
