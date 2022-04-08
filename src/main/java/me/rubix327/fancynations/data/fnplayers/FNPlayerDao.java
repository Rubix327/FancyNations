package me.rubix327.fancynations.data.fnplayers;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FNPlayerDao extends AbstractDao<FNPlayer> implements IFNPlayerManager {

    private final String tableName;

    public FNPlayerDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    @Override
    protected FNPlayer loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");
        int reputation = resultSet.getInt("Reputation");

        return new FNPlayer(id, name, reputation);
    }

    @Override
    public void add(FNPlayer player) {
        String query = "INSERT INTO @Table (Name, Reputation) VALUES ('@Name', @Reputation)";

        query = query
                .replace("@Table", tableName)
                .replace("@Name", player.getName())
                .replace("@Reputation", String.valueOf(player.getReputation()));

        super.executeVoid(query);
    }

}
