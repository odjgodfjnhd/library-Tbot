package library.bot.api;

import library.bot.handlers.TgApiHandler;
import library.bot.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.*;

public class LibraryBot extends TelegramLongPollingBot {

    private final TgApiHandler tgApiHandler;
    private final ExecutorService executorService;

    public LibraryBot() {
        this.tgApiHandler = new TgApiHandler();

        int threadCount = Runtime.getRuntime().availableProcessors() * 2;
        this.executorService = Executors.newFixedThreadPool(threadCount);

        System.out.println("создан тредпул на " + threadCount + " потоков");
    }

    @Override
    public String getBotUsername() {
        return BotConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        executorService.submit(() -> {
            try {
                String result = tgApiHandler.handleUpdateReceived(chatId, messageText);
                sendMessage(chatId, result);
            } catch (Exception e) {
                System.err.println("ошибка от юзера " + chatId + ": " + e.getMessage());
                e.printStackTrace();
                sendMessage(chatId, "Произошла ошибка. Попробуйте позже.");
            }
        });
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        System.out.println("остановка тредпула");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            System.out.println("тредпул остановлен");
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}