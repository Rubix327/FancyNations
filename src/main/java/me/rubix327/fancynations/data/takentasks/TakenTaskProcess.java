package me.rubix327.fancynations.data.takentasks;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TakenTaskProcess extends AbstractProcess<TakenTask> implements ITakenTaskManager {

    private static final HashMap<Integer, TakenTask> dtos = new HashMap<>();

    public TakenTaskProcess() {
        super(dtos, TakenTask.class);
    }

    @Override
    public boolean exists(int playerId, int taskId) {
        return false;
    }

    @Override
    public TakenTask get(int playerId, int taskId) {
        return null;
    }
}
