package me.rubix327.fancynations.data.nations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Nation{

    private final int id;
    private String name;

    public Nation(String name) {
        this.id = DataManager.getNationManager().getMaxId() + 1;
        this.name = name;
    }
}
