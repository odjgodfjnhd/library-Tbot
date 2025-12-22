package library.bot.components.service;

import library.bot.components.repository.RepositoryComponent;
import library.bot.domain.User;

public class RegistrationService {
    private final RepositoryComponent repositoryComponent;

    public RegistrationService(RepositoryComponent repositoryComponent) {
        this.repositoryComponent = repositoryComponent;
    }

    public boolean isUserRegistered(Long chatId) {
        String userId = chatId.toString();
        return repositoryComponent.getUserRepository().findById(userId) != null;
    }

    public boolean registerUser(Long chatId, String userName) {
        String userId = chatId.toString();
        if (isUserRegistered(chatId)) {
            return false;
        }
        User user = new User(userId, userName);
        repositoryComponent.getUserRepository().save(user);
        return true;
    }
}