package me.rubix327.fancynations.data.reputations;

import me.rubix327.fancynations.data.AbstractProcess;

public class ReputationProcess extends AbstractProcess<Reputation> implements IReputationManager {

    private static ReputationProcess instance = null;

    private ReputationProcess() {
        super(Reputation.class);
    }

    public static ReputationProcess getInstance(){
        if (instance == null){
            instance = new ReputationProcess();
        }
        return instance;
    }

    @Override
    public boolean exists(int playerId, int townId) {
        for (Reputation reputation : getAll().values()){
            if (reputation.getPlayerId() == playerId && reputation.getTownId() == townId) return true;
        }
        return false;
    }

    @Override
    public Reputation get(int playerId, int townId) throws NullPointerException {
        for (Reputation reputation : getAll().values()){
            if (reputation.getPlayerId() == playerId && reputation.getTownId() == townId) return reputation;
        }
        throw new NullPointerException("This object does not exist. Use exists() before this method.");
    }
}
