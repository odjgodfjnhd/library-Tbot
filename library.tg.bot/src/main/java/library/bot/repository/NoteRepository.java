package library.bot.repository;

import library.bot.domain.Note;
import java.util.List;

public interface NoteRepository {
    void save(Note note);

    Note findById(String noteId);

    List<Note> findByBookId(String bookId);

    List<Note> findByUserId(String userId);

    List<Note> getAllNotes();

    int getTotalNotes();
}
