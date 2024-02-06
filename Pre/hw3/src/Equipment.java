import java.math.BigInteger;

class Equipment implements Commodity {
    private int id;
    private String name;
    private BigInteger price;

    public Equipment(int id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = new BigInteger(Long.toString(price));
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    /*@Override
    public int compareTo(Commodity other) {
        if (this.getPrice().compareTo(other.getPrice()) == 0) {
            return other.getId() - this.getId();
        }
        return -this.getPrice().compareTo(other.getPrice());
    }*/

    @Override
    public void usedBy(Adventurer cur) throws Exception {
    }

    public Equipment deepClone() {
        Equipment copy = null;
        return copy;
    }

}

