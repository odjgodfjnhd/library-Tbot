package library.bot.repository;

import library.bot.domain.Quote;
import java.util.List;

public interface QuoteRepository
{
    void save(Quote quote);

    Quote findById(int quoteId);

    List<Quote> findByBookId(int bookId);

    List<Quote> findByUserId(int userId);

    List<Quote> findByPage(int page); //Не уверен, что это нужно

    List<Quote> getAllQuotes();

    int getTotalQuotes();
}