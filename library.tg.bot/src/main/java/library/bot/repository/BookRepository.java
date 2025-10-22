package library.bot.repository;

import library.bot.domain.Book;
import java.util.List;


public interface BookRepository
{
    void save(Book book); //сохраняет книгу в хранилище

    Book findById(int bookId); //ищет книгу по id

    List<Book> getAllBooks(); //возвращает все книги из хранилища

    List<Book> findByAuthorId(int authorId); //возвращает все книги указанного автора

    List<Book> findByGenre(String genre); //возвращает все книги указанного жанра

    List<Book> findByYear(int year); //возвращает все книги (написанные?) в указанном году

    List<Book> findByAddedDate(String date); //Возвращает книги по дате добавления

    int getCountOfTotalBooks(); //возвращает количество книг в хранилище
}