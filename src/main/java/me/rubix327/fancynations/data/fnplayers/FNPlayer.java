package me.rubix327.fancynations.data.fnplayers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.IUniqueNamable;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class FNPlayer extends AbstractDto implements IUniqueNamable {

    private final int id;
    private final String name;
    private int reputation;

    public FNPlayer(String name, int reputation) {
        this.id = DataManager.getFNPlayerManager().getMaxId() + 1;
        this.name = name;
        this.reputation = reputation;
    }
}
