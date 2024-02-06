import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.MaintainRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.util.HashSet;

public class InputHandler implements Runnable {
    private final RequestTable halfwayOut;
    private final ArrivalTable arrivalTable;
    private final RequestCounter requestCounter;
    private final HashSet<Integer> maintain;
    private final HashSet<Integer> running;
    private int count;

    public InputHandler(RequestTable h, ArrivalTable a, RequestCounter rc) {
        this.halfwayOut = h;
        this.arrivalTable = a;
        this.requestCounter = rc;
        this.maintain = new HashSet<>();
        this.running = new HashSet<>();
        for (int i = 1; i <= 6; i++) {
            running.add(i);
        }
        this.count = 0;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            Request req = elevatorInput.nextRequest();
            if (req == null) {
                for (int i = 1; i <= count; i++) {
                    requestCounter.acquire();
                }
                running.forEach(id -> halfwayOut.setEnd(id));
                return;
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

    public void handlePersonRequest(PersonRequest personReq) {
        int id = personReq.getPersonId();
        int start = personReq.getFromFloor();
        int end = personReq.getToFloor();
        Person person = new Person(id, start, end);
        halfwayOut.addRequest(person);
        count++;
    }

    public void handleElevatorRequest(ElevatorRequest elevatorReq) {
        int id = elevatorReq.getElevatorId();
        int curFloor = elevatorReq.getFloor();
        int maxNum = elevatorReq.getCapacity();
        double moveTime = elevatorReq.getSpeed();
        int accessible = elevatorReq.getAccess();
        halfwayOut.addGraph(id, accessible);
        Elevator elevator = new Elevator(id, curFloor, maxNum, moveTime, accessible,
                halfwayOut, arrivalTable, requestCounter);
        Thread elevatorThread = new Thread(elevator);
        elevatorThread.start();
        running.add(id);
    }

    public void handleMaintainRequest(MaintainRequest maintainReq) {
        int id = maintainReq.getElevatorId();
        halfwayOut.deleteGraph(id);
        halfwayOut.setMaintain(id);
        maintain.add(id);
    }

}
