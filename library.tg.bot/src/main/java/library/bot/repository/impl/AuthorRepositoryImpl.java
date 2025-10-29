package library.bot.repository.impl;

import library.bot.domain.Author;
import library.bot.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final List<Author> authors= new ArrayList<>();
    private final HashMap<String, List<String>> userToAuthors = new HashMap<>();

    @Override
    public void save(Author author, String userId) {
        if (!authors.contains(author))
        {
            authors.add(author);
        }
        if (!userToAuthors.containsKey(userId)) {
            userToAuthors.put(userId, new ArrayList<String>());
        }
        userToAuthors.get(userId).add(author.getAuthorId());

    }

    @Override
    public Author findById(String authorId) {
        for (Author author : authors)
        {
            if (author.getAuthorId().equals(authorId))
            {
                return author;
            }
        }
        return null;
    }

    @Override
    public Author findByName(String authorName) {
        for (Author author : authors)
        {
            if (author.getAuthorName().equalsIgnoreCase(authorName))
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
    @Override
    public List<Author> getAuthorsByUserId(String userId)
    {
        List<String> authorIds = userToAuthors.get(userId);
        if (authorIds == null)
        {
            return null;
        }
        List<Author> authorsByUserId = new ArrayList<>();
        for (Author author : authors)
        {
            if (authorIds.contains(author.getAuthorId()))
            {
                authorsByUserId.add(author);
            }
        }
        return authorsByUserId;
    }
}