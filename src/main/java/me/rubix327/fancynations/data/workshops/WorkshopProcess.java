package me.rubix327.fancynations.data.workshops;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class WorkshopProcess extends AbstractProcess<Workshop> implements IWorkshopManager {

    private static final HashMap<Integer, Workshop> dtos = new HashMap<>();

    public WorkshopProcess() {
        super(dtos, Workshop.class);
    }

}
