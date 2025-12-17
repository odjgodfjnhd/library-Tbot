package library.bot.DiaryService;

import library.bot.domain.Book;
import library.bot.domain.Note;

import java.util.List;

public interface DiaryService {
    /**
     * Метод для добавления новой книги читетелю
     * @param userName айди юзера, которому нужно добавить книгу
     * @param bookName книга которую хочет добавить юзер
     * @param authorName имя автора
     */
    void userAddBook(String userName, String bookName, String authorName);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет дать рейтинг книге
     * @param bookId айди книги, которой юзер хочет поставить рейтинг
     * @param bookRating рейтинг книги
     */
    void userAddBookRating(String userId, String bookId, int bookRating);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет добавить год
     * @param bookId айди книги, которой юзер хочет поставить год
     * @param bookYear год выхода книги
     */
    void userAddBookYear(String userId, String bookId, int bookYear);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет добавить жанр
     * @param bookId айди книги, которой юзер хочет поставить жанр
     * @param genre жанр книги
     */
    void userAddBookGenre(String userId, String bookId, String genre);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет добавить жанр
     * @param bookId айди книги, которой юзер хочет поставить жанр
     * @param readingStatus статус, который хочет юзер поставить книге
     */
    void userAddReadingStatus(String userId, String bookId, boolean readingStatus);

    /**
     * Метод для создания нового пользователя
     * @param userName имя нового пользователя
     */
    void createNewUser(String userName);

    void addNoteToBook(String userId, String bookName, String authorName, String noteText);
    List<Note> getUserNotesForBook(String userId, String bookName, String authorName);
}
