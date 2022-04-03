package me.rubix327.fancynations.data.townworkers;

import lombok.Getter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;

public class TownWorkerProcess implements ITownWorkerManager{

    @Getter
    private static final HashMap<Integer, TownWorker> townWorkers = new HashMap<>();

    @Override
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exists(int townWorkerId){
        return townWorkers.containsKey(townWorkerId);
    }

    @Override
    public void add(TownWorker worker) {
        DataManager.getTownWorkerManager().getAll().put(worker.getId(), worker);
    }

    public void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("Town with this id does not exist.");
        }
        townWorkers.remove(id);
    }

    @Override
    public TownWorker get(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("Town worker with this id does not exist.");
        }
        return townWorkers.get(id);
    }

    @Override
    public TownWorker get(String playerName) throws IllegalArgumentException{
        for (TownWorker worker : townWorkers.values()){
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(worker.getPlayerId());
            if (fnPlayer.getName().equalsIgnoreCase(playerName)) return worker;
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

    @Override
    public void update(int townWorkerId, String variable, Object newValue) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isMayor(String playerName) {
        if (!isWorker(playerName)) return false;
        return getWorkerType(playerName) == WorkerType.Mayor;
    }

    @Override
    public boolean isWorker(String playerName){
        for (TownWorker worker : townWorkers.values()){
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(worker.getPlayerId());
            if (fnPlayer.getName().equalsIgnoreCase(playerName)) return true;
        }
        return false;
    }

    @Override
    public WorkerType getWorkerType(String playerName) throws IllegalArgumentException{
        return DataManager.getTownWorkerManager().getWorkerType(playerName);
    }

    @Override
    public HashMap<Integer, TownWorker> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }

}
