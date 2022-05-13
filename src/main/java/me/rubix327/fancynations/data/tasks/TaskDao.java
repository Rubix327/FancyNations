package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
        int townId = resultSet.getInt("Town");
        int creatorId = resultSet.getInt("Player");
        String taskName = resultSet.getString("Name");
        String description = resultSet.getString("Description");
        int takeAmount = resultSet.getInt("TakeAmount");
        int minLevel = resultSet.getInt("MinLevel");
        int maxLevel = resultSet.getInt("MaxLevel");
        double moneyReward = resultSet.getDouble("MoneyReward");
        double expReward = resultSet.getDouble("ExpReward");
        int repReward = resultSet.getInt("RepReward");
        int priority = resultSet.getInt("Priority");
        Timestamp placementDateTime = resultSet.getTimestamp("PlacementDatetime");
        int timetoComplete = resultSet.getInt("TimeToComplete");

        return new Task(id, townId, creatorId, taskName, description, takeAmount, minLevel,
                maxLevel, moneyReward, expReward, repReward, priority, placementDateTime, timetoComplete);
    }

    @Override
    public void add(Task task) {
        String query = getQuery("tasks_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(task.getTownId()))
                .replace("@TaskName", task.getName())
                .replace("@Player", String.valueOf(task.getCreatorId()))
                .replace("@Description", task.getDescription())
                .replace("@TakeAmount", String.valueOf(task.getTakeAmount()))
                .replace("@MinLevel", String.valueOf(task.getMinLevel()))
                .replace("@MaxLevel", String.valueOf(task.getMaxLevel()))
                .replace("@MoneyReward", String.valueOf(task.getMoneyReward()))
                .replace("@ExpReward", String.valueOf(task.getExpReward()))
                .replace("@RepReward", String.valueOf(task.getRepReward()))
                .replace("@PlacementDateTime", task.getPlacementDateTime().toString())
                .replace("@TimeToComplete", String.valueOf(task.getTimeToComplete()))
                .replace("@Priority", String.valueOf(task.getPriority()));

        super.executeVoid(query);
    }

}
