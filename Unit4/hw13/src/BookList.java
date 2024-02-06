import java.util.HashMap;

public class BookList {
    private HashMap<String, HashMap<String, Book>> books;

    public BookList() {
        this.books = new HashMap<>();
        books.put("A", new HashMap<>());
        books.put("B", new HashMap<>());
        books.put("C", new HashMap<>());
    }

    public void addBook(Book book) {
        if (books.get(book.getType()).containsKey(book.getId())) {
            books.get(book.getType()).get(book.getId()).addCnt(book.getCnt());
            return;
        }
        books.get(book.getType()).put(book.getId(), book);
    }

    public void clear() {
        books.get("A").clear();
        books.get("B").clear();
        books.get("C").clear();
    }

    public boolean hasB() {
        int cnt = 0;
        for (Book book : books.get("B").values()) {
            cnt += book.getCnt();
        }
        return cnt > 0;
    }

    public boolean hasB(Book book) {
        return books.get("B").containsKey(book.getId())
                && books.get("B").get(book.getId()).getCnt() > 0;
    }

    public boolean hasC(Book book) {
        return books.get("C").containsKey(book.getId())
                && books.get("C").get(book.getId()).getCnt() > 0;
    }

    public Book getBook(String book) {
        return books.get(book.substring(0, 1)).get(book.substring(2));
    }

    public void lostBook(String book) {
        books.get(book.substring(0, 1)).remove(book.substring(2));
    }

    public void borrowBook(Book book) {
        books.get(book.getType()).get(book.getId()).borrowed();
    }

    public void returnBook(Book book) {
        books.get(book.getType()).get(book.getId()).returned();
    }

    public void mergeBookList(BookList bookList) {
        for (HashMap<String, Book> m : bookList.books.values()) {
            for (Book book : m.values()) {
                addBook(book);
            }
        }
    }

    @Override
    public String toString() {
        return books.toString();
    }
}
