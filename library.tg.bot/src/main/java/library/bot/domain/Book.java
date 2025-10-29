package library.bot.domain;
import java.rmi.server.UID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Book
{
    private final String bookTitle;
    private final String bookId;
    private final String authorName;
    private final String authorId;

    public Book(String bookTitle, String authorName, String authorId)
    {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.bookId = UUID.randomUUID().toString();
        this.authorId = authorId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }
}
