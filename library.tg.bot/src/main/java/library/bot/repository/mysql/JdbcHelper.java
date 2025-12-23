package library.bot.repository.mysql;
import javax.sql.DataSource;
import java.sql.*;

public class JdbcHelper {

    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }

    public static <T> T queryForObject(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setParameters(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapper.map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения запроса: " + sql, e);
        }
    }

    public static <T> java.util.List<T> queryForList(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
        try (Connection conn = getConnection();
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

    public static void update(String sql, PreparedStatementSetter setter) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setParameters(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения обновления: " + sql, e);
        }
    }

    private static Connection getConnection() throws SQLException {
        DataSource ds = library.bot.config.DatabaseConfig.getDataSource();
        return ds.getConnection();
    }
}