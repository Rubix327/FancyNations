package me.rubix327.fancynations.data.takentasks;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TakenTaskDao extends AbstractDao<TakenTask> implements ITakenTaskManager {

    private static TakenTaskDao instance = null;

    private TakenTaskDao(String table) {
        super(table);
    }

    public static TakenTaskDao getInstance(String tableName){
        if (instance == null){
            instance = new TakenTaskDao(tableName);
        }
        return instance;
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
                .replace("@Table", table)
                .replace("@PlayerID", String.valueOf(playerId))
                .replace("@TaskID", String.valueOf(taskId));

        return super.executeBool(query);
    }

    public void add(TakenTask takenTask) {
        String query = "INSERT INTO @Table (Player, Task) VALUES (@Player, @Task)";

        query = query
                .replace("@Table", table)
                .replace("@Player", String.valueOf(takenTask.getPlayerId()))
                .replace("@Task", String.valueOf(takenTask.getTaskId()));

        super.executeVoid(query);
    }

    @Override
    public TakenTask get(int playerId, int taskId) {
        String query = "SELECT * FROM @Table WHERE Player = @Player AND Task = @Task";

        query = query
                .replace("@Table", this.table)
                .replace("@Player", String.valueOf(playerId))
                .replace("@Task", String.valueOf(taskId));

        return this.executeObject(query);
    }

}
