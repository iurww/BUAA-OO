package mysrc.exceptions;

import com.oocourse.spec2.exceptions.EqualMessageIdException;

import java.util.HashMap;

public class MyEqualMessageIdException extends EqualMessageIdException {

    private static int total8 = 0;
    private static HashMap<Integer, Integer> cnt8 = new HashMap<>();
    private int id;

    public MyEqualMessageIdException(int id) {
        this.total8++;
        if (cnt8.containsKey(id)) {
            cnt8.put(id, cnt8.get(id) + 1);
        } else {
            cnt8.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("emi-%d, %d-%d", this.total8, id, cnt8.get(id)));
    }
}
