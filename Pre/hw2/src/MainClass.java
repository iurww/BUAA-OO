import java.util.Scanner;

public class MainClass {

    private static Adventurer[] adventurers = new Adventurer[2010];
    private static int cnt = 0;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        int n = cin.nextInt();
        for (int i = 1; i <= n; i++) {
            int op = cin.nextInt();
            int advId = cin.nextInt();
            int j = MainClass.findAdv(advId);

            if (op == 1) {
                String name = cin.next();
                Adventurer p = new Adventurer(advId, name);
                adventurers[++cnt] = p;
            } else if (op == 2) {
                int type = cin.nextInt();
                int id = cin.nextInt();
                String name = cin.next();
                long price = cin.nextLong();
                double basic = cin.nextDouble();
                if (type != 1 && type != 4) {
                    double extra = cin.nextDouble();
                    adventurers[j].addEquipment(type, id, name, price, basic, extra);
                } else {
                    adventurers[j].addEquipment(type, id, name, price, basic);
                }
            } else if (op == 3) {
                int id = cin.nextInt();
                adventurers[j].deleteEquipment(id);
            } else if (op == 4) {
                System.out.println(adventurers[j].getSum());
            } else if (op == 5) {
                System.out.println(adventurers[j].getMax());
            } else if (op == 6) {
                System.out.println(adventurers[j].getCnt());
            } else if (op == 7) {
                int id = cin.nextInt();
                System.out.println(adventurers[j].getEquipInfo(id));
            } else if (op == 8) {
                int id = cin.nextInt();
                adventurers[j].use(id);
            } else if (op == 9) {
                System.out.println(adventurers[j].toString());
            }
        }
    }

    public static int findAdv(int id) {
        for (int i = 1; i <= cnt; i++) {
            if (adventurers[i].getId() == id) {
                return i;
            }
        }
        return 0;
    }

}


