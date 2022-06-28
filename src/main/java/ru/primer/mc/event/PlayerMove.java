package ru.primer.mc.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static ru.primer.mc.Main.playersCheckProgress;
import static ru.primer.mc.Main.playersChecking;

public class PlayerMove implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();

        if(!playersChecking.containsKey(playerName) && !playersCheckProgress.containsKey(playerName)) {
            return;
        }

        e.setCancelled(true);
    }
}
