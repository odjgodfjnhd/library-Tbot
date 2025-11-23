package library.bot.DiaryService.impl;

import library.bot.DiaryService.DiaryService;
import library.bot.domain.Author;
import library.bot.domain.Book;
import library.bot.domain.User;
import library.bot.domain.UserBookMetadata;
import library.bot.repository.AuthorRepository;
import library.bot.repository.BookRepository;
import library.bot.repository.UserBookMetadataRepository;
import library.bot.repository.UserRepository;

public class DiaryServiceImpl implements DiaryService {

    private final BookRepository bookRepository;
    private final UserBookMetadataRepository userBookMetadataRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public DiaryServiceImpl(BookRepository bookRepository, UserBookMetadataRepository userBookMetadataRepository, UserRepository userRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.userBookMetadataRepository = userBookMetadataRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void userAddBook(String userName, String bookName, String authorName) {
        User user = userRepository.findByName(userName);
        if (user == null) return;
        String userId = user.getUserId();
        if (bookRepository.userHaveBook(userId, bookName, authorName)) return;
        Author author = authorRepository.findByName(authorName);
        if (author == null) author = new Author(authorName);
        authorRepository.save(author, userId);
        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);
        if (book == null) book = new Book(bookName, authorName, author.getAuthorId());
        UserBookMetadata bookMetadata = new UserBookMetadata(book.getBookId(), userId);
        bookRepository.save(book, userId);
        userBookMetadataRepository.save(userId, bookMetadata);
    }

    @Override
    public void userAddBookRating(String userId, String bookId, int bookRating) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setBookRating(bookRating);
    }

    @Override
    public void userAddBookYear(String userId, String bookId, int bookYear) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setBookYear(bookYear);
    }

    @Override
    public void userAddBookGenre(String userId, String bookId, String genre) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setGenre(genre);
    }

    @Override
    public void userAddReadingStatus(String userId, String bookId, boolean readingStatus) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setReadingStatus(readingStatus);
    }

    @Override
    public void createNewUser(String userName) {
        if (userRepository.findByName(userName) != null) return;
        User user = new User(userName);
        userRepository.save(user);
    }
}
