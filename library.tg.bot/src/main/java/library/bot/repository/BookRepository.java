package library.bot.repository;

import library.bot.domain.Book;

import java.time.LocalDate;
import java.util.List;


public interface BookRepository
{
    void save(Book book, String userId); //сохраняет книгу в хранилище

    Book findById(String bookId); //ищет книгу по id

    List<Book> getAllBooks(); //возвращает все книги из хранилища

    List<String> findByAuthorId(String authorId); //возвращает все книги указанного автора

    int getCountOfTotalBooks(); //возвращает количество книг в хранилище

    List<String> getBooksByUserId(String userId);

    boolean userHaveBook(String userId, String bookName, String authorName);

    Book findByNameAndAuthor(String bookName, String authorName);
}