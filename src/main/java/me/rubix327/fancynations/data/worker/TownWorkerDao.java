package me.rubix327.fancynations.data.worker;

public class TownWorkerDao extends TownWorkerManager{
    @Override
    public void add(int id, TownWorker worker) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public TownWorker getById(int id) throws NullPointerException {
        return null;
    }

    @Override
    public TownWorker getByName(String playerName) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isMayor(String playerName) {
        return false;
    }

    @Override
    public boolean isWorker(String playerName) {
        return false;
    }

    @Override
    public WorkerType getWorkerType(String playerName) throws IllegalArgumentException {
        return null;
    }
}
