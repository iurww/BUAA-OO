import java.util.HashMap;
import java.util.LinkedHashMap;

public class Library {
    private String school;
    private BookList bookList;
    private HashMap<String, Student> students;
    private LinkedHashMap<String, Library> libraries;
    private ArrangeAdmin arrangeAdmin;
    private BorrowAdmin borrowAdmin;
    private OrderAdmin orderAdmin;
    private Machine machine;
    private BookList logistics;
    private Office office;

    public Library(String school, LinkedHashMap<String, Library> libraries) {
        this.school = school;
        this.bookList = new BookList();
        this.students = new HashMap<>();
        this.libraries = libraries;
        this.arrangeAdmin = new ArrangeAdmin(this);
        this.borrowAdmin = new BorrowAdmin(this);
        this.orderAdmin = new OrderAdmin(this);
        this.machine = new Machine(this);
        this.logistics = new BookList();
        this.office = new Office(this, libraries);
    }

    public String getSchool() {
        return school;
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

    public LinkedHashMap<String, Library> getLibraries() {
        return libraries;
    }

    public Office getOffice() {
        return office;
    }

    public void addBook(Book book) {
        bookList.addBook(book);
    }

    public void borrowBook(String date, String stuId, String book) {
        System.out.printf("[%s] %s-%s queried %s from self-service machine\n",
                date, school, stuId, book);
        System.out.printf("[%s] self-service machine provided information of %s\n", date, book);
        Book borrowBook = new Book(book, 1, school, "");
        Student student = students.getOrDefault(stuId, new Student(stuId, school));
        students.put(stuId, student);
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
            office.addOrder(date, stuId, borrowBook);
        }
    }

    public void checkBorrowC(String date, String stuId, Book borrowBook) {
        if (bookList.hasC(borrowBook)) {
            bookList.borrowBook(borrowBook);
            machine.borrowC(date, stuId, borrowBook);
        } else {
            office.addOrder(date, stuId, borrowBook);
        }
    }

    public void smearBook(String date, String stuId, String book) {
        students.get(stuId).getBook(book).setSmeared(true);
    }

    public void lostBook(String date, String stuId, String book) {
        String originSchool = students.get(stuId).getBook(book).getSchool();
        libraries.get(originSchool).getBookList().lost(book);
        students.get(stuId).lostBook(book);
        borrowAdmin.punish(date, stuId);
    }

    public void returnBook(String date, String stuId, String book) {
        Book borrowBook = new Book(book, 1, students.get(stuId).getBook(book).getSchool(), "");
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

    public void purchase(String date) {
        arrangeAdmin.purchase(date);
    }

    public void collect(String date) {
        arrangeAdmin.collect(date);
    }

    public void order() {
        office.order();
    }

    public void send(String date) {
        office.send(date);
    }

    public void receive(String date) {
        office.receive(date);
    }

    public void distribute(String date) {
        office.distribute(date);
    }

}
