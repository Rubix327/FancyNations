package me.rubix327.fancynations.data.professions;

import me.rubix327.fancynations.data.AbstractProcess;

public class ProfessionProcess extends AbstractProcess<Profession> implements IProfessionManager {

    private static ProfessionProcess instance = null;

    public ProfessionProcess() {
        super(Profession.class);
    }

    public static ProfessionProcess getInstance(){
        if (instance == null){
            instance = new ProfessionProcess();
        }
        return instance;
    }

}
