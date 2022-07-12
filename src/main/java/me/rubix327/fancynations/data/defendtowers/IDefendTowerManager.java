package me.rubix327.fancynations.data.defendtowers;

import java.util.HashMap;

public interface IDefendTowerManager {

    boolean exists(int towerId);
    void add(DefendTower tower);
    DefendTower get(int towerId);
    void update(int towerId, String variable, Object newValue);
    void remove(int towerId);
    HashMap<Integer, DefendTower> getAll();

    int getNextId();

}
