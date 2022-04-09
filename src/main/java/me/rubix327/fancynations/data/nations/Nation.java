package me.rubix327.fancynations.data.nations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.IUniqueNamable;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Nation extends AbstractDto implements IUniqueNamable {

    private final int id;
    private String name;

    public Nation(String name) {
        this.id = DataManager.getNationManager().getMaxId() + 1;
        this.name = name;
    }
}
