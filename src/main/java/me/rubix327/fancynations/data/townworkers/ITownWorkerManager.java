package me.rubix327.fancynations.data.townworkers;

import me.rubix327.fancynations.data.professions.Profession;

import java.util.HashMap;

public interface ITownWorkerManager {

    boolean exists(int townWorkerId);
    void add(TownWorker worker);
    TownWorker get(int townWorkerId) throws NullPointerException;
    TownWorker getByPlayer(String playerName) throws NullPointerException;
    void update(int townWorkerId, String variable, Object newValue);
    void remove(int townWorkerId);
    boolean isWorker(int playerId);
    boolean isMayor(int playerId);
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isWorker(int playerId, int townId);
    boolean isMayor(int playerId, int townId);
    Profession getProfession(int playerId) throws NullPointerException;
    HashMap<Integer, TownWorker> getAll();
    int getMaxId();
}
