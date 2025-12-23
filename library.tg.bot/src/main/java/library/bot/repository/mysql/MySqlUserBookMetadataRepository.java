package library.bot.repository.mysql;

import library.bot.domain.UserBookMetadata;
import library.bot.repository.UserBookMetadataRepository;

import java.util.List;

public class MySqlUserBookMetadataRepository implements UserBookMetadataRepository {
    private final JdbcHelper jdbc = new JdbcHelper();

    @Override
    public void save(String userId, UserBookMetadata metaData) {
        jdbc.update(
                "INSERT INTO user_books (" +
                        "user_id, book_id, genre, book_year, rating, reading_status" +
                        ") VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "genre = VALUES(genre), " +
                        "book_year = VALUES(book_year), " +
                        "rating = VALUES(rating), " +
                        "reading_status = VALUES(reading_status)",
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setString(2, metaData.getBookId());
                    stmt.setString(3, metaData.getGenre());
                    stmt.setObject(4, metaData.getBookYear() != 0 ? metaData.getBookYear() : null);
                    stmt.setObject(5, metaData.getBookRating() != 0 ? metaData.getBookRating() : null);
                    stmt.setBoolean(6, metaData.getReadingStatus());
                }
        );
    }

    @Override
    public List<String> findBooksByGenre(String userId, String genre) {
        if (genre == null) return List.of();
        return jdbc.queryForList(
                "SELECT book_id FROM user_books WHERE user_id = ? AND LOWER(genre) = LOWER(?)",
                rs -> rs.getString("book_id"),
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setString(2, genre);
                }
        );
    }

    @Override
    public List<String> findBooksByBookYear(String userId, int bookYear) {
        return jdbc.queryForList(
                "SELECT book_id FROM user_books WHERE user_id = ? AND book_year = ?",
                rs -> rs.getString("book_id"),
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setInt(2, bookYear);
                }
        );
    }

    @Override
    public List<String> findBooksByRating(String userId, int bookRating) {
        return jdbc.queryForList(
                "SELECT book_id FROM user_books WHERE user_id = ? AND rating = ?",
                rs -> rs.getString("book_id"),
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setInt(2, bookRating);
                }
        );
    }

    @Override
    public List<String> findBookByReadingStatus(String userId, Boolean readingStatus) {
        if (readingStatus == null) return List.of();
        return jdbc.queryForList(
                "SELECT book_id FROM user_books WHERE user_id = ? AND reading_status = ?",
                rs -> rs.getString("book_id"),
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setBoolean(2, readingStatus);
                }
        );
    }

    @Override
    public UserBookMetadata findBookMetaDataByUserIdAndBookId(String userId, String bookId) {
        return jdbc.queryForObject(
                "SELECT genre, book_year, rating, reading_status " +
                        "FROM user_books WHERE user_id = ? AND book_id = ?",
                rs -> {
                    if (rs.next()) {
                        UserBookMetadata meta = new UserBookMetadata(bookId, userId);
                        meta.setGenre(rs.getString("genre"));
                        meta.setBookYear(rs.getInt("book_year"));
                        meta.setBookRating(rs.getInt("rating"));
                        meta.setReadingStatus(rs.getBoolean("reading_status"));
                        return meta;
                    }
                    return null;
                },
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setString(2, bookId);
                }
        );
    }
}