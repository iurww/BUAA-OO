import com.oocourse.elevator3.TimableOutput;

import java.util.HashSet;
import java.util.Iterator;

import static java.lang.Thread.sleep;

public class Elevator implements Runnable {
    private final int id;
    private final RequestTable halfwayOut;
    private final ArrivalTable arrivalTable;
    private final RequestCounter requestCounter;
    private int curFloor;
    private boolean dir;
    private final int maxNum;
    private final int moveTime;
    private final int accessible;
    private final HashSet<Person> inElevator;

    public Elevator(int id, int curFloor, int maxNum, double moveTime, int accessible,
                    RequestTable h, ArrivalTable a, RequestCounter rc) {
        this.id = id;
        this.halfwayOut = h;
        this.arrivalTable = a;
        this.requestCounter = rc;
        this.curFloor = curFloor;
        this.dir = true;
        this.maxNum = maxNum;
        this.moveTime = (int) (moveTime * 1000.0);
        this.accessible = accessible;
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
                synchronized (halfwayOut) {
                    Action temp = getAction();
                    if (temp == Action.MAINTAIN) {
                        maintain();
                        return;
                    } else if (temp == Action.OVER) {
                        return;
                    }
                    halfwayOut.waitInput();
                }
            }
        }
    }

    public Action getAction() {
        if (halfwayOut.isMaintain(id)) {
            return Action.MAINTAIN;
        } else if (hasOut() || hasIn()) {
            return Action.OPEN;
        } else if (inElevator.size() != 0) {
            return Action.MOVE;
        } else {
            if (halfwayOut.isEmpty(id)) {
                return halfwayOut.isEnd(id) ? Action.OVER : Action.WAIT;
            } else {
                return hasReqInCurDir() ? Action.MOVE : Action.REVERSE;
            }
        }
    }

    public boolean hasIn() {
        return halfwayOut.hasIn(curFloor, dir, id) && inElevator.size() < maxNum;
    }

    public boolean hasOut() {
        return inElevator.stream()
                .anyMatch(person -> person.getToFloor() == curFloor);
    }

    public boolean hasReqInCurDir() {
        return halfwayOut.hasReqInCurDir(curFloor, dir, id);
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
        synchronized (arrivalTable) {
            while (!arrivalTable.serviceable(curFloor)) {
                arrivalTable.waitFinish();
            }
            arrivalTable.serveAt(curFloor, id);
        }
        halfwayOut.getNewPath(id);
        if (inElevator.size() > 0) {
            TimableOutput.println(String.format("OPEN-%d-%d", curFloor, id));
            waiting(200);
            getOut();
            getOutAll();
            waiting(200);
            TimableOutput.println(String.format("CLOSE-%d-%d", curFloor, id));
        }
        TimableOutput.println(String.format("MAINTAIN_ABLE-%d", id));
        arrivalTable.serveFinish(curFloor, id);
    }

    public void getOut() {
        HashSet<Person> out = new HashSet<>();
        Iterator<Person> it = inElevator.iterator();
        while (it.hasNext()) {
            Person person = it.next();
            if (person.getToFloor() == curFloor) {
                it.remove();
                TimableOutput.println(String.format("OUT-%d-%d-%d", person.getId(), curFloor, id));
                if (person.getEnd() != curFloor) {
                    out.add(person);
                } else {
                    requestCounter.release();
                }
            }
        }
        for (Person person : out) {
            person.setFromFloor(curFloor);
            halfwayOut.addRequest(person);
        }
    }

    public void getOutAll() {
        for (Person person : inElevator) {
            TimableOutput.println(String.format("OUT-%d-%d-%d", person.getId(), curFloor, id));
            person.setFromFloor(curFloor);
            halfwayOut.addRequest(person);
        }
        inElevator.clear();
    }

    public void getIn() {
        while (inElevator.size() < maxNum) {
            Person person = halfwayOut.getRequest(curFloor, dir, id);
            if (person != null) {
                inElevator.add(person);
                TimableOutput.println(String.format("IN-%d-%d-%d", person.getId(), curFloor, id));
            } else {
                break;
            }
        }
    }

    public void inAndOut() {
        boolean out = hasOut();
        synchronized (arrivalTable) {
            while (!arrivalTable.serviceable(curFloor) ||
                    (!out && !arrivalTable.enterable(curFloor))) {
                arrivalTable.waitFinish();
                out = hasOut();
            }
            arrivalTable.serveAt(curFloor, id);
            if (!out) {
                arrivalTable.enterOnlyAt(curFloor, id);
            }
        }
        TimableOutput.println(String.format("OPEN-%d-%d", curFloor, id));
        waiting(200);
        getOut();
        getIn();
        waiting(200);
        TimableOutput.println(String.format("CLOSE-%d-%d", curFloor, id));
        arrivalTable.serveFinish(curFloor, id);
        if (!out) {
            arrivalTable.enterFinish(curFloor, id);
        }
    }
}
