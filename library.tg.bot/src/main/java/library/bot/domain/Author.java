package library.bot.domain;

public class Author {
    private final String authorName;
    private int authorId;
    private int authorRating = 0;
    private int userId;

    public Author(String authorName)
    {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getUserId() {
        return userId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorRating(int authorRating) {
        if (authorRating >=0 & authorRating <= 5)
        {
            this.authorRating = authorRating;
        }
    }

    public int getAuthorRating() {
        return authorRating;

    }
}
