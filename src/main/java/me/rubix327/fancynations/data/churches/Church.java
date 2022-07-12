package me.rubix327.fancynations.data.churches;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.towns.Town;
import org.bukkit.Location;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Church extends AbstractDto {

    private final int id;
    private final int townId;
    private String name;
    private Location location;
    private int level;

    public Church(int townId, String name, Location location) {
        this.id = DataManager.getChurchManager().getNextId();
        this.townId = townId;
        this.name = name;
        this.location = location;
        this.level = Settings.TownBuildings.DEFAULT_CHURCH_LEVEL;
    }

    public Town getTown(){
        return DataManager.getTownManager().get(townId);
    }
}
