package ru.primer.mc.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

import static ru.primer.mc.Main.*;
import static ru.primer.mc.util.Utils.color;
import static ru.primer.mc.util.Utils.sendmessage;

public class CommandCheater implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = main.getConfig();

        if(!(sender instanceof Player)) {
            System.out.println(color(cfg.getString("not-player")));
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("primechecker.use")) {
            sendmessage(color(cfg.getString("no-permission")), player);
            return true;
        }

        String playerName = sender.getName();

        if(!playersChecking.containsKey(playerName) && !playersCheckProgress.containsKey(playerName)) {
            return true;
        }

        playersChecking.remove(playerName);
        player.resetTitle();

        List<String> commands = cfg.getStringList("commands-confess");

        for(String commandExecute : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandExecute.replace("%player%", playerName));
        }
        return true;
    }
}
