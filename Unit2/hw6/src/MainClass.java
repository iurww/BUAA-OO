import com.oocourse.elevator2.TimableOutput;

import java.util.HashMap;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();

        HashMap<Integer, RequestTable> waitQueues = new HashMap<>();
        RequestTable halfwayOut = new RequestTable();

        for (int i = 1; i <= 6; i++) {
            RequestTable requestTable = new RequestTable();
            waitQueues.put(i, requestTable);
            Elevator elevator = new Elevator(i, 1, 6, 0.4, requestTable, halfwayOut);
            Thread elevatorThread = new Thread(elevator);
            elevatorThread.start();
        }

        Thread inputThread = new Thread(new InputHandler(waitQueues, halfwayOut));
        inputThread.start();

    }

}