package library.bot.repository.mysql;

import library.bot.domain.Book;
import library.bot.repository.BookRepository;

import java.util.List;

public class MySqlBookRepository implements BookRepository {
    @Override
    public void save(Book book, String userId) {
        Book existingBook = findByNameAndAuthor(book.getBookTitle(), book.getAuthorName());
        String bookId = (existingBook != null) ? existingBook.getBookId() : book.getBookId();

        if (existingBook == null) {
            JdbcHelper.update(
                    "INSERT INTO books (book_id, title, author_id, author_name) VALUES (?, ?, ?, ?)",
                    stmt -> {
                        stmt.setString(1, bookId);
                        stmt.setString(2, book.getBookTitle());
                        stmt.setString(3, book.getAuthorId());
                        stmt.setString(4, book.getAuthorName());
                    }
            );
        }

        JdbcHelper.update(
                "INSERT IGNORE INTO user_books (user_id, book_id) VALUES (?, ?)",
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setString(2, bookId);
                }
        );
    }

    @Override
    public Book findById(String bookId) {
        return JdbcHelper.queryForObject(
                "SELECT book_id, title, author_name, author_id FROM books WHERE book_id = ?",
                rs -> rs.next()
                        ? Book.fromDatabase(rs.getString("title"), rs.getString("author_name"), rs.getString("author_id"), rs.getString("book_id"))
                        : null,
                stmt -> stmt.setString(1, bookId)
        );
    }

    @Override
    public List<Book> getAllBooks() {
        return JdbcHelper.queryForList(
                "SELECT book_id, title, author_name, author_id FROM books",
                rs -> Book.fromDatabase(rs.getString("title"), rs.getString("author_name"), rs.getString("author_id"), rs.getString("book_id")),
                stmt -> {}
        );
    }

    @Override
    public List<String> findByAuthorId(String authorId) {
        return JdbcHelper.queryForList(
                "SELECT book_id FROM books WHERE author_id = ?",
                rs -> rs.getString("book_id"),
                stmt -> stmt.setString(1, authorId)
        );
    }

    @Override
    public int getCountOfTotalBooks() {
        return JdbcHelper.queryForObject(
                "SELECT COUNT(*) FROM books",
                rs -> rs.next() ? rs.getInt(1) : 0,
                stmt -> {}
        );
    }

    @Override
    public List<String> getBooksByUserId(String userId) {
        List<String> bookIds = JdbcHelper.queryForList(
                "SELECT book_id FROM user_books WHERE user_id = ?",
                rs -> rs.getString("book_id"),
                stmt -> stmt.setString(1, userId)
        );
        return bookIds.isEmpty() ? List.of() : bookIds;
    }

    @Override
    public boolean userHaveBook(String userId, String bookName, String authorName) {
        String normalizedBookName = bookName.trim().toLowerCase();
        String normalizedAuthorName = authorName.trim().toLowerCase();

        Integer result = JdbcHelper.queryForObject(
                "SELECT EXISTS(" +
                        "   SELECT 1 FROM user_books ub " +
                        "   JOIN books b ON ub.book_id = b.book_id " +
                        "   WHERE ub.user_id = ? " +
                        "     AND LOWER(TRIM(b.title)) = LOWER(?) " +
                        "     AND LOWER(TRIM(b.author_name)) = LOWER(?)" +
                        ")",
                rs -> rs.next() ? rs.getInt(1) : 0,
                stmt -> {
                    stmt.setString(1, userId);
                    stmt.setString(2, normalizedBookName);
                    stmt.setString(3, normalizedAuthorName);
                }
        );

        return result == 1;
    }

    @Override
    public Book findByNameAndAuthor(String bookName, String authorName) {
        String normalizedBookName = bookName.trim().toLowerCase();
        String normalizedAuthorName = authorName.trim().toLowerCase();

        return JdbcHelper.queryForObject(
                "SELECT book_id, title, author_name, author_id FROM books " +
                        "WHERE LOWER(TRIM(title)) = ? AND LOWER(TRIM(author_name)) = ?",
                rs -> rs.next()
                        ? Book.fromDatabase(
                        rs.getString("title"),
                        rs.getString("author_name"),
                        rs.getString("author_id"),
                        rs.getString("book_id")
                )
                        : null,
                stmt -> {
                    stmt.setString(1, normalizedBookName);
                    stmt.setString(2, normalizedAuthorName);
                }
        );
    }
}