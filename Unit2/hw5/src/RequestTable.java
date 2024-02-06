import java.util.ArrayList;
import java.util.HashMap;

public class RequestTable {
    private HashMap<Integer, ArrayList<Person>> requests;
    private int count;
    private boolean end;

    public RequestTable() {
        this.requests = new HashMap<>();
        for (int i = 1; i <= 11; i++) {
            requests.put(i, new ArrayList<>());
        }
        this.count = 0;
        this.end = false;
    }

    public synchronized ArrayList<Person> wantIn(int fromFloor) {
        notifyAll();
        return requests.get(fromFloor);
    }

    public synchronized int getCount() {
        notifyAll();
        return count;
    }

    public synchronized boolean isEnd() {
        notifyAll();
        return end;
    }

    public synchronized boolean isEmpty() {
        notifyAll();
        return count == 0;
    }

    public synchronized void setEnd(boolean end) {
        notifyAll();
        this.end = end;
    }

    public synchronized void addRequest(Person person) {
        requests.get(person.getFromFloor()).add(person);
        count++;
        notifyAll();
    }

    public synchronized Person getRequest(int curFloor, boolean dir) {
        for (Person person : requests.get(curFloor)) {
            if (person.getToFloor() > curFloor == dir) {
                count--;
                requests.get(curFloor).remove(person);
                notifyAll();
                return person;
            }
        }
        notifyAll();
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
