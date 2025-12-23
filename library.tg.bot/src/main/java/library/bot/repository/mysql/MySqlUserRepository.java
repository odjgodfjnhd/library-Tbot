package library.bot.repository.mysql;

import library.bot.domain.User;
import library.bot.repository.UserRepository;

import java.util.List;

public class MySqlUserRepository implements UserRepository {
    private final JdbcHelper jdbc = new JdbcHelper();

    @Override
    public void save(User user) {
        jdbc.update(
                "INSERT INTO users (user_id, user_name) VALUES (?, ?) " +
                        "ON DUPLICATE KEY UPDATE user_name = VALUES(user_name)",
                stmt -> {
                    stmt.setString(1, user.getUserId());
                    stmt.setString(2, user.getUserName());
                }
        );
    }

    @Override
    public User findById(String userId) {
        return jdbc.queryForObject(
                "SELECT user_id, user_name FROM users WHERE user_id = ?",
                rs -> rs.next() ? new User(rs.getString("user_id"), rs.getString("user_name")) : null,
                stmt -> stmt.setString(1, userId)
        );
    }

    @Override
    public User findByName(String username) {
        return jdbc.queryForObject(
                "SELECT user_id, user_name FROM users WHERE user_name = ?",
                rs -> rs.next() ? new User(rs.getString("user_id"), rs.getString("user_name")) : null,
                stmt -> stmt.setString(1, username)
        );
    }

    @Override
    public List<User> getAllUsers() {
        return jdbc.queryForList(
                "SELECT user_id, user_name FROM users",
                rs -> new User(rs.getString("user_id"), rs.getString("user_name")),
                stmt -> {}
        );
    }

    @Override
    public int getTotalUsers() {
        return jdbc.queryForObject(
                "SELECT COUNT(*) FROM users",
                rs -> rs.next() ? rs.getInt(1) : 0,
                stmt -> {}
        );
    }
}