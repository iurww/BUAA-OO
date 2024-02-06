package mysrc.exceptions;

import com.oocourse.spec2.exceptions.MessageIdNotFoundException;

import java.util.HashMap;

public class MyMessageIdNotFoundException extends MessageIdNotFoundException {

    private static int total9 = 0;
    private static HashMap<Integer, Integer> cnt9 = new HashMap<>();
    private int id;

    public MyMessageIdNotFoundException(int id) {
        this.total9++;
        if (cnt9.containsKey(id)) {
            cnt9.put(id, cnt9.get(id) + 1);
        } else {
            cnt9.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("minf-%d, %d-%d", this.total9, id, cnt9.get(id)));
    }
}
