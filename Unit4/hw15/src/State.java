import java.util.Arrays;

public class State {
    public static void print(String date, String book, int op) {
        int[] p = new int[20];
        Arrays.fill(p, 0);
        p[op] = 1;
        if (p[1] > 0) {
            System.out.printf("(State) [%s] %s transfers from collect to collect\n", date, book);
        } else if (p[2] > 0) {
            System.out.printf("(State) [%s] %s transfers from collect to stu\n", date, book);
        } else if (p[3] > 0) {
            System.out.printf("(State) [%s] %s transfers from stu to collect\n", date, book);
        } else if (p[4] > 0) {
            System.out.printf("(State) [%s] %s transfers from collect to collect\n", date, book);
        } else if (p[5] > 0) {
            System.out.printf("(State) [%s] %s transfers from collect to transport\n", date, book);
        } else if (p[6] > 0) {
            System.out.printf("(State) [%s] %s transfers from transport to collect\n", date, book);
        }
    }
}
