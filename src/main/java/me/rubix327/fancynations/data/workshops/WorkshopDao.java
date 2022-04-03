package me.rubix327.fancynations.data.workshops;

import java.util.HashMap;

public class WorkshopDao implements IWorkshopManager{
    @Override
    public boolean exists(int workshopId) {
        return false;
    }

    @Override
    public void add(Workshop workshop) {

    }

    @Override
    public Workshop get(int workshopId) {
        return null;
    }

    @Override
    public void update(int workshopId, String variable, Object newValue) {

    }

    @Override
    public void remove(int workshopId) {

    }

    @Override
    public HashMap<Integer, Workshop> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
