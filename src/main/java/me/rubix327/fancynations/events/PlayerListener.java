package me.rubix327.fancynations.events;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void registerPlayer(PlayerJoinEvent event){
        if (!DataManager.getFNPlayerManager().exists(event.getPlayer().getName())){
            FNPlayer player = new FNPlayer(event.getPlayer().getName());
            DataManager.getFNPlayerManager().add(player);
        }
    }

}
