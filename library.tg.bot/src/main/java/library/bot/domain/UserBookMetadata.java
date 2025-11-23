package library.bot.domain;

import java.time.LocalDate;

public class UserBookMetadata {
    private final String bookId;
    private final String userId;
    private String genre;
    private int bookRating;
    private int bookYear;
    private final LocalDate bookAddedAt;
    private int bookPages;
    private String bookTranslator;
    private Boolean readingStatus;

    public UserBookMetadata(String bookId, String userId) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookAddedAt = LocalDate.now();
        this.bookRating = 0;
        this.bookYear = 0;
        this.bookPages = 0;
        this.readingStatus = false;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGenre() {
        return genre;
    }

    public int getBookRating() {
        return bookRating;
    }

    public int getBookYear() {
        return bookYear;
    }

    public LocalDate getBookAddedAt() {
        return bookAddedAt;
    }

    public int getBookPages() {
        return bookPages;
    }

    public String getBookTranslator() {
        return bookTranslator;
    }

    public Boolean getReadingStatus() {
        return readingStatus;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBookRating(int bookRating) {
        this.bookRating = bookRating;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public void setBookTranslator(String bookTranslator) {
        this.bookTranslator = bookTranslator;
    }

    public void setReadingStatus(Boolean readingStatus) {
        this.readingStatus = readingStatus;
    }
}
