package me.rubix327.fancynations.data;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    Connection connection;

    public void connect() throws ClassNotFoundException, SQLException {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setServerName(Settings.Database.HOST);
        dataSource.setPortNumber(Settings.Database.PORT);
        dataSource.setDatabaseName(Settings.Database.DATABASE);
        dataSource.setUser(Settings.Database.USERNAME);
        dataSource.setPassword(Settings.Database.PASSWORD);

        connection = dataSource.getConnection();
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
}
