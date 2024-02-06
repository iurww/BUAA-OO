package mysrc.main;

import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualMessageIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import com.oocourse.spec3.exceptions.AcquaintanceNotFoundException;
import com.oocourse.spec3.exceptions.MessageIdNotFoundException;
import com.oocourse.spec3.exceptions.EmojiIdNotFoundException;
import com.oocourse.spec3.exceptions.EqualEmojiIdException;
import com.oocourse.spec3.exceptions.PathNotFoundException;

import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.EmojiMessage;

import mysrc.exceptions.MyEqualPersonIdException;
import mysrc.exceptions.MyEqualMessageIdException;
import mysrc.exceptions.MyEqualRelationException;
import mysrc.exceptions.MyEqualGroupIdException;
import mysrc.exceptions.MyRelationNotFoundException;
import mysrc.exceptions.MyPersonIdNotFoundException;
import mysrc.exceptions.MyGroupIdNotFoundException;
import mysrc.exceptions.MyAcquaintanceNotFoundException;
import mysrc.exceptions.MyMessageIdNotFoundException;
import mysrc.exceptions.MyEmojiIdNotFoundException;
import mysrc.exceptions.MyEqualEmojiIdException;
import mysrc.exceptions.MyPathNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class MyNetwork implements Network {
    private HashMap<Integer, MyPerson> people;
    private HashMap<Integer, Integer> map;
    private int blockCnt;
    private int cntTri;
    private HashMap<Integer, MyGroup> groups;
    private HashMap<Integer, MyMessage> messages;
    private HashMap<Integer, Integer> emojis;

    public MyNetwork() {
        this.people = new HashMap<>();
        this.map = new HashMap<>();
        this.blockCnt = 0;
        this.cntTri = 0;
        this.groups = new HashMap<>();
        this.messages = new HashMap<>();
        this.emojis = new HashMap<>();
    }

    public int find(HashMap<Integer, Integer> h, int k) {
        if (h.get(k) == k) {
            return k;
        }
        int tmp = find(h, h.get(k));
        h.put(k, tmp);
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
        int p1 = find(map, id1);
        int p2 = find(map, id2);
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

            int fa = find(map, id1);
            HashSet<Integer> block = new HashSet<>();
            for (Integer id : map.keySet()) {
                if (find(map, id) == fa) {
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
        return find(map, id1) == find(map, id2);
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
            throws EqualMessageIdException, EqualPersonIdException, EmojiIdNotFoundException {
        if (messages.containsKey(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        } else if (message instanceof EmojiMessage &&
                !emojis.containsKey(((EmojiMessage) message).getEmojiId())) {
            throw new MyEmojiIdNotFoundException(((EmojiMessage) message).getEmojiId());
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
            MyPerson p1 = (MyPerson) message.getPerson1();
            MyPerson p2 = (MyPerson) message.getPerson2();
            p1.addSocialValue(message.getSocialValue());
            p2.addSocialValue(message.getSocialValue());
            ((MyPerson) message.getPerson2()).addMessage(message);
            if (message instanceof MyRedEnvelopeMessage) {
                int money = ((MyRedEnvelopeMessage) message).getMoney();
                p1.addMoney(-money);
                p2.addMoney(money);
            } else if (message instanceof MyEmojiMessage) {
                int oldHeat = emojis.get(((MyEmojiMessage) message).getEmojiId());
                emojis.put(((MyEmojiMessage) message).getEmojiId(), oldHeat + 1);
            }
        } else {
            ((MyGroup) message.getGroup()).addSocialValue(message.getSocialValue());
            if (message instanceof MyRedEnvelopeMessage) {
                MyRedEnvelopeMessage redEnvelopeMessage = (MyRedEnvelopeMessage) message;
                int groupSize = redEnvelopeMessage.getGroup().getSize();
                int money = redEnvelopeMessage.getMoney() / groupSize;
                redEnvelopeMessage.getPerson1().addMoney(-groupSize * money);
                ((MyGroup) message.getGroup()).addMoney(money);
            } else if (message instanceof MyEmojiMessage) {
                int oldHeat = emojis.get(((MyEmojiMessage) message).getEmojiId());
                emojis.put(((MyEmojiMessage) message).getEmojiId(), oldHeat + 1);
            }
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
    public void storeEmojiId(int id) throws EqualEmojiIdException {
        if (emojis.containsKey(id)) {
            throw new MyEqualEmojiIdException(id);
        }
        emojis.put(id, 0);
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        return people.get(id).getMoney();
    }

    @Override
    public boolean containsEmojiId(int id) {
        return emojis.containsKey(id);
    }

    @Override
    public int queryPopularity(int id) throws EmojiIdNotFoundException {
        if (!emojis.containsKey(id)) {
            throw new MyEmojiIdNotFoundException(id);
        }
        return emojis.get(id);
    }

    @Override
    public int deleteColdEmoji(int limit) {
        messages.entrySet().removeIf(entry ->
                entry.getValue() instanceof MyEmojiMessage &&
                        emojis.get(((MyEmojiMessage) entry.getValue()).getEmojiId()) < limit);
        emojis.entrySet().removeIf(entry -> entry.getValue() < limit);
        return emojis.size();
    }

    @Override
    public void clearNotices(int personId) throws PersonIdNotFoundException {
        if (!people.containsKey(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        }
        List<Message> personMessages = getPerson(personId).getMessages();
        for (int i = 0; i < personMessages.size(); i++) {
            if (personMessages.get(i) instanceof MyNoticeMessage) {
                personMessages.remove(i);
                i--;
            }
        }
    }

    @Override
    public int queryLeastMoments(int id) throws PersonIdNotFoundException, PathNotFoundException {
        if (!people.containsKey(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        int minl = dijkstra(id);
        if (minl == 0x3f3f3f3f) {
            throw new MyPathNotFoundException(id);
        }
        return minl;
    }

    public int dijkstra(int s) {
        HashMap<Integer, Integer> dis = new HashMap<>();
        HashSet<Integer> vis = new HashSet<>();
        HashMap<Integer, Integer> pre = new HashMap<>();
        for (Integer id : people.keySet()) {
            dis.put(id, 0x3f3f3f3f);
            pre.put(id, id);
        }
        dis.put(s, 0);
        PriorityQueue<Pair> q = new PriorityQueue<>(Comparator.comparingInt(Pair::getFirst));
        q.add(new Pair(0, s));
        while (!q.isEmpty()) {
            int d = q.peek().getFirst();
            int t = q.peek().getSecond();
            q.poll();
            if (vis.contains(t)) {
                continue;
            }
            vis.add(t);
            for (Integer id : people.get(t).getAcquaintance().keySet()) {
                int newd = d + people.get(t).queryValue(people.get(id));
                if (!vis.contains(id) && newd < dis.get(id)) {
                    dis.put(id, newd);
                    q.add(new Pair(newd, id));
                    if (t != s) {
                        pre.put(id, t);
                    }
                }
            }
        }
        int minl = 0x3f3f3f3f;
        for (Integer id : people.get(s).getAcquaintance().keySet()) {
            if (!pre.get(id).equals(id)) {
                int tmp = people.get(s).queryValue(people.get(id)) + dis.get(id);
                minl = (tmp < minl) ? tmp : minl;
            }
        }
        for (Integer id : people.keySet()) {
            for (Integer i : people.get(id).getAcquaintance().keySet()) {
                if (id != s && i != s && find(pre, id) != find(pre, i)) {
                    int tmp = dis.get(id) + dis.get(i) + people.get(id).queryValue(people.get(i));
                    minl = (tmp < minl) ? tmp : minl;
                }
            }
        }
        return minl;
    }

    @Override
    public int deleteColdEmojiOKTest(int limit, ArrayList<HashMap<Integer, Integer>> beforeData,
                                     ArrayList<HashMap<Integer, Integer>> afterData, int result) {
        OkTest okTest = new OkTest(limit, beforeData, afterData, result);
        return okTest.test();
    }
}
