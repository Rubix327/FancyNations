package me.rubix327.fancynations.data.townworkers;

import me.rubix327.fancynations.data.workertypes.WorkerType;

import java.util.HashMap;

public interface ITownWorkerManager {

    boolean exists(int townWorkerId);
    void add(TownWorker worker);
    TownWorker get(int townWorkerId) throws NullPointerException;
    TownWorker get(String playerName) throws NullPointerException;
    void update(int townWorkerId, String variable, Object newValue);
    void remove(int townWorkerId);
    boolean isMayor(int playerId);
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isWorker(int playerId);
    WorkerType getWorkerType(int playerId) throws NullPointerException;
    HashMap<Integer, TownWorker> getAll();
    int getMaxId();
}
