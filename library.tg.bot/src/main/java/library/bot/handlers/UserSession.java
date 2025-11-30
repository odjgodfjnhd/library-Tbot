package library.bot.handlers;

import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private String state;
    private final Map<String, String> data = new HashMap<>();

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void putData(String key, String value) {
        data.put(key, value);
    }

    public String getData(String key) {
        return data.get(key);
    }

    public void clear() {
        this.state = null;
        this.data.clear();
    }
}