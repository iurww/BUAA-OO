class ExpBottle extends Bottle {
    private double expRatio;

    public ExpBottle(int id, String name, long price, double cap, double exp) {
        super(id, name, price, cap);
        this.expRatio = exp;
    }

    public double getExpRatio() {
        return expRatio;
    }

    @Override
    public String toString() {
        return "The expBottle's id is " + this.getId() + ", name is " + this.getName() +
                ", capacity is " + this.getCapacity() + ", filled is " + this.getFilled() +
                ", expRatio is " + this.expRatio + ".";
    }
}
