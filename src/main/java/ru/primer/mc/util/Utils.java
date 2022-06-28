package ru.primer.mc.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static ru.primer.mc.Main.*;

public class Utils {


    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendmessage(String message, Player player) {
        player.sendMessage(color(message));
    }

    public static void schedulerBan(Player player) {
        FileConfiguration cfg = main.getConfig();
        List<String> commands = cfg.getStringList("commands");
        String playerName = player.getName();
        int time = (cfg.getInt("time") * 60) * 20;

        BukkitRunnable timer = new BukkitRunnable() {

            int timerCount = time;

            @Override
            public void run() {
                timerCount = timerCount - 20;

                if(playersCheckProgress.containsKey(playerName)) {
                    this.cancel();
                }

                if(timerCount <= 0) {
                    for (String commandExecute : commands) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandExecute.replace("%player%", playerName));
                    }

                    playersCheckProgress.remove(playerName);
                    playersChecking.remove(playerName);
                    player.resetTitle();
                    this.cancel();
                }

                if (!playersChecking.containsKey(playerName)) {
                    this.cancel();
                }

                if(player == null) {
                    this.cancel();
                }

                if(playersCheckProgress.containsKey(playerName)) {
                    this.cancel();
                }

                if(playersChecking.get(playerName) != null) {
                    String adminName = playersChecking.get(playerName);

                    cfg.getStringList("check-execute").forEach(message -> sendmessage(message
                            .replace("%admin%", adminName)
                            .replace("%minutes%", cfg.getString("time")),
                            player));
                }
            }
        };
        timer.runTaskTimer(main, 20L, 20L);
    }
}