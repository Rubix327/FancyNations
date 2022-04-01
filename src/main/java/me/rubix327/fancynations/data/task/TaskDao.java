package me.rubix327.fancynations.data.task;

import me.rubix327.fancynations.data.DataManager;
import org.mineacademy.fo.Common;

import java.sql.*;
import java.util.HashMap;

public class TaskDao implements ITaskManager{

    private GatheringTask loadGatheringTask(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("Task.Id");
        String townName = resultSet.getString("Town.Name");
        TaskType taskType = TaskType.valueOf(resultSet.getString("TaskType.Name"));
        String creator = resultSet.getString("Player.Name");
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

        return new GatheringTask(id, townName, taskType, creator, taskName, description, takeAmount, minLevel, maxLevel,
                moneyReward, expReward, repReward, priority, placementDateTime, timetoComplete);
    }

    protected static MobKillTask loadMobKillTask(ResultSet resultSet) throws SQLException{
        return new MobKillTask("", TaskType.Mobkill, "", "");
    }

    @Override
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exists(int id) {
        try{
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement("SELECT Id FROM Task WHERE Id = ?");
            ps.setInt(1, id);
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
            String query = "INSERT INTO Task (Id, TownName, TaskType, Name, Player, Description, TakeAmount, MinLevel, " +
                    "MaxLevel, MoneyReward, ExpReward, RepReward, PlacementDateTime, TimeToComplete, Priority, " +
                    "Objectives) VALUES(@ID, @TownName, @TaskType, '@TaskName', '@Player', " +
                    "'@Description', @TakeAmount, @MinLevel, @MaxLevel, @MoneyReward, @ExpReward, @RepReward, " +
                    "'@PlacementDateTime', @TimeToComplete, @Priority, '@Objectives')";

            String townName = String.valueOf(getFieldId("Town", "Name", task.getTownName()));
            String taskTypeId = String.valueOf(getFieldId("TaskType", "Name", task.getTaskType().toString()));
            String playerId = String.valueOf(getFieldId("Player", "Name", task.getCreatorName()));

            query = query.replace("@ID", String.valueOf(task.getId()))
                    .replace("@TownName", townName)
                    .replace("@TaskType", taskTypeId)
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
                    .replace("@Priority", String.valueOf(task.getPriority()))
                    .replace("@Objectives", String.valueOf(
                            task.getObjectives().isEmpty() ? "[]" : task.getObjectives()));
            Common.log(query);

            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().prepareStatement(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    @Override
    public void remove(int id) {
        try{
            String query = "DELETE FROM Task WHERE ID = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement(query);
            ps.executeUpdate();
            Common.log(query);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use TaskDao.exists() before this method.");
    }

    @Override
    public Task get(int id) throws NullPointerException{
        try{
            String query = "SELECT * FROM Task JOIN (TaskType, Player, Town) " +
                    "ON (Task.Id = TaskType.Id AND Task.Player = Player.Id AND Task.TownName = Town.Id) " +
                    "WHERE Task.Id = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement(query);
            Common.log(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                String taskType = resultSet.getString("TaskType.Name");
                if (taskType.equalsIgnoreCase(TaskType.Food.toString()) ||
                        taskType.equalsIgnoreCase(TaskType.Resource.toString()) ||
                        taskType.equalsIgnoreCase(TaskType.Crafting.toString())){
                    return loadGatheringTask(resultSet);
                }
                else if (taskType.equalsIgnoreCase(TaskType.Mobkill.toString())){
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
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement(query);
            ps.executeUpdate();
            Common.log(query);
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
    public HashMap<Integer, Task> getTasks() throws NullPointerException{
        try{
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement("SELECT * FROM Task JOIN (TaskType, Player, Town) " +
                            "ON (Task.Id = TaskType.Id AND Task.Player = Player.Id AND Task.TownName = Town.Id)");
            ResultSet resultSet = ps.executeQuery();
            HashMap<Integer, Task> tasks = new HashMap<>();
            if (resultSet.next()){
                String taskType = resultSet.getString("TaskType.Name");
                if (taskType.equalsIgnoreCase(TaskType.Food.toString()) ||
                        taskType.equalsIgnoreCase(TaskType.Resource.toString()) ||
                        taskType.equalsIgnoreCase(TaskType.Crafting.toString())){
                    tasks.put(resultSet.getInt("ID"), loadGatheringTask(resultSet));
                }
                else if (taskType.equalsIgnoreCase(TaskType.Mobkill.toString())){
                    tasks.put(resultSet.getInt("ID"), loadMobKillTask(resultSet));
                }
                return tasks;
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
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement("SELECT Id from Task ORDER BY Id DESC LIMIT 1");
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

    /**
     Gets id of a field from the specified table where value from 'column' = 'target'.
     @param table Table name
     @param column Column name
     @param target What value are you looking for?
     @return int - id
     */
    private int getFieldId(String table, String column, String target){
        try{
            String query = "SELECT Id FROM @Table WHERE @Column = '@Target'";
            query = query.replace("@Table", table).replace("@Column", column).replace("@Target", target);
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement(query);
            Common.log(ps.toString());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }
}
