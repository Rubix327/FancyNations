package me.rubix327.fancynations.data;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.Bukkit;
import me.rubix327.fancynations.FancyNations;
import org.mineacademy.fo.Common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            assert file != null;
            String setup = new String(file.readAllBytes())
                    .replace("@db", database)
                    .replace("@Mayor", Settings.TownWorkers.MAYOR_DEFAULT_DISPLAYNAME)
                    .replace("@Helper", Settings.TownWorkers.HELPER_DEFAULT_DISPLAYNAME)
                    .replace("@Judge", Settings.TownWorkers.JUDGE_DEFAULT_DISPLAYNAME)
                    .replace("@Other", Settings.TownWorkers.OTHER_DEFAULT_DISPLAYNAME);
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
        } catch (IOException | NullPointerException e) {
            if (Settings.General.SQL_DEBUG) e.printStackTrace();
            Bukkit.getLogger().warning("[FancyNations] dbsetup.sql does not exist.");
        } catch (SQLException e) {
            if (Settings.General.SQL_DEBUG) e.printStackTrace();
            Bukkit.getLogger().warning("[FancyNations] Database is not connected: Maybe login info is incorrect?");
            Bukkit.getLogger().warning("[FancyNations] Set SQL_Debug to true in settings.yml to see the connection error.");
        }
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
     Gets id of a field from the specified table where value from 'column' = 'target'.
     @param table Table name
     @param column Column name
     @param target What value should be in specified column?
     @return int - id
     */
    public static int getRecordId(String table, String column, String target) throws NullPointerException{
        try{
            String query = "SELECT Id FROM @Table WHERE @Column = '@Target'";
            query = query.replace("@Table", table).replace("@Column", column).replace("@Target", target);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            Common.log(ps.toString());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            throw new NullPointerException("No records with these table, column and target.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    public static void logSqlQuery(String query){
        if (Settings.General.SQL_DEBUG) Common.log(query);
    }
}
