package library.bot.handlers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SessionManager {
    private final Map<Long, UserSession> sessions = new ConcurrentHashMap<>();
    private final Map<Long, String> chatIdToUserName = new ConcurrentHashMap<>();

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