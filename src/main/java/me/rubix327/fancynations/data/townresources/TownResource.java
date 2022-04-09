package me.rubix327.fancynations.data.townresources;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.AbstractDto;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TownResource extends AbstractDto {

    private final int id;
    private final int townId;
    private final String name;
    private int amount;

    public TownResource(int townId, String name, int amount) {
        this.id = DataManager.getTownResourceManager().getMaxId() + 1;
        this.townId = townId;
        this.name = name;
        this.amount = amount;
    }
}
