package me.rubix327.fancynations.data.workshops;

import java.util.HashMap;

public interface IWorkshopManager {

    boolean exists(int workshopId);
    void add(Workshop workshop);
    Workshop get(int workshopId);
    void update(int workshopId, String variable, Object newValue);
    void remove(int workshopId);
    HashMap<Integer, Workshop> getAll();
    int getMaxId();

}
