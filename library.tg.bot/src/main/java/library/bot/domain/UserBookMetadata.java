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

    public String getBookTranslator() {
        return bookTranslator != null ? bookTranslator : "Вы ещё не указали переводчика этой книги";
    }

    public int getBookPages() {
        return bookPages;
    }

    public String getBookYear() {
        return bookYear != 0 ? String.valueOf(bookYear) : "Вы ещё не указали год выхода этой книги";
    }

    public String getGenre() {
        return genre != null ? genre : "Вы ещё не указали жанр для этой книги.";
    }

    public String getBookRating() {
        return bookRating != 0 ? String.valueOf(bookRating) : "Вы ещё не поставили оценку этой книге";
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

    public String getReadingStatus() {
        return readingStatus != null && readingStatus ? "Книга прочитана" : "Книга не прочитана";
    }

    public String setBookRating(int bookRating) {
        if (1 <= bookRating && bookRating <= 5) {
            this.bookRating = bookRating;
            return "Рейтинг книги успешно установлен.";
        }
        return "Невалидный рейтинг книги.";
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBookTranslator(String bookTranslator) {
        this.bookTranslator = bookTranslator;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public void setReadingStatus(Boolean readingStatus) {
        this.readingStatus = readingStatus;
    }
}
