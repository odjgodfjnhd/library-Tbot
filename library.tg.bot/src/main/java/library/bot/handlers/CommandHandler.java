package library.bot.handlers;

import library.bot.handlers.SessionManager;
import library.bot.components.service.UserMessageService;

public class CommandHandler {
    private final UserMessageService messageService;
    private final SessionManager sessionManager;

    public CommandHandler(UserMessageService messageService, SessionManager sessionManager) {
        this.messageService = messageService;
        this.sessionManager = sessionManager;
    }

    public String handleCommand(Long chatId, String command) {
        command = command.toLowerCase().trim();

        if ("/start".equals(command) || "/help".equals(command)) {
            return messageService.getHelpText();
        }

        if (!sessionManager.isUserRegistered(chatId)) {
            if ("/create_user".equals(command)) {
                sessionManager.getSession(chatId).setState("WAITING_USER_NAME_FOR_CREATE");
                return "👤 Введите имя пользователя:";
            }
            return "⚠️ Сначала зарегистрируйтесь! Используйте /create_user";
        }

        String userName = sessionManager.getUserNameByChatId(chatId);
        switch (command) {
            case "/create_user":
                return "❌ Вы уже зарегистрированы как «" + userName + "».";

            case "/add_book":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_ADD_BOOK");
                return "📖 Введите название книги:";

            case "/show_authors":
                return messageService.showAuthors(userName);

            default:
                return "Неизвестная команда. Используйте /help";
        }
    }
}