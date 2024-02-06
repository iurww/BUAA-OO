class RareSword extends Sword {
    private double extraExp;

    public RareSword(int id, String name, long price, double sharp, double extraExp) {
        super(id, name, price, sharp);
        this.extraExp = extraExp;
    }

    public double getExtraExp() {
        return extraExp;
    }

    @Override
    public String toString() {
        return "The rareSword's id is " + this.getId() + ", name is " + this.getName() +
                ", sharpness is " + this.getSharpness() +
                ", extraExpBonus is " + this.extraExp + ".";
    }
}
