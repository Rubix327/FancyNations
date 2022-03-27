package me.rubix327.fancynations.data;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    Connection connection;

    private final String host = "localhost";
    private final int port = 3306;
    private final String database = "fancynations";
    private final String username = "root";
    private final String password = "";

    public void connect() throws ClassNotFoundException, SQLException {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setServerName(host);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(database);
        dataSource.setUser(username);
        dataSource.setPassword(password);

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
