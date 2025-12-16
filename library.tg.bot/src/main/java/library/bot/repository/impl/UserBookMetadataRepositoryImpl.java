package library.bot.repository.impl;

import library.bot.domain.UserBookMetadata;
import library.bot.repository.UserBookMetadataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserBookMetadataRepositoryImpl implements UserBookMetadataRepository {

    private final HashMap<String, ArrayList<UserBookMetadata>> usersToMetadata =
            new HashMap<>();

    @Override
    public void save(String userId, UserBookMetadata metaData) {
        usersToMetadata.computeIfAbsent(userId, k -> new ArrayList<>())
                .add(metaData);
    }

    @Override
    public List<String> findBooksByGenre(String userId, String genre) {
        ArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        ArrayList<String> bookIds = new ArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (genre != null && genre.equalsIgnoreCase(meta.getGenre())) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBooksByBookYear(String userId, int bookYear) {
        ArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        ArrayList<String> bookIds = new ArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (meta.getBookYear() == bookYear) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBooksByRating(String userId, int bookRating) {
        ArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        ArrayList<String> bookIds = new ArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (meta.getBookRating() == bookRating) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBookByReadingStatus(String userId, Boolean readingStatus) {
        ArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        ArrayList<String> bookIds = new ArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (readingStatus != null && readingStatus.equals(meta.getReadingStatus())) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public UserBookMetadata findBookMetaDataByUserIdAndBookId(String userId, String bookId) {
        ArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null) {
            return null;
        }

        for (UserBookMetadata meta : userBooksMetadata) {
            if (bookId.equals(meta.getBookId())) {
                return meta;
            }
        }
        return null;
    }
}