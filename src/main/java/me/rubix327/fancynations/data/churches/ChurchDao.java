package me.rubix327.fancynations.data.churches;

import java.util.HashMap;

public class ChurchDao implements IChurchManager{
    @Override
    public boolean exists(int churchId) {
        return false;
    }

    @Override
    public void add(Church church) {

    }

    @Override
    public Church get(int churchId) {
        return null;
    }

    @Override
    public void update(int churchId, String variable, Object newValue) {

    }

    @Override
    public void remove(int churchId) {

    }

    @Override
    public HashMap<Integer, Church> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
