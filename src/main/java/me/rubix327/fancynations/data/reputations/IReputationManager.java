package me.rubix327.fancynations.data.reputations;

import java.util.HashMap;

public interface IReputationManager {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean exists(int reputationId);
    boolean exists(int playerId, int townId);
    void add(Reputation reputation);
    Reputation get(int reputationId);
    Reputation get(int playerId, int townId);
    void update(int reputationId, String variable, Object newValue);
    void remove(int reputationId);
    HashMap<Integer, Reputation> getAll();
    int getMaxId();

}
