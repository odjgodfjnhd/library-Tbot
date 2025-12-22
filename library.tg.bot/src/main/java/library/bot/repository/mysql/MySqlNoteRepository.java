package library.bot.repository.mysql;

import library.bot.domain.Note;
import library.bot.repository.NoteRepository;
import library.bot.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNoteRepository implements NoteRepository {
    private final DataSource dataSource;

    public MySqlNoteRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public void save(Note note) {
        String sql = "INSERT INTO notes (note_id, user_id, book_id, book_name, note_text, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, note.getNoteId());
            stmt.setString(2, note.getUserId());
            stmt.setString(3, note.getBookId());
            stmt.setString(4, note.getBookName());
            stmt.setString(5, note.getNoteText());
            stmt.setDate(6, Date.valueOf(note.getNoteCreatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения записки в MySQL", e);
        }
    }

    @Override
    public Note findById(String noteId) {
        String sql = "SELECT note_id, user_id, book_id, book_name, note_text, created_at " +
                "FROM notes WHERE note_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, noteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска записки по ID", e);
        }
    }

    @Override
    public List<Note> findByBookId(String bookId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT note_id, user_id, book_id, book_name, note_text, created_at " +
                "FROM notes WHERE book_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска записок по ID книги", e);
        }
        return notes;
    }

    @Override
    public List<Note> findByUserId(String userId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT note_id, user_id, book_id, book_name, note_text, created_at " +
                "FROM notes WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска записок по ID пользователя", e);
        }
        return notes;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT note_id, user_id, book_id, book_name, note_text, created_at FROM notes";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notes.add(Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех записок", e);
        }
        return notes;
    }

    @Override
    public int getTotalNotes() {
        String sql = "SELECT COUNT(*) FROM notes";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подсчёта записок", e);
        }
    }
}