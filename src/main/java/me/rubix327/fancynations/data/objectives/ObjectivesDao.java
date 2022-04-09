package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ObjectivesDao extends AbstractDao<Objective> implements IObjectivesManager {

    private final String tableName;

    public ObjectivesDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
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
                .replace("@Table", tableName)
                .replace("@TakenTask", String.valueOf(objective.getTakenTaskId()))
                .replace("@Name", objective.getName())
                .replace("@Amount", String.valueOf(objective.getAmount()));

        super.executeVoid(query);
    }

    public HashMap<Integer, Objective> getAllFor(String playerName, int taskId){
        int playerId = DataManager.getFNPlayerManager().get(playerName).getId();
        int takenTaskId = DataManager.getTakenTaskManager().get(playerId, taskId).getId();

        try{
            String query = "SELECT * FROM @Table WHERE TakenTask = @TakenTaskID";
            query = query
                    .replace("@Table", tableName)
                    .replace("@TakenTaskID", String.valueOf(takenTaskId));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);

            ResultSet resultSet = ps.executeQuery();
            HashMap<Integer, Objective> objectives = new HashMap<>();
            while (resultSet.next()){
                objectives.put(resultSet.getInt("Id"), loadObject(resultSet));
            }
            return objectives;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

}
