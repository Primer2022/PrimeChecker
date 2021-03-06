package ru.primer.mc.event;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

import static ru.primer.mc.Main.*;
import static ru.primer.mc.util.Utils.color;
import static ru.primer.mc.util.Utils.sendmessage;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        FileConfiguration cfg = main.getConfig();

        player.resetTitle();

        Player admin = null;

        if(!playersChecking.containsKey(playerName) && !playersCheckProgress.containsKey(playerName)) {
            return;
        }

        if(playersChecking.get(playerName) != null) {
            System.out.println(playersChecking.get(playerName));
            admin = Bukkit.getPlayerExact(playersChecking.get(playerName));
        }

        if(playersCheckProgress.get(playerName) != null) {
            System.out.println(playersCheckProgress.get(playerName));
            admin = Bukkit.getPlayerExact(playersCheckProgress.get(playerName));
        }

        if(admin != null) {
            sendmessage(color(cfg.getString("player-leave")), admin);
        }

        playersChecking.remove(playerName);
        playersCheckProgress.remove(playerName);

        List<String> commands = cfg.getStringList("commands");

        for(String command : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", playerName));
        }
    }
}
