package mysrc.main;

import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Person;

import java.util.HashMap;

public class MyGroup implements Group {
    private int id;
    private HashMap<Integer, MyPerson> people;
    private int valueSum;
    private int ageSum;

    public MyGroup(int id) {
        this.id = id;
        this.people = new HashMap<>();
        this.valueSum = 0;
        this.ageSum = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Group)) {
            return false;
        }
        return this.id == ((Group) obj).getId();
    }

    @Override
    public void addPerson(Person person) {
        people.put(person.getId(), (MyPerson) person);
        for (Integer id : ((MyPerson) person).getAcquaintance().keySet()) {
            if (people.containsKey(id)) {
                valueSum += 2 * person.queryValue(people.get(id));
            }
        }
        ageSum += person.getAge();
        ((MyPerson) person).addGroupId(id);
    }

    @Override
    public boolean hasPerson(Person person) {
        return people.containsKey(person.getId());
    }

    @Override
    public int getValueSum() {
        return valueSum;
        //int res = 0;
        //for(Integer id1 : people.keySet()) {
        //    for (Integer id2 : people.keySet()) {
        //        res += people.get(id1).queryValue(people.get(id2));
        //    }
        //}
        //return res;
    }

    @Override
    public int getAgeMean() {
        if (people.size() == 0) {
            return 0;
        }
        return ageSum / people.size();
    }

    @Override
    public int getAgeVar() {
        if (people.size() == 0) {
            return 0;
        }
        int sum = 0;
        int ageMean = getAgeMean();
        for (MyPerson person : people.values()) {
            sum += (person.getAge() - ageMean) * (person.getAge() - ageMean);
        }
        return sum / people.size();
    }

    @Override
    public void delPerson(Person person) {
        people.remove(person.getId());
        for (Integer id : people.keySet()) {
            if (people.get(id).isLinked(person)) {
                valueSum -= 2 * person.queryValue(people.get(id));
            }
        }
        ageSum -= person.getAge();
        ((MyPerson) person).removeGroupId(id);
    }

    @Override
    public int getSize() {
        return people.size();
    }

    public void addSocialValue(int num) {
        for (MyPerson person : people.values()) {
            person.addSocialValue(num);
            //valueSum += num;
        }
    }

    public void addValue(int num) {
        valueSum += num;
    }
}
