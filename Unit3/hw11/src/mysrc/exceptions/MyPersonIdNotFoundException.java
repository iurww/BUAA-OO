package mysrc.exceptions;

import com.oocourse.spec3.exceptions.PersonIdNotFoundException;

import java.util.HashMap;

public class MyPersonIdNotFoundException extends PersonIdNotFoundException {
    private static int total3 = 0;
    private static HashMap<Integer, Integer> cnt3 = new HashMap<>();
    private int id;

    public MyPersonIdNotFoundException(int id) {
        this.total3++;
        if (cnt3.containsKey(id)) {
            cnt3.put(id, cnt3.get(id) + 1);
        } else {
            cnt3.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("pinf-%d, %d-%d", this.total3, id, cnt3.get(id)));
    }

}
