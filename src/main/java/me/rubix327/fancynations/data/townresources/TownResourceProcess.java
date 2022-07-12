package me.rubix327.fancynations.data.townresources;

import me.rubix327.fancynations.data.AbstractProcess;

public class TownResourceProcess extends AbstractProcess<TownResource> implements ITownResourceManager {

    private static TownResourceProcess instance = null;

    private TownResourceProcess() {
        super(TownResource.class);
    }

    public static TownResourceProcess getInstance() {
        if (instance == null) {
            instance = new TownResourceProcess();
        }
        return instance;
    }

    @Override
    public boolean exists(int townId, String resourceName) {
        for (TownResource resource : this.getAll().values()) {
            if (resource.getTownId() == townId && resource.getName().equalsIgnoreCase(resourceName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TownResource get(int townId, String resourceName) {
        for (TownResource resource : this.getAll().values()) {
            if (resource.getTownId() == townId && resource.getName().equalsIgnoreCase(resourceName)) {
                return resource;
            }
        }
        throw new NullPointerException("This town resource does not exist. Use exists() method before.");
    }
}
