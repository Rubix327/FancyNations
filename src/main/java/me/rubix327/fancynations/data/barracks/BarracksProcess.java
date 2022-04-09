package me.rubix327.fancynations.data.barracks;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class BarracksProcess extends AbstractProcess<Barracks> implements IBarracksManager {

    private static final HashMap<Integer, Barracks> dtos = new HashMap<>();

    public BarracksProcess() {
        super(dtos, Barracks.class);
    }

}
