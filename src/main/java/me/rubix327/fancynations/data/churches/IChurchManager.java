package me.rubix327.fancynations.data.churches;

import java.util.HashMap;

public interface IChurchManager {

    boolean exists(int churchId);
    void add(Church church);
    Church get(int churchId);
    void update(int churchId, String variable, Object newValue);
    void remove(int churchId);
    HashMap<Integer, Church> getAll();

    int getNextId();

}
