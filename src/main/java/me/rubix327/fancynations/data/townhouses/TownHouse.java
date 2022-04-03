package me.rubix327.fancynations.data.townhouses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TownHouse {

    private final int id;
    private final int townId;
    private int ownerId;
    private int location;

    public TownHouse(int townId, int ownerId, int location) {
        this.id = DataManager.getTownHouseManager().getMaxId() + 1;
        this.townId = townId;
        this.ownerId = ownerId;
        this.location = location;
    }
}
