package library.bot.utils;

import library.bot.domain.Book;
import library.bot.domain.User;
import library.bot.domain.UserBookMetadata;

import java.time.format.DateTimeFormatter;

public interface Utils {

    public static void askUserName() {
        System.out.print("Введите свой userName: ");
    }

    public static void askBookName(String userName) {
        System.out.print("Отлично, " + userName + "! Теперь введите название книги: ");
    }

    public static void askAuthorName(String bookName) {
        System.out.print("Хорошо, но для книги \"" + bookName + "\" нужен автор. Введите автора книги: ");
    }

    public static class Formatter {

        public static String buildBookInfoFull(User user, Book book, UserBookMetadata meta) {
            StringBuilder sb = new StringBuilder();
            sb.append("Название книги: ").append(book.getBookTitle()).append("\n");
            sb.append("Автор книги: ").append(book.getAuthorName()).append("\n");
            sb.append("Дата добавления книги: ")
                    .append(meta.getBookAddedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))).append("\n");
            sb.append("Жанр книги: ").append(meta.getGenre()).append("\n");
            sb.append("Год выхода книги: ")
                    .append(meta.getBookYear() != 0 ? meta.getBookYear() : "Вы ещё не указали год выхода этой книги").append("\n");
            sb.append("Переводчик книги: ").append(meta.getBookTranslator()).append("\n");
            sb.append("Ваш персональный рейтинг этой книге: ")
                    .append(meta.getBookRating() != 0 ? meta.getBookRating() : "Вы ещё не поставили оценку этой книге").append("\n");
            sb.append("Прогресс чтения: ").append(meta.getReadingStatus() ? "Книга прочитана" : "Книга не прочитана");
            return sb.toString();
        }

        public static String buildBookRatingInfo(Book book, UserBookMetadata meta) {
            StringBuilder sb = new StringBuilder();
            sb.append("Название книги: ").append(book.getBookTitle()).append("\n");
            sb.append("Ваш персональный рейтинг этой книге: ")
                    .append(meta.getBookRating() != 0 ? meta.getBookRating() : "Вы ещё не поставили оценку этой книге");
            return sb.toString();
        }

        public static String buildHelpCommands() {
            return """
                Доступные команды:
                  /add_book             -  добавить книгу
                  /create_user          -  добавить читетеля
                  /show_book_info       -  показать информацию о моей книге
                  /rate_book            -  поставить рейтинг книге
                  /set_year             -  указать год выпуска книге
                  /set_genre            -  указать, какого жанра книга
                  /change_status        -  поменять статус книге
                  /show_authors         -  показать, каких авторов я читаю
                  /show_done_books      -  показать, какие книги я прочитал
                  /show_undone_books    -  показать, какие книги я ещё не прочитал
                  /show_books_rated_on  -  Показать книги, которые были оценены на конкретый балл
                  /help                 - Вывести /help
                """;
        }
    }
}
