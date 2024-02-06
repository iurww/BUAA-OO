package mysrc.exceptions;

import com.oocourse.spec2.exceptions.EqualRelationException;

import java.util.HashMap;

public class MyEqualRelationException extends EqualRelationException {
    private static int total2 = 0;
    private static HashMap<Integer, Integer> cnt2 = new HashMap<>();
    private int id1;
    private int id2;

    public MyEqualRelationException(int id1, int id2) {
        this.total2++;
        if (cnt2.containsKey(id1)) {
            cnt2.put(id1, cnt2.get(id1) + 1);
        } else {
            cnt2.put(id1, 1);
        }
        if (id1 != id2) {
            if (cnt2.containsKey(id2)) {
                cnt2.put(id2, cnt2.get(id2) + 1);
            } else {
                cnt2.put(id2, 1);
            }
        }
        this.id1 = id1;
        this.id2 = id2;
    }

    public void print() {
        if (id1 < id2) {
            System.out.println(String.format("er-%d, %d-%d, %d-%d",
                    this.total2, id1, cnt2.get(id1), id2, cnt2.get(id2)));
        } else {
            System.out.println(String.format("er-%d, %d-%d, %d-%d",
                    this.total2, id2, cnt2.get(id2), id1, cnt2.get(id1)));
        }
    }
}
