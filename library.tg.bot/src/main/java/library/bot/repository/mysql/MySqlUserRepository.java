package library.bot.repository.mysql;

import library.bot.domain.User;
import library.bot.repository.UserRepository;
import library.bot.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MySqlUserRepository implements UserRepository {

    private final DataSource dataSource;

    public MySqlUserRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (user_id, user_name) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE user_name = VALUES(user_name)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getUserName());
            int rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сохранения пользователя в MySQL", e);
        }
    }

    @Override
    public User findById(String userId) {
        String sql = "SELECT user_id, user_name FROM users WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("user_id"), rs.getString("user_name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска пользователя по ID", e);
        }
    }

    @Override
    public User findByName(String username) {
        String sql = "SELECT user_id, user_name FROM users WHERE user_name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("user_id"), rs.getString("user_name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска пользователя по имени", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new CopyOnWriteArrayList<>();
        String sql = "SELECT user_id, user_name FROM users";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getString("user_id"), rs.getString("user_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех пользователей", e);
        }
        return users;
    }

    @Override
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подсчёта пользователей", e);
        }
    }
}