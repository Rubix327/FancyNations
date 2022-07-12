package me.rubix327.fancynations.data.professions;

import java.util.HashMap;
import java.util.List;

public interface IProfessionManager {

    boolean exists(int professionId);
    boolean exists(String professionName);

    /**
     * This method is only for creating default entries.
     * It should be ran in onPluginStart method.
     * Do not use it for common creating new records.
     */
    void addIgnore(Profession profession);
    void add(Profession profession);
    Profession get(int professionId);
    Profession get(String professionName);
    HashMap<Integer, Profession> getAll();
    List<String> getNames();

    int getNextId();

}
