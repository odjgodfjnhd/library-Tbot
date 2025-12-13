package library.bot;

import library.bot.api.LibraryBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            LibraryBot bot = new LibraryBot();
            botsApi.registerBot(bot);

            System.out.println("✅ Telegram бот запущен!");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nостановка");
                bot.shutdown();
            }));

        } catch (TelegramApiException e) {
            System.err.println("❌ Ошибка запуска: " + e.getMessage());
            e.printStackTrace();
        }
    }
}