package me.rubix327.fancynations.data.farms;

import java.util.HashMap;

public class FarmProcess implements IFarmManager{
    @Override
    public boolean exists(int farmId) {
        return false;
    }

    @Override
    public void add(Farm farm) {

    }

    @Override
    public Farm get(int farmId) {
        return null;
    }

    @Override
    public void update(int farmId, String variable, Object newValue) {

    }

    @Override
    public void remove(int farmId) {

    }

    @Override
    public HashMap<Integer, Farm> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
