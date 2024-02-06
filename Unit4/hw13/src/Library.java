import java.util.HashMap;

public class Library {
    private BookList bookList;
    private HashMap<String, Student> students;
    private ArrangeAdmin arrangeAdmin;
    private BorrowAdmin borrowAdmin;
    private OrderAdmin orderAdmin;
    private Machine machine;
    private BookList logistics;

    public Library() {
        this.bookList = new BookList();
        this.students = new HashMap<>();
        this.arrangeAdmin = new ArrangeAdmin(this);
        this.borrowAdmin = new BorrowAdmin(this);
        this.orderAdmin = new OrderAdmin(this);
        this.machine = new Machine(this);
        this.logistics = new BookList();
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public BookList getBookList() {
        return bookList;
    }

    public ArrangeAdmin getArrangeAdmin() {
        return arrangeAdmin;
    }

    public BorrowAdmin getBorrowAdmin() {
        return borrowAdmin;
    }

    public OrderAdmin getOrderAdmin() {
        return orderAdmin;
    }

    public Machine getMachine() {
        return machine;
    }

    public BookList getLogistics() {
        return logistics;
    }

    public void addBook(Book book) {
        bookList.addBook(book);
    }

    public void borrowBook(String date, String stuId, String book) {
        System.out.printf("[%s] %s queried %s from self-service machine\n", date, stuId, book);
        Book borrowBook = new Book(book, 1);
        if (borrowBook.getType().equals("B")) {
            checkBorrowB(date, stuId, borrowBook);
        } else if (borrowBook.getType().equals("C")) {
            checkBorrowC(date, stuId, borrowBook);
        }
    }

    public void checkBorrowB(String date, String stuId, Book borrowBook) {
        if (bookList.hasB(borrowBook)) {
            bookList.borrowBook(borrowBook);
            borrowAdmin.borrow(date, stuId, borrowBook);
        } else {
            orderAdmin.order(date, stuId, borrowBook, 0);
        }
    }

    public void checkBorrowC(String date, String stuId, Book borrowBook) {
        if (bookList.hasC(borrowBook)) {
            bookList.borrowBook(borrowBook);
            machine.borrowC(date, stuId, borrowBook);
        } else {
            orderAdmin.order(date, stuId, borrowBook, 1);
        }
    }

    public void smearBook(String date, String stuId, String book) {
        students.get(stuId).getBook(book).setSmeared(true);
    }

    public void lostBook(String date, String stuId, String book) {
        students.get(stuId).lostBook(book);
        borrowAdmin.punish(date, stuId);
    }

    public void returnBook(String date, String stuId, String book) {
        Book borrowBook = new Book(book, 1);
        if (borrowBook.getType().equals("B")) {
            if (students.get(stuId).getBook(book).isSmeared()) {
                borrowAdmin.punish(date, stuId);
                borrowAdmin.returnB(date, stuId, borrowBook, 0);
                students.get(stuId).getBook(book).setSmeared(false);
            } else {
                borrowAdmin.returnB(date, stuId, borrowBook, 1);
            }
        } else if (borrowBook.getType().equals("C")) {
            if (students.get(stuId).getBook(book).isSmeared()) {
                borrowAdmin.punish(date, stuId);
                machine.returnC(date, stuId, borrowBook, 0);
                students.get(stuId).getBook(book).setSmeared(false);
            } else {
                machine.returnC(date, stuId, borrowBook, 1);
            }
        }
    }

    public void collect(String date) {
        arrangeAdmin.collect(date);
    }

}
