package me.rubix327.fancynations.data.defendtowers;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class DefendTowerProcess extends AbstractProcess<DefendTower> implements IDefendTowerManager {

    private static final HashMap<Integer, DefendTower> dtos = new HashMap<>();

    public DefendTowerProcess() {
        super(dtos, DefendTower.class);
    }

}
