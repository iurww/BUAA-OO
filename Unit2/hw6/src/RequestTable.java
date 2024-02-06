import java.util.ArrayList;
import java.util.HashMap;

public class RequestTable {
    private final HashMap<Integer, ArrayList<Person>> requests;
    private int count;
    private boolean end;
    private boolean maintain;

    public RequestTable() {
        this.requests = new HashMap<>();
        for (int i = 1; i <= 11; i++) {
            requests.put(i, new ArrayList<>());
        }
        this.count = 0;
        this.end = false;
        this.maintain = false;
    }

    public synchronized ArrayList<Person> wantIn(int fromFloor) {
        notifyAll();
        return requests.get(fromFloor);
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized boolean getMaintain() {
        return maintain;
    }

    public synchronized boolean isEnd() {
        return end;
    }

    public synchronized boolean isEmpty() {
        return count == 0;
    }

    public synchronized boolean isMaintain() {
        return maintain;
    }

    public synchronized void setEnd(boolean end) {
        this.end = end;
        notifyAll();
    }

    public synchronized void setMaintain(boolean maintain) {
        this.maintain = maintain;
        notifyAll();
    }

    public synchronized void addRequest(Person person) {
        requests.get(person.getFromFloor()).add(person);
        count++;
        notifyAll();
    }

    public synchronized Person getRequest() {
        for (int i = 1; i <= 11; i++) {
            for (Person person : requests.get(i)) {
                count--;
                requests.get(i).remove(person);
                return person;
            }
        }
        return null;
    }

    public synchronized Person getRequest(int curFloor, boolean dir) {
        for (Person person : requests.get(curFloor)) {
            if (person.getToFloor() > curFloor == dir) {
                count--;
                requests.get(curFloor).remove(person);
                return person;
            }
        }
        return null;
    }

    public synchronized void waitInput() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
