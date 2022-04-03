package me.rubix327.fancynations.data.barracks;

import java.util.HashMap;

public class BarracksDao implements IBarracksManager{
    @Override
    public boolean exists(int barracksId) {
        return false;
    }

    @Override
    public void add(Barracks barracks) {

    }

    @Override
    public Barracks get(int barracksId) {
        return null;
    }

    @Override
    public void update(int barracksId, String variable, Object newValue) {

    }

    @Override
    public void remove(int barracksId) {

    }

    @Override
    public HashMap<Integer, Barracks> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
