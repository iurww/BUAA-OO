import java.util.HashMap;

public class JsonObject implements Comparable<JsonObject> {
    private HashMap<String, Object> attributes;

    public JsonObject() {
        attributes = new HashMap<>();
    }

    public void add(String key, Object value) {
        attributes.put(key, value);
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    @Override
    public int compareTo(JsonObject other) {
        if (this.attributes.get("\"count\"").equals(other.attributes.get("\"count\""))) {
            String n1 = (String) this.attributes.get("\"name\"");
            String n2 = (String) other.attributes.get("\"name\"");
            return n1.compareTo(n2);
        }
        int a = Integer.parseInt((String) this.attributes.get("\"count\""));
        int b = Integer.parseInt((String) other.attributes.get("\"count\""));
        return b - a;
    }

    @Override
    public String toString() {
        return attributes.toString();
    }

}
