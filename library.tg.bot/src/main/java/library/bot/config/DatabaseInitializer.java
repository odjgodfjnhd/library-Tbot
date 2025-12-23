package library.bot.config;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        try {
            String sqlScript = readSqlScript("/schema.sql");
            executeSqlScript(sqlScript);
            System.out.println("✅ MySQL: все таблицы созданы");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации базы данных", e);
        }
    }

    private String readSqlScript(String resourcePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private void executeSqlScript(String sqlScript) throws SQLException {
        String[] commands = sqlScript.split(";");
        try (Connection conn = dataSource.getConnection()) {
            for (String command : commands) {
                String trimmed = command.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                    continue;
                }
                try (PreparedStatement stmt = conn.prepareStatement(trimmed)) {
                    stmt.execute();
                }
            }
        }
    }
}