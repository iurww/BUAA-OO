import java.util.Objects;

public class Book {
    private String type;
    private String id;
    private int cnt;
    private boolean isSmeared;
    private String bookedBy;

    public Book(String s, int cnt) {
        this.type = s.substring(0, 1);
        this.id = s.substring(2);
        this.cnt = cnt;
        this.isSmeared = false;
        this.bookedBy = null;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public int getCnt() {
        return cnt;
    }

    public void addCnt(int t) {
        this.cnt += t;
    }

    public boolean isSmeared() {
        return isSmeared;
    }

    public void setSmeared(boolean smeared) {
        isSmeared = smeared;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public void borrowed() {
        cnt--;
    }

    public void returned() {
        cnt++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return type.equals(book.type) && id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

    @Override
    public String toString() {
        return type + "-" + id;
    }
}
