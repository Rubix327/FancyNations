package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.DatabaseManager;
import org.apache.commons.lang.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ObjectivesDao implements IObjectivesManager{

    private Objective loadObjective(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("Objective.Id");
        int takenTaskId = resultSet.getInt("Objective.TakenTask");
        String name = resultSet.getString("Objective.Name");
        int amount = resultSet.getInt("Objective.Amount");

        return new Objective(id, takenTaskId, name, amount);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exists(int id) {
        try{
            String query = "SELECT Id FROM Objective WHERE Id = @ID";
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

    public void add(Objective objective) {
        try{
            String query = "INSERT INTO Objective (TakenTask, Name, Amount) VALUES (@TakenTask, @Name, '@Amount)";
            query = query
                    .replace("@TakenTask", String.valueOf(objective.getTakenTaskId()))
                    .replace("@Name", objective.getName())
                    .replace("@Amount", String.valueOf(objective.getAmount()));

            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().prepareStatement(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    public Objective get(int id) throws NullPointerException{
        try{
            String query = "SELECT * FROM Objective WHERE Id = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return loadObjective(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use Objective.exists() before this method.");
    }

    public void update(int id, String variable, Object newValue) {
        try{
            String query = "UPDATE Objective SET @Var = @Value WHERE ID = @ID";
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
        throw new NullPointerException("Task with this id does not exist. Use Objective.exists() before this method.");
    }

    public void remove(int id) {
        try{
            String query = "DELETE FROM Objective WHERE ID = @ID";
            query = query.replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use Objective.exists() before this method.");
    }

    public HashMap<Integer, Objective> getAll() throws NullPointerException{
        try{
            String query = "SELECT * FROM Objectives JOIN (TakenTask) ON (Objective.Id = TakenTask.Id)";
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            HashMap<Integer, Objective> objectives = new HashMap<>();
            while (resultSet.next()){
                objectives.put(resultSet.getInt("ID"), loadObjective(resultSet));
            }
            return objectives;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    @Override
    public int getMaxId() {
        throw new NotImplementedException();
    }

    public HashMap<String, Integer> getAllFor(String playerName, int taskId){
        int playerId = DataManager.getFNPlayerManager().get(playerName).getId();
        int takenTaskId = DataManager.getTakenTaskManager().get(playerId, taskId).getId();

        try{
            String query = "SELECT * FROM Objective WHERE TakenTask = @TakenTaskId";
            query = query.replace("@TakenTaskId", String.valueOf(takenTaskId));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);

            ResultSet resultSet = ps.executeQuery();
            HashMap<String, Integer> objectives = new HashMap<>();
            while (resultSet.next()){
                objectives.put(resultSet.getString("Name"), resultSet.getInt("Amount"));
            }
            return objectives;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Task with this id does not exist. Use Objective.exists() before this method.");
    }

}
