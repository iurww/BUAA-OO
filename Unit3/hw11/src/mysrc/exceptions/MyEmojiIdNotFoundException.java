package mysrc.exceptions;

import com.oocourse.spec3.exceptions.EmojiIdNotFoundException;

import java.util.HashMap;

public class MyEmojiIdNotFoundException extends EmojiIdNotFoundException {
    private static int total10 = 0;
    private static HashMap<Integer, Integer> cnt10 = new HashMap<>();
    private int id;

    public MyEmojiIdNotFoundException(int id) {
        this.total10++;
        if (cnt10.containsKey(id)) {
            cnt10.put(id, cnt10.get(id) + 1);
        } else {
            cnt10.put(id, 1);
        }
        this.id = id;
    }

    public void print() {
        System.out.println(String.format("einf-%d, %d-%d", this.total10, id, cnt10.get(id)));
    }
}
