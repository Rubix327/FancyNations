package me.rubix327.fancynations.data.nations;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class NationProcess extends AbstractProcess<Nation> implements INationManager {

    private static final HashMap<Integer, Nation> dtos = new HashMap<>();

    public NationProcess() {
        super(dtos, Nation.class);
    }

}
