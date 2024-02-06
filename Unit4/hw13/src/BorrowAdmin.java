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
        Student student = students.getOrDefault(stuId, new Student(stuId));
        students.put(stuId, student);
        if (student.hasB()) {
            temp.addBook(book);
        } else {
            System.out.printf("[%s] %s borrowed %s from borrowing and returning librarian\n",
                    date, stuId, book);
            library.getOrderAdmin().clearB(stuId);
            student.addBook(book);
        }
    }

    public void punish(String date, String stuId) {
        System.out.printf("[%s] %s got punished by borrowing and returning librarian\n",
                date, stuId);
    }

    public void returnB(String date, String stuId, Book book, int type) {
        System.out.printf("[%s] %s returned %s to borrowing and returning librarian\n",
                date, stuId, book);
        Student student = library.getStudents().get(stuId);
        student.returnBook(book);
        if (type > 0) {
            temp.addBook(book);
        } else {
            System.out.printf("[%s] %s got repaired by logistics division\n", date, book);
            library.getLogistics().addBook(book);
        }
    }
}
