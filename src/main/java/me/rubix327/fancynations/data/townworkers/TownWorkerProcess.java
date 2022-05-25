package me.rubix327.fancynations.data.townworkers;

import me.rubix327.fancynations.data.AbstractProcess;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.professions.PredefinedProfession;

public class TownWorkerProcess extends AbstractProcess<TownWorker> implements ITownWorkerManager {

    private static TownWorkerProcess instance = null;

    private TownWorkerProcess() {
        super(TownWorker.class);
    }

    public static TownWorkerProcess getInstance(){
        if (instance == null){
            instance = new TownWorkerProcess();
        }
        return instance;
    }

    public TownWorker getByPlayer(int playerId) throws IllegalArgumentException{
        for (TownWorker worker : getAll().values()){
            if (worker.getFNPlayer().getId() == playerId) return worker;
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

    public TownWorker getByPlayer(String playerName) throws IllegalArgumentException{
        for (TownWorker worker : getAll().values()){
            if (worker.getFNPlayer().getName().equalsIgnoreCase(playerName)) return worker;
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

    public boolean isMayor(int playerId) {
        if (!isWorker(playerId)) return false;
        return FNPlayer.get(playerId).getProfession().getName().equalsIgnoreCase(PredefinedProfession.Mayor.toString());
    }

    public boolean isWorker(int playerId){
        for (TownWorker worker : getAll().values()){
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(worker.getPlayerId());
            if (fnPlayer.getId() == playerId) return true;
        }
        return false;
    }

    public boolean isMayor(int playerId, int townId){
        if (!isWorker(playerId, townId)) return false;
        return FNPlayer.get(playerId).getProfession().getName().equalsIgnoreCase(PredefinedProfession.Mayor.toString());
    }

    public boolean isWorker(int playerId, int townId){
        for (TownWorker worker : getAll().values()){
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(worker.getPlayerId());
            if (fnPlayer.getId() == playerId && worker.getTownId() == townId) return true;
        }
        return false;
    }

}
