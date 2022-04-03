package me.rubix327.fancynations.data.defendtowers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Settings;
import org.bukkit.Location;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class DefendTower {

    private final int id;
    private final int townId;
    private String name;
    private Location location;
    private int level;
    private String loadedResource;
    private int amount;

    public DefendTower(int townId, String name, Location location) {
        this.id = DataManager.getDefendTowerManager().getMaxId() + 1;
        this.townId = townId;
        this.name = name;
        this.location = location;
        this.level = Settings.TownBuildings.DEFAULT_DEFEND_TOWER_LEVEL;
    }
}