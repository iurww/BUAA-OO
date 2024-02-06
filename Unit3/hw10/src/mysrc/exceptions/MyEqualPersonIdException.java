package mysrc.exceptions;

import com.oocourse.spec2.exceptions.EqualPersonIdException;

import java.util.HashMap;

public class MyEqualPersonIdException extends EqualPersonIdException {
    private static int total1 = 0;
    private static HashMap<Integer, Integer> cnt1 = new HashMap<>();
    private int id;

    public MyEqualPersonIdException(int id) {
        this.total1++;
        if (cnt1.containsKey(id)) {
            cnt1.put(id, cnt1.get(id) + 1);
        } else {
            cnt1.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("epi-%d, %d-%d", this.total1, id, cnt1.get(id)));
    }
}
