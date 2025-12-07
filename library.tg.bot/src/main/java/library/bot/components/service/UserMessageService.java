package library.bot.components.service;

import library.bot.components.repository.RepositoryComponent;
import library.bot.components.service.ServiceComponent;
import library.bot.domain.Author;
import library.bot.domain.Book;
import library.bot.domain.User;
import library.bot.domain.UserBookMetadata;
import library.bot.utils.Utils;

import java.util.ArrayList;
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
    public String rateBook(String userName, String bookName, String authorName, int rating) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            return "❌ Книга «" + bookName + "» автора «" + authorName + "» не найдена.";
        }

        if (!repositoryComponent.getBookRepository().userHaveBook(user.getUserId(), bookName, authorName)) {
            return "❌ У вас нет этой книги. Сначала добавьте её через /add_book.";
        }

        serviceComponent.getDiaryService().userAddBookRating(user.getUserId(), book.getBookId(), rating);
        return "⭐ Книге «" + bookName + "» выставлена оценка: " + rating;
    }

    public String showBookInfo(String userName, String bookName, String authorName) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        if (!repositoryComponent.getBookRepository().userHaveBook(user.getUserId(), bookName, authorName)) {
            return "❌ У вас нет книги «" + bookName + "» автора «" + authorName + "».\n" +
                    "Добавьте её через /add_book.";
        }

        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        UserBookMetadata meta = repositoryComponent.getUserBookMetadataRepository()
                .findBookMetaDataByUserIdAndBookId(user.getUserId(), book.getBookId());

        return Utils.Formatter.buildBookInfoFull(user, book, meta);
    }

    public String addBookGenre(String userName, String bookName, String authorName, String genre) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            return "❌ Книга «" + bookName + "» автора «" + authorName + "» не найдена.";
        }

        if (!repositoryComponent.getBookRepository().userHaveBook(user.getUserId(), bookName, authorName)) {
            return "❌ У вас нет этой книги. Сначала добавьте её через /add_book.";
        }

        serviceComponent.getDiaryService().userAddBookGenre(user.getUserId(), book.getBookId(), genre);
        return "🎭 Жанр книги «" + bookName + "» установлен: " + genre;
    }

    public String addBookYear(String userName, String bookName, String authorName, int year) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            return "❌ Книга «" + bookName + "» автора «" + authorName + "» не найдена.";
        }

        if (!repositoryComponent.getBookRepository().userHaveBook(user.getUserId(), bookName, authorName)) {
            return "❌ У вас нет этой книги. Сначала добавьте её через /add_book.";
        }

        serviceComponent.getDiaryService().userAddBookYear(user.getUserId(), book.getBookId(), year);
        return "📅 Год издания книги «" + bookName + "» установлен: " + year;
    }

    public String setReadingStatus(String userName, String bookName, String authorName, boolean isRead) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            return "❌ Книга «" + bookName + "» автора «" + authorName + "» не найдена.";
        }

        if (!repositoryComponent.getBookRepository().userHaveBook(user.getUserId(), bookName, authorName)) {
            return "❌ У вас нет этой книги. Сначала добавьте её через /add_book.";
        }

        serviceComponent.getDiaryService().userAddReadingStatus(user.getUserId(), book.getBookId(), isRead);
        String statusText = isRead ? "✅ Прочитана" : "⏳ Не прочитана";
        return "📖 Статус книги «" + bookName + "» обновлён: " + statusText;
    }
    public String showDoneBooks(String userName) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        List<String> doneBookIds = repositoryComponent.getUserBookMetadataRepository()
                .findBookByReadingStatus(user.getUserId(), true);

        if (doneBookIds.isEmpty()) {
            return "📭 Вы пока не прочитали ни одной книги.\n" +
                    "Добавьте книги через /add_book и отметьте их как прочитанные через /set_read_status.";
        }

        List<Book> doneBooks = new ArrayList<>();
        for (String bookId : doneBookIds) {
            Book book = repositoryComponent.getBookRepository().findById(bookId);
            if (book != null) {
                doneBooks.add(book);
            }
        }

        if (doneBooks.isEmpty()) {
            return "📭 У вас нет данных о прочитанных книгах.";
        }

        StringBuilder sb = new StringBuilder("✅ Прочитанные книги (" + doneBooks.size() + "):\n");
        for (Book book : doneBooks) {
            sb.append("• ").append(book.getBookTitle())
                    .append(" — ").append(book.getAuthorName()).append("\n");
        }
        return sb.toString();
    }

    public String showUndoneBooks(String userName) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        List<String> undoneBookIds = repositoryComponent.getUserBookMetadataRepository()
                .findBookByReadingStatus(user.getUserId(), false);

        if (undoneBookIds.isEmpty()) {
            return "📭 У вас нет непрочитанных книг.\n" +
                    "Все книги отмечены как прочитанные!";
        }

        List<Book> undoneBooks = new ArrayList<>();
        for (String bookId : undoneBookIds) {
            Book book = repositoryComponent.getBookRepository().findById(bookId);
            if (book != null) {
                undoneBooks.add(book);
            }
        }

        if (undoneBooks.isEmpty()) {
            return "📭 У вас нет данных о непрочитанных книгах.";
        }

        StringBuilder sb = new StringBuilder("⏳ Непрочитанные книги (" + undoneBooks.size() + "):\n");
        for (Book book : undoneBooks) {
            sb.append("• ").append(book.getBookTitle())
                    .append(" — ").append(book.getAuthorName()).append("\n");
        }
        return sb.toString();
    }

    public String showBooksRatedOn(String userName, int rating) {

        if (rating < 1 || rating > 5) {
            return "❌ Оценка должна быть от 1 до 5.\n" +
                    "Попробуйте снова через /show_books_rated_on.";
        }

        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return "❌ Пользователь не найден.";
        }

        List<String> bookIds = repositoryComponent.getUserBookMetadataRepository()
                .findBooksByRating(user.getUserId(), rating);

        if (bookIds.isEmpty()) {
            return "📭 У вас нет книг с оценкой " + rating + ".\n" +
                    "Оцените книги через /rate_book!";
        }

        List<Book> books = new ArrayList<>();
        for (String bookId : bookIds) {
            Book book = repositoryComponent.getBookRepository().findById(bookId);
            if (book != null) {
                books.add(book);
            }
        }

        if (books.isEmpty()) {
            return "📭 Нет данных о книгах с оценкой " + rating + ".";
        }

        StringBuilder sb = new StringBuilder("⭐ Книги с оценкой " + rating + " (" + books.size() + "):\n");
        for (Book book : books) {
            sb.append("• ").append(book.getBookTitle())
                    .append(" — ").append(book.getAuthorName()).append("\n");
        }
        return sb.toString();
    }

    public String getHelpText() {
        return Utils.Formatter.buildHelpCommands();
    }
}
