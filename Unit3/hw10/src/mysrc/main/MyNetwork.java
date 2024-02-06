package mysrc.main;

import com.oocourse.spec2.exceptions.EqualPersonIdException;
import com.oocourse.spec2.exceptions.EqualMessageIdException;
import com.oocourse.spec2.exceptions.EqualRelationException;
import com.oocourse.spec2.exceptions.EqualGroupIdException;
import com.oocourse.spec2.exceptions.RelationNotFoundException;
import com.oocourse.spec2.exceptions.PersonIdNotFoundException;
import com.oocourse.spec2.exceptions.GroupIdNotFoundException;
import com.oocourse.spec2.exceptions.AcquaintanceNotFoundException;
import com.oocourse.spec2.exceptions.MessageIdNotFoundException;

import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Message;
import com.oocourse.spec2.main.Network;

import com.oocourse.spec2.main.Person;
import mysrc.exceptions.MyEqualPersonIdException;
import mysrc.exceptions.MyEqualMessageIdException;
import mysrc.exceptions.MyEqualRelationException;
import mysrc.exceptions.MyEqualGroupIdException;
import mysrc.exceptions.MyRelationNotFoundException;
import mysrc.exceptions.MyPersonIdNotFoundException;
import mysrc.exceptions.MyGroupIdNotFoundException;
import mysrc.exceptions.MyAcquaintanceNotFoundException;
import mysrc.exceptions.MyMessageIdNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MyNetwork implements Network {
    private HashMap<Integer, MyPerson> people;
    private HashMap<Integer, Integer> map;
    private int blockCnt;
    private int cntTri;
    private HashMap<Integer, MyGroup> groups;
    private HashMap<Integer, MyMessage> messages;

    public MyNetwork() {
        this.people = new HashMap<>();
        this.map = new HashMap<>();
        this.blockCnt = 0;
        this.cntTri = 0;
        this.groups = new HashMap<>();
        this.messages = new HashMap<>();
    }

    public int find(int k) {
        if (map.get(k) == k) {
            return k;
        }
        int tmp = find(map.get(k));
        map.put(k, tmp);
        return tmp;
    }

    @Override
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

    @Override
    public Person getPerson(int id) {
        return people.get(id);
    }

    @Override
    public void addPerson(Person person) throws MyEqualPersonIdException {
        int id = person.getId();
        if (people.containsKey(id)) {
            throw new MyEqualPersonIdException(id);
        }
        people.put(id, (MyPerson) person);
        map.put(id, id);
        blockCnt++;
    }

    @Override
    public void addRelation(int id1, int id2, int value) throws
            PersonIdNotFoundException, EqualRelationException {
        checkPerson(id1, id2);
        if (people.get(id1).isLinked(people.get(id2))) {
            throw new MyEqualRelationException(id1, id2);
        }
        int p1 = find(id1);
        int p2 = find(id2);
        if (p1 != p2) {
            blockCnt--;
        }
        map.put(p1, p2);
        people.get(id1).addAcquaintance(people.get(id2), value);
        people.get(id2).addAcquaintance(people.get(id1), value);
        this.cntTri += people.get(id1).getDegree() < people.get(id2).getDegree() ?
                people.get(id1).getTri(people.get(id2)) : people.get(id2).getTri(people.get(id1));
        for (Integer groupId : people.get(id1).getGroupId()) {
            if (people.get(id2).getGroupId().contains(groupId)) {
                ((MyGroup) getGroup(groupId)).addValue(2 * value);
            }
        }
    }

    @Override
    public void modifyRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualPersonIdException, RelationNotFoundException {
        checkPerson(id1, id2);
        if (id1 == id2) {
            throw new MyEqualPersonIdException(id1);
        } else if (!people.get(id1).isLinked(people.get(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        MyPerson p1 = people.get(id1);
        MyPerson p2 = people.get(id2);
        if (p1.queryValue(p2) + value > 0) {
            p1.addValue(id2, value);
            p2.addValue(id1, value);
            for (Integer groupId : people.get(id1).getGroupId()) {
                if (people.get(id2).getGroupId().contains(groupId)) {
                    ((MyGroup) getGroup(groupId)).addValue(2 * value);
                }
            }
        } else {
            this.cntTri -= p1.getDegree() < p2.getDegree() ?
                    p1.getTri(p2) : p2.getTri(p1);
            for (Integer groupId : people.get(id1).getGroupId()) {
                if (people.get(id2).getGroupId().contains(groupId)) {
                    ((MyGroup) getGroup(groupId)).addValue(-2 * p1.queryValue(p2));
                }
            }
            p1.deleteAcquaintance(p2);
            p2.deleteAcquaintance(p1);

            int fa = find(id1);
            HashSet<Integer> block = new HashSet<>();
            for (Integer id : map.keySet()) {
                if (find(id) == fa) {
                    block.add(id);
                }
            }

            HashSet<Integer> block1 = new HashSet<>();
            dfs(id1, block1);
            if (!block1.contains(id2)) {
                map.keySet().removeAll(block);
                blockCnt++;
                for (Integer id : block) {
                    if (block1.contains(id)) {
                        map.put(id, id1);
                    } else {
                        map.put(id, id2);
                    }
                }
            }
        }
    }

    public void dfs(int cur, HashSet<Integer> vis) {
        vis.add(cur);
        for (Integer id : people.get(cur).getAcquaintance().keySet()) {
            if (!vis.contains(id)) {
                dfs(id, vis);
            }
        }
    }

    @Override
    public int queryValue(int id1, int id2) throws
            MyPersonIdNotFoundException, MyRelationNotFoundException {
        checkPerson(id1, id2);
        if (people.containsKey(id1) && people.containsKey(id2)
                && !people.get(id1).isLinked(people.get(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        return people.get(id1).queryValue(people.get(id2));
    }

    @Override
    public boolean isCircle(int id1, int id2) throws MyPersonIdNotFoundException {
        checkPerson(id1, id2);
        return find(id1) == find(id2);
    }

    @Override
    public int queryBlockSum() {
        return blockCnt;
    }

    @Override
    public int queryTripleSum() {
        return cntTri;
    }

    @Override
    public void addGroup(Group group) throws EqualGroupIdException {
        if (groups.containsKey(group.getId())) {
            throw new MyEqualGroupIdException(group.getId());
        }
        groups.put(group.getId(), (MyGroup) group);
    }

    @Override
    public Group getGroup(int id) {
        return groups.get(id);
    }

    @Override
    public void addToGroup(int id1, int id2)
            throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if (!groups.containsKey(id2)) {
            throw new MyGroupIdNotFoundException(id2);
        } else if (!people.containsKey(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (getGroup(id2).hasPerson(getPerson(id1))) {
            throw new MyEqualPersonIdException(id1);
        }
        Group group = getGroup(id2);
        if (group.getSize() <= 1111) {
            group.addPerson(getPerson(id1));
        }
    }

    @Override
    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new MyGroupIdNotFoundException(id);
        }
        return getGroup(id).getValueSum();
    }

    @Override
    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new MyGroupIdNotFoundException(id);
        }
        return getGroup(id).getAgeVar();
    }

    @Override
    public void delFromGroup(int id1, int id2)
            throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if (!groups.containsKey(id2)) {
            throw new MyGroupIdNotFoundException(id2);
        } else if (!people.containsKey(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!getGroup(id2).hasPerson(getPerson(id1))) {
            throw new MyEqualPersonIdException(id1);
        }
        getGroup(id2).delPerson(getPerson(id1));
    }

    @Override
    public boolean containsMessage(int id) {
        return messages.containsKey(id);
    }

    @Override
    public void addMessage(Message message)
            throws EqualMessageIdException, EqualPersonIdException {
        if (messages.containsKey(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        } else if (message.getType() == 0 && message.getPerson1() == message.getPerson2()) {
            throw new MyEqualPersonIdException(message.getPerson1().getId());
        }
        messages.put(message.getId(), (MyMessage) message);
    }

    @Override
    public Message getMessage(int id) {
        return messages.get(id);
    }

    @Override
    public void sendMessage(int id)
            throws RelationNotFoundException, MessageIdNotFoundException,
            PersonIdNotFoundException {
        if (!messages.containsKey(id)) {
            throw new MyMessageIdNotFoundException(id);
        } else if (getMessage(id).getType() == 0 &&
                !(getMessage(id).getPerson1().isLinked(getMessage(id).getPerson2()))) {
            throw new MyRelationNotFoundException(getMessage(id).getPerson1().getId(),
                    getMessage(id).getPerson2().getId());
        } else if (getMessage(id).getType() == 1 &&
                !(getMessage(id).getGroup().hasPerson(getMessage(id).getPerson1()))) {
            throw new MyPersonIdNotFoundException(getMessage(id).getPerson1().getId());
        }

        MyMessage message = messages.get(id);
        messages.remove(id);
        if (message.getType() == 0) {
            message.getPerson1().addSocialValue(message.getSocialValue());
            message.getPerson2().addSocialValue(message.getSocialValue());
            ((MyPerson) message.getPerson2()).addMessage(message);
        } else {
            ((MyGroup) message.getGroup()).addSocialValue(message.getSocialValue());
        }
    }

    @Override
    public int querySocialValue(int id) throws PersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        return people.get(id).getSocialValue();
    }

    @Override
    public List<Message> queryReceivedMessages(int id) throws PersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        return people.get(id).getReceivedMessages();
    }

    @Override
    public int queryBestAcquaintance(int id)
            throws PersonIdNotFoundException, AcquaintanceNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else if (people.get(id).getAcquaintance().size() == 0) {
            throw new MyAcquaintanceNotFoundException(id);
        }
        return people.get(id).getBestId();
    }

    @Override
    public int queryCoupleSum() {
        int cnt = 0;
        for (Integer id : people.keySet()) {
            if (people.get(id).getBestId() != 2147483647 &&
                    people.get(people.get(id).getBestId()).getBestId() == id) {
                cnt++;
            }
        }
        return cnt / 2;
    }

    @Override
    public int modifyRelationOKTest(int id1, int id2, int value,
                                    HashMap<Integer, HashMap<Integer, Integer>> beforeData,
                                    HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        boolean flag = false;
        if (!beforeData.containsKey(id1)) {
            flag = true;
        } else if (beforeData.containsKey(id1) && !beforeData.containsKey(id2)) {
            flag = true;
        } else if (id1 == id2) {
            flag = true;
        } else if (!beforeData.get(id1).containsKey(id2)) {
            flag = true;
        }
        if (flag) {
            return beforeData.equals(afterData) ? 0 : -1;
        }
        if (beforeData.get(id1).get(id2) + value > 0) {
            return check1(id1, id2, value, beforeData, afterData);
        } else {
            return check2(id1, id2, value, beforeData, afterData);
        }
    }

    public int check1(int id1, int id2, int value,
                      HashMap<Integer, HashMap<Integer, Integer>> beforeData,
                      HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        if (beforeData.size() != afterData.size()) {
            return 1;
        }
        for (Integer id : beforeData.keySet()) {
            if (!afterData.containsKey(id)) {
                return 2;
            }
        }
        for (Integer id : beforeData.keySet()) {
            if (id != id1 && id != id2 &&
                    !beforeData.get(id).equals(afterData.get(id))) {
                return 3;
            }
        }
        if (!afterData.get(id1).containsKey(id2) || !afterData.get(id2).containsKey(id1)) {
            return 4;
        }
        if (afterData.get(id1).get(id2) != beforeData.get(id1).get(id2) + value) {
            return 5;
        }
        if (afterData.get(id2).get(id1) != beforeData.get(id2).get(id1) + value) {
            return 6;
        }
        if (beforeData.get(id1).keySet().size() != afterData.get(id1).keySet().size()) {
            return 7;
        }
        if (beforeData.get(id2).keySet().size() != afterData.get(id2).keySet().size()) {
            return 8;
        }
        if (!beforeData.get(id1).keySet().equals(afterData.get(id1).keySet())) {
            return 9;
        }
        if (!beforeData.get(id2).keySet().equals(afterData.get(id2).keySet())) {
            return 10;
        }
        for (Integer id : beforeData.get(id1).keySet()) {
            if (id != id2 && !beforeData.get(id1).get(id).equals(afterData.get(id1).get(id))) {
                return 11;
            }
        }
        for (Integer id : beforeData.get(id2).keySet()) {
            if (id != id1 && !beforeData.get(id2).get(id).equals(afterData.get(id2).get(id))) {
                return 12;
            }
        }
        if (beforeData.get(id1).values().size() != afterData.get(id1).values().size()) {
            return 13;
        }
        if (beforeData.get(id2).values().size() != afterData.get(id2).values().size()) {
            return 14;
        }
        return 0;
    }

    public int check2(int id1, int id2, int value,
                      HashMap<Integer, HashMap<Integer, Integer>> beforeData,
                      HashMap<Integer, HashMap<Integer, Integer>> afterData) {
        if (beforeData.size() != afterData.size()) {
            return 1;
        }
        for (Integer id : beforeData.keySet()) {
            if (!afterData.containsKey(id)) {
                return 2;
            }
        }
        for (Integer id : beforeData.keySet()) {
            if (id != id1 && id != id2 &&
                    !beforeData.get(id).equals(afterData.get(id))) {
                return 3;
            }
        }
        if (afterData.get(id1).containsKey(id2) || afterData.get(id2).containsKey(id1)) {
            return 15;
        }
        if (beforeData.get(id1).values().size() != afterData.get(id1).keySet().size() + 1) {
            return 16;
        }
        if (beforeData.get(id2).values().size() != afterData.get(id2).keySet().size() + 1) {
            return 17;
        }
        if (afterData.get(id1).keySet().size() != afterData.get(id1).values().size()) {
            return 18;
        }
        if (afterData.get(id2).keySet().size() != afterData.get(id2).values().size()) {
            return 19;
        }
        for (Integer id : afterData.get(id1).keySet()) {
            if (!beforeData.get(id1).containsKey(id) ||
                    !beforeData.get(id1).get(id).equals(afterData.get(id1).get(id))) {
                return 20;
            }
        }
        for (Integer id : afterData.get(id2).keySet()) {
            if (!beforeData.get(id2).containsKey(id) ||
                    !beforeData.get(id2).get(id).equals(afterData.get(id2).get(id))) {
                return 21;
            }
        }
        return 0;
    }
}
