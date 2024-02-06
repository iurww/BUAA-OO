import java.util.Arrays;

public class Sequence {
    public static void print(String date, int op) {
        int[] p = new int[20];
        Arrays.fill(p, 0);
        p[op] = 1;
        if (p[1] > 0) {
            System.out.printf("(Sequence) [%s] Student sends a message to Library\n", date);
        } else if (p[2] > 0) {
            System.out.printf("(Sequence) [%s] Library sends a message to Student\n", date);
        }
    }
}
