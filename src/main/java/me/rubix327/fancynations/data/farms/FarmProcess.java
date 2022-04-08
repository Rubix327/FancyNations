package me.rubix327.fancynations.data.farms;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class FarmProcess extends AbstractProcess<Farm> implements IFarmManager {

    private static final HashMap<Integer, Farm> dtos = new HashMap<>();

    public FarmProcess() {
        super(dtos, Farm.class);
    }

}
