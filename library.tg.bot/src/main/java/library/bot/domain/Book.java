package library.bot.domain;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book
{
    private final String bookTitle;
    private int bookId;
    private final String authorName;
    private int authorId;
    private final String genre;
    private int bookRating = 0;
    private int bookYear;
    private String bookAddedAt;
    private int bookPages;
    private final String bookTranslator;
    private int userId;

    public Book(String bookTitle, String authorName, String genre, String bookTranslator, int bookYear, int bookPages)
    {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.genre = genre;
        this.bookTranslator = bookTranslator;
        this.bookAddedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"));
        this.bookYear = bookYear;


    }

    public int getBookId() {
        return bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getBookPages() {
        return bookPages;
    }

    public String getBookAddedAt() {
        return bookAddedAt;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public double getBookRating() {
        return bookRating;
    }

    public int getBookYear() {
        return bookYear;
    }

    public String getBookTranslator() {
        return bookTranslator;
    }

    public String getGenre() {
        return genre;
    }

    public void setBookRating(int bookRating) {
        if (0 <=bookRating & 5 >= bookRating)
        {
            this.bookRating = bookRating;
        }
    }
}
