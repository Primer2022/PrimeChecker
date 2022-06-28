package ru.primer.mc.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

import static ru.primer.mc.Main.*;
import static ru.primer.mc.util.Utils.*;

public class CommandCheck implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = main.getConfig();

        if (!(sender instanceof Player)) {
            System.out.println(color(cfg.getString("not-player")));
            return true;
        }

        if (args.length <= 0) {
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();
        Player target = Bukkit.getPlayerExact(args[0]);

        if (!player.hasPermission("primechecker.use")) {
            sendmessage(color(cfg.getString("no-permission")), player);
            return true;
        }

        if (target != null) {
            String targetName = target.getName();

            if(args[0].equals(targetName)) {
                playersChecking.put(targetName, playerName);

                target.sendTitle(color(cfg.getString("title")), color(cfg.getString("subtitle")), 0, 999999999, 0);
                schedulerBan(target);
                return true;
            }
        }

        switch (args[0]) {
            case ("discord"): {
                if (args.length < 3) {
                    return true;
                }

                Player admin = Bukkit.getPlayerExact(args[1]);
                if(admin == null) {
                    return true;
                }
                String adminName = admin.getName();

                if (!playersChecking.containsKey(playerName)) {
                    return true;
                }

                if (!Objects.equals(playersChecking.get(playerName), adminName)) {
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) sb.append(args[i]).append(' ');
                if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
                String discord = sb.toString();

                playersCheckProgress.put(playerName, adminName);
                playersChecking.remove(playerName);

                sendmessage(cfg.getString("send-discord-admin").replace("%discord%", discord), admin);
                sendmessage(cfg.getString("send-discord-player").replace("%discord%", discord), player);
                break;
            }
            case ("ban"): {
                if(args.length < 2) {
                    return true;
                }

                if (!player.hasPermission("primechecker.admin")) {
                    sendmessage(color(cfg.getString("no-permission")), player);
                    return true;
                }

                Player targetBan = Bukkit.getPlayerExact(args[1]);

                if(targetBan == null) {
                    return true;
                }

                String targetNameBan = targetBan.getName();

                List<String> commands = cfg.getStringList("commands");

                for (String commandExecute : commands) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandExecute.replace("%player%", targetNameBan));
                }

                if(!playersChecking.containsKey(targetNameBan) && !playersCheckProgress.containsKey(targetNameBan)) {
                    return true;
                }

                if(playersChecking.containsKey(targetNameBan)) {
                    playersChecking.remove(targetNameBan);
                }

                if(playersCheckProgress.containsKey(targetNameBan)) {
                    playersCheckProgress.remove(targetNameBan);
                }
                targetBan.resetTitle();
                break;
            }
        }
        return true;
    }
}
