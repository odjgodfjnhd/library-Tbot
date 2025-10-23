package library.bot.domain;

import library.bot.repository.AuthorRepository;
import library.bot.repository.impl.AuthorRepositoryImpl;

import java.util.UUID;

public class Author {
    private final String authorName;
    private final String authorId;
    private int authorRating = 0;
    private final String userId;

    public Author(String authorName, String userId)
    {

        this.authorName = authorName;
        this.userId = userId;
        this.authorId = UUID.randomUUID().toString();

    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String setAuthorRating(int authorRating) {
        if (authorRating >=1 & authorRating <= 5)
        {
            this.authorRating = authorRating;
            return "Рейтинг автора успешно установлен.";
        }
        return "Невалидный рейтинг автора.";
    }

    public int getAuthorRating() {
        return authorRating;

    }
}
