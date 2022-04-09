package me.rubix327.fancynations.data.reputations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Reputation extends AbstractDto {

    private final int id;
    private final int playerId;
    private final int townId;
    private int amount;

    public Reputation(int id, int playerId, int townId) {
        this.id = id;
        this.playerId = playerId;
        this.townId = townId;
        this.amount = 0;
    }
}
