package library.bot;

import library.bot.api.LibraryBot;
import library.bot.config.AppMode;
import library.bot.config.BotConfig;
import library.bot.config.DatabaseConfig;
import library.bot.config.DatabaseInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {

        if (AppMode.MYSQL == BotConfig.getAppMode()) {
            new DatabaseInitializer(DatabaseConfig.getDataSource()).initialize();
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
}