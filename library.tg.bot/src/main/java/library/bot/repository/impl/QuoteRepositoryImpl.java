package library.bot.repository.impl;

public class NoteRepositoryImpl implements NoteRepository {
    private final List<Note> notes = new ArrayList<>();

    @Override
    public void save(Note note) {
        notes.add(note);
    }

    @Override
    public Note findById(String noteId) {
        for (Note note : notes) {
            if (note.getNoteId().equals(noteId)) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<Note> findByBookId(String bookId) {
        List<Note> bookIdNotes = new ArrayList<>();
        for (Note note : notes) {
            if (note.getBookId().equals(bookId)) {
                bookIdNotes.add(note);
            }
        }
        return bookIdNotes;
    }

    @Override
    public List<Note> findByUserId(String userId) {
        List<Note> userIdNotes = new ArrayList<>();
        for (Note note : notes) {
            if (note.getUserId().equals(userId)) {
                userIdNotes.add(note);
            }
        }
        return userIdNotes;
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
