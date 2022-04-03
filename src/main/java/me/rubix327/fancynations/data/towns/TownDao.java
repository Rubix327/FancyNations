package me.rubix327.fancynations.data.towns;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public class TownDao implements ITownManager{
    @Override
    public boolean exists(int townId) {
        return false;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public void add(Town town) {

    }

    @Override
    public Town get(int townId) {
        return null;
    }

    @Override
    public Town get(String name) {
        return null;
    }

    @Override
    public void update(int townId, String variable, Object newValue) {

    }

    @Override
    public void remove(int townId) {

    }

    @Override
    public HashMap<Integer, Town> getAll() {
        return null;
    }

    @Override
    public List<String> getTownsFor(CommandSender sender) {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
