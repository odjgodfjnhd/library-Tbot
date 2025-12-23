package library.bot.repository.mysql;

import library.bot.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcHelper {
    private final DataSource dataSource;

    public JdbcHelper() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    public <T> T queryForObject(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setParameters(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapper.map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения запроса: " + sql, e);
        }
    }

    public <T> java.util.List<T> queryForList(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setParameters(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                java.util.List<T> results = new java.util.ArrayList<>();
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения запроса: " + sql, e);
        }
    }

    public void update(String sql, PreparedStatementSetter setter) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setParameters(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения обновления: " + sql, e);
        }
    }

    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }
}