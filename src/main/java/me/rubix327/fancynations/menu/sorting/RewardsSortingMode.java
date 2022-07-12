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
public class RewardsSortingMode implements ISortingMode{

    private static final RewardsSortingMode instance = new RewardsSortingMode();
    public static RewardsSortingMode get() {
        return instance;
    }

    @Override
    public List<Task> getSorted(Collection<Task> elements) {
        List<Task> tasks = new ArrayList<>(elements);
        tasks.sort((o1, o2) -> o1.getMoneyReward() < o2.getMoneyReward() ? 1 : o1.getMoneyReward() != o2.getMoneyReward() ? -1 :
                o1.getExpReward() < o2.getExpReward() ? 1 : o1.getExpReward() != o2.getExpReward() ? -1 :
                        o1.getRepReward() < o2.getRepReward() ? 1 : o1.getRepReward() != o2.getRepReward() ? -1 : 0);
        return tasks;
    }

    @Override
    public ItemStack getItem() {
        return ItemCreator.of(CompMaterial.PURPLE_BANNER)
                .name(name)
                .lore("",
                        "&8▶ порядковый номер",
                        "&7▶ &dлучшие награды",
                        "&8▶ высший приоритет"
                ).make();
    }

    @Override
    public ISortingMode getNext() {
        return PrioritySortingMode.get();
    }
}
