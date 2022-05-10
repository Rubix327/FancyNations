package me.rubix327.fancynations.data.townworkers;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.workertypes.WorkerType;
import me.rubix327.fancynations.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TownWorkerDao extends AbstractDao<TownWorker> implements ITownWorkerManager {

    private static TownWorkerDao instance = null;

    private TownWorkerDao(String table) {
        super(table);
    }

    public static TownWorkerDao getInstance(String tableName){
        if (instance == null){
            instance = new TownWorkerDao(tableName);
        }
        return instance;
    }

    @Override
    protected TownWorker loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int playerId = resultSet.getInt("Player");
        int townId = resultSet.getInt("Town");
        int workerTypeId = resultSet.getInt("WorkerType");
        String displayName = resultSet.getString("DisplayName");
        int salary = resultSet.getInt("Salary");

        return new TownWorker(
                id, playerId, townId, workerTypeId, displayName, salary);
    }

    @Override
    public void add(TownWorker worker) {
        String query = getQuery("town_workers_add");

        query = query
                .replace("@Table", table)
                .replace("@PlayerID", String.valueOf(worker.getPlayerId()))
                .replace("@TownID", String.valueOf(worker.getTownId()))
                .replace("@WorkerTypeID", String.valueOf(worker.getWorkerTypeId()))
                .replace("@DisplayName", String.valueOf(worker.getDisplayName()))
                .replace("@Salary", String.valueOf(worker.getSalary()));

        super.executeVoid(query);
    }

    public TownWorker getByPlayer(String playerName) throws NullPointerException {
        int playerId = DataManager.getFNPlayerManager().get(playerName).getId();
        for (TownWorker worker : getAll().values()){
            if (worker.getPlayerId() == playerId) return worker;
        }
        throw new NullPointerException("Town worker with this player name does not exist.");
    }

    public boolean isMayor(int playerId) {
        if (!isWorker(playerId)) return false;
        return getWorkerType(playerId).getName().equalsIgnoreCase("Mayor");
    }

    public boolean isWorker(int playerId) {
        String query = getQuery("town_workers_is_worker");
        query = query
                .replace("@Table", table)
                .replace("@PlayerID", String.valueOf(playerId));
        return super.executeBool(query);
    }

    public WorkerType getWorkerType(int playerId) throws IllegalArgumentException {
        try{
            String query = getQuery("town_workers_get_type");
            query = query
                    .replace("@Table", table)
                    .replace("@WorkerTypesTable", Settings.DbTables.WORKER_TYPES)
                    .replace("@PlayerID", String.valueOf(playerId));
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            Logger.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                DataManager.getWorkerTypeManager().get(resultSet.getInt("Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Object with this id does not exist. Use Dao.exists() before this method.");
    }
}
