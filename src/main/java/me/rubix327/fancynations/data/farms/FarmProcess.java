package me.rubix327.fancynations.data.farms;

import me.rubix327.fancynations.data.AbstractProcess;

public class FarmProcess extends AbstractProcess<Farm> implements IFarmManager {

    private static FarmProcess instance = null;

    private FarmProcess() {
        super(Farm.class);
    }

    public static FarmProcess getInstance(){
        if (instance == null){
            instance = new FarmProcess();
        }
        return instance;
    }

}
