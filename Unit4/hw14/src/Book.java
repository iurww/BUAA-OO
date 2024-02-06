import java.util.Objects;

public class Book {
    private String type;
    private String school;
    private String id;
    private int cnt;
    private int maxCnt;
    private String allow;
    private boolean isSmeared;
    private String bookedBy;
    private String needBuy;

    public Book(String s, int cnt, String school, String allow) {
        this.type = s.substring(0, 1);
        this.id = s.substring(2);
        this.cnt = cnt;
        this.maxCnt = cnt;
        this.isSmeared = false;
        this.bookedBy = null;
        this.school = school;
        this.allow = allow;
        this.needBuy = null;
    }

    public void setNeedBuy(String needBuy) {
        this.needBuy = needBuy;
    }

    public String getNeedBuy() {
        return needBuy;
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

    public int getMaxCnt() {
        return maxCnt;
    }

    public void addMaxCnt(int t) {
        maxCnt += t;
    }

    public String getAllow() {
        return allow;
    }

    public String getSchool() {
        return school;
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

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public void lost() {
        maxCnt--;
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
