class HealingPotion extends Bottle {
    private double efficiency;

    public HealingPotion(int id, String name, long price, double cap, double effi) {
        super(id, name, price, cap);
        this.efficiency = effi;
    }

    public double getEfficiency() {
        return efficiency;
    }

    @Override
    public void usedBy(Adventurer cur) throws Exception {
        super.usedBy(cur);
        cur.setHealth(cur.getHealth() + getCapacity() * efficiency);
        System.out.println(cur.getName() + " recovered extra " +
                this.getCapacity() * efficiency + ".");
    }

    @Override
    public String toString() {
        return "The healingPotion's id is " + this.getId() + ", name is " + this.getName() +
                ", capacity is " + this.getCapacity() + ", filled is " + this.getFilled() +
                ", efficiency is " + this.efficiency + ".";
    }

    @Override
    public HealingPotion deepClone() {
        HealingPotion copy = new HealingPotion(this.getId(), this.getName(), 0,
                this.getCapacity(), efficiency);
        copy.setPrice(this.getPrice());
        copy.setFilled(this.getFilled());
        return copy;
    }
}
