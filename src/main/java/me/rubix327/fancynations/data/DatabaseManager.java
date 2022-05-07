package me.rubix327.fancynations.data;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;
    private MysqlDataSource dataSource;
    private String database;

    public static DatabaseManager getInstance(){
        if (instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void connect(String fileName) {
        dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setServerName(Settings.Database.HOST);
        dataSource.setPortNumber(Settings.Database.PORT);
        dataSource.setUser(Settings.Database.USERNAME);
        dataSource.setPassword(Settings.Database.PASSWORD);
        database = Settings.Database.DATABASE;

        try{
            connection = dataSource.getConnection();
        } catch (SQLException e){
            if (Settings.General.SQL_DEBUG) { e.printStackTrace(); }
            return;
        }

        String setup = extractQuery(fileName).replace("@db", database);
        executeQuery(setup);
    }

    public boolean isConnected(){
        return (connection != null);
    }

    public void disconnect(){
        if (isConnected()){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public String extractQuery(String fileName){
        String query = null;
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream(fileName);
            assert file != null;
            query = new String(file.readAllBytes());
        } catch (IOException | NullPointerException e) {
            if (Settings.General.SQL_DEBUG) e.printStackTrace();
            Logger.warning(fileName + " does not exist.");
        }
        if (query == null) throw new NullPointerException(Logger.get(fileName + " is empty."));
        return query;
    }

    public void executeQuery(String setup){
        try {
            String[] queries = setup.split(";");
            for (String query : queries) {
                if (query.isBlank()) continue;

                try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
                    logSqlQuery(query);
                    stmt.execute();
                    if (!dataSource.getDatabaseName().isBlank()) {
                        dataSource.setDatabaseName(database);
                    }
                }
            }
        } catch (SQLException e) {
            if (Settings.General.SQL_DEBUG) e.printStackTrace();
            Logger.warning("An error has occurred while executing an SQL query.");
            Logger.warning("Set SQL_Debug to true in settings.yml to see the error.");
        }
    }

    public void extractAndExecuteQuery(String fileName){
        executeQuery(extractQuery(fileName));
    }

    public static void logSqlQuery(String query){
        if (Settings.General.SQL_DEBUG) Logger.info("SQL Debug: " + query);
    }
}