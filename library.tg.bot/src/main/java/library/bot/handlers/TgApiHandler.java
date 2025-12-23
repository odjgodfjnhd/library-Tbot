package library.bot.handlers;

import library.bot.components.repository.RepositoryComponent;
import library.bot.components.service.RegistrationService;
import library.bot.handlers.CommandHandler;
import library.bot.handlers.StateHandler;
import library.bot.components.service.UserMessageService;

public class TgApiHandler {
    private final SessionManager sessionManager;
    private final CommandHandler commandHandler;
    private final StateHandler stateHandler;

    public TgApiHandler() {
        RepositoryComponent repo = new RepositoryComponent();
        UserMessageService service = new UserMessageService(repo);
        RegistrationService regServ = new RegistrationService(repo);
        this.sessionManager = new SessionManager();
        this.commandHandler = new CommandHandler(service, sessionManager, regServ);
        this.stateHandler = new StateHandler(service, sessionManager);
    }

    public String handleUpdateReceived(Long chatId, String text) {
        UserSession session = sessionManager.getSession(chatId);

        if (session.getState() != null) {
            return stateHandler.handleState(chatId, session.getState(), text);
        }

        return commandHandler.handleCommand(chatId, text);
    }
}