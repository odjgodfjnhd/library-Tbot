package library.bot.repository.mysql;

import library.bot.domain.UserBookMetadata;
import library.bot.repository.UserBookMetadataRepository;
import library.bot.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserBookMetadataRepository implements UserBookMetadataRepository {
    private final DataSource dataSource;

    public MySqlUserBookMetadataRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public void save(String userId, UserBookMetadata metaData) {
        String sql = "INSERT INTO user_books (" +
                "user_id, book_id, genre, book_year, rating, reading_status" +
                ") VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "genre = VALUES(genre), " +
                "book_year = VALUES(book_year), " +
                "rating = VALUES(rating), " +
                "reading_status = VALUES(reading_status)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, metaData.getBookId());
            stmt.setString(3, metaData.getGenre());
            stmt.setObject(4, metaData.getBookYear() != 0 ? metaData.getBookYear() : null);
            stmt.setObject(5, metaData.getBookRating() != 0 ? metaData.getBookRating() : null);
            stmt.setBoolean(6, metaData.getReadingStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения метаданных", e);
        }
    }

    @Override
    public List<String> findBooksByGenre(String userId, String genre) {
        if (genre == null) return List.of();
        return findBookIdsByColumn(userId, "genre", genre, "LOWER(genre) = LOWER(?)");
    }

    @Override
    public List<String> findBooksByBookYear(String userId, int bookYear) {
        return findBookIdsByColumn(userId, "book_year", bookYear, "book_year = ?");
    }

    @Override
    public List<String> findBooksByRating(String userId, int bookRating) {
        return findBookIdsByColumn(userId, "rating", bookRating, "rating = ?");
    }

    @Override
    public List<String> findBookByReadingStatus(String userId, Boolean readingStatus) {
        if (readingStatus == null) return List.of();
        return findBookIdsByColumn(userId, "reading_status", readingStatus, "reading_status = ?");
    }

    private List<String> findBookIdsByColumn(String userId, String column, Object value, String condition) {
        List<String> bookIds = new ArrayList<>();
        String sql = "SELECT book_id FROM user_books WHERE user_id = ? AND " + condition;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            if (value instanceof String) {
                stmt.setString(2, (String) value);
            } else if (value instanceof Integer) {
                stmt.setInt(2, (Integer) value);
            } else if (value instanceof Boolean) {
                stmt.setBoolean(2, (Boolean) value);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookIds.add(rs.getString("book_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска книг по " + column, e);
        }
        return bookIds.isEmpty() ? List.of() : bookIds;
    }

    @Override
    public UserBookMetadata findBookMetaDataByUserIdAndBookId(String userId, String bookId) {
        String sql = "SELECT genre, book_year, rating, reading_status, added_at " +
                "FROM user_books WHERE user_id = ? AND book_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserBookMetadata meta = new UserBookMetadata(bookId, userId);
                meta.setGenre(rs.getString("genre"));
                meta.setBookYear(rs.getInt("book_year"));
                meta.setBookRating(rs.getInt("rating"));
                meta.setReadingStatus(rs.getBoolean("reading_status"));
                return meta;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска метаданных", e);
        }
    }
}