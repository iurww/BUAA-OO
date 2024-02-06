package mysrc.exceptions;

import com.oocourse.spec3.exceptions.GroupIdNotFoundException;

import java.util.HashMap;

public class MyGroupIdNotFoundException extends GroupIdNotFoundException {

    private static int total5 = 0;
    private static HashMap<Integer, Integer> cnt5 = new HashMap<>();
    private int id;

    public MyGroupIdNotFoundException(int id) {
        this.total5++;
        if (cnt5.containsKey(id)) {
            cnt5.put(id, cnt5.get(id) + 1);
        } else {
            cnt5.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("ginf-%d, %d-%d", this.total5, id, cnt5.get(id)));
    }
}
