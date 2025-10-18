package library.bot.repository.impl;

import library.bot.domain.Book;
import library.bot.domain.Author;
import library.bot.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository
{
    private final List<Book> books = new ArrayList<>();
    private int lastId = 0; //первая книга получит нулевой id

    @Override
    public void save(Book book)
    {
        book.setId(lastId);
        books.add(book);
        lastId++;
    }

    @Override
    public Book findById(int bookId) {

        int storageSize = books.size();

        for (int i = 0; i < storageSize; i++)
        {
            Book book = books.get(i);
            if (book.getBookId() == bookId)
            {
                return book;
            }
        }

        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override //в процессе
    public List<Book> findByAuthor(Author author) {

    }

    @Override
    public List<Book> findByGenre(String bookGenre) {

        int storageSize = books.size();
        List<Book> result = new ArrayList<>();

        for (int i = 0; i < storageSize; i++) {
            Book book = books.get(i);
            if (book.getGenre() == bookGenre)
            {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> findByYear(int writingYear) {

        int storageSize = books.size();
        List<Book> result = new ArrayList<>();

        for (int i = 0; i < storageSize; i++) {
            Book book = books.get(i);
            if (book.getBookYear() == writingYear)
            {
                result.add(book);
            }
        }
        return result;
    }


    @Override
    public List<Book> findByAddedDate(String date) //в процессе
    {

    }

    @Override
    public int getTotalBooks() //точно ли нужна?
    {
        return books.size();
    }
}
