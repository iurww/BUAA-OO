import java.math.BigInteger;

interface Commodity extends Comparable<Commodity> {

    public BigInteger getPrice();

    public int getId();

    public String toString();

    public void usedBy(Adventurer p) throws Exception;

    default int compareTo(Commodity other) {
        if (this.getPrice().compareTo(other.getPrice()) == 0) {
            return other.getId() - this.getId();
        }
        return -this.getPrice().compareTo(other.getPrice());
    }

}
