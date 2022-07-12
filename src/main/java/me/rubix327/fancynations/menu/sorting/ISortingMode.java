package me.rubix327.fancynations.menu.sorting;

import me.rubix327.fancynations.data.tasks.Task;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public interface ISortingMode {

    String name = "&7» Сортировка";
    List<Task> getSorted(Collection<Task> elements);
    ItemStack getItem();
    ISortingMode getNext();

}
