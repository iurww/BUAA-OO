import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.ElevatorRequest;
import com.oocourse.elevator2.MaintainRequest;
import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Thread.sleep;

public class InputHandler implements Runnable {
    private final HashMap<Integer, RequestTable> waitQueues;
    private final RequestTable halfwayOut;
    private final Random random = new Random();
    private final HashMap<Integer, RequestTable> maintainQueues;

    public InputHandler(HashMap<Integer, RequestTable> r, RequestTable halfwayOut) {
        this.waitQueues = r;
        this.halfwayOut = halfwayOut;
        this.maintainQueues = new HashMap<>();
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            rejoin();
            Request req = elevatorInput.nextRequest();
            if (req == null) {
                maintainQueues.values().forEach(waitQueue -> waitQueue.setEnd(true));
                try {
                    sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rejoin();
                waitQueues.values().forEach(waitQueue -> waitQueue.setEnd(true));
                break;
            } else {
                if (req instanceof PersonRequest) {
                    handlePersonRequest((PersonRequest) req);
                } else if (req instanceof ElevatorRequest) {
                    handleElevatorRequest((ElevatorRequest) req);
                } else if (req instanceof MaintainRequest) {
                    handleMaintainRequest((MaintainRequest) req);
                }
            }
        }
    }

    public RequestTable getQueue() {
        if (waitQueues.size() == 0) {
            return halfwayOut;
        }
        ArrayList<Integer> elevatorId = new ArrayList<>(waitQueues.keySet());
        int id = elevatorId.get(random.nextInt(elevatorId.size()));
        return waitQueues.get(id);
    }

    public void rejoin() {
        if (waitQueues.size() > 0) {
            Person person;
            while ((person = halfwayOut.getRequest()) != null) {
                getQueue().addRequest(person);
            }
        }
    }

    public void handlePersonRequest(PersonRequest personReq) {
        int id = personReq.getPersonId();
        int fromFloor = personReq.getFromFloor();
        int toFloor = personReq.getToFloor();
        getQueue().addRequest(new Person(id, fromFloor, toFloor));
    }

    public void handleElevatorRequest(ElevatorRequest elevatorReq) {
        int id = elevatorReq.getElevatorId();
        int curFloor = elevatorReq.getFloor();
        int maxNum = elevatorReq.getCapacity();
        double moveTime = elevatorReq.getSpeed();
        RequestTable requestTable = new RequestTable();
        waitQueues.put(id, requestTable);
        Elevator elevator = new Elevator(id, curFloor, maxNum, moveTime,
                requestTable, halfwayOut);
        Thread elevatorThread = new Thread(elevator);
        elevatorThread.start();
    }

    public void handleMaintainRequest(MaintainRequest maintainReq) {
        int id = maintainReq.getElevatorId();
        RequestTable del = waitQueues.get(id);
        waitQueues.remove(id);
        maintainQueues.put(id, del);
        del.setMaintain(true);
    }

}
