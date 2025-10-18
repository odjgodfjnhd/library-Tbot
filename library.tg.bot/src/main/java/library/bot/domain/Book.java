package library.bot.domain;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book
{
    private final String title; //Поля с конструктором я перелопатил
    private int id = -1;
    private Author author;
    private final String genre;
    private double rating = 0;
    private int writingYear;
    private String addedAt;
    private int pages;
    private final String translator; //потомок от author -?


    public Book(String title, Author author, String genre, String translator, int year, int pages)
    {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.translator = translator;
        this.addedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"));
        this.writingYear = writingYear;
        this.pages = pages;


    }

    public int getBookId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public int getBookPages() {
        return pages;
    }

    public String getBookAddedAt() {
        return addedAt;
    }

    public String getBookTitle() {
        return title;
    }

    public double getBookRating() {
        return rating;
    }

    public int getBookYear() {
        return writingYear;
    }

    public String getBookTranslator() {
        return translator;
    }

    public String getGenre() {
        return genre;
    }

    public void setId(int id)
    {
     this.id = id;
    }

    public void setBookRating(double rating) {
        if (0 <=rating & 5 >= rating)
        {
            this.rating = rating;
        }
    }
}
