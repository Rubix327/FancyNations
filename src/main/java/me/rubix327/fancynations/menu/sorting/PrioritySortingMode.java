package me.rubix327.fancynations.menu.sorting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.data.tasks.Task;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrioritySortingMode implements ISortingMode{

    private static final PrioritySortingMode instance = new PrioritySortingMode();
    public static PrioritySortingMode get() {
        return instance;
    }

    @Override
    public List<Task> getSorted(Collection<Task> elements) {
        List<Task> tasks = new ArrayList<>(elements);
        tasks.sort((o1, o2) -> (o1.getPriority() < o2.getPriority() ? 1 : o1.getPriority() != o2.getPriority() ? -1 :
                o1.getCompletionsLeft() < o2.getCompletionsLeft() ? -1 :
                        o1.getCompletionsLeft() != o2.getCompletionsLeft() ? 1 : 0));
        return tasks;
    }

    @Override
    public ItemStack getItem() {
        return ItemCreator.of(CompMaterial.CYAN_BANNER)
                .name(name)
                .lore("",
                        "&8▶ порядковый номер",
                        "&8▶ лучшие награды",
                        "&7▶ &bвысший приоритет"
                ).make();
    }

    @Override
    public ISortingMode getNext() {
        return IDSortingMode.get();
    }
}
