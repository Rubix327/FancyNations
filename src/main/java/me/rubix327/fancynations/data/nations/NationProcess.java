package me.rubix327.fancynations.data.nations;

import me.rubix327.fancynations.data.AbstractProcess;

public class NationProcess extends AbstractProcess<Nation> implements INationManager {

    private static NationProcess instance = null;

    private NationProcess() {
        super(Nation.class);
    }

    public static NationProcess getInstance(){
        if (instance == null){
            instance = new NationProcess();
        }
        return instance;
    }

}
