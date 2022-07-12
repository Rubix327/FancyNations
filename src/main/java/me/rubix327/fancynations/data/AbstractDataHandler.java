package me.rubix327.fancynations.data;

import java.util.HashMap;

public abstract class AbstractDataHandler<T extends AbstractDto> {

    public abstract boolean exists(int id);

    public abstract void add(T dto);

    public abstract T get(int id);

    public abstract void update(int id, String variable, Object newValue);

    public abstract void remove(int id);

    public abstract HashMap<Integer, T> getAll();

    public abstract int getNextId();

    public void addIgnore(T dto) {
        if (exists(dto.getId())) return;
        add(dto);
    }

}
