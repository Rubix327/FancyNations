package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class ObjectivesProcess extends AbstractProcess<Objective> implements IObjectivesManager {

    private static ObjectivesProcess instance = null;

    private ObjectivesProcess() {
        super( Objective.class);
    }

    public static ObjectivesProcess getInstance(){
        if (instance == null){
            instance = new ObjectivesProcess();
        }
        return instance;
    }

    @Override
    public HashMap<Integer, Objective> getAllFor(String playerName, int taskId) {
        return null;
    }
}
