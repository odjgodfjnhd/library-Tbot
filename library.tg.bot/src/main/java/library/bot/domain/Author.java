package library.bot.domain;

import library.bot.repository.AuthorRepository;
import library.bot.repository.impl.AuthorRepositoryImpl;

import java.util.UUID;

public class Author {
    private final String authorName;
    private final String authorId;
    private int authorRating = 0;

    public Author(String authorName) {
        this.authorName = authorName;
        this.authorId = UUID.randomUUID().toString();
    }

    Author(String authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public static Author fromDatabase(String authorId, String authorName) {
        return new Author(authorId, authorName);
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
