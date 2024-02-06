import java.util.HashMap;
import java.util.HashSet;

public class ArrivalTable {
    private final HashMap<Integer, HashSet<Integer>> serving;
    private final HashMap<Integer, HashSet<Integer>> entering;

    public ArrivalTable() {
        serving = new HashMap<>();
        entering = new HashMap<>();
        for (int i = 1; i <= 11; i++) {
            serving.put(i, new HashSet<>());
            entering.put(i, new HashSet<>());
        }
    }

    public synchronized void serveAt(int curFloor, int id) {
        serving.get(curFloor).add(id);
        notifyAll();
    }

    public synchronized void enterOnlyAt(int curFloor, int id) {
        entering.get(curFloor).add(id);
        notifyAll();
    }

    public synchronized void serveFinish(int curFloor, int id) {
        serving.get(curFloor).remove(id);
        notifyAll();
    }

    public synchronized void enterFinish(int curFloor, int id) {
        entering.get(curFloor).remove(id);
        notifyAll();
    }

    public synchronized boolean serviceable(int curFloor) {
        return serving.get(curFloor).size() < 4;
    }

    public synchronized boolean enterable(int curFloor) {
        return entering.get(curFloor).size() < 2;
    }

    public synchronized void waitFinish() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
