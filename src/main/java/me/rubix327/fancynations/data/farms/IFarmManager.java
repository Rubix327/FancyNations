package me.rubix327.fancynations.data.farms;

import java.util.HashMap;

public interface IFarmManager {

    boolean exists(int farmId);
    void add(Farm farm);
    Farm get(int farmId);
    void update(int farmId, String variable, Object newValue);
    void remove(int farmId);
    HashMap<Integer, Farm> getAll();

    int getNextId();

}
