package mysrc.exceptions;

import com.oocourse.spec3.exceptions.AcquaintanceNotFoundException;

import java.util.HashMap;

public class MyAcquaintanceNotFoundException extends AcquaintanceNotFoundException {

    private static int total7 = 0;
    private static HashMap<Integer, Integer> cnt7 = new HashMap<>();
    private int id;

    public MyAcquaintanceNotFoundException(int id) {
        this.total7++;
        if (cnt7.containsKey(id)) {
            cnt7.put(id, cnt7.get(id) + 1);
        } else {
            cnt7.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("anf-%d, %d-%d", this.total7, id, cnt7.get(id)));
    }
}
