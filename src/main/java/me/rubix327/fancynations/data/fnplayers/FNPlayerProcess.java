package me.rubix327.fancynations.data.fnplayers;

import me.rubix327.fancynations.data.AbstractProcess;

public class FNPlayerProcess extends AbstractProcess<FNPlayer> implements IFNPlayerManager {

    private static FNPlayerProcess instance = null;

    private FNPlayerProcess() {
        super(FNPlayer.class);
    }

    public static FNPlayerProcess getInstance(){
        if (instance == null){
            instance = new FNPlayerProcess();
        }
        return instance;
    }

}
