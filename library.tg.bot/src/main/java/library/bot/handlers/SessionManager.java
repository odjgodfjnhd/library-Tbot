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
}