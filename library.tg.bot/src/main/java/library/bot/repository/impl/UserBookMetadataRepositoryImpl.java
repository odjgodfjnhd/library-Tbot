package library.bot.repository.impl;

import library.bot.domain.UserBookMetadata;
import library.bot.repository.UserBookMetadataRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserBookMetadataRepositoryImpl implements UserBookMetadataRepository {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<UserBookMetadata>> usersToMetadata =
            new ConcurrentHashMap<>();

    @Override
    public void save(String userId, UserBookMetadata metaData) {
        usersToMetadata.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                .add(metaData);
    }

    @Override
    public List<String> findBooksByGenre(String userId, String genre) {
        CopyOnWriteArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        CopyOnWriteArrayList<String> bookIds = new CopyOnWriteArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (genre != null && genre.equalsIgnoreCase(meta.getGenre())) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBooksByBookYear(String userId, int bookYear) {
        CopyOnWriteArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        CopyOnWriteArrayList<String> bookIds = new CopyOnWriteArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (meta.getBookYear() == bookYear) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBooksByRating(String userId, int bookRating) {
        CopyOnWriteArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        CopyOnWriteArrayList<String> bookIds = new CopyOnWriteArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (meta.getBookRating() == bookRating) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public List<String> findBookByReadingStatus(String userId, Boolean readingStatus) {
        CopyOnWriteArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
        if (userBooksMetadata == null || userBooksMetadata.isEmpty()) {
            return List.of();
        }

        CopyOnWriteArrayList<String> bookIds = new CopyOnWriteArrayList<>();
        for (UserBookMetadata meta : userBooksMetadata) {
            if (readingStatus != null && readingStatus.equals(meta.getReadingStatus())) {
                bookIds.add(meta.getBookId());
            }
        }
        return bookIds;
    }

    @Override
    public UserBookMetadata findBookMetaDataByUserIdAndBookId(String userId, String bookId) {
        CopyOnWriteArrayList<UserBookMetadata> userBooksMetadata = usersToMetadata.get(userId);
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