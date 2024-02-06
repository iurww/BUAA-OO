import java.math.BigInteger;
import java.util.ArrayList;

public class Adventurer {
    private int id;
    private String name;
    private ArrayList<Bottle> bottles;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        bottles = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public void addBottle(Bottle b) {
        this.bottles.add(b);
    }

    public int findBottle(int id) {
        int ans = 2009;
        for (int i = 0; i < bottles.size(); i++) {
            if (bottles.get(i).getId() == id) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    public void deleteBottle(int id) {
        int i = findBottle(id);
        bottles.remove(i);
    }

    public void changePrice(int id, long price) {
        int i = findBottle(id);
        bottles.get(i).changePrice(price);
    }

    public void changeFilled(int id, boolean filled) {
        int i = findBottle(id);
        bottles.get(i).changeFilled(filled);
    }

    public String getBottleName(int id) {
        int i = findBottle(id);
        return bottles.get(i).getName();
    }

    public long getBottlePrice(int id) {
        int i = findBottle(id);
        return bottles.get(i).getPrice();
    }

    public double getBottleCap(int id) {
        int i = findBottle(id);
        return bottles.get(i).getCap();
    }

    public boolean getBottleFill(int id) {
        int i = findBottle(id);
        return bottles.get(i).getFill();
    }

    public void printInfo(int id) {
        int i = findBottle(id);
        System.out.println(bottles.get(i));
    }

    public BigInteger getSum() {
        BigInteger sum = new BigInteger("0");
        for (int i = 0; i < bottles.size(); i++) {
            String t = Long.toString(bottles.get(i).getPrice());
            sum = sum.add(new BigInteger(t));
        }
        return sum;
    }

    public long getMax() {
        long ans = 0;
        for (int i = 0; i < bottles.size(); i++) {
            if (bottles.get(i).getPrice() > ans) {
                ans = bottles.get(i).getPrice();
            }
        }
        return ans;
    }
}