import java.math.BigInteger;

class Bottle extends Equipment {
    private double capacity;
    private boolean filled;

    public Bottle(int id, String name, long price, double capacity) {
        super(id, name, price);
        this.capacity = capacity;
        this.filled = true;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean getFilled() {
        return filled;
    }

    public void setFilled(boolean newFilled) {
        filled = newFilled;
    }

    @Override
    public void usedBy(Adventurer cur) throws Exception {
        if (!getFilled()) {
            throw new Exception("Failed to use " + getName() + " because it is empty.");
        }
        cur.setHealth(cur.getHealth() + capacity / 10.0);
        filled = false;
        this.setPrice(this.getPrice().divide(BigInteger.TEN));
        System.out.println(cur.getName() + " drank " + this.getName() +
                " and recovered " + this.getCapacity() / 10.0 + ".");
    }

    @Override
    public String toString() {
        return "The bottle's id is " + this.getId() + ", name is " + this.getName() +
                ", capacity is " + this.capacity + ", filled is " + this.filled + ".";
    }

    @Override
    public Bottle deepClone() {
        Bottle copy = new Bottle(this.getId(), this.getName(), 0, capacity);
        copy.setPrice(this.getPrice());
        copy.setFilled(this.filled);
        return copy;
    }

}
