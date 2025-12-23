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
    public void userAddBook(String userId, String bookName, String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = new Author(authorName);
            authorRepository.save(author, userId);
        }
        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            book = new Book(bookName, authorName, author.getAuthorId());
            bookRepository.save(book, userId);
        }
        UserBookMetadata metadata = new UserBookMetadata(book.getBookId(), userId);
        userBookMetadataRepository.save(userId, metadata);
    }

    @Override
    public void userAddBookRating(String userId, String bookId, int bookRating) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setBookRating(bookRating);
        userBookMetadataRepository.save(userId, meta);
    }

    @Override
    public void userAddBookYear(String userId, String bookId, int bookYear) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setBookYear(bookYear);
        userBookMetadataRepository.save(userId, meta);
    }

    @Override
    public void userAddBookGenre(String userId, String bookId, String genre) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setGenre(genre);
        userBookMetadataRepository.save(userId, meta);
    }

    @Override
    public void userAddReadingStatus(String userId, String bookId, boolean readingStatus) {
        UserBookMetadata meta = userBookMetadataRepository.findBookMetaDataByUserIdAndBookId(userId, bookId);
        if (meta != null) meta.setReadingStatus(readingStatus);
        userBookMetadataRepository.save(userId, meta);
    }

    @Override
    public void createNewUser(String userId, String userName) {
        User user = new User(userId, userName);
        userRepository.save(user);
    }

    @Override
    public void addNoteToBook(String userId, String bookName, String authorName, String noteText) {
        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);

        if (book == null) {
            throw new IllegalArgumentException("Книга не найдена...");
        }
        Book bookFromDb = bookRepository.findById(book.getBookId());
        Note note = new Note(bookName, book.getBookId(), userId, noteText);
        noteRepository.save(note);
    }

    @Override
    public List<Note> getUserNotesForBook(String userId, String bookName, String authorName) {
        Book book = bookRepository.findByNameAndAuthor(bookName, authorName);
        if (book == null) {
            return List.of();
        }
        return noteRepository.findByBookId(book.getBookId()).stream()
                .filter(note -> userId.equals(note.getUserId()))
                .toList();
    }
}
