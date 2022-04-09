package me.rubix327.fancynations.data.churches;

import me.rubix327.fancynations.data.AbstractProcess;

public class ChurchProcess extends AbstractProcess<Church> implements IChurchManager {

    private static ChurchProcess instance = null;

    private ChurchProcess() {
        super(Church.class);
    }

    public static ChurchProcess getInstance(){
        if (instance == null){
            instance = new ChurchProcess();
        }
        return instance;
    }

}
