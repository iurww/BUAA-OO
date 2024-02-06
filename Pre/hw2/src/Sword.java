class Sword extends Equipment {
    private double sharpness;

    public Sword(int id, String name, long price, double sharpness) {
        super(id, name, price);
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return sharpness;
    }

    public void setSharpness(double t) {
        this.sharpness *= t;
    }

    @Override
    public String toString() {
        return "The sword's id is " + this.getId() + ", name is " + this.getName() +
                ", sharpness is " + this.sharpness + ".";
    }

}