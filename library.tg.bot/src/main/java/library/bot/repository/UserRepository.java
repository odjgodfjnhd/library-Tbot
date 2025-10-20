package library.bot.repository;

import library.bot.domain.User;
import java.util.List;

public interface UserRepository
{
    void save(User user);

    User findById(int userId);

    User findByName(String username);

    List<User> getAllUsers();

    int getTotalUsers();
}