package me.rubix327.fancynations.data.townhouses;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TownHouseProcess extends AbstractProcess<TownHouse> implements ITownHouseManager {

    private static final HashMap<Integer, TownHouse> dtos = new HashMap<>();

    public TownHouseProcess() {
        super(dtos, TownHouse.class);
    }

}
