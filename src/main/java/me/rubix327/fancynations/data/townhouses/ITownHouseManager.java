package me.rubix327.fancynations.data.townhouses;

import java.util.HashMap;

public interface ITownHouseManager {

    boolean exists(int houseId);
    void add(TownHouse house);
    TownHouse get(int houseId);
    void update(int houseId, String variable, Object newValue);
    void remove(int houseId);
    HashMap<Integer, TownHouse> getAll();
    int getMaxId();

}
