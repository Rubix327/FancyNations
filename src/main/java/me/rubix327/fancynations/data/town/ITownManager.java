package me.rubix327.fancynations.data.town;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.task.Task;

import java.util.HashMap;

public interface ITownManager {

    FancyNations plugin = FancyNations.getInstance();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean exists(String townName);
    void add(Town town);
    void remove(String townName);
    Task get(String townName);
    void update(String townName, String variable, Object newValue);
    HashMap<String, Town> getTowns();

}
