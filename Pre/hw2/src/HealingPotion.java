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
    public String toString() {
        return "The healingPotion's id is " + this.getId() + ", name is " + this.getName() +
                ", capacity is " + this.getCapacity() + ", filled is " + this.getFilled() +
                ", efficiency is " + this.efficiency + ".";
    }
}
