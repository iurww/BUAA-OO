import java.math.BigInteger;
import java.util.ArrayList;

class Adventurer {
    private int id;
    private String name;
    private double health;
    private double exp;
    private double money;
    private ArrayList<Equipment> equipments;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        health = 100.0;
        exp = 0.0;
        money = 0.0;
        equipments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int findEquipment(int id) {
        int ans = 2009;
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getId() == id) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    public void addEquipment(int type, int id, String name, long price, double bas) {
        if (type == 1) {
            Bottle p = new Bottle(id, name, price, bas);
            equipments.add(p);
        } else if (type == 4) {
            Sword p = new Sword(id, name, price, bas);
            equipments.add(p);
        }
    }

    public void addEquipment(int type, int id, String name, long price, double bas, double ext) {
        if (type == 2) {
            HealingPotion p = new HealingPotion(id, name, price, bas, ext);
            equipments.add(p);
        } else if (type == 3) {
            ExpBottle p = new ExpBottle(id, name, price, bas, ext);
            equipments.add(p);
        } else if (type == 5) {
            RareSword p = new RareSword(id, name, price, bas, ext);
            equipments.add(p);
        } else if (type == 6) {
            EpicSword p = new EpicSword(id, name, price, bas, ext);
            equipments.add(p);
        }
    }

    public void deleteEquipment(int id) {
        int i = findEquipment(id);
        equipments.remove(i);
    }

    public String getEquipInfo(int id) {
        int i = findEquipment(id);
        return equipments.get(i).toString();
    }

    public void use(int id) {
        int i = findEquipment(id);
        Equipment t = equipments.get(i);
        if (t instanceof Bottle) {
            Bottle p = (Bottle) t;
            if (p.getFilled()) {
                health += p.getCapacity() / 10.0;
                p.setFilled(false);
                p.setPrice();
                System.out.println(name + " drank " + p.getName() +
                        " and recovered " + p.getCapacity() / 10.0 + ".");
                if (p instanceof HealingPotion) {
                    HealingPotion q = (HealingPotion) p;
                    health += q.getCapacity() * q.getEfficiency();
                    System.out.println(name + " recovered extra " +
                            q.getCapacity() * q.getEfficiency() + ".");
                } else if (p instanceof ExpBottle) {
                    ExpBottle q = (ExpBottle) p;
                    exp *= q.getExpRatio();
                    System.out.println(name + "'s exp became " + exp + ".");
                }

            } else {
                System.out.println("Failed to use " + p.getName() + " because it is empty.");
            }
        } else if (t instanceof Sword) {
            Sword p = (Sword) t;
            health -= 10.0;
            exp += 10.0;
            money += p.getSharpness();
            System.out.println(name + " used " + p.getName() + " and earned "
                    + p.getSharpness() + ".");
            if (t instanceof RareSword) {
                RareSword q = (RareSword) p;
                exp += q.getExtraExp();
                System.out.println(name + " got extra exp " + q.getExtraExp() + ".");
            } else if (p instanceof EpicSword) {
                EpicSword q = (EpicSword) p;
                q.setSharpness(q.getEvolveRatio());
                System.out.println(p.getName() + "'s sharpness became "
                        + p.getSharpness() + ".");
            }
        }
    }

    @Override
    public String toString() {
        return "The adventurer's id is " + id + ", name is " + name + ", health is "
                + health + ", exp is " + exp + ", money is " + money + ".";
    }

    public int getCnt() {
        return equipments.size();
    }

    public BigInteger getSum() {
        BigInteger sum = new BigInteger("0");
        for (int i = 0; i < equipments.size(); i++) {
            String t = Long.toString(equipments.get(i).getPrice());
            sum = sum.add(new BigInteger(t));
        }
        return sum;
    }

    public long getMax() {
        long ans = 0;
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getPrice() > ans) {
                ans = equipments.get(i).getPrice();
            }
        }
        return ans;
    }
}