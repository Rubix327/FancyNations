package me.rubix327.fancynations.data.fnplayers;

import java.util.HashMap;

public interface IFNPlayerManager {

    boolean exists(int playerId);
    boolean exists(String name);
    void add(FNPlayer fnPlayer);
    FNPlayer get(int playerId);
    FNPlayer get(String name);
    void update(int playerId, String variable, Object newValue);
    void remove(int playerId);
    HashMap<Integer, FNPlayer> getAll();
    int getMaxId();

}
