package me.rubix327.fancynations.data.townresources;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TownResourceProcess extends AbstractProcess<TownResource> implements ITownResourceManager {

    private static final HashMap<Integer, TownResource> dtos = new HashMap<>();

    public TownResourceProcess() {
        super(dtos, TownResource.class);
    }

}
