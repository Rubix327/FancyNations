package me.rubix327.fancynations.data;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.task.TaskDao;
import me.rubix327.fancynations.data.task.TaskManager;
import me.rubix327.fancynations.data.task.TaskProcess;
import me.rubix327.fancynations.data.worker.TownWorkerDao;
import me.rubix327.fancynations.data.worker.TownWorkerManager;
import me.rubix327.fancynations.data.worker.TownWorkerProcess;

import java.util.Set;

public class DataManager {

    public static int generateId(Set<Integer> set){
        if (set.isEmpty()) return 1;
        return getMaxKey(set) + 1;
    }

    public static Integer getMaxKey(Set<Integer> set) {
        Integer maxKey = null;

        for (Integer id : set) {
            if (maxKey == null || id > maxKey) {
                maxKey = id;
            }
        }
        return maxKey;
    }

    public static boolean isDatabaseChosen(){
        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")){
            return FancyNations.getInstance().database.isConnected();
        }
        return false;
    }

    public static TaskManager getTaskManager(){
        return (isDatabaseChosen() ? new TaskDao() : new TaskProcess());
    }

    public static TownWorkerManager getTownWorkerManager(){
        return (isDatabaseChosen() ? new TownWorkerDao() : new TownWorkerProcess());
    }
}
