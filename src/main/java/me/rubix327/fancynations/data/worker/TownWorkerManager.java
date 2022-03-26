package me.rubix327.fancynations.data.worker;

import lombok.Getter;

import java.util.HashMap;

public class TownWorkerManager {

    @Getter
    private static final HashMap<Integer, TownWorker> townWorkers = new HashMap<>();

    public static boolean exists(int workerId){
        return townWorkers.containsKey(workerId);
    }

    public static void add(int id, TownWorker worker) {
        townWorkers.put(id, worker);
    }
    public static void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        townWorkers.remove(id);
    }

    public static TownWorker getById(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("This mayor does not exist in mayors hashmap.");
        }
        return townWorkers.get(id);
    }

    public static boolean isMayor(String playerName) {
        return getWorkerType(playerName) == WorkerType.Mayor;
    }

    public static boolean isWorker(String playerName){
        for (TownWorker worker : townWorkers.values()){
            if (worker.getPlayerName().equalsIgnoreCase(playerName)) return true;
        }
        return false;
    }

    public static WorkerType getWorkerType(String playerName) throws IllegalArgumentException{
        for (TownWorker worker : townWorkers.values()){
            if (worker.getPlayerName().equalsIgnoreCase(playerName)) return worker.getWorkerType();
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

}
