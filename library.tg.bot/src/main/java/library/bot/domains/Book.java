package library.bot.domains;

public class Book
{
    private final String authorName;
    private final String bookName;
    private int userRating;
    private String bookPhotoURL;
    private String userReview;

    public Book(String authorName, String bookName)
    {
        this.authorName = authorName;
        this.bookName = bookName;
        this.userRating = 0;
        this.bookPhotoURL = "";
        this.userReview = "";
    }

    public void setUserRating(int userRating)
    {
        if (userRating >= 1 && userRating <= 5)
        {
            this.userRating = userRating;
        }
    }

    public void setUserReview(String userReview)
    {
        this.userReview = userReview;
    }

    public void setBookPhotoURL(String bookPhotoURL)
    {
        this.bookPhotoURL = bookPhotoURL;
    }

    public int getUserRating()
    {
        return userRating;
    }
    public String getAuthorName()
    {
        return authorName;
    }
    public String getUserReview()
    {
        return userReview;
    }

    public String getBookPhotoURL() {
        return bookPhotoURL;
    }
}
