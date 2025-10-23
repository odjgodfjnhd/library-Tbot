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
    private String userId;
    private String genre = "Вы ещё не указали жанр для этой книги.";
    private int bookRating = 0;
    private int bookYear = 0;
    private LocalDate bookAddedAt;
    private int bookPages = 0;
    private String bookTranslator = "Вы ещё не указали, кто переводчик этой книги.";


    public Book(String bookTitle, String authorName, String authorId, String userId)
    {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.bookAddedAt = LocalDate.now();
        this.bookId = UUID.randomUUID().toString();
        this.authorId = authorId;
        this.userId = userId;



    }

    public String getBookId() {
        return bookId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public int getBookPages() {
        return bookPages;
    }

    public LocalDate getBookAddedAt() {
        return bookAddedAt;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getBookRating() {
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

    public String setBookRating(int bookRating) {
        if (1 <=bookRating & 5 >= bookRating)
        {
            this.bookRating = bookRating;
            return "Рейтинг книги успешно установлен.";
        }
        return "Невалидный рейтинг книги.";
    }
    public void setGenre(String genre)
    {
        this.genre = genre;
    }
    public void setBookTranslator(String bookTranslator)
    {
        this.bookTranslator = bookTranslator;
    }
    public void setBookYear(int bookYear)
    {
        this.bookYear = bookYear;
    }
}
