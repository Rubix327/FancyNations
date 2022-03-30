package me.rubix327.fancynations.data.worker;

import lombok.Getter;

import java.util.HashMap;

public abstract class TownWorkerManager {

    @Getter
    private static final HashMap<Integer, TownWorker> townWorkers = new HashMap<>();

    public abstract void add(int id, TownWorker worker);
    public abstract void remove(int id);
    public abstract TownWorker getById(int id) throws NullPointerException;
    public abstract TownWorker getByName(String playerName) throws IllegalArgumentException;
    public abstract boolean isMayor(String playerName);
    public abstract boolean isWorker(String playerName);
    public abstract WorkerType getWorkerType(String playerName) throws IllegalArgumentException;

}
