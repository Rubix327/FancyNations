package me.rubix327.fancynations.data.townhouses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.towns.Town;
import org.bukkit.Location;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TownHouse extends AbstractDto {

    private final int id;
    private final int townId;
    private int ownerId;
    private Location location;

    public TownHouse(int townId, int ownerId, Location location) {
        this.id = DataManager.getTownHouseManager().getMaxId() + 1;
        this.townId = townId;
        this.ownerId = ownerId;
        this.location = location;
    }

    public Town getTown(){
        return DataManager.getTownManager().get(townId);
    }

    public FNPlayer getFNPlayer(){
        return DataManager.getFNPlayerManager().get(ownerId);
    }
}
