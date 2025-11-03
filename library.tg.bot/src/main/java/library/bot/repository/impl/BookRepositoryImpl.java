package library.bot.repository.impl;

public class BookRepositoryImpl implements BookRepository {
    private final List<Book> books = new ArrayList<>();
    private final HashMap<String, List<String>> userToBooks = new HashMap<>();

    @Override
    public void save(Book book, String userId) {
        if (!books.contains(book)) {
            books.add(book);
        }
        if (!userToBooks.containsKey(userId)) {
            userToBooks.put(userId, new ArrayList<String>());
        }
        userToBooks.get(userId).add(book.getBookId());
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
        return userToBooks.get(userId);
    }

    @Override
    public boolean userHaveBook(String userId, String bookName, String authorName) {
        List<String> bookIds = getBooksByUserId(userId);
        if (bookIds == null) {
            return false;
        }
        for (String bookId : bookIds) {
            Book book = findById(bookId);
            if (book.getBookTitle().equals(bookName) & book.getAuthorName().equalsIgnoreCase(authorName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Book findByNameAndAuthor(String bookName, String authorName) {
        for (Book book : books) {
            if (book.getBookTitle().equals(bookName) & book.getAuthorName().equalsIgnoreCase(authorName)) {
                return book;
            }
        }
        return null;
    }
}
