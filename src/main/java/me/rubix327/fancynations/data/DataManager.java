package me.rubix327.fancynations.data;

import me.rubix327.fancynations.data.task.Task;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
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
}
