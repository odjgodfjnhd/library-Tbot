package library.bot.domain;

import java.time.LocalDate;

public class UserBookMetadata {
    private final String bookId;
    private final String userId;
    private String genre = "Вы ещё не указали жанр для этой книги.";
    private int bookRating = 0;
    private int bookYear = 0;
    private final LocalDate bookAddedAt;
    private int bookPages = 0;
    private String bookTranslator = "Вы ещё не указали переводчика этой книги";
    private Boolean readingStatus = false;
    public UserBookMetadata(String bookId, String userId)
    {
        this.userId = userId;
        this.bookId = bookId;
        this.bookAddedAt = LocalDate.now();
    }

    public String getBookTranslator() {
        return bookTranslator;
    }

    public int getBookPages() {
        return bookPages;
    }

    public int getBookYear() {
        return bookYear;
    }

    public String getGenre() {
        return genre;
    }

    public int getBookRating() {
        return bookRating;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getBookAddedAt() {
        return bookAddedAt;
    }

    public Boolean getReadingStatus() {
        return readingStatus;
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

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public void setReadingStatus(Boolean readingStatus) {
        this.readingStatus = readingStatus;
    }
}
