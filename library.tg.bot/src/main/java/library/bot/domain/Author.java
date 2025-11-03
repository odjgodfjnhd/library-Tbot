package library.bot.domain;

public class Author {
    private final String authorName;
    private final String authorId;
    private int authorRating = 0;

    public Author(String authorName) {
        this.authorName = authorName;
        this.authorId = UUID.randomUUID().toString();
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String setAuthorRating(int authorRating) {
        if (authorRating >=1 & authorRating <= 5) {
            this.authorRating = authorRating;
            return "Рейтинг автора успешно установлен.";
        }
        return "Невалидный рейтинг автора.";
    }

    public int getAuthorRating() {
        return authorRating;
    }
}
