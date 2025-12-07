package library.bot.repository.impl;

import library.bot.domain.UserBookMetadata;
import library.bot.repository.UserBookMetadataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserBookMetadataRepositoryImpl implements UserBookMetadataRepository {

    HashMap<String, List<UserBookMetadata>> usersToMetadata = new HashMap<>();

    @Override
    public void save(String userId, UserBookMetadata metaData) {
        if (!usersToMetadata.containsKey(userId)) {
            usersToMetadata.put(userId, new ArrayList<>());
        }
        usersToMetadata.get(userId).add(metaData);
    }

    @Override
    public List<String> findBooksByGenre(String userId, String genre) {
        List<String> bookIds = new ArrayList<>();
        List<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null) return bookIds;
        for (UserBookMetadata bookMetadata : userBooksMetadata) {
            if (bookMetadata.getGenre() != null && bookMetadata.getGenre().equalsIgnoreCase(genre)) {
                bookIds.add(bookMetadata.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBooksByBookYear(String userId, int bookYear) {
        List<String> bookIds = new ArrayList<>();
        List<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null) return bookIds;
        for (UserBookMetadata bookMetadata : userBooksMetadata) {
            String yearStr = String.valueOf(bookMetadata.getBookYear());
            try {
                int y = Integer.parseInt(yearStr);
                if (y == bookYear) {
                    bookIds.add(bookMetadata.getBookId());
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBooksByRating(String userId, int bookRating) {
        List<String> bookIds = new ArrayList<>();
        List<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null) return bookIds;

        for (UserBookMetadata meta : userBooksMetadata) {
            if (meta.getBookRating() == bookRating) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBookByReadingStatus(String userId, Boolean readingStatus) {
        List<String> bookIds = new ArrayList<>();
        List<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null) return bookIds;
        for (UserBookMetadata bookMetadata : userBooksMetadata) {
            String metaStatus = String.valueOf(bookMetadata.getReadingStatus());
            if (metaStatus.equalsIgnoreCase(String.valueOf(readingStatus))) {
                bookIds.add(bookMetadata.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public UserBookMetadata findBookMetaDataByUserIdAndBookId(String userId, String bookId) {
        List<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null) return null;
        for (UserBookMetadata bookMetadata : userBooksMetadata) {
            if (bookMetadata.getBookId().equals(bookId)) {
                return bookMetadata;
            }
        }
        return null;
    }
}
