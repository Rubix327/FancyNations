package me.rubix327.fancynations.data.reputations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.towns.Town;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Reputation extends AbstractDto {

    @Getter
    private static IReputationManager manager = DataManager.getReputationsManager();

    private final int id;
    private final int playerId;
    private final int townId;
    private int amount;

    public Reputation(int playerId, int townId) {
        this.id = manager.getMaxId() + 1;
        this.playerId = playerId;
        this.townId = townId;
        this.amount = 0;
    }

    /**
     * Increase reputation of the player in the given town.
     * Works both ways: you can either increase or decrease it using negative number.
     * @param fnPlayerId player id
     * @param townId town id
     * @param amount on which number increase reputation
     */
    public static void increase(int fnPlayerId, int townId, int amount){
        if (!manager.exists(fnPlayerId, townId)){
            manager.add(new Reputation(fnPlayerId, townId));
        }
        Reputation reputation = manager.get(fnPlayerId, townId);
        DataManager.getReputationsManager().update(
                reputation.getId(), "amount", reputation.getAmount() + amount);
    }

    public FNPlayer getFNPlayer(){
        return DataManager.getFNPlayerManager().get(playerId);
    }

    public Town getTown(){
        return DataManager.getTownManager().get(townId);
    }
}
