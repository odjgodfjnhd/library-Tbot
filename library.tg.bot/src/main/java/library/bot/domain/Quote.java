package library.bot.domain;
public class Quote extends Note{
    private int quotePage = 0;
    public Quote(String bookName, String bookId, String userId, String quoteText) {
        super(bookName, bookId, userId, quoteText);
    }
    public void setQuotePage(int page)
    {
        this.quotePage = page;
    }

    public int getQuotePage() {
        return quotePage;
    }
}
