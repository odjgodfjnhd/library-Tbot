package library.bot.repository.mysql;

import library.bot.domain.Book;
import library.bot.repository.BookRepository;
import library.bot.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlBookRepository implements BookRepository {
    private final DataSource dataSource;

    public MySqlBookRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public void save(Book book, String userId) {
        Book existingBook = findByNameAndAuthor(book.getBookTitle(), book.getAuthorName());
        String bookId;
        if (existingBook == null) {
            bookId = book.getBookId();
            String sql = "INSERT INTO books (book_id, title, author_id, author_name) VALUES (?, ?, ?, ?)";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, bookId);
                stmt.setString(2, book.getBookTitle());
                stmt.setString(3, book.getAuthorId());
                stmt.setString(4, book.getAuthorName());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка сохранения книги", e);
            }
        } else {
            bookId = existingBook.getBookId();
        }

        String linkSql = "INSERT IGNORE INTO user_books (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(linkSql)) {
            stmt.setString(1, userId);
            stmt.setString(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка привязки книги к пользователю", e);
        }
    }

    @Override
    public Book findById(String bookId) {
        String sql = "SELECT book_id, title, author_name, author_id FROM books WHERE book_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Book.fromDatabase(rs.getString("title"), rs.getString("author_name"), rs.getString("author_id"), rs.getString("book_id"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска книги по ID", e);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id, title, author_name FROM books";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                books.add(Book.fromDatabase(rs.getString("title"), rs.getString("author_name"), rs.getString("author_id"), rs.getString("book_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех книг", e);
        }
        return books;
    }

    @Override
    public List<String> findByAuthorId(String authorId) {
        List<String> bookIds = new ArrayList<>();
        String sql = "SELECT book_id FROM books WHERE author_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookIds.add(rs.getString("book_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска книг по ID автора", e);
        }
        return bookIds;
    }

    @Override
    public int getCountOfTotalBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подсчёта общего числа книг", e);
        }
    }

    @Override
    public List<String> getBooksByUserId(String userId) {
        List<String> bookIds = new ArrayList<>();
        String sql = "SELECT book_id FROM user_books WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookIds.add(rs.getString("book_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения книг пользователя", e);
        }
        return bookIds.isEmpty() ? List.of() : bookIds; // точно как в IN_MEMORY
    }

    @Override
    public boolean userHaveBook(String userId, String bookName, String authorName) {
        String normalizedBookName = bookName.trim().toLowerCase();
        String normalizedAuthorName = authorName.trim().toLowerCase();

        String sql = "SELECT b.title, b.author_name FROM user_books ub " +
                "JOIN books b ON ub.book_id = b.book_id " +
                "WHERE ub.user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String dbBookTitle = rs.getString("title").trim().toLowerCase();
                String dbAuthorName = rs.getString("author_name").trim().toLowerCase();
                if (dbBookTitle.equals(normalizedBookName) && dbAuthorName.equals(normalizedAuthorName)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки наличия книги у пользователя", e);
        }
    }

    @Override
    public Book findByNameAndAuthor(String bookName, String authorName) {
        String normalizedBookName = bookName.trim().toLowerCase();
        String normalizedAuthorName = authorName.trim().toLowerCase();

        String sql = "SELECT book_id, title, author_name, author_id FROM books";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String dbBookTitle = rs.getString("title").trim().toLowerCase();
                String dbAuthorName = rs.getString("author_name").trim().toLowerCase();
                if (dbBookTitle.equals(normalizedBookName) && dbAuthorName.equals(normalizedAuthorName)) {
                    return Book.fromDatabase(rs.getString("title"), rs.getString("author_name"), rs.getString("author_id"), rs.getString("book_id"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска книги по названию и автору", e);
        }
    }
}