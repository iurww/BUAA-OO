import com.oocourse.spec1.main.Person;

import java.util.HashMap;

public class MyPerson implements Person {
    private int id;
    private String name;
    private int age;
    private HashMap<Integer, MyPerson> acquaintance;
    private HashMap<Integer, Integer> value;

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.acquaintance = new HashMap<>();
        this.value = new HashMap<>();
        this.acquaintance.put(id, this);
        this.value.put(id, 0);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public HashMap<Integer, MyPerson> getAcquaintance() {
        return acquaintance;
    }

    public int getDegree() {
        return acquaintance.size();
    }

    public boolean isLinked(Person person) {
        return acquaintance.containsKey(person.getId()) || person.getId() == this.id;
    }

    public int queryValue(Person person) {
        return value.get(person.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MyPerson)) {
            return false;
        }
        MyPerson other = (MyPerson) obj;
        return this.id == other.getId();
    }

    public void addAcquaintance(Person person, int value) {
        this.acquaintance.put(person.getId(), (MyPerson) person);
        this.value.put(person.getId(), value);
    }

    public int getTri(MyPerson person) {
        int res = 0;
        for (MyPerson myPerson : acquaintance.values()) {
            if (myPerson.isLinked(person) && this.id != myPerson.id && myPerson.id != person.id) {
                res++;
            }
        }
        return res;
    }

    public int compareTo(Person person) {
        return name.compareTo(person.getName());
    }
}