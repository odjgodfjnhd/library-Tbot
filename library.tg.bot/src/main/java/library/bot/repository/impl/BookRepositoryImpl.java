package library.bot.repository.impl;

import library.bot.domain.Book;
import library.bot.repository.BookRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final List<Book> books = new ArrayList<>();
    private final HashMap<String, List<String>> userToBooks = new HashMap<>();

    @Override
    public void save(Book book, String userId) {
        if (!books.contains(book))
        {
            books.add(book);
        }
        if (!userToBooks.containsKey(userId))
        {
            userToBooks.put(userId, new ArrayList<>());
        }
        userToBooks.get(userId).add(book.getBookId());


    }

    @Override
    public Book findById(String bookId) {
        for (Book book : books)
        {
            if (book.getBookId().equals(bookId))
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
    public List<Book> findByAuthorId(String authorId) {
        List<Book> authorBooks = new ArrayList<>();

        for (Book book : books)
        {
            if (book.getAuthorId().equals(authorId))
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
    public List<Book> findByAddedDate(LocalDate date) {
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

    public List<Book> getBooksByUserId(String userId)
    {
        List<String> bookIds = userToBooks.get(userId);
        List<Book> booksByUserId = new ArrayList<>();
        for (Book book : books)
        {
            if (bookIds.contains(book.getBookId()))
            {
                booksByUserId.add(book);
            }
        }
        return booksByUserId;
    }
}
