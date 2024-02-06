class EpicSword extends Sword {

    private double evolveRatio;

    public EpicSword(int id, String name, long price, double sharp, double evo) {
        super(id, name, price, sharp);
        this.evolveRatio = evo;
    }

    public double getEvolveRatio() {
        return evolveRatio;
    }

    @Override
    public void usedBy(Adventurer cur) {
        super.usedBy(cur);
        this.setSharpness(this.getSharpness() * evolveRatio);
        System.out.println(getName() + "'s sharpness became "
                + this.getSharpness() + ".");
    }

    @Override
    public String toString() {
        return "The epicSword's id is " + this.getId() + ", name is " + this.getName() +
                ", sharpness is " + this.getSharpness() +
                ", evolveRatio is " + this.evolveRatio + ".";
    }

    @Override
    public EpicSword deepClone() {
        EpicSword copy = new EpicSword(this.getId(), this.getName(), 0,
                this.getSharpness(), evolveRatio);
        copy.setPrice(this.getPrice());
        return copy;
    }
}
