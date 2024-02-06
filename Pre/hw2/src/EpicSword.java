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
    public String toString() {
        return "The epicSword's id is " + this.getId() + ", name is " + this.getName() +
                ", sharpness is " + this.getSharpness() +
                ", evolveRatio is " + this.evolveRatio + ".";
    }
}
