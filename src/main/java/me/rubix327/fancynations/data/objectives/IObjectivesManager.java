package me.rubix327.fancynations.data.objectives;

import java.util.HashMap;

public interface IObjectivesManager {

    boolean exists(int objectiveId);
    void add(Objective objective);
    Objective get(int objectiveId);
    void update(int objectiveId, String variable, Object newValue);
    void remove(int objectiveId);
    HashMap<Integer, Objective> getAll();
    HashMap<String, Integer> getAllFor(String playerName, int taskId);
    int getMaxId();

}
