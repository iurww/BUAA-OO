import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

public class OrderAdmin {
    private Library library;
    private Map<String, Map<String, Set<Book>>> orderList;
    private ArrayList<Book> needBuy;

    public OrderAdmin(Library library) {
        this.library = library;
        this.orderList = new LinkedHashMap<>();
        this.needBuy = new ArrayList<>();
    }

    public Map<String, Map<String, Set<Book>>> getOrderList() {
        return orderList;
    }

    public ArrayList<Book> getNeedBuy() {
        return needBuy;
    }

    public void order(String date, String stuId, Book book, String type) {
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId, library.getSchool()));
        students.put(stuId, student);
        if ((type.equals("B") && !student.hasB()) || (type.equals("C") && !student.hasC(book))) {
            //System.out.println(library.getBookList());
            if (!library.getBookList().hasBook(book) ||
                    library.getBookList().getBook(book.toString()).getMaxCnt() == 0) {
                book.setAllow("Y");
                book.setNeedBuy(stuId);
                needBuy.add(book);
            }
            Map<String, Set<Book>> curOrder = orderList.getOrDefault(date, new LinkedHashMap<>());
            orderList.put(date, curOrder);
            Set<Book> stuOrder = curOrder.getOrDefault(stuId, new LinkedHashSet<>());
            curOrder.put(stuId, stuOrder);
            boolean flag = true;
            for (Map<String, Set<Book>> m : orderList.values()) {
                if (m.containsKey(stuId)) {
                    for (Book book1 : m.get(stuId)) {
                        if (book1.equals(book) && book1.getBookedBy() == null) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (stuOrder.size() < 3 && flag) {
                System.out.printf("[%s] %s-%s ordered %s-%s from ordering librarian\n",
                        date, student.getSchool(), stuId, book.getSchool(), book);
                System.out.printf("[%s] ordering librarian recorded %s-%s's order of %s-%s\n",
                        date, student.getSchool(), stuId, book.getSchool(), book);
                Sequence.print(date, 1);
                stuOrder.add(book);
            }
        }
    }

    public void clearB(String stuId) {
        orderList.values().stream().filter(m -> m.containsKey(stuId))
                .flatMap(m -> m.get(stuId).stream())
                .filter(book -> book.getType().equals("B"))
                .forEach(book -> book.setBookedBy(stuId));
    }

    public void borrow(String date, String stuId, Book book) {
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId, library.getSchool()));
        students.put(stuId, student);
        System.out.printf("[%s] ordering librarian lent %s-%s to %s-%s\n",
                date, book.getSchool(), book, student.getSchool(), stuId);
        System.out.printf("[%s] %s-%s borrowed %s-%s from ordering librarian\n",
                date, student.getSchool(), stuId, book.getSchool(), book);
        State.print(date, book.toString(), 2);
        Sequence.print(date, 1);
        student.addBook(date, book);

    }

}
