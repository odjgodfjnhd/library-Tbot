package library.bot.DiaryService;

import library.bot.domain.Book;

public interface DiaryService {
    /**
     * Метод для добавления новой книги читетелю
     * @param userName айди юзера, которому нужно добавить книгу
     * @param bookName книга которую хочет добавить юзер
     * @param authorName имя автора
     * @return сообщение с результатом операции
     */
    void userAddBook(String userName, String bookName, String authorName);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет дать рейтинг книге
     * @param bookId айди книги, которой юзер хочет поставить рейтинг
     * @param bookRating рейтинг, который хочет поставить юзер
     */
    void userAddBookRating(String userId, String bookId, int bookRating);

    /**
     * Метод для добавления года книги
     * @param userId айди юзера, который хочет добавить год
     * @param bookId айди книги
     * @param bookYear год выхода книги
     */
    void userAddBookYear(String userId, String bookId, int bookYear);

    /**
     * Метод для добавления жанра книги
     * @param userId айди юзера, который хочет добавить жанр
     * @param bookId айди книги
     * @param genre жанр книги
     */
    void userAddBookGenre(String userId, String bookId, String genre);

    /**
     * Метод для добавления статуса прочтения книги
     * @param userId айди юзера
     * @param bookId айди книги
     * @param readingStatus статус прочтения
     */
    void userAddReadingStatus(String userId, String bookId, boolean readingStatus);

    /**
     * Метод для создания нового пользователя
     * @param userName имя нового пользователя
     */
    void createNewUser(String userName);
}
