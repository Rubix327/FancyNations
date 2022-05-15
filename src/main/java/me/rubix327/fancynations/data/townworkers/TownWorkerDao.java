package me.rubix327.fancynations.data.townworkers;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.professions.PredefinedProfession;

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
        int profession = resultSet.getInt("Profession");
        String displayName = resultSet.getString("DisplayName");
        int salary = resultSet.getInt("Salary");

        return new TownWorker(
                id, townId, playerId, profession, displayName, salary);
    }

    @Override
    public void add(TownWorker worker) {
        String query = getQuery("town_workers_add");

        query = query
                .replace("@Table", table)
                .replace("@PlayerID", String.valueOf(worker.getPlayerId()))
                .replace("@TownID", String.valueOf(worker.getTownId()))
                .replace("@Profession", String.valueOf(worker.getProfessionId()))
                .replace("@DisplayName", String.valueOf(worker.getDisplayName()))
                .replace("@Salary", String.valueOf(worker.getSalary()));

        super.executeVoid(query);
    }

    public TownWorker getByPlayer(int playerId) throws NullPointerException {
        for (TownWorker worker : getAll().values()){
            if (worker.getPlayerId() == playerId) return worker;
        }
        throw new NullPointerException("Town worker with this player name does not exist.");
    }

    public TownWorker getByPlayer(String playerName) throws NullPointerException {
        int playerId = DataManager.getFNPlayerManager().get(playerName).getId();
        for (TownWorker worker : getAll().values()){
            if (worker.getPlayerId() == playerId) return worker;
        }
        throw new NullPointerException("Town worker with this player name does not exist.");
    }

    public boolean isMayor(int playerId){
        if (!isWorker(playerId)) return false;
        return FNPlayer.get(playerId).getProfession().getName().equalsIgnoreCase(PredefinedProfession.Mayor.toString());
    }

    public boolean isWorker(int playerId){
        String query = getQuery("town_workers_is_worker");
        query = query
                .replace("@Table", table)
                .replace("@PlayerID", String.valueOf(playerId));
        return executeBool(query);
    }

    public boolean isMayor(int playerId, int townId) {
        if (!isWorker(playerId, townId)) return false;
        return FNPlayer.get(playerId).getProfession().getName().equalsIgnoreCase(PredefinedProfession.Mayor.toString());
    }

    public boolean isWorker(int playerId, int townId) {
        String query = getQuery("town_workers_is_worker_in_town");
        query = query
                .replace("@Table", table)
                .replace("@PlayerID", String.valueOf(playerId))
                .replace("@TownID", String.valueOf(townId));
        return executeBool(query);
    }
}
