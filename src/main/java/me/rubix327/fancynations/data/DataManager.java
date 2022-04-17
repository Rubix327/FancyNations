package me.rubix327.fancynations.data;

import lombok.Getter;
import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.barracks.BarracksDao;
import me.rubix327.fancynations.data.barracks.BarracksProcess;
import me.rubix327.fancynations.data.barracks.IBarracksManager;
import me.rubix327.fancynations.data.churches.ChurchDao;
import me.rubix327.fancynations.data.churches.ChurchProcess;
import me.rubix327.fancynations.data.churches.IChurchManager;
import me.rubix327.fancynations.data.defendtowers.DefendTowerDao;
import me.rubix327.fancynations.data.defendtowers.DefendTowerProcess;
import me.rubix327.fancynations.data.defendtowers.IDefendTowerManager;
import me.rubix327.fancynations.data.farms.FarmDao;
import me.rubix327.fancynations.data.farms.FarmProcess;
import me.rubix327.fancynations.data.farms.IFarmManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayerDao;
import me.rubix327.fancynations.data.fnplayers.FNPlayerProcess;
import me.rubix327.fancynations.data.fnplayers.IFNPlayerManager;
import me.rubix327.fancynations.data.nations.INationManager;
import me.rubix327.fancynations.data.nations.NationDao;
import me.rubix327.fancynations.data.nations.NationProcess;
import me.rubix327.fancynations.data.objectives.IObjectivesManager;
import me.rubix327.fancynations.data.objectives.ObjectivesDao;
import me.rubix327.fancynations.data.objectives.ObjectivesProcess;
import me.rubix327.fancynations.data.reputations.IReputationManager;
import me.rubix327.fancynations.data.reputations.ReputationDao;
import me.rubix327.fancynations.data.reputations.ReputationProcess;
import me.rubix327.fancynations.data.takentasks.ITakenTaskManager;
import me.rubix327.fancynations.data.takentasks.TakenTaskDao;
import me.rubix327.fancynations.data.takentasks.TakenTaskProcess;
import me.rubix327.fancynations.data.taskprogresses.ITaskProgressManager;
import me.rubix327.fancynations.data.taskprogresses.TaskProgressDao;
import me.rubix327.fancynations.data.taskprogresses.TaskProgressProcess;
import me.rubix327.fancynations.data.tasks.ITaskManager;
import me.rubix327.fancynations.data.tasks.TaskDao;
import me.rubix327.fancynations.data.tasks.TaskProcess;
import me.rubix327.fancynations.data.tasktypes.ITaskTypeManager;
import me.rubix327.fancynations.data.tasktypes.TaskTypeDao;
import me.rubix327.fancynations.data.tasktypes.TaskTypeProcess;
import me.rubix327.fancynations.data.townhouses.ITownHouseManager;
import me.rubix327.fancynations.data.townhouses.TownHouseDao;
import me.rubix327.fancynations.data.townhouses.TownHouseProcess;
import me.rubix327.fancynations.data.townresources.ITownResourceManager;
import me.rubix327.fancynations.data.townresources.TownResourceDao;
import me.rubix327.fancynations.data.townresources.TownResourceProcess;
import me.rubix327.fancynations.data.towns.ITownManager;
import me.rubix327.fancynations.data.towns.TownDao;
import me.rubix327.fancynations.data.towns.TownProcess;
import me.rubix327.fancynations.data.townworkers.ITownWorkerManager;
import me.rubix327.fancynations.data.townworkers.TownWorkerDao;
import me.rubix327.fancynations.data.townworkers.TownWorkerProcess;
import me.rubix327.fancynations.data.workertypes.IWorkerTypeManager;
import me.rubix327.fancynations.data.workertypes.WorkerTypeDao;
import me.rubix327.fancynations.data.workertypes.WorkerTypeProcess;
import me.rubix327.fancynations.data.workshops.IWorkshopManager;
import me.rubix327.fancynations.data.workshops.WorkshopDao;
import me.rubix327.fancynations.data.workshops.WorkshopProcess;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class DataManager {

    public static boolean isDatabaseChosen(){
        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")){
            return FancyNations.getInstance().getDatabase().isConnected();
        }
        return false;
    }

    public static IBarracksManager getBarracksManager(){
        return (isDatabaseChosen() ? BarracksDao.getInstance(Settings.DbTables.BARRACKS) : BarracksProcess.getInstance());
    }

    public static IChurchManager getChurchManager(){
        return (isDatabaseChosen() ? ChurchDao.getInstance(Settings.DbTables.CHURCHES) : ChurchProcess.getInstance());
    }

    public static IDefendTowerManager getDefendTowerManager(){
        return (isDatabaseChosen() ? DefendTowerDao.getInstance(Settings.DbTables.DEFEND_TOWERS) : DefendTowerProcess.getInstance());
    }

    public static IFarmManager getFarmManager(){
        return (isDatabaseChosen() ? FarmDao.getInstance(Settings.DbTables.FARMS) : FarmProcess.getInstance());
    }

    public static IFNPlayerManager getFNPlayerManager(){
        return (isDatabaseChosen() ? FNPlayerDao.getInstance(Settings.DbTables.FN_PLAYERS) : FNPlayerProcess.getInstance());
    }

    public static INationManager getNationManager(){
        return (isDatabaseChosen() ? NationDao.getInstance(Settings.DbTables.NATIONS) : NationProcess.getInstance());
    }

    public static IObjectivesManager getObjectivesManager(){
        return (isDatabaseChosen() ? ObjectivesDao.getInstance(Settings.DbTables.OBJECTIVES) : ObjectivesProcess.getInstance());
    }

    public static ITakenTaskManager getTakenTaskManager(){
        return (isDatabaseChosen() ? TakenTaskDao.getInstance(Settings.DbTables.TAKEN_TASKS) : TakenTaskProcess.getInstance());
    }

    public static ITaskProgressManager getTaskProgressManager(){
        return (isDatabaseChosen() ? TaskProgressDao.getInstance(Settings.DbTables.TASK_PROGRESSES) : TaskProgressProcess.getInstance());
    }

    public static ITaskManager getTaskManager(){
        return (isDatabaseChosen() ? TaskDao.getInstance(Settings.DbTables.TASKS) : TaskProcess.getInstance());
    }

    public static ITaskTypeManager getTaskTypeManager(){
        return (isDatabaseChosen() ? TaskTypeDao.getInstance(Settings.DbTables.TASK_TYPES) : TaskTypeProcess.getInstance());
    }

    public static ITownHouseManager getTownHouseManager(){
        return (isDatabaseChosen() ? TownHouseDao.getInstance(Settings.DbTables.TOWN_HOUSES) : TownHouseProcess.getInstance());
    }

    public static ITownResourceManager getTownResourceManager(){
        return (isDatabaseChosen() ? TownResourceDao.getInstance(Settings.DbTables.TOWN_RESOURCES) : TownResourceProcess.getInstance());
    }

    public static ITownManager getTownManager(){
        return (isDatabaseChosen() ? TownDao.getInstance(Settings.DbTables.TOWNS) : TownProcess.getInstance());
    }

    public static ITownWorkerManager getTownWorkerManager(){
        return (isDatabaseChosen() ? TownWorkerDao.getInstance(Settings.DbTables.TOWN_WORKERS) : TownWorkerProcess.getInstance());
    }

    public static IWorkerTypeManager getWorkerTypeManager(){
        return (isDatabaseChosen() ? WorkerTypeDao.getInstance(Settings.DbTables.WORKER_TYPES) : WorkerTypeProcess.getInstance());
    }

    public static IWorkshopManager getWorkshopManager(){
        return (isDatabaseChosen() ? WorkshopDao.getInstance(Settings.DbTables.WORKSHOPS) : WorkshopProcess.getInstance());
    }

    public static IReputationManager getReputationsManager(){
        return (isDatabaseChosen() ? ReputationDao.getInstance(Settings.DbTables.REPUTATIONS) : ReputationProcess.getInstance());
    }

    /**
     Returns all non-static non-final fields from the specified class.
     @param from class to get fields from (e.g. Task.class)
     @return List - fields
     */
    private static Stream<Field> getNonStaticNonFinalFields(Class<?> from) {
        return Arrays.stream(from.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !Modifier.isFinal(field.getModifiers()));
    }

    /**
    Returns all non-static non-final field names from the specified class.
    @param from class to get fields from (e.g. Task.class)
    @return List - field names
     */
    public static List<String> getClassFields(Class<?> from) {
        List<String> classFields = new ArrayList<>();
        getNonStaticNonFinalFields(from).forEach(field -> classFields.add(field.getName()));
        return classFields;
    }

    /**
     Returns all non-static non-final field names of the required type class from the specified class.
     @param requiredType required field type class (e.g. int.class, double.class, etc.)
     @return List - field names
     */
    public static List<String> getClassFieldsByType(Class<?> from, Class<?> requiredType) {
        List<String> classFields = new ArrayList<>();
        getNonStaticNonFinalFields(from)
                .filter(field -> field.getType() == requiredType)
                .forEach(field -> classFields.add(field.getName().toLowerCase()));
        return classFields;
    }

    /**
     Converts location from Location instance to String.
     @param loc Location instance
     @return Location String
     */
    public static String serializeLocation(Location loc){
        String world = loc.getWorld().getName();
        String x = String.valueOf(loc.getX());
        String y = String.valueOf(loc.getY());
        String z = String.valueOf(loc.getZ());
        String pitch = String.valueOf(loc.getPitch());
        String yaw = String.valueOf(loc.getYaw());

        return String.join(",", world, x, y, z, pitch, yaw);
    }

    /**
     Converts location from String to Location instance.
     @param loc String that contains location
     @return Location instance
     */
    public static Location deserializeLocation(String loc){
        List<String> locStr = Arrays.asList(loc.split(","));
        World world = Bukkit.getWorld(locStr.get(0));
        double x = Double.parseDouble(locStr.get(1));
        double y = Double.parseDouble(locStr.get(2));
        double z = Double.parseDouble(locStr.get(3));
        float pitch = Float.parseFloat(locStr.get(4));
        float yaw = Float.parseFloat(locStr.get(5));

        return new Location(world, x, y, z, pitch, yaw);
    }
}
