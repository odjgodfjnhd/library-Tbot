package library.bot.components.service;

import library.bot.components.repository.RepositoryComponent;
import library.bot.components.service.ServiceComponent;
import library.bot.domain.Author;
import library.bot.domain.User;
import library.bot.utils.Utils;

import java.util.List;

public class UserMessageService {
    private final RepositoryComponent repositoryComponent;
    private final ServiceComponent serviceComponent;

    public UserMessageService(RepositoryComponent repositoryComponent) {
        this.repositoryComponent = repositoryComponent;
        this.serviceComponent = new ServiceComponent(repositoryComponent);
    }

    public String createUser(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return "❌ Имя пользователя не может быть пустым.";
        }
        userName = userName.trim();
        if (repositoryComponent.getUserRepository().findByName(userName) != null) {
            return "❌ Имя «" + userName + "» уже занято. Выберите другое.";
        }

        serviceComponent.getDiaryService().createNewUser(userName);
        return "✅ Пользователь «" + userName + "» успешно зарегистрирован!";
    }

    public String addBook(String userName, String bookName, String authorName) {
        if (bookName == null || bookName.trim().isEmpty()) {
            return "❌ Название книги не может быть пустым.";
        }
        if (authorName == null || authorName.trim().isEmpty()) {
            return "❌ Имя автора не может быть пустым.";
        }
        bookName = bookName.trim();
        authorName = authorName.trim();

        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден. Начните с регистрации.";
        }

        boolean alreadyHas = repositoryComponent.getBookRepository()
                .userHaveBook(user.getUserId(), bookName, authorName);
        if (alreadyHas) {
            return "❌ У вас уже есть книга «" + bookName + "» автора «" + authorName + "».";
        }

        serviceComponent.getDiaryService().userAddBook(userName, bookName, authorName);
        return "✅ Книга «" + bookName + "» автора «" + authorName + "» добавлена!";
    }

    public String showAuthors(String userName) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Ваш профиль не найден. Попробуйте зарегистрироваться заново.";
        }

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

    public String getHelpText() {
        return Utils.Formatter.buildHelpCommands();
    }
}
