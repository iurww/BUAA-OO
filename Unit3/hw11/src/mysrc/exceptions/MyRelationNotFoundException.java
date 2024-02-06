package mysrc.exceptions;

import com.oocourse.spec3.exceptions.RelationNotFoundException;

import java.util.HashMap;

public class MyRelationNotFoundException extends RelationNotFoundException {
    private static int total4 = 0;
    private static HashMap<Integer, Integer> cnt4 = new HashMap<>();
    private int id1;
    private int id2;

    public MyRelationNotFoundException(int id1, int id2) {
        this.total4++;
        if (cnt4.containsKey(id1)) {
            cnt4.put(id1, cnt4.get(id1) + 1);
        } else {
            cnt4.put(id1, 1);
        }
        if (id1 != id2) {
            if (cnt4.containsKey(id2)) {
                cnt4.put(id2, cnt4.get(id2) + 1);
            } else {
                cnt4.put(id2, 1);
            }
        }
        this.id1 = id1;
        this.id2 = id2;
    }

    public void print() {
        if (id1 < id2) {
            System.out.println(String.format("rnf-%d, %d-%d, %d-%d",
                    this.total4, id1, cnt4.get(id1), id2, cnt4.get(id2)));
        } else {
            System.out.println(String.format("rnf-%d, %d-%d, %d-%d",
                    this.total4, id2, cnt4.get(id2), id1, cnt4.get(id1)));
        }
    }
}
