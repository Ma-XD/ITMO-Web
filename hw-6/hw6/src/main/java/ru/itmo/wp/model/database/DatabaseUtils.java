package ru.itmo.wp.model.database;

import org.mariadb.jdbc.MariaDbDataSource;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    private final static String REPOSITORY_IMPL = "RepositoryImpl";

    public static void setStatement(PreparedStatement statement, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class<?> clazz = getClass(arg);
            String className = clazz.getSimpleName();
            String methodName = "set" + className.substring(0, 1).toUpperCase() + className.substring(1);
            try {
                Method method = statement.getClass().getMethod(methodName, int.class, clazz);
                method.invoke(statement, i + 1, clazz.equals(String.class) ? arg.toString() : arg);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new SQLException("Can't set " + arg + " to statement.", e);
            }
        }
    }

    private static Class<?> getClass(Object arg) {
        if (arg instanceof Long) {
            return long.class;
        }
        if (arg instanceof Boolean) {
            return boolean.class;
        }
        if (arg instanceof Enum) {
            return String.class;
        }
        return arg.getClass();
    }

    public static String getEntityName(String repoName) {
        if (repoName.matches("[A-Za-z]+" + REPOSITORY_IMPL)) {
            return repoName.replace(REPOSITORY_IMPL, "");
        }
        throw new RepositoryException("Incorrect name of repository for " + repoName + "." +
                "It can be only *RepositoryImpl");
    }

    public static String getSelectStringSQL(String entityName, Set<String> keySet) {
        String delimiter = "AND";
        return "SELECT * FROM " + entityName + " WHERE " +
                String.join("=? " + delimiter + " ", keySet) + "=?";
    }

    public static String getInsertStringSQL(String entityName, Set<String> keySet) {
        StringBuilder prefix = new StringBuilder();
        StringBuilder suffix = new StringBuilder();
        prefix.append("INSERT INTO `").append(entityName).append("` (");
        suffix.append("VALUES (");
        for (String key : keySet) {
            prefix.append("`").append(key).append("`, ");
            suffix.append("?, ");
        }
        prefix.append("`creationTime`)");
        suffix.append("NOW())");
        return prefix + " " + suffix;
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;
        private static final Properties properties = new Properties();

        static {
            try {
                properties.load(DataSourceHolder.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can't load /application.properties.", e);
            }

            try {
                MariaDbDataSource instance = new MariaDbDataSource();
                instance.setUrl(properties.getProperty("database.url"));
                instance.setUser(properties.getProperty("database.user"));
                instance.setPassword(properties.getProperty("database.password"));
                INSTANCE = instance;
            } catch (SQLException e) {
                throw new RuntimeException("Can't initialize DataSource.", e);
            }

            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't create testing connection via DataSource.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't create testing connection via DataSource.", e);
            }
        }
    }
}
