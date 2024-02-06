import com.oocourse.elevator1.PersonRequest;

import java.util.Arrays;

public class Person {
    private int id;
    private int fromFloor;
    private int toFloor;

    public Person(int id, int fromFloor, int toFloor) {
        this.id = id;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
    }

    public int getId() {
        return id;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{this.id, this.fromFloor, this.toFloor});
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof PersonRequest)) {
            return false;
        } else {
            return ((Person) obj).fromFloor == this.fromFloor
                    && ((Person) obj).toFloor == this.toFloor && ((Person) obj).id == this.id;
        }
    }

    public String toString() {
        return String.format("%d-FROM-%d-TO-%d", this.id, this.fromFloor, this.toFloor);
    }
}
