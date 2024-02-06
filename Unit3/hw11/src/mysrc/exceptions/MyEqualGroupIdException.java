package mysrc.exceptions;

import com.oocourse.spec3.exceptions.EqualGroupIdException;

import java.util.HashMap;

public class MyEqualGroupIdException extends EqualGroupIdException {

    private static int total6 = 0;
    private static HashMap<Integer, Integer> cnt6 = new HashMap<>();
    private int id;

    public MyEqualGroupIdException(int id) {
        this.total6++;
        if (cnt6.containsKey(id)) {
            cnt6.put(id, cnt6.get(id) + 1);
        } else {
            cnt6.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("egi-%d, %d-%d", this.total6, id, cnt6.get(id)));
    }
}
