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
    public void usedBy(Adventurer cur) {
        super.usedBy(cur);
        cur.setExp(cur.getExp() + extraExp);
        System.out.println(cur.getName() + " got extra exp " + extraExp + ".");
    }

    @Override
    public String toString() {
        return "The rareSword's id is " + this.getId() + ", name is " + this.getName() +
                ", sharpness is " + this.getSharpness() +
                ", extraExpBonus is " + this.extraExp + ".";
    }

    @Override
    public RareSword deepClone() {
        RareSword copy = new RareSword(this.getId(), this.getName(), 0,
                this.getSharpness(), extraExp);
        copy.setPrice(this.getPrice());
        return copy;
    }
}
