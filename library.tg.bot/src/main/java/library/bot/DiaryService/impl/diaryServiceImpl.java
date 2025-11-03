package library.bot.DiaryService.impl;

public class DiaryServiceImpl implements DiaryService {

    private final BookRepository bookRepository;
    private final UserBookMetadataRepository userBookMetadataRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public diaryServiceImpl(BookRepository bookRepository, UserBookMetadataRepository userBookMetadataRepository, UserRepository userRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.userBookMetadataRepository = userBookMetadataRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public String userAddBook(String userName, String bookName, String authorName) {
        User user = userRepository.findByName(userName);
        if (user == null) {
            return "Такого пользователя не существует!";
        }
        String userId = user.getUserId();
        if (bookRepository.userHaveBook(userId, bookName, authorName)) {
            return "У вас уже есть эта книга.";
        }
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = new Author(authorName);
        }
        authorRepository.save(author, userId);

        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            book = new Book(bookName, authorName, author.getAuthorId());
        }
        UserBookMetadata bookMetadata = new UserBookMetadata(book.getBookId(), userId);
        bookRepository.save(book, userId);
        userBookMetadataRepository.save(userId, bookMetadata);
        return "Книга успешно добавлена в ваш читательский дневник!";
    }

    @Override
    public String userAddBookRating(String userId, String bookId, int bookRating) {
        return userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId).setBookRating(bookRating);
    }

    @Override
    public void userAddBookYear(String userId, String bookId, int bookYear) {
        userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId).setBookYear(bookYear);
    }

    @Override
    public void userAddBookGenre(String userId, String bookId, String genre) {
        userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId).setGenre(genre);
    }

    @Override
    public void userAddReadingStatus(String userId, String bookId, boolean readingStatus) {
        userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId).setReadingStatus(readingStatus);
    }

    @Override
    public String createNewUser(String userName) {
        if (userRepository.findByName(userName) == null) {
            User user = new User(userName);
            userRepository.save(user);
            return "Пользователь успешно зарегистрирован";
        }
        return "Пользователь с таким userName уже существует. Придумайте какой-то другой userName!";
    }

}
