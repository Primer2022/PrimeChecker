package ru.primer.mc.event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static ru.primer.mc.Main.*;
import static ru.primer.mc.util.Utils.color;
import static ru.primer.mc.util.Utils.sendmessage;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        FileConfiguration cfg = main.getConfig();
        String command = e.getMessage();
        String playerName = player.getName();

        if(!playersChecking.containsKey(playerName) && !playersCheckProgress.containsKey(playerName)) {
            return;
        }

        if (command.startsWith("/check") || command.startsWith("/uncheck") || command.startsWith("/cheater")) {
            return;
        }

        sendmessage(color(cfg.getString("disabled-chat")), player);
        e.setCancelled(true);
    }
}
