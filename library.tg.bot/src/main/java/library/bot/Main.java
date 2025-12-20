package library.bot;

import library.bot.api.LibraryBot;
import library.bot.config.BotConfig;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        if ("MYSQL".equals(BotConfig.getAppMode())) {
            initMySQLDatabase();
        }

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            LibraryBot bot = new LibraryBot();
            botsApi.registerBot(bot);
            System.out.println("🤖 Бот запущен. Режим: " + BotConfig.getAppMode());
        } catch (TelegramApiException e) {
            System.err.println("❌ Ошибка запуска бота: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initMySQLDatabase() {
        String[] ddlCommands = {
                "CREATE TABLE IF NOT EXISTS users (" +
                        "    user_id VARCHAR(50) PRIMARY KEY," +
                        "    user_name VARCHAR(255) NOT NULL UNIQUE" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci",

                "CREATE TABLE IF NOT EXISTS authors (" +
                        "    author_id VARCHAR(36) PRIMARY KEY," +
                        "    author_name VARCHAR(255) NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci",

                "CREATE TABLE IF NOT EXISTS books (" +
                        "    book_id VARCHAR(36) PRIMARY KEY," +
                        "    title VARCHAR(255) NOT NULL," +
                        "    author_id VARCHAR(36) NOT NULL," +
                        "    author_name VARCHAR(255) NOT NULL," +
                        "    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci",

                "CREATE TABLE IF NOT EXISTS user_books (" +
                        "    user_id VARCHAR(50) NOT NULL," +
                        "    book_id VARCHAR(36) NOT NULL," +
                        "    genre VARCHAR(100) DEFAULT NULL," +
                        "    book_year INT DEFAULT NULL," +
                        "    rating INT DEFAULT NULL," +
                        "    reading_status BOOLEAN DEFAULT FALSE," +
                        "    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "    PRIMARY KEY (user_id, book_id)," +
                        "    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE," +
                        "    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci",

                "CREATE TABLE IF NOT EXISTS notes (" +
                        "    note_id VARCHAR(36) PRIMARY KEY," +
                        "    user_id VARCHAR(50) NOT NULL," +
                        "    book_id VARCHAR(36) NOT NULL," +
                        "    book_name VARCHAR(255) NOT NULL," +
                        "    note_text TEXT NOT NULL," +
                        "    created_at DATE NOT NULL," +
                        "    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE," +
                        "    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci",

                "CREATE TABLE IF NOT EXISTS user_authors (" +
                        "    user_id VARCHAR(50) NOT NULL," +
                        "    author_id VARCHAR(36) NOT NULL," +
                        "    PRIMARY KEY (user_id, author_id)," +
                        "    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE," +
                        "    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci"
        };

        try {
            DataSource ds = library.bot.config.DatabaseConfig.getDataSource();
            try (Connection conn = ds.getConnection()) {
                for (String sql : ddlCommands) {
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.execute();
                    }
                }
                System.out.println("✅ MySQL: все таблицы созданы");
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка инициализации MySQL: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}