package library.bot.repository.mysql;

import library.bot.domain.Author;
import library.bot.repository.AuthorRepository;
import library.bot.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySqlAuthorRepository implements AuthorRepository {
    private final DataSource dataSource;

    public MySqlAuthorRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public void save(Author author, String userId) {
        Author existingAuthor = findByName(author.getAuthorName());
        String authorId;
        if (existingAuthor == null) {
            authorId = author.getAuthorId();
            String sql = "INSERT INTO authors (author_id, author_name) VALUES (?, ?)";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, authorId);
                stmt.setString(2, author.getAuthorName());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка сохранения автора", e);
            }
        } else {
            authorId = existingAuthor.getAuthorId();
        }

        String linkSql = "INSERT IGNORE INTO user_authors (user_id, author_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(linkSql)) {
            stmt.setString(1, userId);
            stmt.setString(2, authorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка привязки автора к пользователю", e);
        }
    }

    @Override
    public Author findById(String authorId) {
        String sql = "SELECT author_id, author_name FROM authors WHERE author_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска автора по ID", e);
        }
    }

    @Override
    public Author findByName(String authorName) {
        String normalizedAuthorName = authorName.toLowerCase();

        String sql = "SELECT author_id, author_name FROM authors";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String dbAuthorName = rs.getString("author_name").toLowerCase();
                if (dbAuthorName.equals(normalizedAuthorName)) {
                    return Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска автора по имени", e);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT author_id, author_name FROM authors";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                authors.add(Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех авторов", e);
        }
        return authors;
    }

    @Override
    public int getTotalAuthors() {
        String sql = "SELECT COUNT(*) FROM authors";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подсчёта общего числа авторов", e);
        }
    }

    @Override
    public List<Author> getAuthorsByUserId(String userId) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT a.author_id, a.author_name " +
                "FROM user_authors ua " +
                "JOIN authors a ON ua.author_id = a.author_id " +
                "WHERE ua.user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(Author.fromDatabase(rs.getString("author_id"), rs.getString("author_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения авторов пользователя", e);
        }
        return authors.isEmpty() ? List.of() : authors; // точно как в IN_MEMORY
    }
}