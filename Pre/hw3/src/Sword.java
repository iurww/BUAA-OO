class Sword extends Equipment {
    private double sharpness;

    public Sword(int id, String name, long price, double sharpness) {
        super(id, name, price);
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return sharpness;
    }

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    @Override
    public void usedBy(Adventurer cur) {
        cur.setHealth(cur.getHealth() - 10.0);
        cur.setExp(cur.getExp() + 10.0);
        cur.setMoney(cur.getMoney() + sharpness);
        System.out.println(cur.getName() + " used " + this.getName() + " and earned "
                + sharpness + ".");
    }

    @Override
    public String toString() {
        return "The sword's id is " + this.getId() + ", name is " + this.getName() +
                ", sharpness is " + this.sharpness + ".";
    }

    @Override
    public Sword deepClone() {
        Sword copy = new Sword(this.getId(), this.getName(), 0, sharpness);
        copy.setPrice(this.getPrice());
        return copy;
    }

}