package library.bot.domain;

public class Book {
    private final String bookTitle;
    private final String bookId;
    private final String authorName;
    private final String authorId;

    public Book(String bookTitle, String authorName, String authorId) {
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
