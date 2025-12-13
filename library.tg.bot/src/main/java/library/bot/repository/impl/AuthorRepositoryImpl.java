package library.bot.repository.impl;

import library.bot.domain.Author;
import library.bot.repository.AuthorRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final List<Author> authors = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> userToAuthors =
            new ConcurrentHashMap<>();

    @Override
    public void save(Author author, String userId) {
        if (!authors.contains(author)) {
            authors.add(author);
        }

        userToAuthors.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                .add(author.getAuthorId());
    }

    @Override
    public Author findById(String authorId) {
        for (Author author : authors) {
            if (author.getAuthorId().equals(authorId)) {
                return author;
            }
        }
        return null;
    }

    @Override
    public Author findByName(String authorName) {
        for (Author author : authors) {
            if (author.getAuthorName().equalsIgnoreCase(authorName)) {
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
    public List<Author> getAuthorsByUserId(String userId) {
        CopyOnWriteArrayList<String> authorIds = userToAuthors.get(userId);
        if (authorIds == null || authorIds.isEmpty()) {
            return List.of();
        }

        List<Author> result = new CopyOnWriteArrayList<>();
        for (Author author : authors) {
            if (authorIds.contains(author.getAuthorId())) {
                result.add(author);
            }
        }
        return result;
    }
}