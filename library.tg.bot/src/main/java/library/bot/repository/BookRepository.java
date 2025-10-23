package library.bot.repository;

import library.bot.domain.Book;

import java.time.LocalDate;
import java.util.List;


public interface BookRepository
{
    void save(Book book); //сохраняет книгу в хранилище

    Book findById(String bookId); //ищет книгу по id

    List<Book> getAllBooks(); //возвращает все книги из хранилища

    List<Book> findByAuthorId(String authorId); //возвращает все книги указанного автора

    List<Book> findByGenre(String genre); //возвращает все книги указанного жанра

    List<Book> findByYear(int year); //возвращает все книги (написанные?) в указанном году

    List<Book> findByAddedDate(LocalDate date); //Возвращает книги по дате добавления

    int getCountOfTotalBooks(); //возвращает количество книг в хранилище
}