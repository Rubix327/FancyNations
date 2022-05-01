package me.rubix327.fancynations.data.fnplayers;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FNPlayerDao extends AbstractDao<FNPlayer> implements IFNPlayerManager {

    private static FNPlayerDao instance = null;

    private FNPlayerDao(String table) {
        super(table);
    }

    public static FNPlayerDao getInstance(String tableName){
        if (instance == null){
            instance = new FNPlayerDao(tableName);
        }
        return instance;
    }

    @Override
    protected FNPlayer loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");

        return new FNPlayer(id, name);
    }

    @Override
    public void add(FNPlayer player) {
        String query = "INSERT INTO @Table (Name) VALUES ('@Name')";

        query = query
                .replace("@Table", table)
                .replace("@Name", player.getName());

        super.executeVoid(query);
    }

}
