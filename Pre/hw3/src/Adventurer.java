import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Adventurer implements Commodity {
    private int id;
    private String name;
    private double health;
    private double exp;
    private double money;
    private ArrayList<Commodity> has;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        health = 100.0;
        exp = 0.0;
        money = 0.0;
        has = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "The adventurer's id is " + id + ", name is " + name + ", health is "
                + health + ", exp is " + exp + ", money is " + money + ".";
    }

    @Override
    public BigInteger getPrice() {
        BigInteger ans = new BigInteger("0");
        for (Commodity p : has) {
            ans = ans.add(p.getPrice());
        }
        return ans;
    }

    @Override
    public void usedBy(Adventurer cur) {
        Collections.sort(this.has);
        for (Commodity t : this.has) {
            try {
                t.usedBy(cur);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /*@Override
    public int compareTo(Commodity other) {
        if (this.getPrice().compareTo(other.getPrice()) == 0) {
            return other.getId() - this.getId();
        }
        return -this.getPrice().compareTo(other.getPrice());
    }*/

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getExp() {
        return exp;
    }

    public double getMoney() {
        return money;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int find(int id) {
        int ans = 2009;
        for (int i = 0; i < has.size(); i++) {
            if (has.get(i).getId() == id) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    public void addEquipment(Equipment p) {
        has.add(p);
    }

    public void addEquipment(int type, int id, String name, long price, double bas) {
        String t = Long.toString(price);
        if (type == 1) {
            Bottle p = new Bottle(id, name, price, bas);
            has.add(p);
        } else if (type == 4) {
            Sword p = new Sword(id, name, price, bas);
            has.add(p);
        }
    }

    public void addEquipment(int type, int id, String name, long price, double bas, double ext) {
        String t = Long.toString(price);
        if (type == 2) {
            HealingPotion p = new HealingPotion(id, name, price, bas, ext);
            has.add(p);
        } else if (type == 3) {
            ExpBottle p = new ExpBottle(id, name, price, bas, ext);
            has.add(p);
        } else if (type == 5) {
            RareSword p = new RareSword(id, name, price, bas, ext);
            has.add(p);
        } else if (type == 6) {
            EpicSword p = new EpicSword(id, name, price, bas, ext);
            has.add(p);
        }
    }

    public Adventurer deepClone(HashMap<String, Adventurer> isCloned) {
        Adventurer copy = new Adventurer(id, name);
        copy.setHealth(health);
        copy.setExp(exp);
        copy.setMoney(money);
        for (Commodity item : has) {
            if (item instanceof Adventurer) {
                Adventurer p = (Adventurer) item;
                if (!isCloned.containsKey(p.getName())) {
                    Adventurer copyAdv = p.deepClone(isCloned);
                    copy.addPerson(copyAdv);
                    isCloned.put(copyAdv.getName(), copyAdv);
                } else {
                    copy.addPerson(isCloned.get(p.getName()));
                }
            } else if (item instanceof Equipment) {
                Equipment p = (Equipment) item;
                copy.addEquipment(p.deepClone());
            }
        }
        return copy;
    }

    public void addPerson(Adventurer p) {
        has.add(p);
    }

    public void delete(int id) {
        int i = find(id);
        has.remove(i);
    }

    public BigInteger getSum() {
        BigInteger sum = new BigInteger("0");
        for (int i = 0; i < has.size(); i++) {
            sum = sum.add(has.get(i).getPrice());
        }
        return sum;
    }

    public BigInteger getMax() {
        BigInteger ans = new BigInteger("0");
        for (int i = 0; i < has.size(); i++) {
            if (ans.compareTo(has.get(i).getPrice()) == -1) {
                ans = has.get(i).getPrice();
            }
        }
        return ans;
    }

    public int getCnt() {
        return has.size();
    }

    public String getCommodityInfo(int id) {
        int i = find(id);
        return has.get(i).toString();
    }

    public void useAll() {
        Collections.sort(this.has);
        for (Commodity t : this.has) {
            try {
                t.usedBy(this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}