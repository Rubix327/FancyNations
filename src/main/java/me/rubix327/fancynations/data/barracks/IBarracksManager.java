package me.rubix327.fancynations.data.barracks;

import java.util.HashMap;

public interface IBarracksManager {

    boolean exists(int barracksId);
    void add(Barracks barracks);
    Barracks get(int barracksId);
    void update(int barracksId, String variable, Object newValue);
    void remove(int barracksId);
    HashMap<Integer, Barracks> getAll();

    int getNextId();

}
