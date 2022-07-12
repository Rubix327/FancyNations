package me.rubix327.fancynations.data.townresources;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.towns.Town;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TownResource extends AbstractDto {

    @Getter
    private static ITownResourceManager manager = DataManager.getTownResourceManager();

    private final int id;
    private final int townId;
    private final String name;
    private int amount;

    public TownResource(int townId, String name, int amount) {
        this.id = DataManager.getTownResourceManager().getNextId();
        this.townId = townId;
        this.name = name;
        this.amount = amount;
    }

    public Town getTown() {
        return DataManager.getTownManager().get(townId);
    }

    public static TownResource getOrCreate(int townId, String name) {
        if (manager.exists(townId, name)) {
            return manager.get(townId, name);
        } else {
            TownResource res = new TownResource(townId, name, 0);
            manager.add(res);
            return res;
        }
    }

    public void addResources(int amount) {
        manager.update(this.id, "Amount", getAmount() + amount);
    }
}
