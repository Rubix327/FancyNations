package me.rubix327.fancynations.data.fnplayers;

import java.util.HashMap;

public class FNPlayerProcess implements IFNPlayerManager{
    @Override
    public boolean exists(int playerId) {
        return false;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public void add(FNPlayer fnPlayer) {

    }

    @Override
    public FNPlayer get(int playerId) {
        return null;
    }

    @Override
    public FNPlayer get(String name) {
        return null;
    }

    @Override
    public void update(int playerId, String variable, Object newValue) {

    }

    @Override
    public void remove(int playerId) {

    }

    @Override
    public HashMap<Integer, FNPlayer> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
