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
    String userAddBook(String userName, String bookName, String authorName);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет дать рейтинг книге
     * @param bookId айди книги, которой юзер хочет поставить рейтинг
     * @return сообщение с результатом операции
     */
    String userAddBookRating(String userId, String bookId, int bookRating);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет добавить год
     * @param bookId айди книги, которой юзер хочет поставить год
     * @return сообщение с результатом операции
     */
    void userAddBookYear(String userId, String bookId, int bookYear);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет добавить жанр
     * @param bookId айди книги, которой юзер хочет поставить жанр
     * @return сообщение с результатом операции
     */
    void userAddBookGenre(String userId, String bookId, String genre);

    /**
     * Метод для добавления цитаты юзера о книге
     * @param userId айди юзера, который хочет добавить жанр
     * @param bookId айди книги, которой юзер хочет поставить жанр
     * @param readingStatus статус, который хочет юзер поставить книге
     * @return сообщение с результатом операции
     */
    void userAddReadingStatus(String userId, String bookId, boolean readingStatus);

    String createNewUser(String userName);
}
