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

            case "/rate_book":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_RATE");
                return "⭐ Введите название книги:";

            case "/show_book_info":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_SHOW_INFO");
                return "📘 Введите название книги:";

            case "/add_book_genre":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_ADD_GENRE");
                return "🎭 Введите название книги:";

            case "/add_book_year":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_ADD_YEAR");
                return "📅 Введите название книги:";

            case "/set_status":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_READ_STATUS");
                return "📖 Введите название книги:";

            case "/show_done_books":
                return messageService.showDoneBooks(userName);

            case "/show_undone_books":
                return messageService.showUndoneBooks(userName);

            case "/show_books_rated_on":
                sessionManager.getSession(chatId).setState("WAITING_RATING_TO_SHOW");
                return "⭐ Введите оценку (от 1 до 5), чтобы увидеть книги с этой оценкой:";

            case "/add_note":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_NOTE");
                return "📝 Введите название книги:";

            case "/show_notes":
                sessionManager.getSession(chatId).setState("WAITING_BOOK_NAME_FOR_SHOW_NOTES");
                return "📖 Введите название книги:";

            default:
                return "Неизвестная команда. Используйте /help";
        }
    }
}