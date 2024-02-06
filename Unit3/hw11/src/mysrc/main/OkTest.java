package mysrc.main;

import java.util.ArrayList;
import java.util.HashMap;

public class OkTest {
    private int limit;
    private ArrayList<HashMap<Integer, Integer>> beforeData;
    private ArrayList<HashMap<Integer, Integer>> afterData;
    private int result;

    public OkTest(int limit, ArrayList<HashMap<Integer, Integer>> beforeData,
                  ArrayList<HashMap<Integer, Integer>> afterData, int result) {
        this.limit = limit;
        this.beforeData = beforeData;
        this.afterData = afterData;
        this.result = result;
    }

    public int test() {
        for (Integer emojiId : beforeData.get(0).keySet()) {
            if (beforeData.get(0).get(emojiId) >= limit &&
                    !afterData.get(0).containsKey(emojiId)) {
                return 1;
            }
        }
        for (Integer emojiId : afterData.get(0).keySet()) {
            if (!beforeData.get(0).containsKey(emojiId) ||
                    !beforeData.get(0).get(emojiId).equals(afterData.get(0).get(emojiId))) {
                return 2;
            }
        }
        if (beforeData.get(0).values().stream()
                .filter(value -> value >= limit)
                .count() != afterData.get(0).keySet().size()) {
            return 3;
        }
        if (afterData.get(0).keySet().size() != afterData.get(0).values().size()) {
            return 4;
        }
        for (Integer messageId : beforeData.get(1).keySet()) {
            if (beforeData.get(1).get(messageId) != null &&
                    afterData.get(0).containsKey(beforeData.get(1).get(messageId))) {
                if (!afterData.get(1).containsKey(messageId) ||
                        !beforeData.get(1).get(messageId).equals(afterData.get(1).get(messageId))) {
                    return 5;
                }
            }
        }
        for (Integer messageId : beforeData.get(1).keySet()) {
            if (beforeData.get(1).get(messageId) == null) {
                if (!afterData.get(1).containsKey(messageId) ||
                        afterData.get(1).get(messageId) != null) {
                    return 6;
                }
            }
        }
        int cnt = 0;
        for (Integer messageId : beforeData.get(1).keySet()) {
            if (beforeData.get(1).get(messageId) != null &&
                    afterData.get(0).containsKey(beforeData.get(1).get(messageId))) {
                cnt++;
            } else if (beforeData.get(1).get(messageId) == null) {
                cnt++;
            }
        }
        if (afterData.get(1).size() != cnt) {
            return 7;
        }
        if (result != afterData.get(0).size()) {
            return 8;
        }
        return 0;
    }
}
