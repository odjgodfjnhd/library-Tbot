package library.bot.repository.mysql;

import library.bot.domain.Note;
import library.bot.repository.NoteRepository;

import java.sql.Date;
import java.util.List;

public class MySqlNoteRepository implements NoteRepository {
    private final JdbcHelper jdbc = new JdbcHelper();

    @Override
    public void save(Note note) {
        jdbc.update(
                "INSERT INTO notes (note_id, user_id, book_id, book_name, note_text, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                stmt -> {
                    stmt.setString(1, note.getNoteId());
                    stmt.setString(2, note.getUserId());
                    stmt.setString(3, note.getBookId());
                    stmt.setString(4, note.getBookName());
                    stmt.setString(5, note.getNoteText());
                    stmt.setDate(6, Date.valueOf(note.getNoteCreatedAt()));
                }
        );
    }

    @Override
    public Note findById(String noteId) {
        return jdbc.queryForObject(
                "SELECT note_id, user_id, book_id, book_name, note_text, created_at " +
                        "FROM notes WHERE note_id = ?",
                rs -> rs.next()
                        ? Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                )
                        : null,
                stmt -> stmt.setString(1, noteId)
        );
    }

    @Override
    public List<Note> findByBookId(String bookId) {
        return jdbc.queryForList(
                "SELECT note_id, user_id, book_id, book_name, note_text, created_at " +
                        "FROM notes WHERE book_id = ?",
                rs -> Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                ),
                stmt -> stmt.setString(1, bookId)
        );
    }

    @Override
    public List<Note> findByUserId(String userId) {
        return jdbc.queryForList(
                "SELECT note_id, user_id, book_id, book_name, note_text, created_at " +
                        "FROM notes WHERE user_id = ?",
                rs -> Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                ),
                stmt -> stmt.setString(1, userId)
        );
    }

    @Override
    public List<Note> getAllNotes() {
        return jdbc.queryForList(
                "SELECT note_id, user_id, book_id, book_name, note_text, created_at FROM notes",
                rs -> Note.fromDatabase(
                        rs.getString("note_id"),
                        rs.getString("book_name"),
                        rs.getString("book_id"),
                        rs.getString("user_id"),
                        rs.getString("note_text"),
                        rs.getDate("created_at").toLocalDate()
                ),
                stmt -> {}
        );
    }

    @Override
    public int getTotalNotes() {
        return jdbc.queryForObject(
                "SELECT COUNT(*) FROM notes",
                rs -> rs.next() ? rs.getInt(1) : 0,
                stmt -> {}
        );
    }
}