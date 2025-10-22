package library.bot.repository.impl;

import library.bot.domain.Author;
import library.bot.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final List<Author> authors= new ArrayList<>();

    @Override
    public void save(Author author) {
        authors.add(author);

    }

    @Override
    public Author findById(int authorId) {
        for (Author author : authors)
        {
            if (author.getAuthorId() == authorId)
            {
                return author;
            }
        }
        return null;
    }

    @Override
    public Author findByName(String name) {
        for (Author author : authors)
        {
            if (author.getAuthorName().equals(name))
            {
                return author;
            }
        }
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authors;
    }

    @Override
    public int getTotalAuthors() {
        return authors.size();
    }
}
