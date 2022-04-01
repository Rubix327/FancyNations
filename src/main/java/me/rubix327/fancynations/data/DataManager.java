package me.rubix327.fancynations.data;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.data.task.ITaskManager;
import me.rubix327.fancynations.data.task.TaskDao;
import me.rubix327.fancynations.data.task.TaskProcess;
import me.rubix327.fancynations.data.worker.TownWorkerDao;
import me.rubix327.fancynations.data.worker.TownWorkerManager;
import me.rubix327.fancynations.data.worker.TownWorkerProcess;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DataManager {

    public static boolean isDatabaseChosen(){
        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")){
            return FancyNations.getInstance().database.isConnected();
        }
        return false;
    }

    public static ITaskManager getTaskManager(){
        return (isDatabaseChosen() ? new TaskDao() : new TaskProcess());
    }

    public static TownWorkerManager getTownWorkerManager(){
        return (isDatabaseChosen() ? new TownWorkerDao() : new TownWorkerProcess());
    }

    private static Stream<Field> getNonStaticNonFinalFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !Modifier.isFinal(field.getModifiers()));
    }

    /**
    Returns all non-static non-final field names from the specified class.
    @param clazz class (e.g. Task.class)
    @return List - field names
     */
    public static List<String> getClassFields(Class clazz) {
        List<String> CLASS_FIELDS = new ArrayList<>();
        getNonStaticNonFinalFields(clazz).forEach(field -> CLASS_FIELDS.add(field.getName()));
        return CLASS_FIELDS;
    }

    /**
     Returns all non-static non-final field names of the required type class from the specified class.
     @param requiredType required class (e.g. int.class, double.class, etc.)
     @return List - field names
     */
    public static List<String> getClassFieldsByType(Class from, Class requiredType) {
        List<String> CLASS_FIELDS = new ArrayList<>();
        getNonStaticNonFinalFields(from)
                .filter(field -> field.getType() == requiredType)
                .forEach(field -> CLASS_FIELDS.add(field.getName().toLowerCase()));
        return CLASS_FIELDS;
    }
}
