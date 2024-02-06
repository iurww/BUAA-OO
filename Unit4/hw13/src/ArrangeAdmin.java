import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArrangeAdmin {
    private Library library;

    public ArrangeAdmin(Library library) {
        this.library = library;
    }

    public void collect(String date) {
        BookList temp = new BookList();
        temp.mergeBookList(library.getBorrowAdmin().getTemp());
        temp.mergeBookList(library.getMachine().getTemp());
        temp.mergeBookList(library.getLogistics());

        Map<String, Map<String, Set<Book>>> orderList = library.getOrderAdmin().getOrderList();
        ArrayList<Book> list = new ArrayList<>();
        for (Map<String, Set<Book>> m : orderList.values()) {
            for (String stuId : m.keySet()) {
                for (Book book : m.get(stuId)) {
                    if (book.getBookedBy() == null) {
                        book.setBookedBy(stuId);
                        list.add(book);
                    }
                }
            }
        }
        HashSet<String> borrowB = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            if (book.getType().equals("B")) {
                if (temp.hasB(book) && !borrowB.contains(book.getBookedBy())) {
                    temp.borrowBook(book);
                    library.getOrderAdmin().borrow(date, book.getBookedBy(), book);
                    borrowB.add(book.getBookedBy());
                }
            } else if (book.getType().equals("C")) {
                if (temp.hasC(book)) {
                    temp.borrowBook(book);
                    library.getOrderAdmin().borrow(date, book.getBookedBy(), book);
                } else {
                    book.setBookedBy(null);
                }
            }
        }
        for (Book book : list) {
            if (book.getType().equals("B") && !borrowB.contains(book.getBookedBy())) {
                book.setBookedBy(null);
            }
        }
        library.getBookList().mergeBookList(temp);
        library.getBorrowAdmin().getTemp().clear();
        library.getMachine().getTemp().clear();
        library.getLogistics().clear();

    }
}
