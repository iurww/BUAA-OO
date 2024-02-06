import java.util.HashMap;

public class Machine {
    private Library library;
    private BookList temp;

    public Machine(Library library) {
        this.library = library;
        this.temp = new BookList();
    }

    public BookList getTemp() {
        return temp;
    }

    public void borrowC(String date, String stuId, Book book) {
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId));
        students.put(stuId, student);
        if (student.hasC(book)) {
            temp.addBook(book);
        } else {
            System.out.printf("[%s] %s borrowed %s from self-service machine\n", date, stuId, book);
            student.addBook(book);
        }
    }

    public void returnC(String date, String stuId, Book book, int type) {
        System.out.printf("[%s] %s returned %s to self-service machine\n", date, stuId, book);
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId));
        students.put(stuId, student);
        student.returnBook(book);
        if (type > 0) {
            temp.addBook(book);
        } else {
            System.out.printf("[%s] %s got repaired by logistics division\n", date, book);
            library.getLogistics().addBook(book);
        }
    }
}
