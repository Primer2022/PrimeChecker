package ru.primer.mc.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static ru.primer.mc.Main.*;
import static ru.primer.mc.util.Utils.color;
import static ru.primer.mc.util.Utils.sendmessage;

public class CommandUncheck implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = main.getConfig();

        if(!(sender instanceof Player)) {
            System.out.println(color(cfg.getString("not-player")));
            return true;
        }

        if(args.length != 1) {
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);

        if(!player.hasPermission("primechecker.admin")) {
            sendmessage(color(cfg.getString("no-permission")), player);
            return true;
        }

        if(target == null){
            return true;
        }

        String targetName = target.getName();

        if(!playersChecking.containsKey(targetName) && !playersCheckProgress.containsKey(targetName)) {
            return true;
        }

        if(playersChecking.containsKey(targetName)) {
            playersChecking.remove(targetName);
        }

        if(playersCheckProgress.containsKey(targetName)) {
            playersCheckProgress.remove(targetName);
        }

        target.resetTitle();

        sendmessage(cfg.getString("uncheck-player"), target);
        sendmessage(cfg.getString("uncheck-admin"), player);


        return true;
    }
}
