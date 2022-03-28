package me.rubix327.fancynations.data.worker;

public class TownWorkerProcess extends TownWorkerManager{

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean exists(int workerId){
        return TownWorkerManager.getTownWorkers().containsKey(workerId);
    }

    public void add(int id, TownWorker worker) {
        TownWorkerManager.getTownWorkers().put(id, worker);
    }
    public void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("Town with this id does not exist.");
        }
        TownWorkerManager.getTownWorkers().remove(id);
    }

    public TownWorker getById(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("Town worker with this id does not exist.");
        }
        return TownWorkerManager.getTownWorkers().get(id);
    }

    public TownWorker getByName(String playerName) throws IllegalArgumentException{
        for (TownWorker worker : TownWorkerManager.getTownWorkers().values()){
            if (worker.getPlayerName().equalsIgnoreCase(playerName)) return worker;
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

    public boolean isMayor(String playerName) {
        if (!isWorker(playerName)) return false;
        return getWorkerType(playerName) == WorkerType.Mayor;
    }

    public boolean isWorker(String playerName){
        for (TownWorker worker : TownWorkerManager.getTownWorkers().values()){
            if (worker.getPlayerName().equalsIgnoreCase(playerName)) return true;
        }
        return false;
    }

    public WorkerType getWorkerType(String playerName) throws IllegalArgumentException{
        for (TownWorker worker : TownWorkerManager.getTownWorkers().values()){
            if (worker.getPlayerName().equalsIgnoreCase(playerName)) return worker.getWorkerType();
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

}
