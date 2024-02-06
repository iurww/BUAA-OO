import com.oocourse.elevator1.TimableOutput;

import java.util.HashSet;

import static java.lang.Thread.sleep;

public class Elevator implements Runnable {
    private int id;
    private RequestTable requests;
    private int curFloor;
    private boolean dir;
    private HashSet<Person> inElevator;

    public Elevator(int id, RequestTable requests) {
        this.id = id;
        this.requests = requests;
        this.curFloor = 1;
        this.dir = true;
        this.inElevator = new HashSet<>();
    }

    @Override
    public void run() {
        while (true) {
            Action action = getAction();
            //System.out.println(action);
            if (action == Action.OVER) {
                return;
            } else if (action == Action.MOVE) {
                move();
            } else if (action == Action.REVERSE) {
                dir = !dir;
            } else if (action == Action.OPEN) {
                inAndOut();
            } else {
                requests.waitInput();
            }
        }
    }

    public Action getAction() {
        if (hasOut() || hasIn()) {
            return Action.OPEN;
        }
        if (inElevator.size() != 0) {
            return Action.MOVE;
        } else {
            if (requests.isEmpty()) {
                return requests.isEnd() ? Action.OVER : Action.WAIT;
            } else {
                return hasReqInCurDir() ? Action.MOVE : Action.REVERSE;
            }
        }
    }

    public boolean hasIn() {
        return requests.wantIn(curFloor).stream()
                .anyMatch(person -> person.getToFloor() > curFloor == dir)
                && inElevator.size() < 6;
    }

    public boolean hasOut() {
        return inElevator.stream()
                .anyMatch(person -> person.getToFloor() == curFloor);
    }

    public boolean hasReqInCurDir() {
        int f = dir ? 1 : -1;
        for (int i = curFloor + f; i <= 11 && i >= 1; i += f) {
            if (requests.wantIn(i).size() > 0) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        try {
            sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        curFloor = dir ? curFloor + 1 : curFloor - 1;
        TimableOutput.println(String.format("ARRIVE-%d-%d", curFloor, id));
    }

    public void inAndOut() {
        TimableOutput.println(String.format("OPEN-%d-%d", curFloor, id));
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inElevator.removeIf(person -> {
            if (person.getToFloor() == curFloor) {
                TimableOutput.println(String.format("OUT-%d-%d-%d", person.getId(), curFloor, id));
            }
            return person.getToFloor() == curFloor;
        });
        while (inElevator.size() < 6) {
            Person person = requests.getRequest(curFloor, dir);
            if (person != null) {
                inElevator.add(person);
                TimableOutput.println(String.format("IN-%d-%d-%d", person.getId(), curFloor, id));
            } else {
                break;
            }
        }

        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println(String.format("CLOSE-%d-%d", curFloor, id));
    }
}
