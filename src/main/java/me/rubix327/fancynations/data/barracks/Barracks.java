package me.rubix327.fancynations.data.barracks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Settings;
import org.bukkit.Location;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Barracks {

    private final int id;
    private final int townId;
    private String name;
    private Location location;
    private int level;

    public Barracks(int townId, String name, Location location) {
        this.id = DataManager.getBarracksManager().getMaxId() + 1;
        this.townId = townId;
        this.name = name;
        this.location = location;
        this.level = Settings.TownBuildings.DEFAULT_BARRACKS_LEVEL;
    }
}
