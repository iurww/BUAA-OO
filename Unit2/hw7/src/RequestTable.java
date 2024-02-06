import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RequestTable {
    private final HashMap<Integer, ArrayList<Person>> requests;
    private final Graph graph;
    private HashSet<Integer> end;
    private HashSet<Integer> maintain;
    private int accessible;

    public RequestTable(int accessible, Graph g) {
        this.requests = new HashMap<>();
        for (int i = 1; i <= 11; i++) {
            requests.put(i, new ArrayList<>());
        }
        this.graph = g;
        this.end = new HashSet<>();
        this.maintain = new HashSet<>();
        this.accessible = accessible;
    }

    public void addGraph(int id, int accessible) {
        graph.add(id, accessible);
    }

    public void deleteGraph(int id) {
        graph.delete(id);
    }

    public synchronized boolean hasIn(int curFloor, boolean dir, int id) {
        return requests.get(curFloor).stream()
                .anyMatch(person -> person.getToFloor() > curFloor == dir &&
                        person.getElevatorId() == id);
    }

    public synchronized boolean hasReqInCurDir(int curFloor, boolean dir, int id) {
        int f = dir ? 1 : -1;
        for (int i = curFloor + f; i <= 11 && i >= 1; i += f) {
            for (Person person : requests.get(i)) {
                if (person.getElevatorId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void getNewPath(int id) {
        for (int i = 1; i <= 11; i++) {
            for (Person person : requests.get(i)) {
                if (person.getElevatorId() == id) {
                    person.setPath(graph.getWay(person.getFromFloor(), person.getEnd()));
                }
            }
        }
    }

    public boolean canArrive(int toFloor) {
        return (accessible & (1 << (toFloor - 1))) != 0;
    }

    public synchronized boolean isEnd(int id) {
        return end.contains(id);
    }

    public synchronized boolean isEmpty(int id) {
        for (int i = 1; i <= 11; i++) {
            for (Person person : requests.get(i)) {
                if (person.getElevatorId() == id) {
                    return false;
                }
            }
        }
        return true;
    }

    public synchronized boolean isMaintain(int id) {
        return maintain.contains(id);
    }

    public synchronized void setEnd(int id) {
        notifyAll();
        end.add(id);
    }

    public synchronized void setMaintain(int id) {
        notifyAll();
        maintain.add(id);
    }

    public synchronized void addRequest(Person person) {
        notifyAll();
        ArrayList<Integer> path = graph.getWay(person.getFromFloor(), person.getEnd());
        person.setPath(path);
        requests.get(person.getFromFloor()).add(person);
    }

    public synchronized Person getRequest(int curFloor, boolean dir, int id) {
        notifyAll();
        for (Person person : requests.get(curFloor)) {
            if (person.getToFloor() > curFloor == dir && person.getElevatorId() == id) {
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

    @Override
    public synchronized String toString() {
        String s = "";
        for (int i = 1; i <= 11; i++) {
            for (Person person : requests.get(i)) {
                s += person.toString() + " ";
            }
        }
        return s;
    }

}
