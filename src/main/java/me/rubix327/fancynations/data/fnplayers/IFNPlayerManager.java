package me.rubix327.fancynations.data.fnplayers;

import java.util.HashMap;
import java.util.List;

public interface IFNPlayerManager{

    boolean exists(int playerId);
    boolean exists(String name);
    void add(FNPlayer fnPlayer);

    /**
     * This method is only for creating default entries.
     * It should be ran in onPluginStart method.
     * Do not use it for common creating new records.
     */
    void addIgnore(FNPlayer fnPlayer);
    FNPlayer get(int playerId);
    FNPlayer get(String name);
    void update(int playerId, String variable, Object newValue);
    void remove(int playerId);
    HashMap<Integer, FNPlayer> getAll();
    int getMaxId();
    List<String> getNames();

}
