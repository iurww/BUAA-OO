import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Graph {
    private HashSet<Integer>[][] graph = new HashSet[12][12];
    private final ArrayList<Integer> order;

    public Graph() {
        for (int i = 1; i <= 11; i++) {
            for (int j = 1; j <= 11; j++) {
                graph[i][j] = new HashSet<>();
                if (i != j) {
                    for (int k = 1; k <= 6; k++) {
                        graph[i][j].add(k);
                    }
                }
            }
        }
        order = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            order.add(i);
        }
    }

    public synchronized void add(int id, int accessible) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i != j && (accessible >> i & 1) > 0 && (accessible >> j & 1) > 0) {
                    graph[i + 1][j + 1].add(id);
                }
            }
        }
    }

    public synchronized void delete(int id) {
        for (int i = 1; i <= 11; i++) {
            for (int j = 1; j <= 11; j++) {
                graph[i][j].remove(id);
            }
        }
    }

    public synchronized ArrayList<Integer> getWay(int fromFloor, int toFloor) {
        boolean[] vis = new boolean[12];
        int[] pre = new int[12];
        int[] id = new int[12];
        ArrayList<Integer> q = new ArrayList<>();
        Arrays.fill(pre, -1);
        vis[fromFloor] = true;
        q.add(fromFloor);
        while (!q.isEmpty()) {
            int cur = q.get(0);
            q.remove(0);
            if (cur == toFloor) {
                break;
            }
            Collections.shuffle(order);
            for (int j : order) {
                HashSet<Integer> ids = graph[cur][j];
                if (ids.size() > 0 && !vis[j]) {
                    q.add(j);
                    vis[j] = true;
                    Integer[] array = ids.toArray(new Integer[ids.size()]);
                    int randomId = array[(int) (Math.random() * ids.size())];
                    pre[j] = cur;
                    id[j] = randomId;
                }
            }
        }
        int t = toFloor;
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(toFloor);
        while (t != fromFloor) {
            ans.add(id[t]);
            ans.add(pre[t]);
            t = pre[t];
        }
        Collections.reverse(ans);
        return ans;
    }
}
