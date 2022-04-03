package me.rubix327.fancynations.data.townhouses;

import java.util.HashMap;

public class TownHouseProcess implements ITownHouseManager{
    @Override
    public boolean exists(int houseId) {
        return false;
    }

    @Override
    public void add(TownHouse house) {

    }

    @Override
    public TownHouse get(int houseId) {
        return null;
    }

    @Override
    public void update(int houseId, String variable, Object newValue) {

    }

    @Override
    public void remove(int houseId) {

    }

    @Override
    public HashMap<Integer, TownHouse> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
