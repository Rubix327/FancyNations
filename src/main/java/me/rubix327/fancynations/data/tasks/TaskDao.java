package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

public class TaskDao implements ITaskManager{

    private GatheringTask loadGatheringTask(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("Task.Id");
        int townId = resultSet.getInt("Task.Town");
        int taskTypeId = resultSet.getInt("Task.Id");
        String subtype = resultSet.getString("Task.Subtype");
        String creator = resultSet.getString("Player.Name");
        String taskName = resultSet.getString("Task.Name");
        String description = resultSet.getString("Task.Description");
        int takeAmount = resultSet.getInt("Task.TakeAmount");
        int minLevel = resultSet.getInt("Task.MinLevel");
        int maxLevel = resultSet.getInt("Task.MaxLevel");
        double moneyReward = resultSet.getDouble("Task.MoneyReward");
        double expReward = resultSet.getDouble("Task.ExpReward");
        int repReward = resultSet.getInt("Task.RepReward");
        int priority = resultSet.getInt("Task.Priority");
        Timestamp placementDateTime = resultSet.getTimestamp("Task.PlacementDatetime");
        int timetoComplete = resultSet.getInt("Task.TimeToComplete");

        return new GatheringTask(id, townId, taskTypeId, creator, taskName, description, takeAmount, minLevel, maxLevel,
                moneyReward, expReward, repReward, priority, placementDateTime, timetoComplete);
    }

    protected static MobKillTask loadMobKillTask(ResultSet resultSet) throws SQLException{
        return new MobKillTask(1, 1, "", "");
    }

    @Override
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exists(int id) {
        try{
            String query = "SELECT Id FROM Task WHERE Id = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    @Override
    public void add(Task task) {
        try{
            String query = "INSERT INTO Task (Town, TaskType, Player, Name, Description, TakeAmount, MinLevel, " +
                    "MaxLevel, MoneyReward, ExpReward, RepReward, PlacementDateTime, TimeToComplete, Priority) " +
                    "VALUES(@TownId, @TaskTypeId, '@Player', '@TaskName', " +
                    "'@Description', @TakeAmount, @MinLevel, @MaxLevel, @MoneyReward, @ExpReward, @RepReward, " +
                    "'@PlacementDateTime', @TimeToComplete, @Priority)";

            String playerId = String.valueOf(DatabaseManager.getRecordId("Player", "Name", task.getCreatorName()));

            query = query
                    .replace("@TownId", String.valueOf(task.getTownId()))
                    .replace("@TaskTypeId", String.valueOf(task.getTaskTypeId()))
                    .replace("@TaskName", task.getTaskName())
                    .replace("@Player", playerId)
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

            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().prepareStatement(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    @Override
    public Task get(int id) throws NullPointerException{
        try{
            String query = "SELECT * FROM Task JOIN (TaskType, Player, Town) " +
                    "ON (Task.Id = TaskType.Id AND Task.Player = Player.Id AND Task.TownName = Town.Id) " +
                    "WHERE Task.Id = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                String taskType = resultSet.getString("TaskType.Name");
                if (DataManager.gatheringTypes.contains(taskType)){
                    return loadGatheringTask(resultSet);
                }
                else if (DataManager.mobkillTypes.contains(taskType)){
                    return loadMobKillTask(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use TaskDao.exists() before this method.");
    }

    @Override
    public void update(int id, String variable, Object newValue) {
        try{
            String query = "UPDATE Task SET @Var = @Value WHERE ID = @ID";
            query = query.replace("@Var", variable)
                    .replace("@Value", String.valueOf(newValue))
                    .replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use TaskDao.exists() before this method.");
    }

    @Override
    public void remove(int id) {
        try{
            String query = "DELETE FROM Task WHERE ID = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use TaskDao.exists() before this method.");
    }

    /**
     Gets all the tasks from Task table. If no tasks exist then it will return an empty HashMap<>
     @return tasks hashmap
     */
    @Override
    public HashMap<Integer, Task> getAll() throws NullPointerException{
        try{
            String query = "SELECT * FROM Task JOIN (TaskType, Player, Town) " +
                    "ON (Task.Id = TaskType.Id AND Task.Player = Player.Id AND Task.TownName = Town.Id)";
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            HashMap<Integer, Task> tasks = new HashMap<>();
            while (resultSet.next()){
                String taskType = resultSet.getString("TaskType.Name");
                if (DataManager.gatheringTypes.contains(taskType)){
                    tasks.put(resultSet.getInt("ID"), loadGatheringTask(resultSet));
                }
                else if (DataManager.mobkillTypes.contains(taskType)){
                    tasks.put(resultSet.getInt("ID"), loadMobKillTask(resultSet));
                }
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    @Override
    public int getMaxId(){
        try{
            String query = "SELECT Id from Task ORDER BY Id DESC LIMIT 1";
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }
}
