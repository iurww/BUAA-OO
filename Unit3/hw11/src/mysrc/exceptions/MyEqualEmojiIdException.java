package mysrc.exceptions;

import com.oocourse.spec3.exceptions.EqualEmojiIdException;

import java.util.HashMap;

public class MyEqualEmojiIdException extends EqualEmojiIdException {
    private static int total11 = 0;
    private static HashMap<Integer, Integer> cnt11 = new HashMap<>();
    private int id;

    public MyEqualEmojiIdException(int id) {
        this.total11++;
        if (cnt11.containsKey(id)) {
            cnt11.put(id, cnt11.get(id) + 1);
        } else {
            cnt11.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("eei-%d, %d-%d", this.total11, id, cnt11.get(id)));
    }
}
