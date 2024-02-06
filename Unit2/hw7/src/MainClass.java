import com.oocourse.elevator3.TimableOutput;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();

        Graph graph = new Graph();
        RequestTable halfwayOut = new RequestTable(0x3fffffff, graph);
        ArrivalTable arrivalTable = new ArrivalTable();
        RequestCounter requestCounter = new RequestCounter();

        for (int i = 1; i <= 6; i++) {
            Elevator elevator = new Elevator(i, 1, 6, 0.4, 2047,
                    halfwayOut, arrivalTable, requestCounter);
            Thread elevatorThread = new Thread(elevator);
            elevatorThread.start();
        }

        Thread inputThread = new Thread(new InputHandler(halfwayOut, arrivalTable, requestCounter));
        inputThread.start();

    }
}