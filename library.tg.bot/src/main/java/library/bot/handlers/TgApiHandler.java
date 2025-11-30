package library.bot.handlers;

import library.bot.components.repository.RepositoryComponent;
import library.bot.components.service.ServiceComponent;
import library.bot.domain.Author;
import library.bot.domain.Book;
import library.bot.domain.User;
import library.bot.domain.UserBookMetadata;
import library.bot.repository.AuthorRepository;
import library.bot.repository.BookRepository;
import library.bot.repository.UserBookMetadataRepository;
import library.bot.repository.UserRepository;
import library.bot.utils.Utils;
import library.bot.handlers.SessionManager;

import java.util.List;

public class TgApiHandler {

    private final RepositoryComponent repositoryComponent;
    private final ServiceComponent serviceComponent;
    private final SessionManager sessionManager = new SessionManager();

    public TgApiHandler() {
        this.repositoryComponent = new RepositoryComponent();
        this.serviceComponent = new ServiceComponent(repositoryComponent);
    }

    public String handleUpdateReceived(Long chatId, String text) {
        String command = text.trim().toLowerCase();
        UserSession session = sessionManager.getSession(chatId);
        if (session.getState() != null) {
            return handleState(chatId, text, session);
        }
        if (command.equals("/start") || command.equals("/help")) {
            return getHelpText();
        }

        if (!sessionManager.isUserRegistered(chatId)) {
            if (text.trim().equalsIgnoreCase("/create_user")) {
                session.setState("WAITING_USER_NAME_FOR_CREATE");
                return "👤 Введите имя пользователя (будет использоваться как userName):";
            }
            return "⚠️ Сначала зарегистрируйтесь! Используйте команду /create_user";
        }

        return switch (command) {
            case "/create_user" -> {
                String userName = sessionManager.getUserNameByChatId(chatId);
                yield "❌ Вы уже зарегистрированы как <<" + userName + ">>";
            }
            case "/add_book" -> {
                session.setState("WAITING_BOOK_NAME_FOR_ADD_BOOK");
                yield "📖 Введите название книги:";
            }
            case "/show_authors" -> showAuthors(chatId);
            default -> "Неизвестная команда. Используйте /help";
        };
    }

    private String handleState(Long chatId, String input, UserSession session) {
        String state = session.getState();
        String cleanInput = input.trim();

        if (cleanInput.isEmpty()) {
            return "❌ Ввод не может быть пустым. Попробуйте снова.";
        }

        try {
            switch (state) {
                // ============ Регистрация пользователя ============
                case "WAITING_USER_NAME_FOR_CREATE" -> {
                    String userName = cleanInput;

                    if (repositoryComponent.getUserRepository().findByName(userName) != null) {
                        return "❌ Имя «" + userName + "» уже занято. Выберите другое.";
                    }

                    serviceComponent.getDiaryService().createNewUser(userName);
                    sessionManager.registerUser(chatId, userName);

                    session.clear();
                    return "✅ Добро пожаловать, " + userName + "!\n" +
                            "Теперь вы можете использовать все команды.";
                }

                // ============ Добавление книги (без спроса userName) ============
                case "WAITING_BOOK_NAME_FOR_ADD_BOOK" -> {
                    session.putData("bookName", cleanInput);
                    session.setState("WAITING_AUTHOR_NAME_FOR_ADD BOOK");
                    return "✍️ Введите имя автора:";
                }

                case "WAITING_AUTHOR_NAME_FOR_ADD BOOK" -> {
                    String bookName = session.getData("bookName");
                    String authorName = cleanInput;
                    String userName = sessionManager.getUserNameByChatId(chatId);
                    User user = repositoryComponent.getUserRepository().findByName(userName);
                    boolean alreadyHasBook = repositoryComponent.getBookRepository()
                            .userHaveBook(user.getUserId(), bookName, authorName);

                    if (alreadyHasBook) {
                        session.clear();
                        return "❌ У вас уже есть книга «" + bookName + "» автора «" + authorName + "».\n" +
                                "Нельзя добавить дубликат.";
                    }

                    serviceComponent.getDiaryService().userAddBook(userName, bookName, authorName);
                    session.clear();
                    return "✅ Книга «" + bookName + "» автора «" + authorName + "» добавлена в ваш дневник!";
                }

                default -> {
                    session.clear();
                    return "⚠️ Неизвестное состояние. Используйте /help";
                }
            }
        } catch (Exception e) {
            session.clear();
            return "❌ Ошибка: " + e.getMessage();
        }
    }

    private String getHelpText() {
        return Utils.Formatter.buildHelpCommands();
    }

    private String showAuthors(Long chatId) {
        String userName = sessionManager.getUserNameByChatId(chatId);

        User user = repositoryComponent.getUserRepository().findByName(userName);

        List<Author> authors = repositoryComponent.getAuthorRepository()
                .getAuthorsByUserId(user.getUserId());

        if (authors == null || authors.isEmpty()) {
            return "📭 Вы пока не читаете ни одного автора.\n" +
                    "Добавьте книгу через /add_book и начните сегодня!";
        }

        StringBuilder sb = new StringBuilder("📚 Вы читаете этих авторов (" + authors.size() + "):\n");
        for (Author author : authors) {
            sb.append("• ").append(author.getAuthorName()).append("\n");
        }
        return sb.toString();
    }
}
