package me.rubix327.fancynations.data;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    Connection connection;

    public void connect() {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setServerName(Settings.Database.HOST);
        dataSource.setPortNumber(Settings.Database.PORT);
        dataSource.setUser(Settings.Database.USERNAME);
        dataSource.setPassword(Settings.Database.PASSWORD);

        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("dbsetup.sql");
            String database = Settings.Database.DATABASE;
            String setup = new String(file.readAllBytes()).replace("@db", database);
            connection = dataSource.getConnection();
            String[] queries = setup.split(";");
            for (String query : queries) {
                if (query.isBlank()) continue;

                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.execute();
                    if (!dataSource.getDatabaseName().isBlank()) {
                        dataSource.setDatabaseName(database);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[FancyNations] dbsetup.sql does not exist.");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[FancyNations] Database is not connected: Maybe login info is incorrect?");
            Bukkit.getLogger().warning("[FancyNations] Using file system instead of database.");
        }
    }

    public boolean isConnected(){
        return (connection != null);
    }

    public void disconnect(){
        if (isConnected()){
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
