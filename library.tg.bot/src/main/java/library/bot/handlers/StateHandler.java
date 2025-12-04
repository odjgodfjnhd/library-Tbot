package library.bot.handlers;

import library.bot.handlers.SessionManager;
import library.bot.components.service.UserMessageService;

public class StateHandler {
    private final UserMessageService messageService;
    private final SessionManager sessionManager;

    public StateHandler(UserMessageService messageService, SessionManager sessionManager) {
        this.messageService = messageService;
        this.sessionManager = sessionManager;
    }

    public String handleState(Long chatId, String state, String input) {
        switch (state) {
            case "WAITING_USER_NAME_FOR_CREATE": {
                sessionManager.registerUser(chatId, input);
                sessionManager.clearSession(chatId);
                return messageService.createUser(input);
            }

            case "WAITING_BOOK_NAME_FOR_ADD_BOOK": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_ADD_BOOK");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_ADD_BOOK": {
                String bookName = sessionManager.getSession(chatId).getData("bookName");
                sessionManager.clearSession(chatId);
                String userName = sessionManager.getUserNameByChatId(chatId);
                return messageService.addBook(userName, bookName, input);
            }

            default: {
                sessionManager.clearSession(chatId);
                return "⚠️ Неизвестное состояние. Используйте /help";
            }
        }
    }
}