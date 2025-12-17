package library.bot.repository.impl;

import library.bot.domain.Book;
import library.bot.repository.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookRepositoryImpl implements BookRepository {

    private final List<Book> books = new CopyOnWriteArrayList<>();
    private final HashMap<String, ArrayList<String>> userToBooks =
            new HashMap<>();

    @Override
    public void save(Book book, String userId) {
        if (!books.contains(book)) {
            books.add(book);
        }

        userToBooks.computeIfAbsent(userId, k -> new ArrayList<>())
                .add(book.getBookId());
    }

    @Override
    public Book findById(String bookId) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
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
    public List<String> findByAuthorId(String authorId) {
        List<String> authorBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthorId().equals(authorId)) {
                authorBooks.add(book.getBookId());
            }
        }
        return authorBooks;
    }

    @Override
    public int getCountOfTotalBooks() {
        return books.size();
    }

    @Override
    public List<String> getBooksByUserId(String userId) {
        List<String> bookIds = userToBooks.get(userId);
        return bookIds != null ? bookIds : List.of();
    }

    @Override
    public boolean userHaveBook(String userId, String bookName, String authorName) {
        List<String> bookIds = getBooksByUserId(userId);
        if (bookIds.isEmpty()) {
            return false;
        }
        for (String bookId : bookIds) {
            Book book = findById(bookId);
            if (book != null &&
                    book.getBookTitle().equals(bookName) &&
                    book.getAuthorName().equalsIgnoreCase(authorName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Book findByNameAndAuthor(String bookName, String authorName) {
        for (Book book : books) {
            if (book.getBookTitle().trim().equalsIgnoreCase(bookName) &&
                    book.getAuthorName().trim().equalsIgnoreCase(authorName)) {
                return book;
            }
        }
        return null;
    }
}