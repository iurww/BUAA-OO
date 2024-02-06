import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainClass {

    private static HashMap<String, ArrayList<Adventurer>> version
            = new HashMap<String, ArrayList<Adventurer>>();
    private static String curName = "1";

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        ArrayList<Adventurer> adv = new ArrayList<>();
        version.put(curName, adv);

        int n = cin.nextInt();
        for (int i = 1; i <= n; i++) {
            int op = cin.nextInt();
            int advId = 0;
            int j = 0;
            if (op <= 10) {
                advId = cin.nextInt();
                j = findAdv(advId);
            }

            if (op == 1) {
                String name = cin.next();
                Adventurer p = new Adventurer(advId, name);
                version.get(curName).add(p);
            } else if (op == 2) {
                int type = cin.nextInt();
                int id = cin.nextInt();
                String name = cin.next();
                long price = cin.nextLong();
                double basic = cin.nextDouble();
                if (type != 1 && type != 4) {
                    double extra = cin.nextDouble();
                    version.get(curName).get(j).addEquipment(type, id, name, price, basic, extra);
                } else {
                    version.get(curName).get(j).addEquipment(type, id, name, price, basic);
                }
            } else if (op == 3) {
                int id = cin.nextInt();
                version.get(curName).get(j).delete(id);
            } else if (op == 4) {
                System.out.println(version.get(curName).get(j).getSum());
            } else if (op == 5) {
                System.out.println(version.get(curName).get(j).getMax());
            } else if (op == 6) {
                System.out.println(version.get(curName).get(j).getCnt());
            } else if (op == 7) {
                int id = cin.nextInt();
                System.out.println(version.get(curName).get(j).getCommodityInfo(id));
            } else if (op == 8) {
                version.get(curName).get(j).useAll();
            } else if (op == 9) {
                System.out.println(version.get(curName).get(j).toString());
            } else if (op == 10) {
                int hire = cin.nextInt();
                int hireId = findAdv(hire);
                version.get(curName).get(j).addPerson(version.get(curName).get(hireId));
            } else if (op == 11) {
                String branchName = cin.next();
                MainClass.newBranch(branchName);
            } else if (op == 12) {
                String branchName = cin.next();
                curName = branchName;
            }
        }
    }

    public static int findAdv(int id) {
        for (int i = 0; i < version.get(curName).size(); i++) {
            if (version.get(curName).get(i).getId() == id) {
                return i;
            }
        }
        return 2001;
    }

    public static void newBranch(String branchName) {
        HashMap<String, Adventurer> isCloned = new HashMap<String, Adventurer>();
        ArrayList<Adventurer> copy = new ArrayList<>();
        for (Adventurer t : version.get(curName)) {
            if (!isCloned.containsKey(t.getName())) {
                Adventurer copyAdv = t.deepClone(isCloned);
                copy.add(copyAdv);
                isCloned.put(copyAdv.getName(), copyAdv);
            } else {
                copy.add(isCloned.get(t.getName()));
            }
        }
        version.put(branchName, copy);
        curName = branchName;
    }

}




