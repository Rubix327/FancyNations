package me.rubix327.fancynations.data.townresources;

import me.rubix327.fancynations.data.AbstractProcess;

public class TownResourceProcess extends AbstractProcess<TownResource> implements ITownResourceManager {

    private static TownResourceProcess instance = null;

    private TownResourceProcess() {
        super(TownResource.class);
    }

    public static TownResourceProcess getInstance(){
        if (instance == null){
            instance = new TownResourceProcess();
        }
        return instance;
    }

}
