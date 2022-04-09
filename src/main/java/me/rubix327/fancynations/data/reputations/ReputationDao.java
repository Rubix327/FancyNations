package me.rubix327.fancynations.data.reputations;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReputationDao extends AbstractDao<Reputation> implements IReputationManager{

    private static ReputationDao instance = null;

    protected ReputationDao(String table) {
        super(table);
    }

    public static ReputationDao getInstance(String tableName){
        if (instance == null){
            instance = new ReputationDao(tableName);
        }
        return instance;
    }

    @Override
    protected Reputation loadObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        int playerId = resultSet.getInt("Player");
        int townId = resultSet.getInt("Town");
        int amount = resultSet.getInt("Amount");

        return new Reputation(id, playerId, townId, amount);
    }

    @Override
    public boolean exists(int playerId, int townId) {
        String query = "SELECT Id FROM @Table WHERE Player = @Player AND Town = @Town";

        query = query
                .replace("@Table", table)
                .replace("@Player", String.valueOf(playerId))
                .replace("@Town", String.valueOf(townId));

        return super.executeBool(query);
    }

    @Override
    public void add(Reputation dto) {
        String query = "INSERT INTO @Table (Player, Town, Amount) VALUES(@Player, @Town, @Amount)";

        query = query
                .replace("@Table", table)
                .replace("@Player", String.valueOf(dto.getPlayerId()))
                .replace("@Town", String.valueOf(dto.getTownId()))
                .replace("@Amount", String.valueOf(dto.getAmount()));

        super.executeVoid(query);
    }

    @Override
    public Reputation get(int playerId, int townId) {
        String query = "SELECT * FROM @Table WHERE Player = @Player AND Town = @Town";

        query = query
                .replace("@Table", table)
                .replace("@Player", String.valueOf(playerId))
                .replace("@Town", String.valueOf(townId));

        return super.executeObject(query);
    }
}
