import java.util.Scanner;

public class MainClass {

    private static Adventurer[] adventurers = new Adventurer[2010];
    private static int cnt = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        for (int i = 1; i <= n; i++) {

            int op = scanner.nextInt();
            int advId = scanner.nextInt();
            int j = MainClass.findAdv(advId);

            if (op == 1) {
                String name = scanner.next();
                Adventurer p = new Adventurer(advId, name);
                adventurers[++cnt] = p;
            } else if (op == 2) {
                int botId = scanner.nextInt();
                String name = scanner.next();
                long price = scanner.nextLong();
                double capacity = scanner.nextDouble();
                Bottle b = new Bottle(botId, name, price, capacity);
                adventurers[j].addBottle(b);
            } else if (op == 3) {
                int botId = scanner.nextInt();
                adventurers[j].deleteBottle(botId);
            } else if (op == 4) {
                int botId = scanner.nextInt();
                long newPrice = scanner.nextLong();
                adventurers[j].changePrice(botId, newPrice);
            } else if (op == 5) {
                int botId = scanner.nextInt();
                boolean newFilled = scanner.nextBoolean();
                adventurers[j].changeFilled(botId, newFilled);
            } else if (op == 6) {
                int botId = scanner.nextInt();
                System.out.println(adventurers[j].getBottleName(botId));
            } else if (op == 7) {
                int botId = scanner.nextInt();
                System.out.println(adventurers[j].getBottlePrice(botId));
            } else if (op == 8) {
                int botId = scanner.nextInt();
                System.out.println(adventurers[j].getBottleCap(botId));
            } else if (op == 9) {
                int botId = scanner.nextInt();
                System.out.println(adventurers[j].getBottleFill(botId));
            } else if (op == 10) {
                int botId = scanner.nextInt();
                adventurers[j].printInfo(botId);
            } else if (op == 11) {
                System.out.println(adventurers[j].getSum());
            } else if (op == 12) {
                System.out.println(adventurers[j].getMax());
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




