package me.rubix327.fancynations.data.town;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter(AccessLevel.PACKAGE)
public class Town  {

    private final String name;

    public Town(String name) {
        this.name = name;
    }
}
