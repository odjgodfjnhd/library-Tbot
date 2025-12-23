package library.bot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static String getBotToken() {
        return dotenv.get("TELEGRAM_BOT_TOKEN");
    }

    public static String getBotUsername() {
        return dotenv.get("TELEGRAM_BOT_USERNAME");
    }

    public static AppMode getAppMode() {
        String mode = dotenv.get("APP_MODE", "IN_MEMORY");
        try {
            return AppMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Неверное значение APP_MODE в .env");
        }
    }

    public static String getDbUrl() {
        return dotenv.get("DB_URL");
    }

    public static String getDbUser() {
        return dotenv.get("DB_USER");
    }

    public static String getDbPassword() {
        return dotenv.get("DB_PASSWORD");
    }
}