package me.rubix327.fancynations.data.objectives;

import java.util.HashMap;

public interface IObjectivesManager {

    boolean exists(int objectiveId);
    void add(Objective objective);
    Objective get(int objectiveId);
    void update(int objectiveId, String variable, Object newValue);
    void remove(int objectiveId);
    HashMap<Integer, Objective> getAll();
    /**
     * Get all objectives for the specified player from the specified task.
     * @param playerName Player name for which you try to find objectives
     * @param taskId Task id for which you try to find objectives
     * @return HashMap - {ID: Objective} / may be empty if objectives not found for this player!
     */
    HashMap<Integer, Objective> getAllFor(String playerName, int taskId);
    int getMaxId();

}
