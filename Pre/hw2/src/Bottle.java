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
    public String toString() {
        return "The bottle's id is " + this.getId() + ", name is " + this.getName() +
                ", capacity is " + this.capacity + ", filled is " + this.filled + ".";
    }

}
