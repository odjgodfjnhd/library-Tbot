package library.bot.repository;

import library.bot.domain.Note;
import java.util.List;

public interface NoteRepository
{
    void save(Note note);

    Note findById(int noteId);

    List<Note> findByBookId(int bookId);

    List<Note> findByUserId(int userId);

    List<Note> getAllNotes();

    int getTotalNotes();
}