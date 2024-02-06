import com.oocourse.elevator2.TimableOutput;

import java.util.HashSet;

import static java.lang.Thread.sleep;

public class Elevator implements Runnable {
    private final int id;
    private final RequestTable requests;
    private final RequestTable halfwayOut;
    private int curFloor;
    private boolean dir;
    private final int maxNum;
    private final int moveTime;
    private final HashSet<Person> inElevator;

    public Elevator(int id, int curFloor, int maxNum, double moveTime,
                    RequestTable requests, RequestTable halfwayOut) {
        this.id = id;
        this.requests = requests;
        this.halfwayOut = halfwayOut;
        this.curFloor = curFloor;
        this.dir = true;
        this.maxNum = maxNum;
        this.moveTime = (int) (moveTime * 1000.0);
        this.inElevator = new HashSet<>();
    }

    @Override
    public void run() {
        while (true) {
            Action action = getAction();
            //System.out.println(action);
            if (action == Action.OVER) {
                return;
            } else if (action == Action.MAINTAIN) {
                maintain();
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
        if (requests.isMaintain()) {
            return Action.MAINTAIN;
        } else if (hasOut() || hasIn()) {
            return Action.OPEN;
        } else if (inElevator.size() != 0) {
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
        synchronized (requests) {
            return requests.wantIn(curFloor).stream()
                    .anyMatch(person -> person.getToFloor() > curFloor == dir)
                    && inElevator.size() < maxNum;
        }
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

    public void waiting(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        waiting(moveTime);
        curFloor = dir ? curFloor + 1 : curFloor - 1;
        TimableOutput.println(String.format("ARRIVE-%d-%d", curFloor, id));
    }

    public void maintain() {
        moveRequests();
        if (inElevator.size() > 0) {
            TimableOutput.println(String.format("OPEN-%d-%d", curFloor, id));
            waiting(200);
            getOut();
            getOutAll();
            waiting(200);
            TimableOutput.println(String.format("CLOSE-%d-%d", curFloor, id));
        }
        TimableOutput.println(String.format("MAINTAIN_ABLE-%d", id));
    }

    public void getOut() {
        inElevator.removeIf(person -> {
            if (person.getToFloor() == curFloor) {
                TimableOutput.println(String.format("OUT-%d-%d-%d", person.getId(), curFloor, id));
            }
            return person.getToFloor() == curFloor;
        });
    }

    public void getOutAll() {
        for (Person person : inElevator) {
            TimableOutput.println(String.format("OUT-%d-%d-%d", person.getId(), curFloor, id));
            person.setFromFloor(curFloor);
            halfwayOut.addRequest(person);
        }
        inElevator.clear();
    }

    public void moveRequests() {
        Person person;
        while ((person = requests.getRequest()) != null) {
            halfwayOut.addRequest(person);
        }
    }

    public void getIn() {
        while (inElevator.size() < maxNum) {
            Person person = requests.getRequest(curFloor, dir);
            if (person != null) {
                inElevator.add(person);
                TimableOutput.println(String.format("IN-%d-%d-%d", person.getId(), curFloor, id));
            } else {
                break;
            }
        }
    }

    public void inAndOut() {
        TimableOutput.println(String.format("OPEN-%d-%d", curFloor, id));
        waiting(200);
        getOut();
        getIn();
        waiting(200);
        TimableOutput.println(String.format("CLOSE-%d-%d", curFloor, id));
    }
}
