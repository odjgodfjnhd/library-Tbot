package library.bot.domain;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Note {
    protected final String noteId;
    protected final String bookId;
    protected final String bookName;
    protected final String userId;
    protected final LocalDate noteCreatedAt;
    protected String noteText;

    public Note(String bookName, String bookId, String userId, String noteText) {
        this.bookName = bookName;
        this.bookId = bookId;
        this.userId = userId;
        this.noteText = noteText;
        this.noteId = UUID.randomUUID().toString();
        this.noteCreatedAt = LocalDate.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getBookName() {
        return bookName;
    }

    public LocalDate getNoteCreatedAt() {
        return noteCreatedAt;
    }

    public String getNoteText() {
        return noteText;
    }
}
