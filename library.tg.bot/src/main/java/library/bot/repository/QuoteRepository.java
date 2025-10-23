package library.bot.repository;

import library.bot.domain.Quote;
import java.util.List;

public interface QuoteRepository
{
    void save(Quote quote);

    Quote findById(String quoteId);

    List<Quote> findByBookId(String bookId);

    List<Quote> findByUserId(String userId);

    List<Quote> getAllQuotes();

    int getTotalQuotes();
}