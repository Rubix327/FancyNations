package me.rubix327.fancynations.data.takentasks;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TakenTaskDao extends AbstractDao<TakenTask> implements ITakenTaskManager {

    private final String tableName;

    public TakenTaskDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    @Override
    protected TakenTask loadObject(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("Id");
        int playerId = resultSet.getInt("Player");
        int taskId = resultSet.getInt("Task");

        return new TakenTask(id, playerId, taskId);
    }

    public boolean exists(int playerId, int taskId) {
        String query = "SELECT Id FROM @Table WHERE Player = @PlayerID AND Task = @TaskID";

        query = query
                .replace("@Table", tableName)
                .replace("@PlayerID", String.valueOf(playerId))
                .replace("@TaskID", String.valueOf(taskId));

        return super.executeBool(query);
    }

    public void add(TakenTask takenTask) {
        String query = "INSERT INTO @Table (Player, Task) VALUES (@Player, @Task)";

        query = query
                .replace("@Table", tableName)
                .replace("@Player", String.valueOf(takenTask.getPlayerId()))
                .replace("@Task", String.valueOf(takenTask.getTaskId()));

        super.executeVoid(query);
    }

    public TakenTask get(int playerId, int taskId) {
        for (TakenTask takenTask : getAll().values()){
            if (takenTask.getPlayerId() == playerId && takenTask.getTaskId() == taskId) return takenTask;
        }
        throw new NullPointerException("Taken task with this name does not exist.");
    }

}
