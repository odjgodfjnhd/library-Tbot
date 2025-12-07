package library.bot.handlers;

import library.bot.handlers.SessionManager;
import library.bot.components.service.UserMessageService;

public class StateHandler {
    private final UserMessageService messageService;
    private final SessionManager sessionManager;

    public StateHandler(UserMessageService messageService, SessionManager sessionManager) {
        this.messageService = messageService;
        this.sessionManager = sessionManager;
    }

    public String handleState(Long chatId, String state, String input) {
        switch (state) {
            //Блок состояний для создания пользователя//
            case "WAITING_USER_NAME_FOR_CREATE": {
                sessionManager.registerUser(chatId, input);
                sessionManager.clearSession(chatId);
                return messageService.createUser(input);
            }
            //Тут завершается набор состояний для регистрации пользователя//

            //Блок состояний для добавления книги пользователю//
            case "WAITING_BOOK_NAME_FOR_ADD_BOOK": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_ADD_BOOK");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_ADD_BOOK": {
                String bookName = sessionManager.getSession(chatId).getData("bookName");
                sessionManager.clearSession(chatId);
                String userName = sessionManager.getUserNameByChatId(chatId);
                return messageService.addBook(userName, bookName, input);
            }
            //Тут завершается набор состояний для добавления книги пользователю//

            //Блок состояний для установления рейтинга книге//
            case "WAITING_BOOK_NAME_FOR_RATE": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_RATE");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_RATE": {
                sessionManager.getSession(chatId).putData("authorName", input);
                sessionManager.getSession(chatId).setState("WAITING_RATING_FOR_BOOK");
                return "Оцените книгу от 1 до 5:";
            }

            case "WAITING_RATING_FOR_BOOK": {
                String bookName = sessionManager.getSession(chatId).getData("bookName");
                String authorName = sessionManager.getSession(chatId).getData("authorName");

                try {
                    int rating = Integer.parseInt(input.trim());
                    if (rating < 1 || rating > 5) {
                        sessionManager.clearSession(chatId);
                        return "❌ Рейтинг должен быть от 1 до 5.\n" +
                                "Попробуйте поставить оценку книге снова через /rate_book.";
                    }
                    sessionManager.clearSession(chatId);
                    String userName = sessionManager.getUserNameByChatId(chatId);
                    return messageService.rateBook(userName, bookName, authorName, rating);
                } catch (NumberFormatException e) {
                    sessionManager.clearSession(chatId);
                    return "❌ Введите целое число от 1 до 5.\n" +
                            "Попробуйте поставить оценку книге снова через /rate_book.";
                }
            }
            //Тут завершается набор состояний для установления рейтинга книге//

            //Блок состояний для вывода подробной информации по книге//
            case "WAITING_BOOK_NAME_FOR_SHOW_INFO": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_SHOW_INFO");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_SHOW_INFO": {
                String bookName = sessionManager.getSession(chatId).getData("bookName");
                sessionManager.clearSession(chatId);
                String userName = sessionManager.getUserNameByChatId(chatId);
                return messageService.showBookInfo(userName, bookName, input);
            }
            //Тут завершается набор состояний для вывода подробной информации по книге//

            //Блок состояний для добавления жанра книге//
            case "WAITING_BOOK_NAME_FOR_ADD_GENRE": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_ADD_GENRE");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_ADD_GENRE": {
                sessionManager.getSession(chatId).putData("authorName", input);
                sessionManager.getSession(chatId).setState("WAITING_GENRE_FOR_BOOK");
                return "Введите жанр книги (например: роман, фантастика, детектив):";
            }

            case "WAITING_GENRE_FOR_BOOK": {
                String bookName = sessionManager.getSession(chatId).getData("bookName");
                String authorName = sessionManager.getSession(chatId).getData("authorName");
                sessionManager.clearSession(chatId);
                String userName = sessionManager.getUserNameByChatId(chatId);
                return messageService.addBookGenre(userName, bookName, authorName, input);
            }
            //Тут завершается набор состояний для добавления жанра книге//

            //Блок состояний для добавления года издания книги
            case "WAITING_BOOK_NAME_FOR_ADD_YEAR": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_ADD_YEAR");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_ADD_YEAR": {
                sessionManager.getSession(chatId).putData("authorName", input);
                sessionManager.getSession(chatId).setState("WAITING_YEAR_FOR_BOOK");
                return "Введите год издания книги (например: 1967):";
            }

            case "WAITING_YEAR_FOR_BOOK": {
                String bookName = sessionManager.getSession(chatId).getData("bookName");
                String authorName = sessionManager.getSession(chatId).getData("authorName");

                try {
                    int year = Integer.parseInt(input.trim());
                    // Опционально: проверка разумного диапазона
                    if (year < 1000 || year > 2100) {
                        return "❌ Укажите корректный год (от 1000 до 2100).\n" +
                                "Попробуйте снова через /add_book_year.";
                    }
                    sessionManager.clearSession(chatId);
                    String userName = sessionManager.getUserNameByChatId(chatId);
                    return messageService.addBookYear(userName, bookName, authorName, year);
                } catch (NumberFormatException e) {
                    return "❌ Введите целое число (например: 1967).\n" +
                            "Попробуйте снова через /add_book_year.";
                }
            }
            //Тут завершается набор состояний для добавления года издания книги

            //Блок состояний для установки статуса книги
            case "WAITING_BOOK_NAME_FOR_READ_STATUS": {
                sessionManager.getSession(chatId).putData("bookName", input);
                sessionManager.getSession(chatId).setState("WAITING_AUTHOR_NAME_FOR_READ_STATUS");
                return "✍️ Введите имя автора:";
            }

            case "WAITING_AUTHOR_NAME_FOR_READ_STATUS": {
                sessionManager.getSession(chatId).putData("authorName", input);
                sessionManager.getSession(chatId).setState("WAITING_READ_STATUS");
                return "Выберите статус:\n" +
                        "• `прочитана` — если вы завершили чтение\n" +
                        "• `не прочитана` — если ещё не читали или в процессе";
            }

            case "WAITING_READ_STATUS": {
                String lowerInput = input.trim().toLowerCase();
                Boolean isRead = null;

                if ("прочитана".equals(lowerInput) || "read".equals(lowerInput) || "да".equals(lowerInput) || "yes".equals(lowerInput)) {
                    isRead = true;
                } else if ("не прочитана".equals(lowerInput) || "not read".equals(lowerInput) || "нет".equals(lowerInput) || "no".equals(lowerInput)) {
                    isRead = false;
                }

                if (isRead == null) {
                    return "❌ Неверный статус. Используйте:\n" +
                            "`прочитана` или `не прочитана`\n" +
                            "Попробуйте снова через /set_read_status.";
                }

                String bookName = sessionManager.getSession(chatId).getData("bookName");
                String authorName = sessionManager.getSession(chatId).getData("authorName");
                sessionManager.clearSession(chatId);
                String userName = sessionManager.getUserNameByChatId(chatId);
                return messageService.setReadingStatus(userName, bookName, authorName, isRead);
            }
            //Тут завершается набор состояний для установки статуса книги

            default: {
                sessionManager.clearSession(chatId);
                return "⚠️ Неизвестное состояние. Используйте /help";
            }
        }
    }
}