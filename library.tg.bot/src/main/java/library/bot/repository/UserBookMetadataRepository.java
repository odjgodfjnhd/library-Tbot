package library.bot.repository;

import library.bot.domain.Book;
import library.bot.domain.UserBookMetadata;

import java.util.List;

public interface UserBookMetadataRepository {
    void save(String userId, UserBookMetadata metaData);
    List<String> findBooksByGenre(String userId, String genre);
    List<String> findBooksByBookYear(String userId, int bookYear);
    List<String> findBooksByRating(String userId, int bookRating);
    List<String> findBookByReadingStatus(String userId, Boolean readingStatus);
    UserBookMetadata findBookMetaDataByUserIdAndBookId(String userId, String bookId);
}
