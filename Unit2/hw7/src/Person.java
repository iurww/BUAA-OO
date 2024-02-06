import java.util.ArrayList;
import java.util.Arrays;

public class Person {
    private int id;
    private final int start;
    private final int end;

    private int fromFloor;
    private int toFloor;
    private int elevatorId;
    private ArrayList<Integer> path;

    public Person(int id, int start, int end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.fromFloor = start;
        this.toFloor = end;
        this.elevatorId = 0;
    }

    public int getId() {
        return id;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    public void setFromFloor(int fromFloor) {
        this.fromFloor = fromFloor;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
        this.fromFloor = path.get(0);
        this.elevatorId = path.get(1);
        this.toFloor = path.get(2);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{this.id, this.start, this.end});
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Person)) {
            return false;
        } else {
            return ((Person) obj).start == this.start
                    && ((Person) obj).end == this.end && ((Person) obj).id == this.id;
        }
    }

    public String toString() {
        return String.format("%d-FROM-%d-TO-%d-USE-%d",
                this.id, this.fromFloor, this.toFloor, this.elevatorId);
    }
}
