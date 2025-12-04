package library.bot.handlers;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private final Map<Long, UserSession> sessions = new HashMap<>();
    private final Map<Long, String> chatIdToUserName = new HashMap<>();

    public UserSession getSession(Long chatId) {
        return sessions.computeIfAbsent(chatId, k -> new UserSession());
    }

    public void clearSession(Long chatId) {
        sessions.remove(chatId);
    }

    public void registerUser(Long chatId, String userName) {
        chatIdToUserName.put(chatId, userName);
    }

    public String getUserNameByChatId(Long chatId) {
        return chatIdToUserName.get(chatId);
    }

    public boolean isUserRegistered(Long chatId) {
        return chatIdToUserName.containsKey(chatId);
    }
}