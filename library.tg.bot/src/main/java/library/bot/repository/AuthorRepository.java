package library.bot.repository;

import library.bot.domain.Author;
import java.util.List;

public interface AuthorRepository
{
    void save(Author author);

    Author findById(int authorId);

    Author findByName(String name);

    List<Author> getAllAuthors();

    int getTotalAuthors();
}