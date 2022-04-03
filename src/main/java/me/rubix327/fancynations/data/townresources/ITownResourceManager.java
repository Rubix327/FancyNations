package me.rubix327.fancynations.data.townresources;

import java.util.HashMap;

public interface ITownResourceManager {

    boolean exists(int resourceId);
    void add(TownResource townResource);
    TownResource get(int resourceId);
    void update(int resourceId, String variable, Object newValue);
    void remove(int resourceId);
    HashMap<Integer, TownResource> getAll();
    int getMaxId();

}
