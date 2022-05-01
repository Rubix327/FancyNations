package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ObjectivesDao extends AbstractDao<Objective> implements IObjectivesManager {

    private static ObjectivesDao instance = null;

    private ObjectivesDao(String table) {
        super(table);
    }

    public static ObjectivesDao getInstance(String tableName){
        if (instance == null){
            instance = new ObjectivesDao(tableName);
        }
        return instance;
    }

    @Override
    protected Objective loadObject(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("Id");
        int takenTaskId = resultSet.getInt("TakenTask");
        String name = resultSet.getString("Name");
        int amount = resultSet.getInt("Amount");

        return new Objective(id, takenTaskId, name, amount);
    }

    public void add(Objective objective) {
        String query = "INSERT INTO @Table (TakenTask, Name, Amount) VALUES (@TakenTask, '@Name', @Amount)";

        query = query
                .replace("@Table", table)
                .replace("@TakenTask", String.valueOf(objective.getTakenTaskId()))
                .replace("@Name", objective.getName())
                .replace("@Amount", String.valueOf(objective.getAmount()));

        super.executeVoid(query);
    }

    public HashMap<Integer, Objective> getAllFor(String playerName, int taskId){
        int playerId = DataManager.getFNPlayerManager().get(playerName).getId();
        int takenTaskId = DataManager.getTakenTaskManager().get(playerId, taskId).getId();

        String query = "SELECT * FROM @Table WHERE TakenTask = @TakenTaskID";
        query = query
                .replace("@Table", table)
                .replace("@TakenTaskID", String.valueOf(takenTaskId));

        return executeAll(query);
    }

}
