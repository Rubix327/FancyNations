package me.rubix327.fancynations.data.defendtowers;

import java.util.HashMap;

public class DefendTowerProcess implements IDefendTowerManager{
    @Override
    public boolean exists(int towerId) {
        return false;
    }

    @Override
    public void add(DefendTower tower) {

    }

    @Override
    public DefendTower get(int towerId) {
        return null;
    }

    @Override
    public void update(int towerId, String variable, Object newValue) {

    }

    @Override
    public void remove(int towerId) {

    }

    @Override
    public HashMap<Integer, DefendTower> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
