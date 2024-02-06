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
    public void usedBy(Adventurer cur) throws Exception {
        super.usedBy(cur);
        cur.setExp(cur.getExp() * expRatio);
        System.out.println(cur.getName() + "'s exp became " + cur.getExp() + ".");
    }

    @Override
    public String toString() {
        return "The expBottle's id is " + this.getId() + ", name is " + this.getName() +
                ", capacity is " + this.getCapacity() + ", filled is " + this.getFilled() +
                ", expRatio is " + this.expRatio + ".";
    }

    @Override
    public ExpBottle deepClone() {
        ExpBottle copy = new ExpBottle(this.getId(), this.getName(), 0,
                this.getCapacity(), expRatio);
        copy.setPrice(this.getPrice());
        copy.setFilled(this.getFilled());
        return copy;
    }
}
