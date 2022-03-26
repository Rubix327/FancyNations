package me.rubix327.fancynations.data;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Town  {

    private final String name;

    public Town(String name) {
        this.name = name;
    }
}
