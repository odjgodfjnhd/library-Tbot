package library.bot.repository.mysql;

import library.bot.domain.Author;
import library.bot.repository.AuthorRepository;

import java.util.List;

public class MySqlAuthorRepository implements AuthorRepository {
    private final JdbcHelper jdbc = new JdbcHelper();

    @Override
    public void save(Author author, String userId) {
        Author existingAuthor = findByName(author.getAuthorName());
        String authorId = (existingAuthor != null) ? existingAuthor.getAuthorId() : author.getAuthorId();

        if (existingAuthor == null) {
            jdbc.update(
                    "INSERT INTO authors (author_id, author_name) VALUES (?, ?)",
                    stmt -> {
                        stmt.setString(1, authorId);
                        stmt.setString(2, author.getAuthorName());
                    }
            );
        }

        jdbc.update(
                "INSERT IGNORE INTO user_authors (user_id, author_id) VALUES (?, ?)",
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setString(2, authorId);
                }
        );
    }

    @Override
    public Author findById(String authorId) {
        return jdbc.queryForObject(
                "SELECT author_id, author_name FROM authors WHERE author_id = ?",
                rs -> rs.next() ? Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name")) : null,
                stmt -> stmt.setString(1, authorId)
        );
    }

    @Override
    public Author findByName(String authorName) {
        String normalized = authorName.toLowerCase();
        return jdbc.queryForObject(
                "SELECT author_id, author_name FROM authors",
                rs -> {
                    while (rs.next()) {
                        if (rs.getString("author_name").toLowerCase().equals(normalized)) {
                            return Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name"));
                        }
                    }
                    return null;
                },
                stmt -> {} // нет параметров
        );
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.queryForList(
                "SELECT author_id, author_name FROM authors",
                rs -> Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name")),
                stmt -> {}
        );
    }

    @Override
    public int getTotalAuthors() {
        return jdbc.queryForObject(
                "SELECT COUNT(*) FROM authors",
                rs -> rs.next() ? rs.getInt(1) : 0,
                stmt -> {}
        );
    }

    @Override
    public List<Author> getAuthorsByUserId(String userId) {
        List<Author> authors = jdbc.queryForList(
                "SELECT a.author_id, a.author_name " +
                        "FROM user_authors ua " +
                        "JOIN authors a ON ua.author_id = a.author_id " +
                        "WHERE ua.user_id = ?",
                rs -> Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name")),
                stmt -> stmt.setString(1, userId)
        );
        return authors.isEmpty() ? List.of() : authors;
    }
}