package me.rubix327.fancynations.data.townresources;

import java.util.HashMap;

public class TownResourceDao implements ITownResourceManager{
    @Override
    public boolean exists(int resourceId) {
        return false;
    }

    @Override
    public void add(TownResource townResource) {

    }

    @Override
    public TownResource get(int resourceId) {
        return null;
    }

    @Override
    public void update(int resourceId, String variable, Object newValue) {

    }

    @Override
    public void remove(int resourceId) {

    }

    @Override
    public HashMap<Integer, TownResource> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
