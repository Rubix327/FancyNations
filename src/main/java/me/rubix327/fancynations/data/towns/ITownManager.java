package me.rubix327.fancynations.data.towns;

import me.rubix327.fancynations.FancyNations;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public interface ITownManager {

    FancyNations plugin = FancyNations.getInstance();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean exists(int townId);
    boolean exists(String name);
    void add(Town dto);
    Town get(int townId);
    Town get(String name);
    void update(int townId, String variable, Object newValue);
    void remove(int townId);
    HashMap<Integer, Town> getAll();
    List<String> getTownsFor(CommandSender sender);
    int getMaxId();

}
