package ru.primer.mc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.primer.mc.command.CommandCheater;
import ru.primer.mc.command.CommandCheck;
import ru.primer.mc.command.CommandUncheck;
import ru.primer.mc.event.AsyncPlayerChat;
import ru.primer.mc.event.PlayerCommandPreprocess;
import ru.primer.mc.event.PlayerMove;
import ru.primer.mc.event.PlayerQuit;

import java.util.Collections;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    public static HashMap<String, String> playersChecking = new HashMap<>(Collections.emptyMap());

    public static HashMap<String, String> playersCheckProgress = new HashMap(Collections.emptyMap());

    public static Main main;

    @Override
    public void onEnable() {
        getCommand("check").setExecutor(new CommandCheck());
        getCommand("uncheck").setExecutor(new CommandUncheck());
        getCommand("cheater").setExecutor(new CommandCheater());
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        main = this;
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.resetTitle();
        }
    }
}
