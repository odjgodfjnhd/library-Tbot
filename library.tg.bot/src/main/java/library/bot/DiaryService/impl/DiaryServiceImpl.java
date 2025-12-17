package library.bot.DiaryService.impl;

import library.bot.DiaryService.DiaryService;
import library.bot.domain.*;
import library.bot.repository.*;

import java.util.List;

public class DiaryServiceImpl implements DiaryService {

    private final BookRepository bookRepository;
    private final UserBookMetadataRepository userBookMetadataRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final NoteRepository noteRepository;

    public DiaryServiceImpl(BookRepository bookRepository, UserBookMetadataRepository userBookMetadataRepository, UserRepository userRepository, AuthorRepository authorRepository, NoteRepository noteRepository) {
        this.bookRepository = bookRepository;
        this.userBookMetadataRepository = userBookMetadataRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.noteRepository = noteRepository;
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

    @Override
    public void addNoteToBook(String userId, String bookName, String authorName, String noteText) {
        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            throw new IllegalArgumentException("Книга «" + bookName + "» автора «" + authorName + "» не найдена.");
        }
        System.out.println("DEBUG: Найдена книга: " + book.getBookTitle() + " | ID: " + book.getBookId());

        if (!bookRepository.userHaveBook(userId, bookName, authorName)) {
            throw new IllegalArgumentException("У вас нет этой книги. Сначала добавьте её через /add_book.");
        }

        Note note = new Note(bookName, book.getBookId(), userId, noteText);
        System.out.println("DEBUG: Создана заметка с bookId: " + note.getBookId());
        noteRepository.save(note);
    }

    @Override
    public List<Note> getUserNotesForBook(String userId, String bookName, String authorName) {
        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);
        System.out.println("DEBUG: При поиске заметок — ID книги: " + (book != null ? book.getBookId() : "null"));
        System.out.println(("DEBUG: название книги: " + bookName + "Автор книги: " + authorName));
        if (book == null) {
            return List.of();
        }
        return noteRepository.findByBookId(book.getBookId()).stream()
                .filter(note -> userId.equals(note.getUserId()))
                .toList();
    }
}
