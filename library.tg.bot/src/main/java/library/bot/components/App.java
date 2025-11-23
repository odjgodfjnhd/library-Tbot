package library.bot.components;

import library.bot.DiaryService.DiaryService;
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

import java.util.List;
import java.util.Scanner;

public class App {

    private final RepositoryComponent repositoryComponent;
    private final ServiceComponent serviceComponent;
    private final Scanner scanner = new Scanner(System.in);

    public App() {
        this.repositoryComponent = new RepositoryComponent();
        this.serviceComponent = new ServiceComponent(repositoryComponent);
    }

    public void start() {
        printWelcomeScreen();
        printHelp();
        while (true) {
            System.out.print("\nВведите команду: ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "/add_book" -> addBook();
                case "/create_user" -> createUser();
                case "/show_authors" -> showAuthors();
                case "/show_book_info" -> showBookInfo();
                case "/rate_book" -> rateBook();
                /**
                 case "/set_year" -> setYear();
                 case "/set_genre" -> setGenre();
                 case "/change_status" -> changeStatus();
                 case "/show_done_books" -> showDoneBooks();
                 case "/show_undone_books" -> showUndoneBooks();
                 case "/show_books_rated_on" -> showBooksRatedOn();
                 */
                case "/help" -> printHelp();
                default -> System.out.println("Неизвестная команда! Введите /help, чтобы посмотреть список доступных команд.");
            }
        }
    }

    private void printWelcomeScreen() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   Добро пожаловать в ваш персональный читательский дневник!  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    private void addBook() {
        System.out.println("\n     *****Добавление книги*****     ");
        Utils.askUserName();
        String userName = scanner.nextLine();
        Utils.askBookName(userName);
        String bookName = scanner.nextLine();
        Utils.askAuthorName(bookName);
        String authorName = scanner.nextLine();
        serviceComponent.getDiaryService().userAddBook(userName, bookName, authorName);
        System.out.println("Книга успешно добавлена в ваш читательский дневник!");
    }

    private void createUser() {
        System.out.println("\n     *****Регистрация пользователя*****     ");
        System.out.print("Введите, как к вам обращаться, это будет ваш userName: ");
        String userName = scanner.nextLine();
        serviceComponent.getDiaryService().createNewUser(userName);
        System.out.println("Пользователь успешно зарегистрирован");
    }

    private void showAuthors() {
        System.out.println("\n     *****Просмотр авторов*****     ");
        Utils.askUserName();
        String userName = scanner.nextLine();
        UserRepository userRepository = repositoryComponent.getUserRepository();
        User user = userRepository.findByName(userName);
        if (user == null) {
            System.out.println("Такого пользователя не существует!");
            return;
        }
        AuthorRepository authorRepository = repositoryComponent.getAuthorRepository();
        List<Author> authors = authorRepository.getAuthorsByUserId(user.getUserId());
        if (authors == null || authors.isEmpty()) {
            System.out.println("Вы пока не читаете ни одного автора. Добавьте книгу и начните сегодня!");
            return;
        }

        System.out.println("Вы читаете этих авторов (" + authors.size() + "):");
        for (Author author : authors) {
            System.out.println(author.getAuthorName());
        }
    }

    private void showBookInfo() {
        System.out.println("\n     *****Вывод подробной информации о книге*****     ");
        Utils.askUserName();
        String userName = scanner.nextLine();
        Utils.askBookName(userName);
        String bookName = scanner.nextLine();
        Utils.askAuthorName(bookName);
        String authorName = scanner.nextLine();
        boolean result = doesUserHaveBook(userName, bookName, authorName);
        if (!result) {
            System.out.println("Ошибка: у пользователя нет этой книги");
            return;
        }

        User user = repositoryComponent.getUserRepository().findByName(userName);
        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        UserBookMetadata bookMetadata = repositoryComponent.getUserBookMetadataRepository()
                .findBookMetaDataByUserIdAndBookId(user.getUserId(), book.getBookId());

        System.out.println(Utils.Formatter.buildBookInfoFull(user, book, bookMetadata));
    }

    private void rateBook() {
        System.out.println("\n     *****Добавление рейтинга книге*****     ");
        Utils.askUserName();
        String userName = scanner.nextLine();
        Utils.askBookName(userName);
        String bookName = scanner.nextLine();
        Utils.askAuthorName(bookName);
        String authorName = scanner.nextLine();
        boolean result = doesUserHaveBook(userName, bookName, authorName);
        if (!result) {
            System.out.println("Ошибка: у пользователя нет этой книги");
            return;
        }

        User user = repositoryComponent.getUserRepository().findByName(userName);
        Book book = repositoryComponent.getBookRepository().findByNameAndAuthor(bookName, authorName);
        System.out.println("Какую оценку вы хотите добавить книге (рейтинг от 1 до 5 целое число): ");
        int bookRating = scanner.nextInt();
        scanner.nextLine();
        serviceComponent.getDiaryService().userAddBookRating(user.getUserId(), book.getBookId(), bookRating);
    }

    private void printHelp() {
        System.out.println(Utils.Formatter.buildHelpCommands());
    }

    private boolean doesUserHaveBook(String userName, String bookName, String authorName) {
        User user = repositoryComponent.getUserRepository().findByName(userName);
        if (user == null) {
            return false;
        }
        return repositoryComponent.getBookRepository().userHaveBook(user.getUserId(), bookName, authorName);
    }
}