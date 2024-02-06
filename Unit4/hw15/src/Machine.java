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
        Student student = students.getOrDefault(stuId, new Student(stuId, library.getSchool()));
        students.put(stuId, student);
        if (student.hasC(book)) {
            System.out.printf("[%s] self-service machine refused lending %s-%s to %s-%s\n"
                    , date, book.getSchool(), book, student.getSchool(), stuId);
            Sequence.print(date, 2);
            temp.addBook(book);
        } else {
            System.out.printf("[%s] self-service machine lent %s-%s to %s-%s\n",
                    date, book.getSchool(), book, student.getSchool(), stuId);
            System.out.printf("[%s] %s-%s borrowed %s-%s from self-service machine\n",
                    date, student.getSchool(), stuId, book.getSchool(), book);
            State.print(date, book.toString(), 2);
            Sequence.print(date, 2);
            student.addBook(date, book);
        }
    }

    public void returnC(String date, String stuId, Book book, int type) {
        System.out.printf("[%s] %s-%s returned %s-%s to self-service machine\n",
                date, library.getSchool(), stuId, book.getSchool(), book);
        System.out.printf("[%s] self-service machine collected %s-%s from %s-%s\n",
                date, book.getSchool(), book, library.getSchool(), stuId);
        State.print(date, book.toString(), 3);
        Sequence.print(date, 2);
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId, library.getSchool()));
        students.put(stuId, student);
        student.returnBook(book);
        if (book.getSchool().equals(library.getSchool())) {
            if (type > 0) {
                temp.addBook(book);
            } else {
                System.out.printf("[%s] %s-%s got repaired by logistics division in %s\n",
                        date, book.getSchool(), book, library.getSchool());
                State.print(date, book.toString(), 4);
                State.print(date, book.toString(), 1);
                library.getLogistics().addBook(book);
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
