package library.bot.components.repository;

import library.bot.config.BotConfig;
import library.bot.repository.*;
import library.bot.repository.impl.*;
import library.bot.repository.mysql.*;

public class RepositoryComponent {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final UserBookMetadataRepository userBookMetadataRepository;
    private final NoteRepository noteRepository;

    public RepositoryComponent() {
        String mode = BotConfig.getAppMode();
        if ("MYSQL".equals(mode)) {
            this.userRepository = new MySqlUserRepository();
            this.bookRepository = new MySqlBookRepository();
            this.noteRepository = new MySqlNoteRepository();
            this.userBookMetadataRepository = new MySqlUserBookMetadataRepository();
            this.authorRepository = new MySqlAuthorRepository();
        } else {
            this.userRepository = new library.bot.repository.impl.UserRepositoryImpl();
            this.bookRepository = new library.bot.repository.impl.BookRepositoryImpl();
            this.noteRepository = new library.bot.repository.impl.NoteRepositoryImpl();
            this.userBookMetadataRepository = new library.bot.repository.impl.UserBookMetadataRepositoryImpl();
            this.authorRepository = new library.bot.repository.impl.AuthorRepositoryImpl();
        }
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

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserBookMetadataRepository getUserBookMetadataRepository() {
        return userBookMetadataRepository;
    }
}
