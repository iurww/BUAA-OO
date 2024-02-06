package mysrc.main;

import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyPerson implements Person {
    private int id;
    private String name;
    private int age;
    private HashMap<Integer, MyPerson> acquaintance;
    private HashMap<Integer, Integer> value;
    private int socialValue;
    private ArrayList<Message> messages;
    private int maxValue;
    private int bestId;
    private HashSet<Integer> groupId;
    private int money;

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.acquaintance = new HashMap<>();
        this.value = new HashMap<>();
        this.socialValue = 0;
        this.messages = new ArrayList<>();
        this.maxValue = -100000000;
        this.bestId = 2147483647;
        this.groupId = new HashSet<>();
        this.money = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public HashSet<Integer> getGroupId() {
        return groupId;
    }

    public void addGroupId(int groupId) {
        this.groupId.add(groupId);
    }

    public void removeGroupId(int groupId) {
        this.groupId.remove(groupId);
    }

    public void update(int newValue, int id) {
        if (newValue > maxValue || (newValue == maxValue && id < bestId)) {
            maxValue = newValue;
            bestId = id;
        }
    }

    public HashMap<Integer, MyPerson> getAcquaintance() {
        return acquaintance;
    }

    public int getDegree() {
        return acquaintance.size();
    }

    @Override
    public boolean isLinked(Person person) {
        return acquaintance.containsKey(person.getId()) || person.getId() == this.id;
    }

    public void addValue(int id, int num) {
        value.put(id, value.get(id) + num);
        update(this.value.get(id), id);
        if (num < 0 && id == bestId) {
            this.maxValue = -100000000;
            this.bestId = 2147483647;
            for (Integer id0 : value.keySet()) {
                update(value.get(id0), id0);
            }
        }
    }

    @Override
    public int queryValue(Person person) {
        if (!value.containsKey(person.getId())) {
            return 0;
        }
        return person.getId() == id ? 0 : value.get(person.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MyPerson)) {
            return false;
        }
        return this.id == ((MyPerson) obj).getId();
    }

    @Override
    public void addSocialValue(int num) {
        socialValue += num;
    }

    @Override
    public int getSocialValue() {
        return socialValue;
    }

    public void addAcquaintance(Person person, int value) {
        this.acquaintance.put(person.getId(), (MyPerson) person);
        this.value.put(person.getId(), value);
        update(value, person.getId());
    }

    public void deleteAcquaintance(Person person) {
        this.acquaintance.remove(person.getId());
        this.value.remove(person.getId());
        this.maxValue = -100000000;
        this.bestId = 2147483647;
        for (Integer id : value.keySet()) {
            update(value.get(id), id);
        }
    }

    public int getBestId() {
        return bestId;
    }

    @Override
    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public ArrayList<Message> getReceivedMessages() {
        ArrayList<Message> ans = new ArrayList<>();
        for (int i = 0; i < messages.size() && i < 5; i++) {
            ans.add(messages.get(i));
        }
        return ans;
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

    public void addMessage(MyMessage message) {
        messages.add(0, message);
    }

    @Override
    public int getMoney() {
        return money;
    }

    public void addMoney(int num) {
        money += num;
    }
}