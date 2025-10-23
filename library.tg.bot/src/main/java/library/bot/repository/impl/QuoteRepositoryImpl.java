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
    public Quote findById(String quoteId) {
        for (Quote quote : quotes)
        {
            if (quote.getNoteId().equals(quoteId))
            {
                return quote;
            }
        }
        return null;
    }

    @Override
    public List<Quote> findByBookId(String bookId) {
        List<Quote> bookIdQuotes = new ArrayList<>();
        for (Quote quote : quotes)
        {
            if (quote.getBookId().equals(bookId))
            {
                bookIdQuotes.add(quote);
            }
        }
        return bookIdQuotes;
    }

    @Override
    public List<Quote> findByUserId(String userId) {
        List<Quote> UserIdQuotes = new ArrayList<>();
        for (Quote quote : quotes)
        {
            if (quote.getUserId().equals(userId))
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
