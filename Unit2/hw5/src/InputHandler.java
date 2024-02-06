import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

import java.util.ArrayList;
import java.util.Random;

public class InputHandler implements Runnable {
    private ArrayList<RequestTable> waitQueues;
    private int id;
    private Random random = new Random();

    public InputHandler(ArrayList<RequestTable> r, int id) {
        this.waitQueues = r;
        this.id = id;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            PersonRequest req = elevatorInput.nextPersonRequest();
            if (req == null) {
                waitQueues.forEach(waitQueue -> waitQueue.setEnd(true));
                //System.out.println("input end");
                break;
            } else {
                int id = req.getPersonId();
                int fromFloor = req.getFromFloor();
                int toFloor = req.getToFloor();
                waitQueues.get(random.nextInt(6)).
                        addRequest(new Person(id, fromFloor, toFloor));
                //System.out.println(waitQueues.get(id % 6).getCount());
            }
        }
    }

}
