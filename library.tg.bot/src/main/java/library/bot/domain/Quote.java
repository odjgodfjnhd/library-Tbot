package library.bot.domain;
public class Quote extends Note{
    private int quotePage;
    public Quote(String bookName, String noteText, int quotePage) {
        super(bookName, noteText);
        this.quotePage = quotePage;
    }
}
