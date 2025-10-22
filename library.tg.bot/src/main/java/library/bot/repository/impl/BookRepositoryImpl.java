package library.bot.repository.impl;

import library.bot.domain.Book;
import library.bot.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final List<Book> books = new ArrayList<>();

    @Override
    public void save(Book book) {
        books.add(book);

    }

    @Override
    public Book findById(int bookId) {
        for (Book book : books)
        {
            if (book.getBookId() == bookId)
            {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public List<Book> findByAuthorId(int authorId) {
        List<Book> authorBooks = new ArrayList<>();

        for (Book book : books)
        {
            if (book.getAuthorId() == authorId)
            {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }

    @Override
    public List<Book> findByGenre(String genre) {
        List<Book> genreBooks = new ArrayList<>();
        for (Book book : books)
        {
            if (book.getGenre().equals(genre))
            {
                genreBooks.add(book);
            }
        }
        return genreBooks;
    }

    @Override
    public List<Book> findByYear(int year) {
        List<Book> yearBooks = new ArrayList<>();
        for (Book book : books)
        {
            if (book.getBookYear() == year)
            {
                yearBooks.add(book);
            }
        }
        return yearBooks;
    }

    @Override
    public List<Book> findByAddedDate(String date) {
        List<Book> dateBooks = new ArrayList<>();
        for (Book book : books)
        {
            if (book.getBookAddedAt().equals(date))
            {
                dateBooks.add(book);
            }
        }
        return dateBooks;

    }

    @Override
    public int getCountOfTotalBooks() {
        return books.size();
    }
}
