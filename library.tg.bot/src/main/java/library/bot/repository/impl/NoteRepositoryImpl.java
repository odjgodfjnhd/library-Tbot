package library.bot.repository.impl;

import library.bot.domain.Book;
import library.bot.domain.Note;
import library.bot.domain.Quote;
import library.bot.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    private final List<Note> notes = new ArrayList<>();
    @Override
    public void save(Note note) {
        notes.add(note);
    }

    @Override
    public Note findById(int noteId) {
        for (Note note : notes)
        {
            if (note.getNoteId() == noteId)
            {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<Note> findByBookId(int bookId) {
        List<Note> bookIdNotes = new ArrayList<>();
        for (Note note : notes)
        {
            if (note.getBookId() == bookId)
            {
                bookIdNotes.add(note);
            }
        }
        return bookIdNotes;
    }

    @Override
    public List<Note> findByUserId(int userId) {
        List<Note> UserIdNotes = new ArrayList<>();
        for (Note note : notes)
        {
            if (note.getUserId() == userId)
            {
                UserIdNotes.add(note);
            }
        }
        return UserIdNotes;
    }

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }

    @Override
    public int getTotalNotes() {
        return notes.size();
    }
}
