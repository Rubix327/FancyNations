package me.rubix327.fancynations.data.townworkers;

import java.util.HashMap;

public interface ITownWorkerManager {

    boolean exists(int townWorkerId);
    void add(TownWorker worker);
    TownWorker get(int townWorkerId) throws NullPointerException;
    TownWorker get(String playerName) throws IllegalArgumentException;
    void update(int townWorkerId, String variable, Object newValue);
    void remove(int townWorkerId);
    boolean isMayor(String playerName);
    boolean isWorker(String playerName);
    WorkerType getWorkerType(String playerName) throws IllegalArgumentException;
    HashMap<Integer, TownWorker> getAll();
    int getMaxId();
}
