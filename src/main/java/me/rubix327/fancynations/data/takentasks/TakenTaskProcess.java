package me.rubix327.fancynations.data.takentasks;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TakenTaskProcess extends AbstractProcess<TakenTask> implements ITakenTaskManager {

    private static TakenTaskProcess instance = null;

    private TakenTaskProcess() {
        super(TakenTask.class);
    }

    public static TakenTaskProcess getInstance(){
        if (instance == null){
            instance = new TakenTaskProcess();
        }
        return instance;
    }

    @Override
    public boolean exists(int playerId, int taskId) {
        for (TakenTask takenTask : getAll().values()){
            if (takenTask.getPlayerId() == playerId && takenTask.getTaskId() == taskId) return true;
        }
        return false;
    }

    @Override
    public TakenTask get(int playerId, int taskId) throws NullPointerException {
        for (TakenTask takenTask : getAll().values()){
            if (takenTask.getPlayerId() == playerId && takenTask.getTaskId() == taskId) return takenTask;
        }
        throw new NullPointerException("This object does not exist. Use exists() before this method.");
    }

    @Override
    public HashMap<Integer, TakenTask> getAll(boolean log) {
        return super.getAll();
    }

    @Override
    public int getCountFor(int taskId) {
        return getAll().values().stream().filter(e -> e.getTaskId() == taskId).toList().size();
    }
}
