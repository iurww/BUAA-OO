import com.oocourse.spec1.main.Network;
import com.oocourse.spec1.main.Person;

import java.util.ArrayList;
import java.util.HashMap;

public class MyNetwork implements Network {
    private HashMap<Integer, MyPerson> people;
    private HashMap<Integer, Integer> map;
    private HashMap<Integer, Integer> cnt;
    private int cntTri;

    public MyNetwork() {
        this.people = new HashMap<>();
        this.map = new HashMap<>();
        this.cnt = new HashMap<>();
        this.cntTri = 0;
    }

    public int find(int k) {
        if (map.get(k) == k) {
            return k;
        }
        int tmp = find(map.get(k));
        map.put(k, tmp);
        return tmp;
    }

    public boolean contains(int id) {
        return people.containsKey(id);
    }

    public void checkPerson(int id1, int id2) throws MyPersonIdNotFoundException {
        if (!people.containsKey(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (people.containsKey(id1) && !people.containsKey(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        }
    }

    public Person getPerson(int id) {
        return people.get(id);
    }

    public void addPerson(Person person) throws MyEqualPersonIdException {
        int id = person.getId();
        if (people.containsKey(id)) {
            throw new MyEqualPersonIdException(id);
        }
        people.put(id, (MyPerson) person);
        map.put(id, id);
        cnt.put(id, 1);
    }

    public void addRelation(int id1, int id2, int value) throws
            MyPersonIdNotFoundException, MyEqualRelationException {
        checkPerson(id1, id2);
        if (people.containsKey(id1) && people.containsKey(id2)
                && people.get(id1).isLinked(people.get(id2))) {
            throw new MyEqualRelationException(id1, id2);
        }
        int p1 = find(id1);
        int p2 = find(id2);
        if (p1 != p2) {
            cnt.put(p2, cnt.get(p1) + cnt.get(p2));
            cnt.remove(p1);
        }
        map.put(p1, p2);
        people.get(id1).addAcquaintance(people.get(id2), value);
        people.get(id2).addAcquaintance(people.get(id1), value);
        this.cntTri += people.get(id1).getDegree() < people.get(id2).getDegree() ?
            people.get(id1).getTri(people.get(id2)) : people.get(id2).getTri(people.get(id1));
    }

    public int queryValue(int id1, int id2) throws
            MyPersonIdNotFoundException, MyRelationNotFoundException {
        checkPerson(id1, id2);
        if (people.containsKey(id1) && people.containsKey(id2)
                && !people.get(id1).isLinked(people.get(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        return people.get(id1).queryValue(people.get(id2));
    }

    public boolean isCircle(int id1, int id2) throws MyPersonIdNotFoundException {
        checkPerson(id1, id2);
        return find(id1) == find(id2);
    }

    public int queryBlockSum() {
        return cnt.size();
    }

    public int queryTripleSum() {
        return cntTri;
    }

    public boolean queryTripleSumOKTest(HashMap<Integer, HashMap<Integer, Integer>> beforeData,
                           HashMap<Integer, HashMap<Integer, Integer>> afterData, int result) {
        if (!beforeData.equals(afterData)) {
            return false;
        }
        int res = 0;
        ArrayList<Integer> persons = new ArrayList<>(beforeData.keySet());
        for (int i = 0; i < persons.size(); i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                int x = persons.get(i);
                int y = persons.get(j);
                if (beforeData.get(x).containsKey(y)) {
                    for (int k = j + 1; k < persons.size(); k++) {
                        int z = persons.get(k);
                        if (beforeData.get(y).containsKey(z) && beforeData.get(z).containsKey(x)) {
                            res++;
                        }
                    }
                }
            }
        }
        return res == result;
    }

}
