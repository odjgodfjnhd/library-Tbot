package library.bot.domain;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Note {
    protected int noteId;
    protected int bookId;
    protected String bookName;
    protected int userId;
    protected final String noteCreatedAt;
    protected String noteText;

    public Note(String bookName, String noteText)
    {
        this.bookName = bookName;
        this.noteText = noteText;
        this.noteCreatedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"));
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getNoteCreatedAt() {
        return noteCreatedAt;
    }

    public String getNoteText() {
        return noteText;
    }
}
