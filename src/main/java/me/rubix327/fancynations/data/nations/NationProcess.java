package me.rubix327.fancynations.data.nations;

import java.util.HashMap;

public class NationProcess implements INationManager{
    @Override
    public boolean exists(int nationId) {
        return false;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public void add(Nation nation) {

    }

    @Override
    public Nation get(int nationId) {
        return null;
    }

    @Override
    public Nation get(String name) {
        return null;
    }

    @Override
    public void update(int nationId, String variable, Object newValue) {

    }

    @Override
    public void remove(int nationId) {

    }

    @Override
    public HashMap<Integer, Nation> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
