package me.rubix327.fancynations.data.takentasks;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
        Timestamp takingDatetime = resultSet.getTimestamp("TakingDateTime");

        return new TakenTask(id, playerId, taskId, takingDatetime);
    }

    public boolean exists(int playerId, int taskId) {
        String query = getQuery("taken_tasks_exists");

        query = query
                .replace("@Table", table)
                .replace("@PlayerId", String.valueOf(playerId))
                .replace("@TaskId", String.valueOf(taskId));

        return executeBool(query);
    }

    public void add(TakenTask takenTask) {
        String query = getQuery("taken_tasks_add");

        query = query
                .replace("@Table", table)
                .replace("@Player", String.valueOf(takenTask.getPlayerId()))
                .replace("@Task", String.valueOf(takenTask.getTaskId()))
                .replace("@TakingDateTime", takenTask.getTakingDatetime().toString());

        executeVoid(query);
    }

    @Override
    public TakenTask get(int playerId, int taskId) {
        String query = getQuery("taken_tasks_get");

        query = query
                .replace("@Table", this.table)
                .replace("@PlayerId", String.valueOf(playerId))
                .replace("@TaskId", String.valueOf(taskId));

        return executeObject(query);
    }

    public int getCountFor(int taskId){
        String query = getQuery("taken_tasks_get_count");

        query = query
                .replace("@Table", table)
                .replace("@TaskId", String.valueOf(taskId));

        return executeInteger(query);
    }

}
