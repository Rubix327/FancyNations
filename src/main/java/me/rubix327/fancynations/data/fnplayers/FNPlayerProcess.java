package me.rubix327.fancynations.data.fnplayers;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class FNPlayerProcess extends AbstractProcess<FNPlayer> implements IFNPlayerManager {

    private static final HashMap<Integer, FNPlayer> dtos = new HashMap<>();

    public FNPlayerProcess() {
        super(dtos, FNPlayer.class);
    }

}
