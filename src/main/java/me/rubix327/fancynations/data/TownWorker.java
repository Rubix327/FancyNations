package me.rubix327.fancynations.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter(AccessLevel.PACKAGE)
public abstract class TownWorker {

    private static int maxTownWorkerId = 0;

    private final int id;
    private final String playerName;
    private double salary;

    protected TownWorker(String playerName) {
        this.id = generateId();
        this.playerName = playerName;
        this.salary = 1;
    }

    private int generateId(){
        maxTownWorkerId += 1;
        return maxTownWorkerId;
    }
}
