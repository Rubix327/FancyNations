package me.rubix327.fancynations.data.nations;

import java.util.HashMap;
import java.util.List;

public interface INationManager {

    boolean exists(int nationId);
    boolean exists(String name);
    void add(Nation nation);
    Nation get(int nationId);
    Nation get(String name);
    void update(int nationId, String variable, Object newValue);
    void remove(int nationId);
    HashMap<Integer, Nation> getAll();

    int getNextId();

    List<String> getNames();

}
