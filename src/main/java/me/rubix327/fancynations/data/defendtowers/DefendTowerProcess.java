package me.rubix327.fancynations.data.defendtowers;

import me.rubix327.fancynations.data.AbstractProcess;

public class DefendTowerProcess extends AbstractProcess<DefendTower> implements IDefendTowerManager {

    private static DefendTowerProcess instance = null;

    private DefendTowerProcess() {
        super(DefendTower.class);
    }

    public static DefendTowerProcess getInstance(){
        if (instance == null){
            instance = new DefendTowerProcess();
        }
        return instance;
    }

}
