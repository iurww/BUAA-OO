import java.util.Objects;

public class Student {
    private String school;
    private String id;
    private BookList borrow;

    public Student(String id, String school) {
        this.school = school;
        this.id = id;
        this.borrow = new BookList();
    }

    public BookList getBorrow() {
        return borrow;
    }

    public String getId() {
        return id;
    }

    public String getSchool() {
        return school;
    }

    public boolean hasB() {
        return borrow.hasB();
    }

    public boolean hasC(Book book) {
        return borrow.hasC(book);
    }

    public void addBook(String date, Book book) {
        book.setBorrowDate(date);
        borrow.addBook(book);
    }

    public void returnBook(Book book) {
        borrow.borrowBook(book);
    }

    public Book getBook(String book) {
        return borrow.getBook(book);
    }

    public void lostBook(String book) {
        borrow.lostBook(book);
    }

    public void getMessage() {

    }

    public void getOrderedBook() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
