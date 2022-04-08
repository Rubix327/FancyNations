package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class ObjectivesProcess extends AbstractProcess<Objective> implements IObjectivesManager {

    private static final HashMap<Integer, Objective> dtos = new HashMap<>();

    public ObjectivesProcess() {
        super(dtos, Objective.class);
    }

    @Override
    public HashMap<Integer, Objective> getAllFor(String playerName, int taskId) {
        return null;
    }
}
