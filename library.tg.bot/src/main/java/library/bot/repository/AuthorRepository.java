package library.bot.repository;

import library.bot.domain.Author;
import java.util.List;

public interface AuthorRepository {
    void save(Author author, String userId);

    Author findById(String authorId);

    Author findByName(String authorName);

    List<Author> getAllAuthors();

    int getTotalAuthors();

    List<Author> getAuthorsByUserId(String userId);
}
