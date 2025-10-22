package library.bot.repository.impl;

import library.bot.domain.Quote;
import library.bot.domain.User;
import library.bot.repository.QuoteRepository;

import java.util.ArrayList;
import java.util.List;

public class QuoteRepositoryImpl implements QuoteRepository {
    private final List<Quote> quotes = new ArrayList<>();
    @Override
    public void save(Quote quote) {
        quotes.add(quote);
    }

    @Override
    public Quote findById(int quoteId) {
        for (Quote quote : quotes)
        {
            if (quote.getNoteId() == quoteId)
            {
                return quote;
            }
        }
        return null;
    }

    @Override
    public List<Quote> findByBookId(int bookId) {
        List<Quote> bookIdQuotes = new ArrayList<>();
        for (Quote quote : quotes)
        {
            if (quote.getBookId() == bookId)
            {
                bookIdQuotes.add(quote);
            }
        }
        return bookIdQuotes;
    }

    @Override
    public List<Quote> findByUserId(int userId) {
        List<Quote> UserIdQuotes = new ArrayList<>();
        for (Quote quote : quotes)
        {
            if (quote.getUserId() == userId)
            {
                UserIdQuotes.add(quote);
            }
        }
        return UserIdQuotes;
    }

    @Override
    public List<Quote> getAllQuotes() {
        return quotes;
    }

    @Override
    public int getTotalQuotes() {
        return quotes.size();
    }
}
