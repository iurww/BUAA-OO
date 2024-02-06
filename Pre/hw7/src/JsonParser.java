import java.util.ArrayList;

public class JsonParser {
    private String jsonLine;
    private int index;

    public JsonParser(String jsonLine) {
        this.jsonLine = jsonLine;
        index = 0;
    }

    public JsonObject parseObject() {
        JsonObject jobj = new JsonObject();
        while (jsonLine.charAt(index) != '}') {
            index++;
            String key = this.parseString();
            index++;
            Object value;
            if (jsonLine.charAt(index) == '}') {
                break;
            } else if (jsonLine.charAt(index) == '{') {
                value = this.parseObject();
            } else if (jsonLine.charAt(index) == '[') {
                value = this.parseArray();
            } else if (jsonLine.charAt(index) == '\"') {
                value = this.parseString();
            } else {
                value = this.parseValue();
            }
            //System.out.println(key);
            //System.out.println(value);
            jobj.add(key, value);
        }
        index++;
        return jobj;
    }

    public ArrayList<Object> parseArray() {
        ArrayList<Object> jarr = new ArrayList<>();
        while (jsonLine.charAt(index) != ']') {
            index++;
            Object value;
            if (jsonLine.charAt(index) == ']') {
                break;
            } else if (jsonLine.charAt(index) == '{') {
                value = this.parseObject();
            } else if (jsonLine.charAt(index) == '[') {
                value = this.parseArray();
            } else if (jsonLine.charAt(index) == '\"') {
                value = this.parseString();
            } else {
                value = this.parseValue();
            }
            jarr.add(value);
        }
        index++;
        return jarr;
    }

    public String parseString() {
        String key = "";
        while (jsonLine.charAt(++index) != '"') {
            key += jsonLine.charAt(index);
        }
        index++;
        return "\"" + key + "\"";
    }

    public String parseValue() {
        String key = "";
        while (jsonLine.charAt(index) != ',' && jsonLine.charAt(index) != ']'
                && jsonLine.charAt(index) != '}') {
            key += jsonLine.charAt(index++);
        }
        return key;
    }

}
