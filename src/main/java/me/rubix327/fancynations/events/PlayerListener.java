package me.rubix327.fancynations.events;

import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mineacademy.fo.Common;

public class PlayerListener implements Listener {

    @EventHandler
    public void registerPlayer(PlayerJoinEvent event) {
        if (!DataManager.getFNPlayerManager().exists(event.getPlayer().getName())) {
            FNPlayer player = new FNPlayer(event.getPlayer().getName());
            DataManager.getFNPlayerManager().add(player);
        }
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        LivingEntity killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        final String name;

        if (killer == null) return;
        if (killed.hasMetadata("mobname")) {
            name = Settings.General.MYTHICMOBS_PREFIX + killed.getMetadata("mobname").get(0).asString();
        } else {
            name = killed.getType().toString();
        }
        Common.runAsync(() -> {
            TaskProgress.increaseMobProgress(killer, name);
        });
    }

}
