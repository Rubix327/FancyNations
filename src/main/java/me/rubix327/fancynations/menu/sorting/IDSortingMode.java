package me.rubix327.fancynations.menu.sorting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.data.tasks.Task;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IDSortingMode implements ISortingMode{

    private static final IDSortingMode instance = new IDSortingMode();
    public static ISortingMode get() {
        return instance;
    }

    @Override
    public List<Task> getSorted(Collection<Task> elements) {
        List<Task> tasks = new ArrayList<>(elements);
        tasks.sort(Comparator.comparingInt(Task::getId));
        return tasks;
    }

    @Override
    public ItemStack getItem() {
        return ItemCreator.of(CompMaterial.GREEN_BANNER)
                .name(name)
                .lore("",
                        "&7▶ &2дата создания",
                        "&8▶ лучшие награды",
                        "&8▶ высший приоритет"
                ).make();
    }

    @Override
    public ISortingMode getNext() {
        return RewardsSortingMode.get();
    }
}
