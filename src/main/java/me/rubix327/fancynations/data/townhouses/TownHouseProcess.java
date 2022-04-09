package me.rubix327.fancynations.data.townhouses;

import me.rubix327.fancynations.data.AbstractProcess;

public class TownHouseProcess extends AbstractProcess<TownHouse> implements ITownHouseManager {

    private static TownHouseProcess instance = null;

    private TownHouseProcess() {
        super(TownHouse.class);
    }

    public static TownHouseProcess getInstance(){
        if (instance == null){
            instance = new TownHouseProcess();
        }
        return instance;
    }

}
