public class Bottle {
    private int id;
    private String name;
    private long price;
    private double capacity;
    private boolean filled;

    public Bottle(int id, String name, long price, double capacity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.filled = true;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public long getPrice() {
        return this.price;
    }

    public double getCap() {
        return this.capacity;
    }

    public boolean getFill() {
        return this.filled;
    }

    public void changePrice(long price) {
        this.price = price;
    }

    public void changeFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        String s1 = "The bottle's id is " + id + ", name is " + name;
        String s2 = ", capacity is " + capacity + ", filled is " + filled + ".";
        return s1 + s2;
    }

}