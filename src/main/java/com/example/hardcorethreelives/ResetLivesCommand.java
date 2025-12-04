package com.example.hardcorethreelives;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetLivesCommand implements CommandExecutor {

    private final HardcoreThreeLives plugin;

    public ResetLivesCommand(HardcoreThreeLives plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage("§cAlleen operators kunnen dit.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cGebruik: /resetlives <speler>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        plugin.setDeaths(target.getUniqueId(), 0);
        sender.sendMessage("§aLives van " + target.getName() + " zijn gereset!");

        return true;
    }
}
