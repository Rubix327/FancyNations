package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

public class TaskDao extends AbstractDao<Task> implements ITaskManager {

    private static TaskDao instance = null;

    private TaskDao(String table) {
        super(table);
    }

    public static TaskDao getInstance(String tableName){
        if (instance == null){
            instance = new TaskDao(tableName);
        }
        return instance;
    }

    @Override
    protected Task loadObject(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("Id");
        String taskName = resultSet.getString("Name");
        int townId = resultSet.getInt("Town");
        String creatorTypeName = resultSet.getString("CreatorType");
        int creatorId = resultSet.getInt("Creator");
        String description = resultSet.getString("Description");
        int completionsLeft = resultSet.getInt("CompletionsLeft");
        int minLevel = resultSet.getInt("MinLevel");
        int maxLevel = resultSet.getInt("MaxLevel");
        double moneyReward = resultSet.getDouble("MoneyReward");
        double expReward = resultSet.getDouble("ExpReward");
        int repReward = resultSet.getInt("RepReward");
        int priority = resultSet.getInt("Priority");
        Timestamp placementDateTime = resultSet.getTimestamp("PlacementDatetime");
        Timestamp terminationDateTime = resultSet.getTimestamp("TerminationDatetime");
        int timeToComplete = resultSet.getInt("TimeToComplete");

        return new Task(id, townId, taskName, creatorId, creatorTypeName, description, completionsLeft, minLevel,
                maxLevel, moneyReward, expReward, repReward, priority, placementDateTime, terminationDateTime, timeToComplete);
    }

    @Override
    public void add(Task task) {
        String query = getQuery("tasks_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(task.getTownId()))
                .replace("@TaskName", task.getName())
                .replace("@CreatorType", task.getCreatorTypeName())
                .replace("@Creator", String.valueOf(task.getCreatorId()))
                .replace("@Description", task.getDescription())
                .replace("@CompletionsLeft", String.valueOf(task.getCompletionsLeft()))
                .replace("@MinLevel", String.valueOf(task.getMinLevel()))
                .replace("@MaxLevel", String.valueOf(task.getMaxLevel()))
                .replace("@MoneyReward", String.valueOf(task.getMoneyReward()))
                .replace("@ExpReward", String.valueOf(task.getExpReward()))
                .replace("@RepReward", String.valueOf(task.getRepReward()))
                .replace("@PlacementDateTime", task.getPlacementDateTime().toString())
                .replace("@TerminationDateTime", task.getTerminationDateTime().toString())
                .replace("@TimeToComplete", String.valueOf(task.getTimeToComplete()))
                .replace("@Priority", String.valueOf(task.getPriority()));

        executeVoid(query);
    }

    public HashMap<Integer, Task> getAllFor(int townId){
        String query = getQuery("tasks_get_all_for");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(townId));

        return executeAll(query);
    }

}
