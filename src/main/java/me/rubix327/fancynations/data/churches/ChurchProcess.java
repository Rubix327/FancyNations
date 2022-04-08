package me.rubix327.fancynations.data.churches;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class ChurchProcess extends AbstractProcess<Church> implements IChurchManager {

    private static final HashMap<Integer, Church> dtos = new HashMap<>();

    public ChurchProcess() {
        super(dtos, Church.class);
    }

}
