import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class OrderAdmin {
    private Library library;
    private Map<String, Map<String, Set<Book>>> orderList;

    public OrderAdmin(Library library) {
        this.library = library;
        this.orderList = new LinkedHashMap<>();
    }

    public Map<String, Map<String, Set<Book>>> getOrderList() {
        return orderList;
    }

    public void order(String date, String stuId, Book book, int type) {
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId));
        students.put(stuId, student);
        if ((type == 0 && !student.hasB()) || (type == 1 && !student.hasC(book))) {
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
                            break; } } } }
            if (stuOrder.size() < 3 && flag) {
                System.out.printf("[%s] %s ordered %s from ordering librarian\n",
                        date, stuId, book);
                stuOrder.add(book);
            }
        }
    }

    public void clearB(String stuId) {
        orderList.values().stream().filter(m -> m.containsKey(stuId)).flatMap(m -> m.get(stuId)
   .stream()).filter(book -> book.getType().equals("B")).forEach(book -> book.setBookedBy(stuId)); }

    public void borrow(String date, String stuId, Book book) {
        HashMap<String, Student> students = library.getStudents();
        Student student = students.getOrDefault(stuId, new Student(stuId));
        students.put(stuId, student);
        System.out.printf("[%s] %s borrowed %s from ordering librarian\n",
                date, stuId, book);
        student.addBook(book);

    }

}
