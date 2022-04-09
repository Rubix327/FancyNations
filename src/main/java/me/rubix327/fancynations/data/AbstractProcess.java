package me.rubix327.fancynations.data;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractProcess<T> extends AbstractDataHandler<T>{

    Class<T> clazz;
    HashMap<Integer, T> dtos;

    public AbstractProcess(HashMap<Integer, T> dtos, Class<T> clazz){
        this.clazz = clazz;
        this.dtos = dtos;
    }

    @Override
    public boolean exists(int id) {
        return dtos.containsKey(id);
    }

    @Override
    public void add(T dto) {
        dtos.put(((AbstractDto)dto).getId(), dto);
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

    @Override
    public int getMaxId() {
        List<Integer> keys = dtos.keySet().stream().toList();
        keys.sort(Collections.reverseOrder());
        return keys.get(0);
    }

    public boolean exists(String name) throws NullPointerException {
        for (T dto : dtos.values()){
            if (dto instanceof IUniqueNamable) {
                if (((IUniqueNamable)dto).getName().equalsIgnoreCase(name)) return true;
            }
        }
        throw new NullPointerException("This object either does not exist or it is not instance of IUniqueNamable.");
    }

    public T get(String name) throws NullPointerException {
        for (T dto : dtos.values()){
            if (dto instanceof IUniqueNamable){
                if (((IUniqueNamable)dto).getName().equalsIgnoreCase(name)) return dto;
            }
        }
        throw new NullPointerException("This object either does not exist or it is not instance of IUniqueNamable.");
    }

}
