package me.rubix327.fancynations.data.barracks;

import me.rubix327.fancynations.data.AbstractProcess;

public class BarracksProcess extends AbstractProcess<Barracks> implements IBarracksManager {

    private static BarracksProcess instance = null;

    private BarracksProcess() {
        super(Barracks.class);
    }

    public static BarracksProcess getInstance(){
        if (instance == null){
            instance = new BarracksProcess();
        }
        return instance;
    }

}
