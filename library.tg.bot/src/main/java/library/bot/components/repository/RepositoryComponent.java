package library.bot.components.repository;

import library.bot.repository.*;
import library.bot.repository.impl.*;

public class RepositoryComponent {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final UserBookMetadataRepository userBookMetadataRepository;
    private final NoteRepository noteRepository;
    private final QuoteRepository quoteRepository;

    public RepositoryComponent()
    {
        this.bookRepository = new BookRepositoryImpl();
        this.authorRepository = new AuthorRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.userBookMetadataRepository = new UserBookMetadataRepositoryImpl();
        this.noteRepository = new NoteRepositoryImpl();
        this.quoteRepository = new QuoteRepositoryImpl();
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public QuoteRepository getQuoteRepository() {
        return quoteRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserBookMetadataRepository getUserBookMetadataRepository() {
        return userBookMetadataRepository;
    }
}
