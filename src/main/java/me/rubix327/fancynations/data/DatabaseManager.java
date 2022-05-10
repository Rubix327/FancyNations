package me.rubix327.fancynations.data;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;
    private MysqlDataSource dataSource;
    private String database;
    @Getter
    private HashMap<String, String> queries;

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

    /**
     * Convert text from a file to the flat string.
     * @param fileName to export from
     * @return flat string
     */
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

    /**
     * Executes all the queries found in the string.
     * Text is split by a semicolon and then each query is executed.
     * @param setup to take queries from
     */
    public void executeQuery(String setup){
        try {
            String[] queries = setup.split(";");
            for (String query : queries) {
                if (query.isBlank()) continue;

                try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
                    Logger.logSqlQuery(query);
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

    /**
     * Find and execute all the queries from a file.
     * @param fileName to execute from
     */
    public void extractAndExecuteQuery(String fileName){
        executeQuery(extractQuery(fileName));
    }

    /**
     * <p>Loads all queries from the given file in the following format:</p>
     * <p><b>name1: query1;</b></p>
     * <p><b>name2: query2;</b></p>
     * @param file to extract queries from
     */
    public void loadQueries(String file){
        String setup = extractQuery(file);
        HashMap<String, String> queries = new HashMap<>();
        for (String query : setup.split(";")) {
            if (query.isBlank()) continue;
            String key = query.substring(0, query.indexOf(":"))
                    .replace("\r\n", "")
                    .replace("\n", "")
                    .replace("\r", "");
            String value = query.substring(query.indexOf(":") + 2)
                    .replace("\r\n", "")
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace("\"", "")
                    .replace("+", "")
                    .replace("  ", "");
            queries.put(key, value);
        }
        this.queries = queries;
    }

    public String getQuery(String key) throws NullPointerException{
        if (!queries.containsKey(key)){
            throw new NullPointerException("Key " + key + " does not exist in queries hashmap (DatabaseManager)");
        }
        return queries.get(key);
    }

}