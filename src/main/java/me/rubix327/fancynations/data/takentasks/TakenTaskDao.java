package me.rubix327.fancynations.data.takentasks;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.DatabaseManager;
import org.apache.commons.lang.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TakenTaskDao implements ITakenTaskManager {

    private TakenTask loadTakenTask(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("TakenTask.Id");
        int playerId = resultSet.getInt("TakenTask.PlayerId");
        int taskId = resultSet.getInt("TakenTask.TaskId");

        return new TakenTask(id, playerId, taskId);
    }

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
    public boolean exists(int playerId, int taskId) {
        try{
            String query = "SELECT Id FROM Task WHERE Player = @PlayerID AND Task = @TaskID";
            query = query.replace("@PlayerID", String.valueOf(playerId))
                    .replace("@TaskID", String.valueOf(taskId));

            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    public void add(TakenTask takenTask) {
        try{
            String query = "INSERT INTO TakenTask (Player, Task) VALUES (@Player, @Task)";
            query = query
                    .replace("@Player", String.valueOf(takenTask.getPlayerId()))
                    .replace("@Task", String.valueOf(takenTask.getTaskId()));

            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().prepareStatement(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    public TakenTask get(int takenTaskId) throws NullPointerException{
        throw new NotImplementedException();
    }

    @Override
    public TakenTask get(int playerId, int taskId) {
        return null;
    }

    @Override
    public void update(int takenTaskId, String variable, Object newValue) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(int takenTaskId) {
        throw new NotImplementedException();
    }

    @Override
    public HashMap<Integer, TakenTask> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public int getMaxId() {
        throw new NotImplementedException();
    }

    public int getId(int playerId, int taskId) throws NullPointerException{
        try{
            String query = "SELECT * FROM TakenTask WHERE Player = @PlayerID AND Task = @TaskID";
            query = query.replace("@PlayerID", String.valueOf(playerId))
                    .replace("@TaskID", String.valueOf(taskId));

            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use TakenTask.exists() before this method.");
    }

}
