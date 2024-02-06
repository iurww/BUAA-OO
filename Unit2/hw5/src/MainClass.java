import com.oocourse.elevator1.TimableOutput;

import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();

        ArrayList<RequestTable> waitQueues = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            RequestTable requestTable = new RequestTable();
            waitQueues.add(requestTable);
            Elevator elevator = new Elevator(i, requestTable);
            Thread elevatorThread = new Thread(elevator);
            elevatorThread.start();
        }

        Thread inputThread = new Thread(new InputHandler(waitQueues, 1));
        inputThread.start();

    }

}