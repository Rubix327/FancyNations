package me.rubix327.fancynations.data.task;

import me.rubix327.fancynations.data.DataManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class TaskDao implements ITaskManager{

    protected static GatheringTask loadGatheringTask(ResultSet resultSet) throws SQLException{
        // Mandatory variables
        String townName = resultSet.getString("TownName");
        TaskType taskType = TaskType.valueOf(resultSet.getString("TaskType"));
        String creator = resultSet.getString("CreatorName");
        String taskName = resultSet.getString("TaskName");

        GatheringTask task = new GatheringTask(townName, taskType, creator, taskName);

        // Optional variables
        Optional.ofNullable(resultSet.getString("Description")).ifPresent(task::setDescription);
        Optional.of(resultSet.getInt("TakeAmount")).ifPresent(task::setTakeAmount);
        Optional.of(resultSet.getInt("MinLevel")).ifPresent(task::setMinLevel);
        Optional.of(resultSet.getInt("MaxLevel")).ifPresent(task::setMaxLevel);
        Optional.of(resultSet.getDouble("MoneyReward")).ifPresent(task::setMoneyReward);
        Optional.of(resultSet.getDouble("ExpReward")).ifPresent(task::setExpReward);
        Optional.of(resultSet.getInt("RepReward")).ifPresent(task::setRepReward);
        Optional.of(resultSet.getInt("Priority")).ifPresent(task::setPriority);

        return task;
    }

    protected static MobKillTask loadMobKillTask(ResultSet resultSet) throws SQLException{
        return new MobKillTask("", TaskType.Mobkill, "", "");
    }

    @Override
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exists(int id) {
        return false;
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Task get(int id) throws NullPointerException{
        try{
            PreparedStatement ps = DataManager.getTaskManager().plugin.database.getConnection().
                    prepareStatement("SELECT TaskType FROM Task WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                String taskType = resultSet.getString("TaskType");
                if (taskType.equalsIgnoreCase(TaskType.Food.toString()) ||
                        taskType.equalsIgnoreCase(TaskType.Resource.toString()) ||
                        taskType.equalsIgnoreCase(TaskType.Crafting.toString())){
                    return loadGatheringTask(resultSet);
                }
                else{
                    return loadMobKillTask(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist.");
    }

    @Override
    public void update(int id, String variable, Object newValue) {

    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return null;
    }

}
