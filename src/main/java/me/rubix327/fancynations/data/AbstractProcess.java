package me.rubix327.fancynations.data;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractProcess<T extends AbstractDto> extends AbstractDataHandler<T>{

    private final Class<T> clazz;
    private final HashMap<Integer, T> dtos = new HashMap<>();

    protected AbstractProcess(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean exists(int id) {
        return dtos.containsKey(id);
    }

    @Override
    public void add(T dto) {
        dtos.put((dto).getId(), dto);
    }

    @Override
    public T get(int id) {
        return dtos.get(id);
    }

    @Override
    public void update(int barracksId, String variable, Object newValue) {
        String methodName = "set" + StringUtils.upperCase(variable);
        try{
            clazz.getDeclaredMethod(methodName, newValue.getClass()).invoke(this, newValue);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        if (!(exists(id))) return;
        dtos.remove(id);
    }

    @Override
    public HashMap<Integer, T> getAll() {
        return dtos;
    }

    public int getNextId() {
        if (dtos.isEmpty()) return 1;
        return Collections.max(dtos.keySet()) + 1;
    }

    public boolean exists(String name) throws NullPointerException {
        for (T dto : dtos.values()) {
            if (dto instanceof IUniqueNamable) {
                if (((IUniqueNamable) dto).getName().equalsIgnoreCase(name)) return true;
            }
        }
        return false;
    }

    public T get(String name) throws NullPointerException {
        for (T dto : dtos.values()){
            if (dto instanceof IUniqueNamable){
                if (((IUniqueNamable)dto).getName().equalsIgnoreCase(name)) return dto;
            }
        }
        throw new NullPointerException("This object either does not exist or it is not an instance of IUniqueNamable.");
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (T dto : dtos.values()){
            if (dto instanceof IUniqueNamable){
                names.add(((IUniqueNamable) dto).getName());
            }
        }
        return names;
    }

}
