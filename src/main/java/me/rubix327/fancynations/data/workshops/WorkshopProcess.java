package me.rubix327.fancynations.data.workshops;

import me.rubix327.fancynations.data.AbstractProcess;

public class WorkshopProcess extends AbstractProcess<Workshop> implements IWorkshopManager {

    private static WorkshopProcess instance = null;

    private WorkshopProcess() {
        super(Workshop.class);
    }

    public static WorkshopProcess getInstance(){
        if (instance == null){
            instance = new WorkshopProcess();
        }
        return instance;
    }

}
