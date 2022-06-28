package ru.primer.mc.event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static ru.primer.mc.Main.*;
import static ru.primer.mc.util.Utils.color;
import static ru.primer.mc.util.Utils.sendmessage;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onSend(AsyncPlayerChatEvent e) {
        FileConfiguration cfg = main.getConfig();
        Player player = e.getPlayer();
        String playerName = player.getName();

        if(!playersChecking.containsKey(playerName) && !playersCheckProgress.containsKey(playerName)) {
            return;
        }

        sendmessage(color(cfg.getString("disabled-chat")), player);
        e.setCancelled(true);
    }
}
