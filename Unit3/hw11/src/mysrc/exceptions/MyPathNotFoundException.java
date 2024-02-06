package mysrc.exceptions;

import com.oocourse.spec3.exceptions.PathNotFoundException;

import java.util.HashMap;

public class MyPathNotFoundException extends PathNotFoundException {
    private static int total12 = 0;
    private static HashMap<Integer, Integer> cnt12 = new HashMap<>();
    private int id;

    public MyPathNotFoundException(int id) {
        this.total12++;
        if (cnt12.containsKey(id)) {
            cnt12.put(id, cnt12.get(id) + 1);
        } else {
            cnt12.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("pnf-%d, %d-%d", this.total12, id, cnt12.get(id)));
    }
}
