package me.rubix327.fancynations.data;

import lombok.Getter;
import me.rubix327.fancynations.FancyNations;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class DataManager {

    public static final List<String> gatheringTypes = Arrays.asList("food", "resource", "crafting");
    public static final List<String> mobkillTypes = Collections.singletonList("mobkill");

    public static boolean isDatabaseChosen(){
        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")){
            return FancyNations.getInstance().database.isConnected();
        }
        return false;
    }

    public static IBarracksManager getBarracksManager(){
        return (isDatabaseChosen() ? new BarracksDao(Settings.DbTables.BARRACKS) : new BarracksProcess());
    }

    public static IChurchManager getChurchManager(){
        return (isDatabaseChosen() ? new ChurchDao(Settings.DbTables.CHURCHES) : new ChurchProcess());
    }

    public static IDefendTowerManager getDefendTowerManager(){
        return (isDatabaseChosen() ? new DefendTowerDao(Settings.DbTables.DEFEND_TOWERS) : new DefendTowerProcess());
    }

    public static IFarmManager getFarmManager(){
        return (isDatabaseChosen() ? new FarmDao(Settings.DbTables.FARMS) : new FarmProcess());
    }

    public static IFNPlayerManager getFNPlayerManager(){
        return (isDatabaseChosen() ? new FNPlayerDao(Settings.DbTables.FN_PLAYERS) : new FNPlayerProcess());
    }

    public static INationManager getNationManager(){
        return (isDatabaseChosen() ? new NationDao(Settings.DbTables.NATIONS) : new NationProcess());
    }

    public static IObjectivesManager getObjectivesManager(){
        return (isDatabaseChosen() ? new ObjectivesDao(Settings.DbTables.OBJECTIVES) : new ObjectivesProcess());
    }

    public static ITakenTaskManager getTakenTaskManager(){
        return (isDatabaseChosen() ? new TakenTaskDao(Settings.DbTables.TAKEN_TASKS) : new TakenTaskProcess());
    }

    public static ITaskProgressManager getTaskProgressManager(){
        return (isDatabaseChosen() ? new TaskProgressDao(Settings.DbTables.TASK_PROGRESSES) : new TaskProgressProcess());
    }

    public static ITaskManager getTaskManager(){
        return (isDatabaseChosen() ? new TaskDao(Settings.DbTables.TASKS) : new TaskProcess());
    }

    public static ITaskTypeManager getTaskTypeManager(){
        return (isDatabaseChosen() ? new TaskTypeDao(Settings.DbTables.TASK_TYPES) : new TaskTypeProcess());
    }

    public static ITownHouseManager getTownHouseManager(){
        return (isDatabaseChosen() ? new TownHouseDao(Settings.DbTables.TOWN_HOUSES) : new TownHouseProcess());
    }

    public static ITownResourceManager getTownResourceManager(){
        return (isDatabaseChosen() ? new TownResourceDao(Settings.DbTables.TOWN_RESOURCES) : new TownResourceProcess());
    }

    public static ITownManager getTownManager(){
        return (isDatabaseChosen() ? new TownDao(Settings.DbTables.TOWNS) : new TownProcess());
    }

    public static ITownWorkerManager getTownWorkerManager(){
        return (isDatabaseChosen() ? new TownWorkerDao(Settings.DbTables.TOWN_WORKERS) : new TownWorkerProcess());
    }

    public static IWorkerTypeManager getWorkerTypeManager(){
        return (isDatabaseChosen() ? new WorkerTypeDao(Settings.DbTables.WORKER_TYPES) : new WorkerTypeProcess());
    }

    public static IWorkshopManager getWorkshopManager(){
        return (isDatabaseChosen() ? new WorkshopDao(Settings.DbTables.WORKSHOPS) : new WorkshopProcess());
    }

    /**
     Returns all non-static non-final fields from the specified class.
     @param clazz class (e.g. Task.class)
     @return List - fields
     */
    private static Stream<Field> getNonStaticNonFinalFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !Modifier.isFinal(field.getModifiers()));
    }

    /**
    Returns all non-static non-final field names from the specified class.
    @param clazz class (e.g. Task.class)
    @return List - field names
     */
    public static List<String> getClassFields(Class<?> clazz) {
        List<String> CLASS_FIELDS = new ArrayList<>();
        getNonStaticNonFinalFields(clazz).forEach(field -> CLASS_FIELDS.add(field.getName()));
        return CLASS_FIELDS;
    }

    /**
     Returns all non-static non-final field names of the required type class from the specified class.
     @param requiredType required class (e.g. int.class, double.class, etc.)
     @return List - field names
     */
    public static List<String> getClassFieldsByType(Class<?> from, Class<?> requiredType) {
        List<String> CLASS_FIELDS = new ArrayList<>();
        getNonStaticNonFinalFields(from)
                .filter(field -> field.getType() == requiredType)
                .forEach(field -> CLASS_FIELDS.add(field.getName().toLowerCase()));
        return CLASS_FIELDS;
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
